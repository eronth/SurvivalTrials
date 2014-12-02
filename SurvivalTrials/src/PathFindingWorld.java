import java.util.ArrayList;

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

	// wd is the world dimension, used to ensure the pathfinding world is of appropriate size.
	PathFindingWorld(World w) {
		area=new PathFindingLand[w.world.length][w.world[0].length];
		for (int i = 0; i<w.world.length; i++) {
			for (int j = 0; j<w.world[0].length; j++) {
				area[i][j] = new PathFindingLand();
				area[i][j].setIsWalkable(D.isWalkable(w, i, j));
			}
		}
		area[9][6].movementCost=1000;
		//initializeLandMovementCost(w);
	}
	void generatePath(Coordinates cur, int costSoFar) {
		
		//TODO clean up? The following only occurs on the first execution.
		if (costSoFar == 0) {
			startCoords = new Coordinates(cur);
			area[cur.x][cur.y].setHeuristic(cur, targetCoords);
			//area[cur.x][cur.y].setIsClosedList(true);
		}
		//TODO clean up?
		
		
		Coordinates tmp = new Coordinates();
		Integer total = 0;
		//Calculate the total cost F for each of the surrounding regions.
		// north
		
		// Iterate through every direction from north (1) to southwest (8)
		for (int i = 1; i < 9; i++) {
			tmp=cur.directionalCoord(i);
			//System.out.println(tmp.toString());// area: " + area[tmp.x][tmp.y]);
			area[tmp.x][tmp.y].setHeuristic(tmp, targetCoords);
			// calculate total move distance.
			// costSoFar increases by 10 if in a cardinal direction and 14 if a diagonal.
			total = calculateTotalF(tmp, costSoFar+10+(D.isCardinalDirection(i)?0:4));
			if(!area[tmp.x][tmp.y].getIsClosedList() && (!area[tmp.x][tmp.y].getIsOpenList() || total < area[tmp.x][tmp.y].totalCost) ) {
				area[tmp.x][tmp.y].totalCost = total;
				area[tmp.x][tmp.y].setIsOpenList(true);
				area[tmp.x][tmp.y].setDirection(i);//D.invertDirection(i));
				if (costSoFar==0){
					System.out.println("CHECKME: Coordinates: "+tmp.toString() + "Direction: "+i);
				}
			}
		}
			
		
		// Find the lowest F on the open list of Fs.
		Coordinates next = null;
		for (int j = 0; j<area[0].length; j++) {
			for (int i = 0; i<area.length; i++) {
				//if null
				
				if(next == null && area[i][j].getIsOpenList() && !area[i][j].getIsClosedList()) {
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
		//System.out.print(next.toString());
		area[next.x][next.y].setIsClosedList(true);
		
		// Recursive call of generatePath() to finish making a path.
		if (!next.equals(targetCoords)) {
			generatePath(next, area[next.x][next.y].totalCost);
		} else {
			fillPath(next);
		}
	}
	void fillPath(Coordinates c) {
		ArrayList<Integer> tempPath = new ArrayList<Integer>(); 
		int direction = 0;
		while (!c.equals(startCoords)) {
			direction = area[c.x][c.y].getDirection();
			tempPath.add(direction);
			c.setDirection(D.invertDirection(direction));// = c.directionalCoord(direction);
			//System.out.println(tempPath);
			//System.out.println("direction: " + direction + " coordinates: " + c.toString());
			//TODO complete the addition system.
		}
		tempPath.add(area[c.x][c.y].getDirection());
		path = new ArrayList<Integer>();
		//TODO remove permanently int numSteps = tempPath.size();
		for (int i=tempPath.size()-1; i>0; i--) {
			path.add(tempPath.get(i));//D.invertDirection(tempPath.get(i)));
		}
	}
	
	/**
	 * Used to calculate the total cost of movement (F) of a given tile.
	 */
	private int calculateTotalF(Coordinates c, int costSoFar) {
		//TODO actually calculate final F
		//TODO use the formula F = G + H;
		//TODO Sorely incomplete
		return costSoFar*area[c.x][c.y].movementCost + area[c.x][c.y].heuristicCost;
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
				s.append(area[i][j].movementCost + " ");
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