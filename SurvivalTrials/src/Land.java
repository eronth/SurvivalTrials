
public class Land {
	// a lot of these ints will likely be changed to something else, such as Structure or Item data types
	public Structure structure = null;// structures are walls, doors, firepits, chairs, trees, large rocks, etc. Stuff that people will have trouble walking around.
	public int item[] = new int[4];
	public Creature creature = null;
	public int landType=0; // starts typeless
	public double movementMod=1;
	
	
	Land(int _landType){
		switch(_landType){
		case 0:
			initLand(_landType,1);
			break;
		case D.WATER:
			initLand(_landType,1);
			break;
		case D.SAND:
			initLand(_landType,1);
			break;
		case D.SALTWATER:
			initLand(_landType,.6);
			break;
		case D.DIRT:
			initLand(_landType,1);
			break;
		case D.GRASS:
			initLand(_landType,1);
			break;
		case D.STONE:
			initLand(_landType,1);
			break;
		case D.SNOW:
			initLand(_landType,.7);
			break;
		case D.ICE:
			initLand(_landType,.5);
			break;
		case D.DESERT:
			initLand(_landType,.8);
			break;
		default:
			initLand(D.SALTWATER,.5);
			break;
		}
	}
	// =Initializes land with the type and how it affects walking/running speed. Movement mod is 1 if movement is not effected.
	void initLand(int _landType, double _movementMod){
		landType=_landType;
		movementMod=_movementMod;
	}
}
