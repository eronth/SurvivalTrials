import java.util.Random;


public class D {
	// remember to use "public static final" to make it accessable, universal, and unchanging.
	public static Random RAND=new Random();
	// land types follow
	
	public static long seed;
	public static final int NONE=0;
	// Land type ints
	public static final int SAND=1;
	public static final int WATER=2;
	public static final int SALTWATER=3;
	public static final int DIRT=4;
	public static final int STONE=5;
	
	// Cardinal directions
	public static final int NORTH=1;
	public static final int EAST=2;
	public static final int WEST=3;
	public static final int SOUTH=4;
	
	
	public static String stringifyCreature(Creature creature){
		return "†";
	}
	public static String stringifyStructure(int structure){
		return "s";
	}
	public static String stringifyItem(int item){
		return "i";
	}
	public static String stringifyLand(int land){
		String ret="";
		switch (land){
			case NONE: ret="l";
					break;
			case SALTWATER: ret="w";
					break;
			case WATER: ret="~";
					break;
			case SAND: ret=".";
					break;
			case DIRT: ret=",";
					break;
			case STONE: ret="-";
					break;
			case 6: ret="/";
					default:
						ret+=land;
		}
		return ret;
	}
	public static void seedRand(){
		seed=System.currentTimeMillis();
		RAND.setSeed(seed);
	}

}
