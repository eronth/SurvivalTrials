
public class Land {
	// a lot of these ints will likely be changed to something else, such as Structure or Item data types
	public Structure structure = null; // Structures are walls, doors, firepits, chairs, trees, large rocks, etc. Stuff that people will have trouble walking around.
	public int item[] = new int[4];
	public Item items[] = new Item[4]; // Second array to keep track of special items
	public Creature creature = null;
	public int landType=0; // starts typeless
	
	Land(){
		
	}
}
