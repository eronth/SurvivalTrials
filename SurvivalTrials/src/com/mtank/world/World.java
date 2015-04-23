package com.mtank.world;

import com.mtank.constants.TypeValue;
import com.mtank.creature.Creature;
import com.mtank.game.Coordinates;
import com.mtank.game.Game;
import com.mtank.game.Stringify;
import com.mtank.item.Item;
import com.mtank.structure.Structure;

import java.util.ArrayList;


public class World {
    int landsToAdd = 5;
    int changedLands = 0;
	public int worldDimension=10;
	public Land[][] world;// = new Land[worldDimension][worldDimension];
    public ArrayList<ArrayList<Coordinates>> biomes;
	
	
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
				world[i][j]=new Land(TypeValue.Land.WATER, i, j);
			}
		}
	}
	void initializeIslandLand(int x,int y,int xc,int yc){
		// Function works by setting the current spot to a non-SEAWATER value. It then possibly calls the function on neighboring squars, with a chance to fail based on an increasing number counter. 
		world[x][y].landType=TypeValue.NONE;
		int xlen=(xc>x?xc-x:x-xc);
		int ylen=(yc>y?yc-y:y-yc);
		double dist = (Math.sqrt( Math.pow(xlen,2) + Math.pow(ylen,2) ));
		
		
		
		// Values for inequalities were chosen to ensure the borders are always SALTWATER		
		if(x>1 && world[x-1][y].landType!=0/**/ && (Game.RAND.nextInt(2*worldDimension/3)-dist)>=-0) 
		{
			initializeIslandLand(x-1,y,xc,yc); 
		}
		if(y>1 && world[x][y-1].landType!=0/**/ && (Game.RAND.nextInt(2*worldDimension/3)-dist)>=-0)
		{ 
			initializeIslandLand(x,y-1,xc,yc); 
		}
		if(y<world.length-2 && world[x][y+1].landType!=0/**/ && (Game.RAND.nextInt(2*worldDimension/3)-dist)>=-0) 
		{ 
			initializeIslandLand(x,y+1,xc,yc); 
		}
		if(x<world.length-2 && world[x+1][y].landType!=0/**/ && (Game.RAND.nextInt(2*worldDimension/3)-dist)>=-0)
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
					world[i][j]=new Land(0,i,j);
				}
			}
		}
	}
	void enlargeLakes(){
		for (int i=0;i<world.length;i++){
			for (int j=0;j<world[0].length;j++){
				if(world[i][j].landType==TypeValue.Land.WATER){
					world[i][j] = new Land(TypeValue.Land.STONE,i,j);	// Stone is acting as a temporary placeholder here.
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
		world[x][y] = new Land(TypeValue.Land.WATER,x,y);
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
		world[i][j] = new Land(TypeValue.Land.SALTWATER,i,j);
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
				if (world[j][i].creature!=null && world[j][i].creature.creatureType!=0){
					ret += Stringify.creature(world[j][i].creature);
				}else if (world[j][i].structure != null && world[j][i].structure.structureType != 0){			
					ret += Stringify.structure(world[j][i].structure);
				}else if (world[j][i].item[0]!=0){
					ret += Stringify.item(world[j][i].item[0]);
				}else{
					ret += Stringify.land(world[j][i].landType);
				}
				ret+=" ";
			}
			ret+="\n";
		}
		System.out.print(ret);
		return ret;
	}

	//======================================
	//START AUSTIN WORK
    //TODO: Figure out why NONE lands are remaining among the generated.
    //TODO: Add structures to certain biomes.
    //TODO: Add mountain and a beach.
	//======================================

	//Inits biomes for the world
	void initializeBiomes(){
        //biomes is a list of biomes containing the various biomes coordinates
        ArrayList<ArrayList<Coordinates>> biomes = new ArrayList();
        //growlist is a list of biomes and their cells that need to grow.
        ArrayList<ArrayList<Coordinates>> growList = new ArrayList();

        //"Plains", "Forest", "Desert", "Dessert", "Ice"
        int[] biome = {TypeValue.Land.DIRT, TypeValue.Land.GRASS, TypeValue.Land.SAND, TypeValue.Land.SNOW, TypeValue.Land.ICE};
        int center=(world.length)/2;

        double angleRads = Math.toRadians(360.0 / (double) biome.length);
        Coordinates[] endpoints = new Coordinates[biome.length];
        Coordinates[] startpoints = new Coordinates[biome.length];

        for (int i = 0; i < biome.length; i++) {
            biomes.add(new ArrayList());
            double currentAngle = i*angleRads;
            int yStep = Math.max(1, Math.round((float) (5 * Math.sin(currentAngle))));
            int xStep = Math.max(1, Math.round((float) (5 * Math.cos(currentAngle))));
            for(int x = center, y = center, steps = 1; ; steps++) {

                if (world[x][y].landType == TypeValue.Land.SALTWATER) {
                    steps--;

                    endpoints[i] = new Coordinates(x - xStep, y - yStep);
                    int rand = Game.RAND.nextInt(steps) + 1;
                    startpoints[i] = new Coordinates(center + xStep * rand, center + yStep * rand);
                    break;
                }
                x+=xStep;
                y+=yStep;

            }
        }
        for(int i = 0; i < startpoints.length; i++) {
            biomes.get(i).add(startpoints[i]);
            growList.add(biomes.get(i));
        }

        int loopCount = 0;
        int independant = 0;
        for(int i = 0; loopCount < 20 ;i++) {
            independant++;
            if(i == 5)
                i=0;
            if(growList.get(i).isEmpty()) {
                loopCount++;
                continue;
            }
            int growSpot = Game.RAND.nextInt(growList.get(i).size());
            ArrayList<Coordinates> newSpots = grow(biome[i], growList.get(i).get(growSpot));
            if(newSpots.size() == 0) {
                growList.get(i).remove(growSpot);
            }
            else {
                growList.get(i).addAll(newSpots);
            }
            loopCount=0;
        }
        System.out.println(independant);
        System.out.println(changedLands);
        //fill un-filled spaces with beach/desert?
	}

    private ArrayList<Coordinates> grow(int biome, Coordinates c) {
        ArrayList<Coordinates> newGrowth = new ArrayList();
        if(world[c.x+1][c.y].landType == TypeValue.NONE)
            newGrowth.add(new Coordinates(c.x+1,c.y));
        if(world[c.x-1][c.y].landType == TypeValue.NONE)
            newGrowth.add(new Coordinates(c.x-1,c.y));
        if(world[c.x][c.y+1].landType == TypeValue.NONE)
            newGrowth.add(new Coordinates(c.x,c.y+1));
        if(world[c.x][c.y-1].landType == TypeValue.NONE)
            newGrowth.add(new Coordinates(c.x,c.y-1));
        if(newGrowth.size()==0) {
            return newGrowth;
        }
        int spot = Game.RAND.nextInt(newGrowth.size());

        world[newGrowth.get(spot).x][newGrowth.get(spot).y].landType = biome;
        changedLands++;
        landsToAdd--;

        landsToAdd+=newGrowth.size()-1;
        System.out.println(c.x + " " + c.y + " " + newGrowth.get(spot).x + " " + newGrowth.get(spot).y + " " + landsToAdd);
        newGrowth.remove(spot);
        return newGrowth;
    }

    //kept to pull out beach and mountain, overrides whatever is in their position.
	void addBiome(String c, int x, int y){
		switch(c){

		//Strategy: find south shores for +-(4-6) and each puts sand up for 3-5 squares
		case("Beach")://current strat, circle around center, replacing land
			int beachSize = Game.RAND.nextInt(4)+5;
			int beachLength = Game.RAND.nextInt(4)+3;
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
		}


	}

	//======================================
	//END AUSTIN WORK
	//======================================
	
	
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
		for(j=0; j<world[c.x][c.y].item.length; j++){ // creatureycreaturele through itemstructure that creaturean be on eacreatureh land and look for open structurepot
			if(world[c.x][c.y].item[j]==0){
				world[c.x][c.y].items[j]=item;
				world[c.x][c.y].item[j]=item.itemType;
				item.position.set(c);
			}
		}
	}
	
	
	
	
}
