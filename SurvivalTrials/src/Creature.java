
public class Creature {
	String firstName="Phillip";
	String lastName="Fry";
	public int creatureType=0;
	public Coordinates position = new Coordinates();
	public Coordinates target = new Coordinates();
	int actionChoice=0;
	int actionCountdown=0;
	// Base Attributes
	int baseEndurance;		// How much abuse you can take.
	int baseStrength;		// How much abuse you can deal.
	int baseIntelligence;	// How good you are at tinkering, and magic.
	// Base Physical Status
	int satiation=100;		// This is a satiation meter, low numbers means incredible hunger while high numbers mean no hunger.
	int quench=100;			// This is a quench meter, low numbers means incredible thirst while high numbers mean no thirst.
	int energy_lt=100;		// This is long-term energy, used to determine how tired a person has become over the course of a day.
	int energy_st=100;		// This is short-term energy, used for short sprints and moving over max encumberance. It is burned and recharged much faster. 
	int health=100;
	double weight=70;		// Weight is done in kg
	// Base Mind Status
	int sanity=100;			// How much gruesome and horrid things you've seen. Affects your ability to make decisions.
	int humanity=100;		// How opposed to murder you are.
	int galvany=100;		// How happy and optimistic you are.
	PathFindingWorld pathfind;
	int pathSet=0;
	// Useful pathfinding data structures
	
	
	
	Creature(){
		creatureType=0;
	}
	Creature(World w,String fn,String ln,int type, double weight, int enduranceStart, int strengthStart, int intelligenceStart) {
		
	}
	Creature(World w,String fn,String ln,int type, double weight, int enduranceStart, int strengthStart, int intelligenceStart, int sanityStart, int humanityStart, int galvanyStart) {
		firstName=fn;
		lastName=ln;
		creatureType=type;
		baseEndurance=enduranceStart;
		baseStrength=strengthStart;
		baseIntelligence = intelligenceStart;
		sanity=sanityStart;
		humanity=humanityStart;
		galvany=galvanyStart;
		pathfind=new PathFindingWorld(w);
	}
	// This is the switch case for which action the creature will be performing this turn.
	void action(World w){
		switch (actionChoice) {
			case 0:
				break;
			case 1:
				initWalk(w,0);
				break;
		}
	}
	// Walking prep function. Sets the countdown to 10
	void initWalk(World w, int _direction){
		actionChoice=actionChoice+1;
		actionCountdown=10;
		walk(w,_direction);
	}
	// Walk function. Moves creature in a direction given by xDirection and yDirection
	// TODO rewrite code.
	boolean walk(World w, int direction){
		boolean retval = false;
		if(actionCountdown<=0){
			Coordinates coord=new Coordinates();
			coord.x=position.x+D.XMOD(direction);
			coord.y=position.y+D.YMOD(direction);
			if ((w.world[coord.x][coord.y].creature==null||w.world[coord.x][coord.y].creature.creatureType==0) && w.world[coord.x][coord.y].landType!=D.SALTWATER && w.world[coord.x][coord.y].landType!=D.WATER){
				w.placeCreature(this,coord);
			}
			actionCountdown=10;
			pathSet++;
			retval = true;
		}else{
			actionCountdown-=((w.world[this.position.x][this.position.y].movementMod*getSpeed())+9)/10;
			retval = false;
		}
		return retval;
	}
	int getSpeed(){
		int ret=0;
		ret = (int)( ((getStrength()-(getWeight()-70))*(getEnergy_lt())/100) ); // Move at a speed based on weight and energy
		return ret;
	}
	int getStrength(){
		int ret=0;
		ret=baseStrength;
		return ret;
	}
	int getEnergy_lt(){
		return energy_lt;
	}
	double getWeight(){
		double ret=0;
		ret = weight;
		return ret;
	}
	
	Coordinates getPosition() {
		return position;
	}
	void setPosition(Coordinates c) {
		position = c;
	}
}
