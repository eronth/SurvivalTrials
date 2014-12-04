package com.mtank.ai;
import com.mtank.game.Coordinates;


public class PathFindingLand {
	int totalCost = 100000;	// F, the combination of values.
	int movementCost = 1;	// The modifier for this land (slower on sand or faster on road).
	int heuristicCost = 0;	// H, an estimated cost to reach B from here.
	
	private int direction = 0; 		// Direction of cheapest movement.
	private boolean isOpenList;		// List of items being considered for movementl, Lowest score moves to closed list.
	private boolean isClosedList;	// List of items currently on movement list (may or may not be used).
	private boolean isWalkable;
	
	public boolean getIsWalkable() {
		return isWalkable;
	}
	public void setIsWalkable(boolean b) {
		isWalkable=b;
	}
	public boolean getIsOpenList() {
		return isOpenList;
	}
	public void setIsOpenList(boolean b) {
		isOpenList = b;
		isClosedList = (b ? isClosedList : false );
	}
	public boolean getIsClosedList() {
		return isClosedList;
	}
	public void setIsClosedList (boolean b) {
		isClosedList = b;
		isOpenList = (b ? true : isOpenList);
	}
	public int getHeuristic() {
		return heuristicCost;
	}
	public void setHeuristic(Coordinates a, Coordinates b) {
		heuristicCost = (a.x>b.x ? a.x-b.x : b.x-a.x) + (a.y>b.y ? a.y-b.y : b.y-a.y);
	}
	public void setHeuristic(int h) {
		heuristicCost = h;
	}
	
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
}
