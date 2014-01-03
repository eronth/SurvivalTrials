
public class Creature {
	String firstName="Phillip";
	String lastName="Fry";
	public int creatureType=0;
	public int xPos=-1,yPos=-1;
	int actionChoice=0;
	int actionCountdown=0;
	// Base Attributes
	int baseEndurance;
	int baseStrength;
	int baseIntelligence;
	// Base Physical Status
	int satiation=100;	// This is a satiation meter, low numbers means incredible hunger while high numbers mean no hunger.
	int quench=100;		// This is a quench meter, low numbers means incredible thirst while high numbers mean no thirst.
	int energy_lt=100;	// This is long-term energy, used to determine how tired a person has become over the course of a day.
	int energy_st=100;	// This is short-term energy, used for short sprints and moving over max encumberance. It is burned and recharged much faster. 
	int health=100;
	int weight=70;		// Weight is done in kg
	// Base Mind Status
	int sanity=100;
	int humanity=100;
	int galvany=100;
	
	
	Creature(){
		creatureType=0;
	}
	Creature(String fn, String ln,int type, int weight, int enduranceStart, int strengthStart, int intelligenceStart, int sanityStart, int humanityStart, int galvanyStart){
		firstName=fn;
		lastName=ln;
		creatureType=type;
		baseEndurance=enduranceStart;
		baseStrength=strengthStart;
		baseIntelligence = intelligenceStart;
		sanity=sanityStart;
		humanity=humanityStart;
		galvany=galvanyStart;
	}
	// This is the switch case for which action the creature will be performing this turn.
	void action(World w){
		switch (actionChoice){
			case 0:
				break;
			case 1: 
				initWalk(w,0,D.NORTH);
				break;
			case 2:	
				walk(w, 0, D.NORTH);
				break;
				
			case 3: 
				initWalk(w,0,D.EAST);
				break;
			case 4:	
				walk(w, D.EAST, 0);
				break;
				
			case 5:
				initWalk(w, D.WEST, 0);
				break;
			case 6:
				walk(w,D.WEST,0);
				break;
				
			case 7: 
				initWalk(w, 0, D.SOUTH);
				break;
			case 8: 
				walk(w,0,D.SOUTH);
				break;
			default:
				break;
		}
	}
	// Walking prep function. Sets the countdown to 10
	void initWalk(World w, int xDirection, int yDirection){
		actionChoice=actionChoice+1;
		actionCountdown=10;
		walk(w,xDirection,yDirection);
	}
	// Walk function. Moves creature in a direction given by xDirection and yDirection 
	void walk(World w, int xDirection, int yDirection){
		if(actionCountdown<=0){
			int xmod=0,ymod=0;
			if(xDirection==D.WEST){
				xmod=-1;
			}else if(xDirection==D.EAST){
				xmod=1;
			}
			if(yDirection==D.NORTH){
				ymod=-1;
			}else if(yDirection==D.SOUTH){
				ymod=1;
			}
			int x=xPos+xmod;
			int y=yPos+ymod;
			if ((w.world[x][y].creature==null||w.world[x][y].creature.creatureType==0) && w.world[x][y].landType!=D.SALTWATER && w.world[x][y].landType!=D.WATER){
				w.placeCreature(this,x,y);
			}
			actionCountdown=10;
		}else{
			actionCountdown-=(getSpeed()+9)/10;
		}
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
	int getWeight(){
		int ret=0;
		ret = weight;
		return ret;
	}
	
}
