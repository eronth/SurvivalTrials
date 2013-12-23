
public class World {
	public int worldDimension=10;
	public Land[][] world;// = new Land[worldDimension][worldDimension]; 
	
	
	World(int size){
		worldDimension=size;
		world = new Land[worldDimension][worldDimension]; 
		initializeWorldsLand();
		initializeSea();
		int center=(world[0].length-1)/2;
		initializeIslandLand(center,center,center,center);
		smallWaterCleanup(); // Last touches to the water system.
		addSalt(0,0);
		enlargeLakes(); // Add much needed size to lakes. They tend to form pitifully small.
		initializeBiomes();
	}
	void initializeWorldsLand(){
		//start by initializing the Land variable.
		for(int i=0;i<world.length;i++){
			for(int j=0;j<world[0].length;j++){
				world[i][j]=new Land();
			}
		}
	}
	void initializeSea(){
		for(int i=0; i<world.length;i++){
			for(int j=0;j<world[0].length;j++){
				world[i][j].landType=D.WATER;
			}
		}
	}
	void initializeIslandLand(int x,int y,int xc, int yc){
		// Function works by setting the current spot to a non-SEAWATER value. It then possibly calls the function on neighboring squars, with a chance to fail based on an increasing number counter. 
		world[x][y].landType=0;
		int xlen=(xc>x?xc-x:x-xc);
		int ylen=(yc>y?yc-y:y-yc);
		double dist = (Math.sqrt( Math.pow(xlen,2) + Math.pow(ylen,2) ));
		
		
		
		// Values for inequalities were chosen to ensure the borders are always SALTWATER		
		if(x>1 && world[x-1][y].landType!=0/**/ && (D.RAND.nextInt(2*worldDimension/3)-dist)>=-0) 
		{
			initializeIslandLand(x-1,y,xc,yc); 
		}
		if(y>1 && world[x][y-1].landType!=0/**/ && (D.RAND.nextInt(2*worldDimension/3)-dist)>=-0)
		{ 
			initializeIslandLand(x,y-1,xc,yc); 
		}
		if(y<world.length-2 && world[x][y+1].landType!=0/**/ && (D.RAND.nextInt(2*worldDimension/3)-dist)>=-0) 
		{ 
			initializeIslandLand(x,y+1,xc,yc); 
		}
		if(x<world.length-2 && world[x+1][y].landType!=0/**/ && (D.RAND.nextInt(2*worldDimension/3)-dist)>=-0)
		{ 
			initializeIslandLand(x+1,y,xc,yc); 
		}
	}

	void smallWaterCleanup(){
		// The following code plugs up the little 1x1 holes of water.
		for(int i=1;i<world.length-1;i++){
			for(int j=1;j<world[0].length-1;j++){
				if(world[i][j].landType==D.WATER && 
						world[i-1][j].landType==0 && world[i][j-1].landType==0 && world[i+1][j].landType==0 && world[i][j+1].landType==0){
					world[i][j].landType=0;
				}
			}
		}
	}
	void enlargeLakes(){
		for (int i=0;i<world.length;i++){
			for (int j=0;j<world[0].length;j++){
				if(world[i][j].landType==D.WATER){
					world[i][j].landType=D.STONE;	// Stone is acting as a temporary placeholder here.
				}
			}
		}
		for (int i=0;i<world.length;i++){
			for (int j=0;j<world[0].length;j++){
				if(world[i][j].landType==D.STONE){
					fillLake(i,j); // Replace stone with lake water, additionally, expand! sort of a crappy function name for now.
				}
			}
		}
	}
	void fillLake(int x,int y){
		// fillLake spreads water outwards from the first viable source it finds... then moves 1 space further out to help enlarge the lake.
		int oldType=world[x][y].landType;
		world[x][y].landType=D.WATER;
		if(oldType!=0 && oldType!=D.SALTWATER){
			if((world[x+1][y].landType==0 || world[x][y].landType==D.STONE) && !touchingSaltWater(x+1,y)){
				fillLake(x+1,y);
			}
			if((world[x][y+1].landType==0 || world[x][y].landType==D.STONE) && !touchingSaltWater(x,y+1)){
				fillLake(x,y+1);
			}
			if((world[x-1][y].landType==0 || world[x][y].landType==D.STONE) && !touchingSaltWater(x-1,y)){
				fillLake(x-1,y);
			}
			if((world[x][y-1].landType==0 || world[x][y].landType==D.STONE) && !touchingSaltWater(x,y-1)){
				fillLake(x,y-1);
			}
			//TODO: Consider adding if statements for diagonals, possibly with only a % chance of success
		}
	}
	// == Takes in the x, y location of a piece of land. The land then returns true if it is touching SALTWATER vertically or horizontally. Diagonally is not included.
	boolean touchingSaltWater(int x, int y){
		return( world[x+1][y].landType==D.SALTWATER || world[x][y+1].landType==D.SALTWATER || world[x-1][y].landType==D.SALTWATER || world[x][y-1].landType==D.SALTWATER);
	}
	// == Takes in the x and y positions for a block of water. Turns the entire body of water including the initial square from WATER to SALTWATER. 
	private void addSalt(int i,int j){
		world[i][j].landType=D.SALTWATER;
		if(i>0 && world[i-1][j].landType==D.WATER){
			addSalt(i-1,j);
		}
		if(j>0 && world[i][j-1].landType==D.WATER){
			addSalt(i,j-1);
		}
		if(i<world.length-1 && world[i+1][j].landType==D.WATER){
			addSalt(i+1,j);
		}
		if(j<world[0].length-1 && world[i][j+1].landType==D.WATER){
			addSalt(i,j+1);
		}
	}
	public String printWorld(){
		String ret="";
		for(int i=0; i<world.length;i++){
			for(int j=0;j<world[0].length;j++){
				ret+=" ";
				if (world[j][i].creature!=null && world[j][i].creature.creatureType!=0){
					ret += D.stringifyCreature(world[j][i].creature);
				}else if (world[j][i].structure != null && world[j][i].structure.structureType != 0){			
					ret += D.stringifyStructure(world[j][i].structure);
				}else if (world[j][i].item[0]!=0){
					ret += D.stringifyItem(world[j][i].item[0]);
				}else{
					ret += D.stringifyLand(world[j][i].landType);
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
		//TODO: initialize biomes. Probably create 1-2 biome functions that take in a biome type variable and seed location.
		//find biome location
		//add beach
		addBiome("Beach",findBiomeX("Beach"),findBiomeY("Beach"));
		//add set biomes; forest, desert, mountain
		addBiome("Mountain",findBiomeX("Mountain"),findBiomeY("Mountain"));
		addBiome("Desert",findBiomeX("Desert"),findBiomeY("Desert"));
		addBiome("Forest",findBiomeX("Forest"),findBiomeY("Forest"));
		//add maybe biomes; tiaga, tundra, 
		//fill in non-biome squares
		fillInEmptyCells();
	}
	
	private void fillInEmptyCells() {
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
			while(world[center][retVal].landType != D.SALTWATER)
				retVal++;
			//make the spot we're on land(go north 1)
			retVal--;
			break;
			
		case("Mountain"):
			retVal = center;
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
			retVal = center;
			break;
		}
		
		return retVal;
	}
	
	void addBiome(String c, int x, int y){
		switch(c){
		
		//Strategy: find south shores for +-(4-6) and each puts sand up for 3-5 squares
		case("Beach")://current strat, circle around center, replacing land
			int beachSize = D.RAND.nextInt(4)+5;
			int beachLength = D.RAND.nextInt(4)+3;
			for(int i = x-(beachSize/2);i< x+(beachSize/2);i++){
				int southShore = y-5;
				while(world[i][southShore].landType != D.SALTWATER)
					southShore++;
				southShore--;
				for(int j = 0; j < beachLength;j++){
					world[i][southShore-j].landType = D.SAND;
				}
			}
		break;
		case("Mountain"):
			//x and y are center of island. generate mountain around
			//TODO AUSTIN YOU WERE HERE
			break;
		}
	
	}
	
	
	void placeStructure(Structure s, int x, int y){
		world[x][y].structure=s;
	}
	void placeCreature(Creature p, int x, int y){
		if(p.xPos!=-1){
			world[p.xPos][p.yPos].creature=null;
		}
		world[x][y].creature=p;
		p.xPos=x;
		p.yPos=y;
	}
	
}
