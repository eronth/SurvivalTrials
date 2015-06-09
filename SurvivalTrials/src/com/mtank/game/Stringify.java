package com.mtank.game;


import com.mtank.constants.Direction;
import com.mtank.constants.Graphic;
import com.mtank.constants.TypeValue;
import com.mtank.creature.Creature;
import com.mtank.item.Item;
import com.mtank.structure.Structure;

	public class Stringify {
	public static String creature(Creature creature){
		return "Y";//✝ deceased
	}
	public static String structure(Structure s){
		String ret="";
		boolean hasResource = s.resource.size()>0;
		switch (s.structureType){
			case TypeValue.NONE: 
				ret=Graphic.STRUCTURE;
				break;
			case TypeValue.Structure.TREE: 
				ret=(hasResource?Graphic.FULL_TREE:Graphic.REDUCED_TREE);
				break;
			case TypeValue.Structure.FRUITTREE: 
				ret=(hasResource?Graphic.FULL_FRUITTREE:Graphic.REDUCED_FRUITTREE);
				break;
			case TypeValue.Structure.CACTUS:
				ret=(hasResource?Graphic.FULL_CACTUS:Graphic.REDUCED_CACTUS);
				break;
			case TypeValue.Structure.BOULDER:
				ret=(hasResource?Graphic.FULL_BOULDER:Graphic.REDUCED_BOULDER);
				break;
			case TypeValue.Structure.FIREPIT:
				ret=(hasResource?Graphic.LIT_FIREPIT:Graphic.UNLIT_FIREPIT);
				break;
			case TypeValue.Structure.WALL: 
				ret=(hasResource?Graphic.UNDAMAGED_WALL:Graphic.DAMAGED_WALL);
				break;
			case TypeValue.Structure.DOOR:
				ret=Graphic.DOOR;
			default:
				ret=""+s.structureType;
		}
		return ret;
	}
	public static String item(Item item){
		String ret="";
		switch (item.itemType){
			case TypeValue.NONE: ret=Graphic.ITEM;
					break;
			case TypeValue.Item.WOOD: ret=Graphic.WOOD;
					break;
			case TypeValue.Item.STONE: ret=Graphic.ROCK;
					break;
			case TypeValue.Item.FRUIT: ret=Graphic.FRUIT;
					break;
			case TypeValue.Item.CACTIPODE: ret=Graphic.CACTIPODE;
					break;
			default:
				ret=""+item;
		}
		return ret;
	}
	public static String land(int land){
		String ret="";
		switch (land){
			case TypeValue.NONE: 
				ret=Graphic.LAND;
				break;
			case TypeValue.Land.SALTWATER: 
				ret=Graphic.SALTWATER;
				break;
			case TypeValue.Land.WATER: 
				ret=Graphic.WATER;
				break;
			case TypeValue.Land.SAND: 
				ret=Graphic.SAND;
				break;
			case TypeValue.Land.DIRT: 
				ret=Graphic.DIRT;
				break;
			case TypeValue.Land.GRASS: 
				ret=Graphic.GRASS;
				break;
			case TypeValue.Land.STONE:
				ret=Graphic.STONE;
				break;
			default:
				ret=""+land;
		}
		return ret;
	}
	public static String direction(int direction) {
		String ret="";
		switch (direction) {
			case TypeValue.NONE:
				ret="No Direction";
				break;
			case Direction.SOUTH:
				ret="South";
				break;
			case Direction.SOUTHWEST:
				ret="Southwest";
				break;
			case Direction.SOUTHEAST:
				ret="Southeast";
				break;
			case Direction.NORTH:
				ret="North";
				break;
			case Direction.NORTHWEST:
				ret="Northwest";
				break;
			case Direction.NORTHEAST:
				ret="Northeast";
				break;
			case Direction.EAST:
				ret="East";
				break;
			case Direction.WEST:
				ret="West";
				break;
		}
		return ret;
	}
}
