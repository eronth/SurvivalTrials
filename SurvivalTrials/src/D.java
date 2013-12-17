import java.util.Random;


public class D {
	// remember to use "public static final" to make it accessible, universal, and unchanging.
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
	
	// Land type display characters
	public static final String LAND_GFX="l";
	public static final String SAND_GFX=".";
	public static final String WATER_GFX="~";
	public static final String SALTWATER_GFX="w";
	public static final String DIRT_GFX=",";
	public static final String STONE_GFX="-";
	public static final String BLANK_GFX="/";
	
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
			case NONE: ret=LAND_GFX;
					break;
			case SALTWATER: ret=SALTWATER_GFX;
					break;
			case WATER: ret=WATER_GFX;
					break;
			case SAND: ret=SAND_GFX;
					break;
			case DIRT: ret=DIRT_GFX;
					break;
			case STONE: ret=DIRT_GFX;
					break;
			case 6: ret=BLANK_GFX;
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
