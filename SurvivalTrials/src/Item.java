
public class Item {
	public int itemType=0; // starts typeless
	public int xPos=-1,yPos=-1;

	int capacity=0; // Total room for other items.
	Item contents[] = new Item[0]; // Used for bags, chests, or items with capacity.
	
	//Base attributes
	String name="";				// Item name/identifying text.
	String description="";		// Used for flavor text or unique items.
	int weight=0;				// Weight is done in kg.
	int volume=0;				// Might later remove.
	int materialType=0; 		// Material the item is made of. Used to determine what it is effective against or how strong it is.
	
	//Advanced attributes
	boolean resource=false; 	// Move to subclass?
	boolean harvestable=false; 	// Same as above
	boolean held=false; 		// Is item held or placed on the ground.
	int quantity=0;				// For stacks of items, mostly resources.
	
	//Weapon attributes
	int damage=0;
	int range=0;
	int speed=0;
	
	Item(){
	}
	
	// Generate default items
	Item(int _itemType){
		switch(_itemType){
			case D.ITM_RES_WOOD:
			case D.ITM_RES_STONE:
			case D.ITM_RES_FRUIT:
			case D.ITM_RES_CACTIPODE:
				initResource(_itemType);
				break;
			case D.NONE:
				initUnique();
				break;
		}
	}
	
	//TODO: migrate to switch case initilization
	Item(int type, String _name, String _description, int _capacity){ // Constructor for bag, chest, or other item with capacity.
		itemType=type;
		name=_name;
		description=_description;
		capacity=_capacity;
		contents=new Item[capacity];
	}
	
	// Working on initialization similar to structure.
	private void initUnique(){
		initGeneric(0,"","",0,0,0);
	}
	private void initResource(int _itemType){
		initGeneric(_itemType,"Test","used for building things",0,0,1);
		resource=true;
		harvestable=true;
	}
	private void initGeneric(int type, String _name, String _description, int _capacity, int _materialType, int _quantity){
		itemType=type;
		name=_name;
		description=_description;
		capacity=_capacity;
		contents=new Item[capacity];
		materialType=_materialType;
		quantity=_quantity;
	}
}