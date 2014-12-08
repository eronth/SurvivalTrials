package com.mtank.game;

import com.mtank.constants.Direction;
//import Coordinates;



public class Coordinates {
	public int x=0;
	public int y=0;
	public Coordinates(){
		
	}
	public Coordinates(int x,int y){
		this.x=x;
		this.y=y;
	}
	public Coordinates(Coordinates c) {
		this.x=c.x;
		this.y=c.y;
	}
	

	
	public boolean equals(Coordinates c){
		return (this.x==c.x && this.y==c.y);
	}
	public Coordinates directionalCoord(int direction) {
		//TODO figure out why this was just "return new Coordinates();".
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
	 * Sets the x and y values of the current coordinate to be the same as <coord>.
	 */
	public void set(Coordinates coord) {
		set(coord.x, coord.y);
	}
	
	/**
	 * Sets the x and y values of the current Coordinate to <x> and <y>.
	 */
	public void set(int x,int y) {
		this.x=x;
		this.y=y;
	}
	
	@Override
	public String toString(){
		return "("+this.x+","+this.y+")";
	}
	
}
