package com.mtank.constants;

import com.mtank.game.Coordinates;


public class Direction {
	// Cardinal directions
	public static final int NORTH=1;
	public static final int EAST=2;
	public static final int WEST=3;
	public static final int SOUTH=4;
	public static final int NORTHEAST=5;
	public static final int NORTHWEST=6;
	public static final int SOUTHEAST=7;
	public static final int SOUTHWEST=8;
	
	/**
	 * Returns true if <direction> is a cardinal direction and false otherwise.
	 * Cardinal directions are defined as NORTH, SOUTH, EAST, and WEST.
	 */
	static public boolean isCardinalDirection(int direction) {
		return ((direction==NORTH) ||
				(direction==EAST)  ||
				(direction==WEST)  ||
				(direction==SOUTH));
	}
	
	// modifyX and modifyY are used to modify positions. 
	//  Example usage would be if you have a character placing something to the NORTHEAST of himself. 
	//  :modifyX will return +1 since EAST is located +1 along the x axis.
	//  :modifyY will return -1 since NORTH is located -1 along the y axis.
	// XMOD and YMOD assume you're looking for 1 space away in the given direction.
	private static int modifyX(int direction){
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
	private static int modifyY(int direction){
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
	/**
	 * Returns coordinates to the location of one step to <direction> of <c>.
	 */
	public static Coordinates modify(Coordinates c, int direction) {
		return new Coordinates(c.x+modifyX(direction), c.y+modifyY(direction));
	}
	
	/**
	 * Returns the inverted direction of the argument.
	 */
	public static int invertDirection(int _direction) {
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

	
	
}
