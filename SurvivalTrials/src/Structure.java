
public class Structure {
	public Coordinates position = new Coordinates();
	int structureType = 0; // Type of structure (e.g. Wall, Door, Fire pit, tree, etc)
	int resourceType = 0; // Resources given by structure. In cases of walls and doors, Resources are typically the material used to build it.
	int materialType = 0; // Material the structure is made of. Used to determine what class of weapons is strong or weak against it.
	int maxHealth;
	int currHealth;
	int resourceCount;
	int properTool;		// Used to determine what tool or damage type is highly effective against this structure.
	
	Structure(){	
	}
	// Initialize structure based on given _structureType. In some cases, _materialType is used to deterimined what it's made of. 
	Structure(int _structureType, int _materialType){
		switch(_structureType){
		case D.TREE:
			initTree();
			break;
		case D.FRUITTREE:
			initFruittree();
			break;
		case D.CACTUS:
			initCactus();
			break;
		case D.BOULDER:
			initBoulder();
			break;
		case D.WALL:
			initWall(_materialType);
			break;
		case D.DOOR:
			initDoor(_materialType);
			break;
		case D.FIREPIT:
			initFirepit();
			break;
		}
	}
	// The following is a series of initialization functions that lead into the generic one.
	private void initTree() {
		initGeneric(D.TREE,60,D.RES_WOOD,3,D.MAT_WOOD);
	}
	private void initFruittree() {
		initGeneric(D.FRUITTREE,60,D.RES_FRUIT,D.RAND.nextInt(3)+3,D.MAT_WOOD);
	}
	private void initCactus() {
		initGeneric(D.CACTUS,100,D.RES_CACTIPODE,5,D.MAT_CACTIPODE);
	}
	private void initBoulder() {
		initGeneric(D.BOULDER,100,D.RES_STONE,8,D.MAT_STONE);
	}
	private void initWall(int _materialType) {
		initGeneric(D.WALL,100,_materialType,2,_materialType);
	}
	private void initDoor(int _materialType) {
		initGeneric(D.DOOR,70,_materialType,2,_materialType);
	}
	private void initFirepit() {
		initGeneric(D.FIREPIT,20,D.RES_WOOD,0,D.STONE);		
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
