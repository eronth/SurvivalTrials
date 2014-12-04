package com.mtank.world;

import com.mtank.constants.TypeValue;
import com.mtank.creature.Creature;
import com.mtank.item.Item;
import com.mtank.structure.Structure;


public class Land {
	// a lot of these ints will likely be changed to something else, such as Structure or Item data types
	public Structure structure = null; // Structures are walls, doors, firepits, chairs, trees, large rocks, etc. Stuff that people will have trouble walking around.
	public int item[] = new int[4];
	public Item items[] = new Item[4]; // Second array to keep track of special items
	public Creature creature = null;
	public int landType=0; // starts typeless
	public double movementMod=1;
	
	
	Land(int _landType){
		switch(_landType){
		case 0:
			initLand(_landType,1);
			break;
		case TypeValue.Land.WATER:
			initLand(_landType,1);
			break;
		case TypeValue.Land.SAND:
			initLand(_landType,1);
			break;
		case TypeValue.Land.SALTWATER:
			initLand(_landType,.6);
			break;
		case TypeValue.Land.DIRT:
			initLand(_landType,1);
			break;
		case TypeValue.Land.GRASS:
			initLand(_landType,1);
			break;
		case TypeValue.Land.STONE:
			initLand(_landType,1);
			break;
		case TypeValue.Land.SNOW:
			initLand(_landType,.7);
			break;
		case TypeValue.Land.ICE:
			initLand(_landType,.5);
			break;
		case TypeValue.Land.DESERT:
			initLand(_landType,.8);
			break;
		default:
			initLand(TypeValue.Land.SALTWATER,.5);
			break;
		}
	}
	// =Initializes land with the type and how it affects walking/running speed. Movement mod is 1 if movement is not effected.
	void initLand(int _landType, double _movementMod) {
		landType=_landType;
		movementMod=_movementMod;
	}
	

	public boolean isWalkable() {
		return (// Returns walkable if no creatures is in the way.
				(this.creature==null||this.creature.creatureType==0) && 
				// Returns walkable if no structures obstruct movement.
				(this.structure==null||this.structure.structureType==0) 
				);
	}
}
