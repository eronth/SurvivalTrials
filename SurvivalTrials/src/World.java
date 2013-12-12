
public class World {
	private int worldDimension=10;
	public Land[][] world;// = new Land[worldDimension][worldDimension]; 
	
	
	World(int size){
		worldDimension=size;
		world = new Land[worldDimension][worldDimension]; 
		initializeWorldsLand();
		initializeSea();
		initializeIslandLand();
		purifyWater();
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
				world[i][j].landType=D.SALTWATER;
			}
		}
	}
	void initializeIslandLand(){
		int center=(world[0].length-1)/2;
		//int xCenter=yCenter+yCenter/3;
		initializeIslandAid(center,center,center,center);
		world[center][center].landType=2;
	}
	void initializeIslandAid(int x,int y,int xc, int yc){
		// Function works by setting the current spot to a non-SEAWATER value. It then possibly calls the function on neighboring squars, with a chance to fail based on an increasing number counter. 
		world[x][y].landType=0;
		int xlen=(xc>x?xc-x:x-xc);
		int ylen=(yc>y?yc-y:y-yc);
		double dist = (Math.sqrt( Math.pow(xlen,2) + Math.pow(ylen,2) ));
		
		
		
		// Values for inequalities were chosen to ensure the borders are always SALTWATER		
		if(x>1 && world[x-1][y].landType!=0/**/ && (D.RAND.nextInt(2*worldDimension/3)-dist)>=-0)//worldDimension/4) 
		{
			initializeIslandAid(x-1,y,xc,yc); 
		}
		if(y>1 && world[x][y-1].landType!=0/**/ && (D.RAND.nextInt(2*worldDimension/3)-dist)>=-0)//worldDimension/2) 
		{ 
			initializeIslandAid(x,y-1,xc,yc); 
		}
		if(y<world.length-2 && world[x][y+1].landType!=0/**/ && (D.RAND.nextInt(2*worldDimension/3)-dist)>=-0)//worldDimension/2) 
		{ 
			initializeIslandAid(x,y+1,xc,yc); 
		}
		if(x<world.length-2 && world[x+1][y].landType!=0/**/ && (D.RAND.nextInt(2*worldDimension/3)-dist)>=-0)//worldDimension/2) 
		{ 
			initializeIslandAid(x+1,y,xc,yc); 
		}
		//System.out.println("\n");
		//printWorld();
	}
	void purifyWater(){
		// The following code plugs up the little 1x1 holes of water.
		for(int i=1;i<world.length-1;i++){
			for(int j=1;j<world[0].length-1;j++){
				if(world[i][j].landType==D.SALTWATER && 
						world[i-1][j].landType==0 && world[i][j-1].landType==0 && world[i+1][j].landType==0 && world[i][j+1].landType==0){
					world[i][j].landType=0;
				}
			}
		}
		// The following code turns any water not connected to the ocean into fresh water.
		
		//TODO: turn SALTWATER lakes into WATER;
			
		
	}
	
	
	public String printWorld(){
		String ret="";
		for(int i=0; i<world.length;i++){
			for(int j=0;j<world[0].length;j++){
				ret+=" ";
				if(world[j][i].creature[0]!=0){
					ret+="c";
				}else if(world[j][i].structure!=0){			
					ret+="s";
				}else if(world[j][i].item[0]!=0){
					ret+="i";
				}else{
					ret+=D.stringifyLand(world[j][i].landType);
				}
				ret+=" ";
			}
			ret+="\n";
		}
		System.out.print(ret);
		return ret;
	}
	
		
		

}
