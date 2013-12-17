
public class Land {
	// a lot of these ints will likely be changed to something else, such as Structure or Item data types
	public int structure=0;// structures are walls, doors, firepits, chairs, trees, large rocks, etc. Stuff that people will have trouble walking around.
	public int item[] = new int[4];
	public Creature creature=new Creature();
	public int landType=0; // starts typeless
	
	void Land(){
	}
}
