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
	
	/**
	 * Returns an appropriate value to modify the x-coordinate based on the direction.
	 * @param direction
	 * @return
	 */
	public static int modifyX(int direction) {
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
	/**
	 * Returns an appropriate value to modify the y-coordinate based on the direction.
	 * @param direction
	 * @return
	 */
	public static int modifyY(int direction) {
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
	public static int invert(int _direction) {
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
	/**
	 * Returns the direction slightly right of the argument direction. 
	 * Example: Would return NORTHEAST if the argument is NORTH and WEST if the argument is SOUTHWEST.
	 */
	public static int slightRight(int direction) {
	int ret = 0;
	switch(direction){
	case NORTH:
		ret=NORTHEAST;
		break;
	case SOUTH:
		ret=SOUTHWEST;
		break;
	case EAST:
		ret=SOUTHEAST;
		break;
	case WEST:
		ret=NORTHWEST;
		break;
	case NORTHEAST:
		ret=EAST;
		break;
	case NORTHWEST:
		ret=NORTH;
		break;
	case SOUTHEAST:
		ret=SOUTH;
		break;
	case SOUTHWEST:
		ret=WEST;
		break;
	}
	return ret;
	}
	/**
	 * Returns the direction slightly left of the argument direction. 
	 * Example: Would return NORTHWEST if the argument is NORTH and SOUTH if the argument is SOUTHWEST.
	 */
	public static int slightLeft(int direction) {
		int ret = 0;
		switch(direction){
		case NORTH:
			ret=NORTHWEST;
			break;
		case SOUTH:
			ret=SOUTHEAST;
			break;
		case EAST:
			ret=NORTHEAST;
			break;
		case WEST:
			ret=SOUTHWEST;
			break;
		case NORTHEAST:
			ret=NORTH;
			break;
		case NORTHWEST:
			ret=WEST;
			break;
		case SOUTHEAST:
			ret=EAST;
			break;
		case SOUTHWEST:
			ret=SOUTH;
			break;
		}
		return ret;
	}
	
}
