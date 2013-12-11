
public class World {
	int xSize=20;
	int ySize=20;
	Land[][] world = new Land[xSize][ySize]; 
	
	
	World(){
		initializeLand();
		initializeSea();
		
	}
	void initializeLand(){
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
	
	
	public String printWorld(){
		String ret="";
		for(int i=0; i<xSize;i++){
			for(int j=0;j<ySize;j++){
				ret+=" ";
				if(world[j][i].creature[0]!=0){
					
				}else if(world[j][i].structure!=0){			
					
				}else if(world[j][i].item[0]!=0){
					
				}else if(world[j][i].landType!=0){
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
