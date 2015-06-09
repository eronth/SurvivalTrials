package com.mtank.ai;

import java.util.ArrayList;

import com.mtank.constants.Direction;
import com.mtank.game.Coordinates;
import com.mtank.game.Stringify;
import com.mtank.world.World;

public class PathFindingWorld {
	// A complete explanation of path-finding is as follows:
	// Start with the origin square on a closed list, all other squares on an open list.
	// F = G + H
	// F is "cost" of a given square. You shall choose the lowest F to determine the newest square on your closed list.
	// G is the cost to move to a given square. AKA your total cost so far.
	// H is your heuristic, or estimated cost to final destination.
	// Typical path-finding has a movement cost of 10 for UDLR and 14 for diagonals (prior to modifiers).
	// EVERYTHING ADJACENT TO WHERE YOU'VE CHECKED IS CONSIDERED "OPEN LIST". Remember to add a direction.
	// Holy shit I might be some kind of retard. ... Wh... why don't I have a PathfindingLand class?
	
	private PathFindingLand[][] area;
	public ArrayList<Integer> path = new ArrayList<Integer>();
	public Coordinates targetCoords = new Coordinates();
	public Coordinates startCoords = new Coordinates();
	public Coordinates tmp = new Coordinates();

	// w is the world dimension, used to ensure the pathfinding world is of appropriate size.
	public PathFindingWorld(World w) {
		area=new PathFindingLand[w.world.length][w.world[0].length];
		for (int i = 0; i<w.world.length; i++) {
			for (int j = 0; j<w.world[0].length; j++) {
				area[i][j] = new PathFindingLand();
				area[i][j].setIsWalkable(w.world[i][j].isWalkable());
				area[i][j].setMovemetnCostModifier(w.world[i][j].getMovementModifier());
			}
		}
		//area[9][6].movementCost=1000;
		//initializeLandMovementCost(w);
	}
	
	
	public void generatePath(Coordinates cur, int costSoFar) {
		boolean test=false;
		//try {
			test = cur.equals(targetCoords);
		//} catch (Exception e) {
			//System.out.println("Current "+cur+" and target "+ targetCoords+".");
		//}
		if (test) {
			// Runs if you have reached your destination.
			if (costSoFar != 0) {
				fillPath(cur, startCoords, targetCoords);
			}
		} else {
			//TODO change code to avoid "un-walkable" squares.
			if (costSoFar == 0) {
				area[cur.x][cur.y].setIsClosedList(true);
				System.out.print("Starter " + targetCoords);
				startCoords = new Coordinates(cur);
				area[cur.x][cur.y].setHeuristic(cur, targetCoords);
				// TODO set cost-modifier on each land here to get characters walking around.
				//area[cur.x][cur.y].setIsClosedList(true);
			}
			
			Integer total = 0;
			//Calculate the total cost F for each of the surrounding regions.
			// Iterate through every direction from north (1) to southwest (8)
			for (int i = 1; i < 9; i++) {
				tmp=cur.directionalCoord(i);
				if (tmp.x>0 && tmp.y>0 && tmp.x<area.length && tmp.y<area.length) {
					//System.out.println(tmp.toString());// area: " + area[tmp.x][tmp.y]);
					area[tmp.x][tmp.y].setHeuristic(tmp, targetCoords);
					// calculate total move distance.
					// costSoFar increases by 10 if in a cardinal direction and 14 if a diagonal.
					total = calculateTotalF(tmp, costSoFar, !Direction.isCardinalDirection(i));
					if( !area[tmp.x][tmp.y].getIsClosedList() && (!area[tmp.x][tmp.y].getIsOpenList() || total < area[tmp.x][tmp.y].totalCost) ) {
						area[tmp.x][tmp.y].totalCost = total;
						area[tmp.x][tmp.y].setIsOpenList(true);
						area[tmp.x][tmp.y].setDirection(i);//D.invertDirection(i));
						if (costSoFar==0) {
							System.out.println("CHECKME: Coordinates: "+tmp.toString() + " Direction: "+i + " Cost:"+total);
						}
					}
				}
			}
			
			// Find the lowest F on the open list of Fs.
			Coordinates next = null;
			for (int j = 0; j<area[0].length; j++) {
				for (int i = 0; i<area.length; i++) {
					//if null
					
					if ( i>0 && j>0 && i<area.length && j<area.length &&
							next == null && area[i][j].getIsOpenList() && !area[i][j].getIsClosedList()) {
						next = new Coordinates(i,j);
					} else if (next != null 
							&& (area[i][j].getIsOpenList() && !area[i][j].getIsClosedList()) 
							&& area[i][j].getIsWalkable()
							&& area[i][j].totalCost < area[next.x][next.y].totalCost) {
						next.set(i, j);
						if (costSoFar==0)System.out.println("CHECKME: next: " + next.toString() + " direction: "+area[i][j].getDirection());
					}
				}
			}
			//System.out.println("next" + next + " " + targetCoords + " " + area.length + " ");
			try {
				area[next.x][next.y].setIsClosedList(true);
			} catch (Exception e) {
				System.out.println("Next (c): " + next);
				System.out.println("Target (c): " + targetCoords);
				System.out.println("Area size is " + area.length + ".");
			}
			
			// Recursive call of generatePath() to finish making a path.
			generatePath(next, area[next.x][next.y].totalCost);
		}
	}
	void fillPath(Coordinates c, Coordinates start, Coordinates end) {
		// The tempPath will be an inverted version of the final path.
        ArrayList<Integer> tempPath = new ArrayList<Integer>();
        System.out.println("Coordinates in space! ");
        while (!c.equals(startCoords)) {
            tempPath.add(area[c.x][c.y].getDirection());
            System.out.print("Head " + Stringify.direction(area[c.x][c.y].getDirection()) + " at " + c + ". ");
            c.setDirection(Direction.invert(area[c.x][c.y].getDirection()));
        }
        //Final add to add the start coordinates in?
        //tempPath.add(area[c.x][c.y].getDirection());
        
        // Clear path and invert tempPath into it.
        path = new ArrayList<Integer>();
        for (int i=tempPath.size()-1; i>=0; i--) {
            path.add(tempPath.get(i));
        }
        System.out.println("Going from " + start + " to " + end + "."
        		+ "\nHere is the path to take: " + path);
	}
	
	/**
	 * Used to calculate the total cost of movement (F) of a given tile.
	 */
	private int calculateTotalF(Coordinates c, int costSoFar, boolean isDiagonal) {
		///TODO actually calculate final F
        //TODO Sorely incomplete
        // Calculated using F (total cost) = G (cost to move) + H (estimated cost left to move)
        // G is calculated by adding the cost of moving so far to the terrain difficulty (x diagonal if needed).
        // H is precalculated before now and stored as heuristic.
        // F (total cost) = CostSoFar + (costModifier*diagonalvsstraight) + Heuristic.
        
        //total = calculateTotalF(tmp, costSoFar+10+(Direction.isCardinalDirection(i)?0:4));
        int f;
        int g;
        int h;
        int newCost;
        newCost = (int)(  (isDiagonal?14:10) * area[c.x][c.y].movementCostModifier  );
        h = area[c.x][c.y].heuristicCost;
        g = costSoFar + newCost;
        f = g+h;
        return f;
        //return (int) (costSoFar*area[c.x][c.y].movementCostModifier + area[c.x][c.y].heuristicCost);
	}
	
	public String printTotalCostWorld() {
		StringBuffer s = new StringBuffer();
		s.append("Total Cost World\n");
		for (int j = 0; j<area.length; j++) {
			for (int i = 0; i<area[0].length; i++) {
				s.append(area[i][j].totalCost + " ");
			}
			s.append("\n");
		}
		System.out.print(s.toString());
		return s.toString();
	}
	public String printMovementWorld() {
		StringBuffer s = new StringBuffer();
		s.append("Movement Cost World\n");
		for (int j = 0; j<area.length; j++) {
			for (int i = 0; i<area[0].length; i++) {
				s.append(area[i][j].movementCostModifier + " ");
			}
			s.append("\n");
		}
		System.out.print(s.toString());
		return s.toString();
	}
	public String printHeuristicWorld() {
		StringBuffer s = new StringBuffer();
		s.append("Heuristic Cost World\n");
		for (int j = 0; j<area.length; j++) {
			for (int i = 0; i<area[0].length; i++) {
				s.append(area[i][j].heuristicCost<10?"0":"");
				s.append(area[i][j].heuristicCost + " ");
			}
			s.append("\n");
		}
		System.out.print(s.toString());
		return s.toString();
	}
	public String printDirectionWorld() {
		StringBuffer s = new StringBuffer();
		s.append("Direction World\n");
		for (int j = 0; j<area.length; j++) {
			for (int i = 0; i<area[0].length; i++) {
				//s.append(D.directionToArrow(area[i][j].direction) + " ");
				s.append( ((area[i][j].getDirection()==0)?".":area[i][j].getDirection()) + " " );
			}
			s.append("\n");
		}
		System.out.print(s.toString());
		return s.toString();
	}
	public String printListWorld() {
		StringBuffer s = new StringBuffer();
		s.append("List World? (Which means... what again?)\n");
		for (int j = 0; j<area.length; j++) {
			for (int i = 0; i<area[0].length; i++) {
				s.append( (area[i][j].getIsOpenList() ? (area[i][j].getIsClosedList() ? "x" : "o") : "-") + " ");
			}
			s.append("\n");
		}
		System.out.print(s.toString());
		return s.toString();
	}
	
	
	public Coordinates getTargetCoords() {
		return targetCoords;
	}
	public void setTargetCoords(Coordinates coord) {
		targetCoords.set(coord);
	}	
}
