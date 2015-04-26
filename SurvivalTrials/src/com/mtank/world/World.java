package com.mtank.world;
import com.mtank.constants.TypeValue;
import com.mtank.creature.Creature;
import com.mtank.game.Coordinates;
import com.mtank.game.Game;
import com.mtank.game.Stringify;
import com.mtank.item.Item;
import com.mtank.structure.Structure;


public class World {
	public int worldDimension=10;
	public Land[][] world;// = new Land[worldDimension][worldDimension]; 
	
	
	public World(int size){
		worldDimension=size;
		world = new Land[worldDimension][worldDimension]; 
		initializeWorldLand();
		int center=(world[0].length-1)/2;
		initializeIslandLand(center,center,center,center);
		smallWaterCleanup(); // Last touches to the water system.
		addSalt(0,0);
		enlargeLakes(); // Add much needed size to lakes. They tend to form pitifully small.
		initializeBiomes();
	}
	void initializeWorldLand(){
		//start by initializing the Land variable.
		for(int i=0;i<world.length;i++){
			for(int j=0;j<world[0].length;j++){
				world[i][j]=new Land(TypeValue.Land.WATER);
			}
		}
	}
	void initializeIslandLand(int x,int y,int xc,int yc){
		// Function works by setting the current spot to a non-SEAWATER value. It then possibly calls the function on neighboring squars, with a chance to fail based on an increasing number counter. 
		world[x][y].landType=0;
		int xlen=(xc>x?xc-x:x-xc);
		int ylen=(yc>y?yc-y:y-yc);
		double dist = (Math.sqrt( Math.pow(xlen,2) + Math.pow(ylen,2) ));
		
		
		
		// Values for inequalities were chosen to ensure the borders are always SALTWATER		
		if(x>1 && world[x-1][y].landType!=0/**/ && (Game.RANDY.nextInt(2*worldDimension/3)-dist)>=-0) 
		{
			initializeIslandLand(x-1,y,xc,yc); 
		}
		if(y>1 && world[x][y-1].landType!=0/**/ && (Game.RANDY.nextInt(2*worldDimension/3)-dist)>=-0)
		{ 
			initializeIslandLand(x,y-1,xc,yc); 
		}
		if(y<world.length-2 && world[x][y+1].landType!=0/**/ && (Game.RANDY.nextInt(2*worldDimension/3)-dist)>=-0) 
		{ 
			initializeIslandLand(x,y+1,xc,yc); 
		}
		if(x<world.length-2 && world[x+1][y].landType!=0/**/ && (Game.RANDY.nextInt(2*worldDimension/3)-dist)>=-0)
		{ 
			initializeIslandLand(x+1,y,xc,yc); 
		}
	}

	void smallWaterCleanup(){
		// The following code plugs up the little 1x1 holes of water.
		for(int i=1;i<world.length-1;i++){
			for(int j=1;j<world[0].length-1;j++){
				if(world[i][j].landType==TypeValue.Land.WATER && 
						world[i-1][j].landType==0 && world[i][j-1].landType==0 && world[i+1][j].landType==0 && world[i][j+1].landType==0){
					world[i][j]=new Land(0);
				}
			}
		}
	}
	void enlargeLakes(){
		for (int i=0;i<world.length;i++){
			for (int j=0;j<world[0].length;j++){
				if(world[i][j].landType==TypeValue.Land.WATER){
					world[i][j] = new Land(TypeValue.Land.STONE);	// Stone is acting as a temporary placeholder here.
				}
			}
		}
		for (int i=0;i<world.length;i++){
			for (int j=0;j<world[0].length;j++){
				if(world[i][j].landType==TypeValue.Land.STONE){
					fillLake(i,j); // Replace stone with lake water, additionally, expand! sort of a crappy function name for now.
				}
			}
		}
	}
	void fillLake(int x,int y){
		// fillLake spreads water outwards from the first viable source it finds... then moves 1 space further out to help enlarge the lake.
		int oldType=world[x][y].landType;
		world[x][y] = new Land(TypeValue.Land.WATER);
		if(oldType!=0 && oldType!=TypeValue.Land.SALTWATER){
			if((world[x+1][y].landType==0 || world[x][y].landType==TypeValue.Land.STONE) && !touchingSaltWater(x+1,y)){
				fillLake(x+1,y);
			}
			if((world[x][y+1].landType==0 || world[x][y].landType==TypeValue.Land.STONE) && !touchingSaltWater(x,y+1)){
				fillLake(x,y+1);
			}
			if((world[x-1][y].landType==0 || world[x][y].landType==TypeValue.Land.STONE) && !touchingSaltWater(x-1,y)){
				fillLake(x-1,y);
			}
			if((world[x][y-1].landType==0 || world[x][y].landType==TypeValue.Land.STONE) && !touchingSaltWater(x,y-1)){
				fillLake(x,y-1);
			}
			//TODO: Consider adding if statements for diagonals, possibly with only a % chance of success
		}
	}
	// == Takes in the x, y location of a piece of land. The land then returns true if it is touching SALTWATER vertically or horizontally. Diagonally is not included.
	boolean touchingSaltWater(int x, int y){
		return( world[x+1][y].landType==TypeValue.Land.SALTWATER || world[x][y+1].landType==TypeValue.Land.SALTWATER || world[x-1][y].landType==TypeValue.Land.SALTWATER || world[x][y-1].landType==TypeValue.Land.SALTWATER);
	}
	// == Takes in the x and y positions for a block of water. Turns the entire body of water including the initial square from WATER to SALTWATER. 
	private void addSalt(int i,int j){
		world[i][j] = new Land(TypeValue.Land.SALTWATER);
		if(i>0 && world[i-1][j].landType==TypeValue.Land.WATER){
			addSalt(i-1,j);
		}
		if(j>0 && world[i][j-1].landType==TypeValue.Land.WATER){
			addSalt(i,j-1);
		}
		if(i<world.length-1 && world[i+1][j].landType==TypeValue.Land.WATER){
			addSalt(i+1,j);
		}
		if(j<world[0].length-1 && world[i][j+1].landType==TypeValue.Land.WATER){
			addSalt(i,j+1);
		}
	}
	public String printWorld(){
		String ret="";
		for(int i=0; i<world.length;i++){
			for(int j=0;j<world[0].length;j++){
				ret+=" ";
				if (world[j][i].creature!=null && world[j][i].creature.creatureType!=0) {
					ret += Stringify.creature(world[j][i].creature);
				} else if (world[j][i].structure != null && world[j][i].structure.structureType != 0) {			
					ret += Stringify.structure(world[j][i].structure);
				} else if (!world[j][i].item.isEmpty() && world[j][i].item.get(0) != null && world[j][i].item.get(0).itemType!=0) {
					ret += Stringify.item(world[j][i].item.get(0));
				} else {
					ret += Stringify.land(world[j][i].landType);
				}
				ret+=" ";
			}
			ret+="\n";
		}
		System.out.print(ret);
		return ret;
	}
	//Inits biomes for the world
	void initializeBiomes(){
		int[] chances = {20,20,0,20,20,20};
		//biomes: forest, mountain, plain, beach, desert
		addBiome("Beach",findBiomeX("Beach"),findBiomeY("Beach"));
		addBiome("Mountain",findBiomeX("Mountain"),findBiomeY("Mountain"));
		addBiome("Forest",findBiomeX("Forest"),findBiomeY("Forest"));
		addBiome("Desert",findBiomeX("Desert"),findBiomeY("Desert"));
		
		
		for(int i = 0;i < world.length;i++){
			for(int j = 0; j < world.length;j++){
				if(world[i][j].landType == TypeValue.NONE){
					lookAroundYou(chances);
					int rand = Game.RANDY.nextInt(6)+1;
					while(rand == TypeValue.Land.SALTWATER || rand == TypeValue.Land.WATER){
						rand = TypeValue.Land.DIRT;
					}
					world[i][j].landType = rand;
				}
			}
		}
	}
	
	private void lookAroundYou(int[] chances) {
		// TODO Auto-generated method stub
		
	}

	//Finds the Y portion for Biome C according to Austin's strategy at the time.
		//Beach goes straight down from center
	//current biomes: Beach, Mountain
	//Input: String matching a current biome type.
	//Output: 
	private int findBiomeY(String c) {
		int retVal = 0;
		int center=(world[0].length-1)/2;
		
		switch(c){
		
		case("Beach"):
			//from center of land, go down until we find saltwater
			retVal = center;
			while(world[center][retVal].landType != TypeValue.Land.SALTWATER)
				retVal++;
			//make the spot we're on land(go north 1)
			retVal--;
			break;
			
		case("Mountain"):
			retVal = center - 2;
			break;
		case("Forest"):
			retVal = center - 10;
			break;
		case("Desert"):
			retVal = center - 10;
			break;
		}
		
		return retVal;
	}
	private int findBiomeX(String c) {
		int retVal = 0;
		int center=(world[0].length-1)/2;
		
		switch(c){
		case("Beach"):
			retVal = center;
			break;
		case("Mountain"):
			retVal = center - 2;
			break;
		case("Forest"):
			retVal = center + 10;
			break;
		case("Desert"):
			retVal = center - 10;
			break;
		}
		
		return retVal;
	}
	
	void addBiome(String c, int x, int y){
		switch(c){
		
		//Strategy: find south shores for +-(4-6) and each puts sand up for 3-5 squares
		case("Beach")://current strat, circle around center, replacing land
			int beachSize = Game.RANDY.nextInt(4)+5;
			int beachLength = Game.RANDY.nextInt(4)+3;
			for(int i = x-(beachSize/2);i< x+(beachSize/2);i++){
				int southShore = y-5;
				while(world[i][southShore].landType != TypeValue.Land.SALTWATER)
					southShore++;
				southShore--;
				for(int j = 0; j < beachLength;j++){
					world[i][southShore-j].landType = TypeValue.Land.SAND;
				}
			}
		break;
		case("Mountain"):
			for(int i = y; i < y+5;i++){
				for(int j = x; j< x+5; j++){
					world[i][j].landType = TypeValue.Land.STONE;
				}
			}
			break;
		case("Forest"):
			for(int i = y; i < y+5;i++){
				for(int j = x; j< x+5; j++){
					world[i][j].landType = TypeValue.Land.GRASS;
					if(Game.RANDY.nextFloat() > 0.75)
						placeStructure(new Structure(TypeValue.Structure.TREE), new Coordinates(i,j));
				}
			}
			break;
		case("Desert"):
			for(int i = y; i < y+5;i++){
				for(int j = x; j< x+5; j++){
					world[i][j].landType = TypeValue.Land.SAND;
					if(Game.RANDY.nextFloat() > 0.75)
						placeStructure(new Structure(TypeValue.Structure.CACTUS), new Coordinates(i,j));
				}
			}
			break;
		}
		
	
	}
	
	
	void placeStructure(Structure structure, Coordinates c){
		world[c.x][c.y].structure=structure;
		structure.position.set(c);
	}
	public void placeCreature(Creature creature, Coordinates c){
		if(creature.position.x!=-1){
			world[creature.position.x][creature.position.y].creature=null;
		}
		world[c.x][c.y].creature=creature;
		creature.position.set(c); 
	}
	void placeItem(Item item, Coordinates c){
		int j;
		boolean placed = false;
		for(j=0; !placed && j<world[c.x][c.y].item.size(); j++){
			if (world[c.x][c.y].item.get(j) != null) {
				item.position.set(c);
				world[c.x][c.y].item.add(item);
			}
		}
	}
	
	/**
	 * Refreshes the world to ensure all items/structures/creatures agree upon where they exist.
	 */
	void refresh() {
		Coordinates i = new Coordinates();
		Coordinates tmp = new Coordinates();
		for (i.x = 0; i.x<world.length; i.x++) {
			for (i.y = 0; i.y<world[0].length; i.y++) {
				if (world[i.x][i.y].creature != null && !world[i.x][i.y].creature.position.equals(i)) {
					tmp.set(world[i.x][i.y].creature.position);
					world[tmp.x][tmp.y].creature = world[i.x][i.y].creature; 
					world[i.x][i.y].creature = null;
				}
				if (world[i.x][i.y].structure != null ) {
					tmp.set(world[i.x][i.y].structure.position);
					world[tmp.x][tmp.y].structure = world[i.x][i.y].structure; 
					world[i.x][i.y].structure = null;
				}
				if (world[i.x][i.y].item != null ) {
					for (int j = 0; j<world[i.x][i.y].item.size(); j++) {
						tmp.set(world[i.x][i.y].item.get(j).position);
						world[tmp.x][tmp.y].item.add(world[i.x][i.y].item.get(j));
						world[i.x][i.y].item = null;
					}
				}
			}
		}
		
	}
}
