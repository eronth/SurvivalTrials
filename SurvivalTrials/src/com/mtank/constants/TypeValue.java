package com.mtank.constants;


public class TypeValue {
	public static final int NONE=0;
	
	// Land type ints
	public class Land {
		public static final int SAND=1;
		public static final int WATER=2;
		public static final int SALTWATER=3;
		public static final int DIRT=4;
		public static final int GRASS=5;
		public static final int STONE=6;
		public static final int SNOW=7;
		public static final int ICE=8;
		public static final int DESERT=9;
	};
	
	// Structure type ints
	public class Structure {
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
	}
	
	public class Creature {
		public static final int PERSON=1;
		public static final int RAT=2;
		public static final int BOAR=3;
		public static final int BEAR=5;
		public static final int GOAT=4;
		public static final int CAT=6;
		public static final int DEER=7;
	}
		
	// Resource type ints
	public class Resource {
		public static final int WOOD=1;
		public static final int STONE=2;
		public static final int FRUIT=3;
		public static final int CACTIPODE=4;
		// TODO: fill out with more resources
	}

	// Material type ints
	public class Material {
		public static final int WOOD=Resource.WOOD;
		public static final int STONE=Resource.STONE;
		public static final int CACTIPODE=Resource.CACTIPODE;
		// TODO: fill out with more materials.
	}
	// Item type ints
	public class Item {
		public static final int WOOD=1;
		public static final int STONE=2;
		public static final int FRUIT=3;
		public static final int CACTIPODE=4;
	}
}
