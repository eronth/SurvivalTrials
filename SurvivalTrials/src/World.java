
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
		// System.out.println("\n");
		// printWorld();
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
				}else if (world[j][i].structure!=0){			
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
		addBiome('B',findBiomeX('B'),findBiomeY('B'));
		//add Mountain (stone)
	}
	
	private int findBiomeY(char c) {
		// TODO Auto-generated method stub
		return -1;
	}
	private int findBiomeX(char c) {
		// TODO Auto-generated method stub
		return -1;
	}
	void addBiome(char c, int x, int y){
		
	}
	
	void placeCreature(Creature p, int x, int y){
		if(p.xPos!=-1){
			//todo: remove from old spot here.
			world[p.xPos][p.yPos].creature=null;
		}
		world[x][y].creature=p;
		p.xPos=x;
		p.yPos=y;
	}
		
		

}
