import java.util.Random;


public class D {
	// remember to use "public static final" to make it accessible, universal, and unchanging.
	public static Random RAND=new Random();
	public static long seed;
	public static final int NONE=0;

	// Prepping some info for HUD use. All values are temporary and subject to change.
	public static final String COMMON_CURRENCY_SYMBOL="¤";
	public static final String FOOD_CURRENCY_SYMBOL="£";
	public static final String SEX_CURRENCY_SYMBOL="₫";
	public static final String MAGE_SYMBOL="☥";
	public static final String WARRIOR_SYMBOL="†";
	public static final String DOCTOR_SYMBOL="☤";
	
	// Land type ints
	public static final int SAND=1;
	public static final int WATER=2;
	public static final int SALTWATER=3;
	public static final int DIRT=4;
	public static final int GRASS=5;
	public static final int STONE=6;
	
	// Land type display characters
	public static final String LAND_GFX="l";
	public static final String SAND_GFX=".";
	public static final String WATER_GFX="~";
	public static final String SALTWATER_GFX="w";
	public static final String DIRT_GFX=",";
	public static final String STONE_GFX="E";
	public static final String GRASS_GFX=",";
	
	// Structure type ints
	public static final int TREE=1;
	public static final int FRUITTREE=2;
	public static final int CACTUS=3;
	public static final int BOULDER=4;
	public static final int WALL=5;
	public static final int DOOR=6;
	public static final int FIREPIT=7;
	public static final int STRENGTH_RUNE_SLOT=50;
	public static final int HEALTH_RUNE_SLOT=51;
	public static final int TOUGHNESS_RUNE_SLOT=52;
	public static final int SPEED_RUNE_SLOT=53;
	public static final int CHAMPION_RUNE_SLOT=54;
	public static final int MAGICPOWER_RUNE_SLOT=55;
	public static final int RESISTANCE_RUNE_SLOT=56;
	public static final int MAGICRANGE_RUNE_SLOT=57;
	public static final int CASTSPEED_RUNE_SLOT=58;
	public static final int HEALING_RUNE_SLOT=59;
	public static final int MAGE_RUNE_SLOT=60;
	
	// Structure type display characters
	public static final String STRUCTURE_GFX="␚";
	public static final String FULL_TREE_GFX="T";
	public static final String REDUCED_TREE_GFX="t";
	public static final String FULL_FRUITTREE_GFX="F";
	public static final String REDUCED_FRUITTREE_GFX="f";
	public static final String FULL_CACTUS_GFX="╬";  //NOT MONOSPACED - need to update
	public static final String REDUCED_CACTUS_GFX="╥";  //POSSIBLY NOT MONOSPACED - may need to update
	public static final String FULL_BOULDER_GFX="O";
	public static final String REDUCED_BOULDER_GFX="o";
	public static final String DOOR_GFX="∩";
	public static final String LIT_FIREPIT_GFX="â";
	public static final String UNLIT_FIREPIT_GFX="x";
	public static final String UNDAMAGED_WALL_GFX="■";
	public static final String DAMAGED_WALL_GFX="▮";
	public static final String STRENGTH_RUNE_SLOT_GFX="▲";
	public static final String HEALTH_RUNE_SLOT_GFX="▲";
	public static final String TOUGHNESS_RUNE_SLOT_GFX="▲";
	public static final String SPEED_RUNE_SLOT_GFX="●";
	public static final String CHAMPION_RUNE_SLOT_GFX="◬";
	public static final String MAGICPOWER_RUNE_SLOT_GFX="◤";
	public static final String RESISTANCE_RUNE_SLOT_GFX="◥";
	public static final String MAGICRANGE_RUNE_SLOT_GFX="◣";
	public static final String CASTSPEED_RUNE_SLOT_GFX="◢";
	public static final String HEALING_RUNE_SLOT_GFX="◆";
	public static final String WARLOCK_RUNE_SLOT_GFX="◈";
		
	// Resource type ints
	public static final int RES_WOOD=1;
	public static final int RES_STONE=2;
	public static final int RES_FRUIT=3;
	public static final int RES_CACTIPODE=4;
	// TODO: fill out with more resources

	// Material type ints
	public static final int MAT_WOOD=RES_WOOD;
	public static final int MAT_STONE=RES_STONE;
	public static final int MAT_CACTIPODE=RES_CACTIPODE;
	// TODO: fill out with more materials.
	
	// Item type ints
	public static final int ITM_RES_WOOD=1;
	public static final int ITM_RES_STONE=2;
	public static final int ITM_RES_FRUIT=3;
	public static final int ITM_RES_CACTIPODE=4;
	
	// Item type display characters
	public static final String ITM_GFX="ǂ";
	public static final String ITM_RES_WOOD_GFX="ɯ";
	public static final String ITM_RES_STONE_GFX="Ƨ";
	public static final String ITM_RES_FRUIT_GFX="ǒ";
	public static final String ITM_RES_CACTIPODE_GFX="ǫ";
	
	// Cardinal directions
	public static final int NORTH=1;
	public static final int EAST=2;
	public static final int WEST=3;
	public static final int SOUTH=4;
	
	public static String stringifyCreature(Creature creature){
		return "Y";//✝ deceased
	}
	public static String stringifyStructure(Structure structure){
		String ret="";
		boolean hasResource = structure.resourceCount>0;
		switch (structure.structureType){
			case NONE: 
				ret=STRUCTURE_GFX;
				break;
			case TREE: 
				ret=(hasResource?FULL_TREE_GFX:REDUCED_TREE_GFX);
				break;
			case FRUITTREE: 
				ret=(hasResource?FULL_FRUITTREE_GFX:REDUCED_FRUITTREE_GFX);
				break;
			case CACTUS:
				ret=(hasResource?FULL_CACTUS_GFX:REDUCED_CACTUS_GFX);
				break;
			case BOULDER:
				ret=(hasResource?FULL_BOULDER_GFX:REDUCED_BOULDER_GFX);
				break;
			case FIREPIT:
				ret=(hasResource?LIT_FIREPIT_GFX:UNLIT_FIREPIT_GFX);
				break;
			case WALL: 
				ret=(hasResource?UNDAMAGED_WALL_GFX:DAMAGED_WALL_GFX);
				break;
			case DOOR:
				ret=DOOR_GFX;
			default:
				ret=""+structure.structureType;
		}
		return ret;
	}
	public static String stringifyItem(int item){
		String ret="";
		switch (item){
			case NONE: ret=ITM_GFX;
					break;
			case ITM_RES_WOOD: ret=ITM_RES_WOOD_GFX;
					break;
			case ITM_RES_STONE: ret=ITM_RES_STONE_GFX;
					break;
			case ITM_RES_FRUIT: ret=ITM_RES_FRUIT_GFX;
					break;
			case ITM_RES_CACTIPODE: ret=ITM_RES_CACTIPODE_GFX;
					break;
			default:
				ret=""+item;
		}
		return ret;
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
			case GRASS: ret=GRASS_GFX;
					break;
			case STONE: ret=STONE_GFX;
				break;
			default:
				ret=""+land;
		}
		return ret;
	}
	public static void seedRand(){
		seed=System.currentTimeMillis();
		RAND.setSeed(seed);
	}
}
