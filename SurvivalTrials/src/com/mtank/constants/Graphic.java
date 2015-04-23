package com.mtank.constants;

public class Graphic {
	
	
	// HUD values.
	public static final String COMMON_CURRENCY_SYMBOL="¤";
	public static final String FOOD_CURRENCY_SYMBOL="£";
	public static final String SEX_CURRENCY_SYMBOL="₫";
	
	// Classes
	public static final String MAGE_SYMBOL="☥";
	public static final String WARRIOR_SYMBOL="†";
	public static final String DOCTOR_SYMBOL="☤";
	
	// Land display graphics
	public static final String LAND="l";
	public static final String SAND=".";
	public static final String WATER="~";
	public static final String SALTWATER="w";
	public static final String DIRT=";";
	public static final String STONE="E";
	public static final String GRASS=",";
	public static final String SNOW="X";
	public static final String ICE="R";
	public static final String DESERT="Z";
	
	// Structure display graphics
	public static final String STRUCTURE="␚";
	public static final String FULL_TREE="T";
	public static final String REDUCED_TREE="t";
	public static final String FULL_FRUITTREE="F";
	public static final String REDUCED_FRUITTREE="f";
	public static final String FULL_CACTUS="╬";  //NOT MONOSPACED - need to update
	public static final String REDUCED_CACTUS="╥";  //POSSIBLY NOT MONOSPACED - may need to update
	public static final String FULL_BOULDER="O";
	public static final String REDUCED_BOULDER="o";
	public static final String DOOR="∩";
	public static final String LIT_FIREPIT="â";
	public static final String UNLIT_FIREPIT="x";
	public static final String UNDAMAGED_WALL="■";
	public static final String DAMAGED_WALL="▮";
	public static final String STRENGTH_RUNE_SLOT="▲";
	public static final String HEALTH_RUNE_SLOT="▲";
	public static final String TOUGHNESS_RUNE_SLOT="▲";
	public static final String SPEED_RUNE_SLOT="●";
	public static final String CHAMPION_RUNE_SLOT="◬";
	public static final String MAGICPOWER_RUNE_SLOT="◤";
	public static final String RESISTANCE_RUNE_SLOT="◥";
	public static final String MAGICRANGE_RUNE_SLOT="◣";
	public static final String CASTSPEED_RUNE_SLOT="◢";
	public static final String HEALING_RUNE_SLOT="◆";
	public static final String WARLOCK_RUNE_SLOT="◈";
	
	// Creature display graphics
	public static final String CREATURE="Y";
	
	// Item display graphics
	public static final String ITEM="ǂ";
	public static final String WOOD="ɯ";
	public static final String ROCK="Ƨ";
	public static final String FRUIT="ǒ";
	public static final String CACTIPODE="ǫ";
	
	
	public static String directionToArrow(int d) {
		String ret="";
		switch (d){
		case Direction.NORTH:
			ret="^ ";//"↑       ";
			break;
		case Direction.SOUTH:
			ret="v ";//"↓       ";
			break;
		case Direction.EAST:
			ret="> ";//"→ ";
			break;
		case Direction.WEST:
			ret="< ";//"← ";
			break;
		case Direction.NORTHEAST:
			ret="/*";//"↗      ";
			break;
		case Direction.NORTHWEST:
			ret="*\\";//"↖      ";
			break;
		case Direction.SOUTHEAST:
			ret="\\.";//"↘     ";
			break;
		case Direction.SOUTHWEST:
			ret="/.";//"↙     ";
			break;
		case TypeValue.NONE:
			ret="o ";
			break;
		default:
			ret="X";
			break;
		}
		return ret;
	}
}
