import java.util.Random;


public class D {
	// remember to use "public static final" to make it accessable, universal, and unchanging.
	public static Random RAND=new Random();
	// land types follow
	public static final int SALTWATER=1;
	public static final int WATER=2;
	public static final int SAND=3;
	public static final int DIRT=4;
	public static final int STONE=5;
	public static long seed;
	
	
	public static String stringifyLand(int land){
		String ret="";
		switch (land){
			case 0: ret="l";
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
