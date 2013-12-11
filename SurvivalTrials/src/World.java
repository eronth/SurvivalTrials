
public class World {
	int worldDimension=50;
	Land[][] world = new Land[worldDimension][worldDimension]; 
	
	
	World(){
		initializeWorldsLand();
		initializeSea();
		
		initializeIsland();
		
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
	void initializeIsland(){
		int center=(world[0].length-1)/2;
		//int xCenter=yCenter+yCenter/3;
		initializeIslandAid(center,center,center,center);
		world[center][center].landType=2;
	}
	void initializeIslandAid(int x,int y,int xc, int yc){
		//System.out.println("x:"+x+" y:"+y+" dist:"+dist);
		// Function works by setting the current spot to a non-SEAWATER value. It then possibly calls the function on neighboring squars, with a chance to fail based on an increasing number counter. 
		world[x][y].landType=0;
		int xlen=(xc>x?xc-x:x-xc);
		int ylen=(yc>y?yc-y:y-yc);
		double dist = (Math.sqrt( Math.pow(xlen,2) + Math.pow(ylen,2) ));
		
		System.out.println("pip pap "+dist+" "+xlen+" "+ylen);
		printWorld();
		
		
		if(x>0 && world[x-1][y].landType!=0/**/ && (D.RAND.nextInt(worldDimension/2)-dist)>=-0)//worldDimension/4) 
		{
			initializeIslandAid(x-1,y,xc,yc); 
		}
		if(y>0 && world[x][y-1].landType!=0/**/ && (D.RAND.nextInt(worldDimension/2)-dist)>=-0)//worldDimension/2) 
		{ 
			initializeIslandAid(x,y-1,xc,yc); 
		}
		if(y<world.length-1 && world[x][y+1].landType!=0/**/ && (D.RAND.nextInt(worldDimension/2)-dist)>=-0)//worldDimension/2) 
		{ 
			initializeIslandAid(x,y+1,xc,yc); 
		}
		if(x<world.length-1 && world[x+1][y].landType!=0/**/ && (D.RAND.nextInt(worldDimension/2)-dist)>=-0)//worldDimension/2) 
		{ 
			initializeIslandAid(x+1,y,xc,yc); 
		}
		
		
		
	
	}
	
	
	public String printWorld(){
		String ret="";
		for(int i=0; i<worldDimension;i++){
			for(int j=0;j<worldDimension;j++){
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
