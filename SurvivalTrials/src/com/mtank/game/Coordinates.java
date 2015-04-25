package com.mtank.game;

import com.mtank.constants.Direction;


public class Coordinates {
	public int x=0;
	public int y=0;
	public Coordinates(){
		
	}
	/**
	 * Generates new coordinates with same x-y values as the passed x and y values.
	 */
	public Coordinates(int x, int y){
		this.x=x;
		this.y=y;
	}
	/**
	 * Generates new coordinates using the same values as c.
	 * @param c
	 */
	public Coordinates(Coordinates c) {
		this.x=c.x;
		this.y=c.y;
	}
	public boolean equals(Coordinates c){
		return (this.x==c.x && this.y==c.y);
	}
	public Coordinates directionalCoord(int direction) {
		return new Coordinates(this.x+Direction.modifyX(direction),this.y+Direction.modifyY(direction));
	}
	public void setDirection(int direction) {
		this.set(Direction.modify(this,direction));
		//this.x+=Direction.modifyX(direction);
		//this.y+=Direction.modifyY(direction);
	}

	public Coordinates north() {
		return directionalCoord(Direction.NORTH);
	}
	
	public Coordinates northwest() {
		return directionalCoord(Direction.NORTHWEST);
	}
	
	public Coordinates northeast() {
		return directionalCoord(Direction.NORTHEAST);
	}
	
	public Coordinates west() {
		return directionalCoord(Direction.WEST);
	}
	
	public Coordinates east() {
		return directionalCoord(Direction.EAST);
	}
	
	public Coordinates southeast() {
		return directionalCoord(Direction.SOUTHEAST);
	}
	
	public Coordinates southwest() {
		return directionalCoord(Direction.SOUTHWEST);
	}
	
	public Coordinates south() {
		return directionalCoord(Direction.SOUTH);
	}
	
	/**
	 * Sets the x and y values of the current coordinate to be the same as coord.
	 */
	public void set(Coordinates coord) {
		set(coord.x, coord.y);
	}	
	/**
	 * Sets the x and y values of the current Coordinate to x and y.
	 */
	public void set(int x,int y) {
		setX(x);
		setY(y);
	}
	/**
	 * Set the x value of the current Coordinate to x.
	 */
	public void setX(int x) {
		this.x=x;
	}
	/**
	 * Set the y value of the current Coordinate to y.
	 */
	public void setY(int y) {
		this.y=y;
	}
	
	public boolean isAdjacentTo(Coordinates c) {
		return ((this.x-1==c.x || this.x+1==c.x) && this.y==c.y) || ((this.y-1==c.y || this.y+1==c.y) && this.x==c.x);
	}
	
	@Override
	public String toString(){
		return "("+this.x+","+this.y+")";
	}
	
}
