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
	public static final int SNOW=7;
	public static final int ICE=8;
	public static final int DESERT=9;
	
	// Land type display characters
	public static final String LAND_GFX="l";
	public static final String SAND_GFX=".";
	public static final String WATER_GFX="~";
	public static final String SALTWATER_GFX="w";
	public static final String DIRT_GFX=",";
	public static final String STONE_GFX="-";
	public static final String GRASS_GFX="/";
	
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
	public static final String FULL_CACTUS_GFX="╬";
	public static final String REDUCED_CACTUS_GFX="╥";
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
	
	// Cardinal directions
	public static final int NORTH=1;
	public static final int EAST=2;
	public static final int WEST=3;
	public static final int SOUTH=4;
	public static final int NORTHEAST=5;
	public static final int NORTHWEST=6;
	public static final int SOUTHEAST=7;
	public static final int SOUTHWEST=8;
	
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
	
	
	static boolean isCardinalDirection(int _direction) {
		return ((_direction==NORTH) ||
				(_direction==EAST)  ||
				(_direction==WEST)  ||
				(_direction==SOUTH));
	}
	// XMOD and YMOD are used to modify positions. 
	//  Example usage would be if you have a character placing something to the NORTHEAST of himself. 
	//  :XMOD will return +1 since EAST is located +1 along the x axis.
	//  :YMOD will return -1 since NORTH is located -1 along the y axis.
	// XMOD and YMOD assume you're looking for 1 space away in the given direction.
	public static int XMOD(int direction){
		int xmod=0;
		switch(direction){
		case EAST:
		case NORTHEAST:
		case SOUTHEAST:
			xmod=+1;
			break;
		case WEST:
		case NORTHWEST:
		case SOUTHWEST:
			xmod=-1;
			break;
		case 0:
			break;
		}
		return xmod;
	}
	public static int YMOD(int direction){
		int ymod=0;
		switch(direction){
		case NORTH:
		case NORTHEAST:
		case NORTHWEST:
			ymod=-1;
			break;
		case SOUTH:
		case SOUTHEAST:
		case SOUTHWEST:
			ymod=+1;
			break;
		case 0:
			break;
		}
		return ymod;
	}
	public static int invertDirection(int _direction){
		int ret=0;
		switch(_direction){
		case NORTH:
			ret=SOUTH;
			break;
		case SOUTH:
			ret=NORTH;
			break;
		case EAST:
			ret=WEST;
			break;
		case WEST:
			ret=EAST;
			break;
		case NORTHEAST:
			ret=SOUTHWEST;
			break;
		case NORTHWEST:
			ret=SOUTHEAST;
			break;
		case SOUTHEAST:
			ret=NORTHWEST;
			break;
		case SOUTHWEST:
			ret=NORTHEAST;
			break;
		}
		return ret;
	}
	public static boolean isWalkable(World w, Coordinates c){
		return (
				// Returns walkable if no creatures is in the way.
				(w.world[c.x][c.y].creature==null||w.world[c.x][c.y].creature.creatureType==0) && 
				// Returns walkable if no structures obstruct movement.
				(w.world[c.x][c.y].structure==null||w.world[c.x][c.y].structure.structureType==0) 
				);
	}
	public static boolean isWalkable(World w, int x, int y) {
		Coordinates c = new Coordinates(x,y);
		return isWalkable(w, c);
	}
	public static String directionToArrow(int d) {
		String ret="";
		switch (d){
		case NORTH:
			ret="^ ";//"↑       ";
			break;
		case SOUTH:
			ret="v ";//"↓       ";
			break;
		case EAST:
			ret="> ";//"→ ";
			break;
		case WEST:
			ret="< ";//"← ";
			break;
		case NORTHEAST:
			ret="/*";//"↗      ";
			break;
		case NORTHWEST:
			ret="*\\";//"↖      ";
			break;
		case SOUTHEAST:
			ret="\\.";//"↘     ";
			break;
		case SOUTHWEST:
			ret="/.";//"↙     ";
			break;
		case NONE:
			ret="o ";
			break;
		default:
			ret="X";
			break;
		}
		return ret;
	}
}
