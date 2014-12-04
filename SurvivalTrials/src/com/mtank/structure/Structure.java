package com.mtank.structure;
import com.mtank.constants.TypeValue;
import com.mtank.game.Coordinates;
import com.mtank.game.Game;


public class Structure {
	public Coordinates position = new Coordinates();
	public int structureType = 0; // Type of structure (e.g. Wall, Door, Fire pit, tree, etc)
	int resourceType = 0; // Resources given by structure. In cases of walls and doors, Resources are typically the material used to build it.
	int materialType = 0; // Material the structure is made of. Used to determine what class of weapons is strong or weak against it.
	int maxHealth;
	int currHealth;
	public int resourceCount;
	int properTool;		// Used to determine what tool or damage type is highly effective against this structure.
	
	Structure(){	
	}
	// Initialize structure based on given _structureType. In some cases, _materialType is used to deterimined what it's made of. 
	public Structure(int _structureType, int _materialType){
		switch(_structureType){
		case TypeValue.Structure.TREE:
			initTree();
			break;
		case TypeValue.Structure.FRUITTREE:
			initFruittree();
			break;
		case TypeValue.Structure.CACTUS:
			initCactus();
			break;
		case TypeValue.Structure.BOULDER:
			initBoulder();
			break;
		case TypeValue.Structure.WALL:
			initWall(_materialType);
			break;
		case TypeValue.Structure.DOOR:
			initDoor(_materialType);
			break;
		case TypeValue.Structure.FIREPIT:
			initFirepit();
			break;
		}
	}
	Structure(int structureType, int materialType, Coordinates c) {
		this.structureType=structureType;
		this.materialType=materialType;
		this.position.set(c);
	}
	
	
	// The following is a series of initialization functions that lead into the generic one.
	private void initTree() {
		initGeneric(TypeValue.Structure.TREE,60,TypeValue.Resource.WOOD,3,TypeValue.Material.WOOD);
	}
	private void initFruittree() {
		initGeneric(TypeValue.Structure.FRUITTREE,60,TypeValue.Resource.FRUIT,Game.RAND.nextInt(3)+3,TypeValue.Material.WOOD);
	}
	private void initCactus() {
		initGeneric(TypeValue.Structure.CACTUS,100,TypeValue.Resource.CACTIPODE,5,TypeValue.Material.CACTIPODE);
	}
	private void initBoulder() {
		initGeneric(TypeValue.Structure.BOULDER,100,TypeValue.Resource.STONE,8,TypeValue.Material.STONE);
	}
	private void initWall(int _materialType) {
		initGeneric(TypeValue.Structure.WALL,100,_materialType,2,_materialType);
	}
	private void initDoor(int _materialType) {
		initGeneric(TypeValue.Structure.DOOR,70,_materialType,2,_materialType);
	}
	private void initFirepit() {
		initGeneric(TypeValue.Structure.FIREPIT,20,TypeValue.Resource.WOOD,0,TypeValue.Material.STONE);		
	}
	// Used to ensure all initializations are on par with one another.
	private void initGeneric(int _structureType, int _maxHealth, int _resourceType, int _resourceCount, int _materialType){
		structureType = _structureType;
		resourceType = _resourceType;
		materialType = _materialType;
		maxHealth = currHealth = _maxHealth;
		resourceCount = _resourceCount;
		// int properTool; currently removed
	}

}
