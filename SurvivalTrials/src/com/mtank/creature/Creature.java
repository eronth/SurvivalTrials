package com.mtank.creature;

import com.mtank.ai.PathFindingWorld;
import com.mtank.constants.Action;
import com.mtank.game.Coordinates;
import com.mtank.game.Game;
import com.mtank.game.mainClass;
import com.mtank.world.World;


public class Creature {
	String firstName="Phillip";
	String lastName="Fry";
	public int creatureType=0;
	public Coordinates position = new Coordinates();
	private int facingDirection = 1;
	private int actionChoice=0;
	private int actionCountdownTimer=0;
	// Base Attributes
	private int baseEndurance;		// How much abuse you can take.
	private int baseStrength;		// How much abuse you can deal.
	private int baseIntelligence;	// How good you are at tinkering, and magic.
	// Base Physical Status
	private int satiation=100;		// This is a satiation meter, low numbers means incredible hunger while high numbers mean no hunger.
	private int quench=100;			// This is a quench meter, low numbers means incredible thirst while high numbers mean no thirst.
	private int energy_lt=100;		// This is long-term energy, used to determine how tired a person has become over the course of a day.
	private int energy_st=100;		// This is short-term energy, used for short sprints and moving over max encumberance. It is burned and recharged much faster. 
	private int health=100;
	private int maxHealth=100;
	private double weight=70;		// Weight is done in kg
	// Base Mind Status
	private int sanity=100;			// How much gruesome and horrid things you've seen. Affects your ability to make decisions.
	private int humanity=100;		// How opposed to murder you are.
	private int galvany=100;		// How happy and optimistic you are.
	private PathFindingWorld pathfind;
	private boolean pathSet=false;
	// Useful pathfinding data structures
	
	
	/**
	 * Initialize a blank creature object.
	 */
	Creature() {
	}
	
	/**
	 * Initialize a creature with a first name of <code>fn</code>, last name of <code>ln</code> and type of <code>type</code>. All other stats are also initialized here.
	 */
	public Creature(String fn,String ln,int type, double weight, int enduranceStart, int strengthStart, int intelligenceStart, int sanityStart, int humanityStart, int galvanyStart) {
		firstName=fn;
		lastName=ln;
		creatureType=type;
		baseEndurance=enduranceStart;
		baseStrength=strengthStart;
		baseIntelligence = intelligenceStart;
		sanity=sanityStart;
		humanity=humanityStart;
		galvany=galvanyStart;
		pathfind=new PathFindingWorld(mainClass.island);
	}
	
	/**
	 * Finds the ideal path between creatures current location and <code>target</code>.
	 */
	public void generatePath(Coordinates target) {
		pathfind.targetCoords = new Coordinates(target);
		pathfind.generatePath(position, 0);
	}
	
	// This is the switch case for which action the creature will be performing this turn.
	public void action(World w){
		switch (actionChoice) {
			case Action.IDLE:
				break;
			case Action.WANDER:
				
				break;
			//case 1:
			//	initWalk(w,0);
			//	break;
			default:
				break;
		}
	}
	
	
	// XXX ================================================
	/**
	 * Allows a creature to wander without direction.
	 */
	public void wander() {
		/*if (actionCountdownTimer == 0) {
			actionCountdownTimer = 10;
		} else {
			actionCountdownTimer--;
			if(actionCountdownTimer == 0) {
				//TODO perform action
			}
		}*/
		// TODO STEPS:
		// TODO Determine direction (based on current direction?)
		int random = Game.RAND.nextInt()%100;
		if (random>50) {
			//same direction
		} else if (random>25) {
			//slightly angled direction
		} else if (random>5) {
			//90 degree difference
		} else {
			//nearly backwards
		}
		// TODO Set countdown timer. Should be based on speed/diagonal?/terrain difficulty.
		// TODO if countdown is set, count down
		// TODO if countdown hits 0, act!
	}
	
	// XXX ================================================
	
	
	// Walking prep function. Sets the countdown to 10
	void initWalk(World w, int _direction){
		actionChoice=actionChoice+1;
		actionCountdownTimer=10;
		walk(w,_direction);
	}
	// Walk function. Moves creature in a direction given by xDirection and yDirection
	// TODO rewrite code.
	boolean walk(World w, int direction){
		// TODO write an actual walk function.
		return true;
	}
	
	
	
	
	/**
	 * Returns the speed of the creature based on many factors, including their strength, weight, energy.
	 * @return
	 */
	int getSpeed(){
		return (int)( ((getStrength()-(getWeight()-70))*(getLongTermEnergy())/100) ); // Move at a speed based on weight and energy
	}
	
	/**
	 * Return creature's endurance.
	 */
	int getEndourance() {
		return getBaseEndurance();//*modifier + increase
	}
	/**
	 * Returns creature's base endurance value.
	 */
	int getBaseEndurance() {
		return this.baseEndurance;
	}
	/**
	 * Sets creature's bas4e endurance value to baseEndurance.
	 */
	void setBaseEndurance(int baseEndurance) {
		this.baseEndurance = baseEndurance;
	}
	
	/**
	 * Return creature's strength.
	 */
	int getStrength() {
		return getStrength();//*modifier +bonus
	}
	/**
	 * Returns creature's base strength value.
	 */
	int getBaseStrength() {
		return this.baseStrength;
	}
	/**
	 * Sets creatures base strength value to baseStrength.
	 */
	void setBaseStrength(int baseStrength) {
		this.baseStrength=baseStrength;
	}
	
	/**
	 * Return creature's intelligence.
	 */
	int getIntelligence() {
		return getBaseIntelligence();//*modifier +bonus
	}
	/**
	 * Returns creature's base intelligence value.
	 */
	int getBaseIntelligence() {
		return this.baseIntelligence;
	}
	/**
	 * Sets creatures base intelligence to baseIntelligence.
	 * @param baseIntelligence
	 */
	void setBaseIntelligence(int baseIntelligence) {
		this.baseIntelligence=baseIntelligence;
	}
	
	/**
	 * Returns the satiation level of a creature, or their lack of hunger.
	 * @return
	 */
	int getSatiationLevel() {
		return this.satiation;
	}
	/**
	 * Returns the hunger level of a creature, which is the inverse of the satiation level.
	 * @return
	 */
	int getHungerLevel() {
		return (100-this.satiation);
	}
	/**
	 * Sets the satiation level to satiation.
	 */
	void setSatiationLevel(int satiation) {
		this.satiation=(satiation>100)?100:(satiation<0)?0:satiation;
	}
	/**
	 * Sets the satiation level to the inverse of hunger.
	 * @param hunger
	 */
	void setHungerLevel(int hunger) {
		this.satiation=(hunger<0)?100:(hunger>100)?100:(100-hunger);
	}
	/**
	 * Modifies satiation by satiationChange. Positive numbers increase satiation and negative numbers decrease.
	 * @return
	 */
	void modifySatiationLevel(int satiationChange) {
		this.satiation+=satiationChange;
		if(this.satiation > 100) {
			satiation=100;
		} else if (this.satiation < 0) {
			satiation=0;
		}
	}
	/**
	 * Modifies satiation by hungerChange. Positive numbers increase hunger and decrease satiation, negative numbers do the inverse.
	 * @param hungerChange
	 */
	void modifyHungerLevel(int hungerChange) {
		this.satiation-=hungerChange;
		if(this.satiation > 100) {
			satiation=100;
		} else if (this.satiation < 0) {
			satiation=0;
		}
	}
	
	/**
	 * Returns the the quench level of a creature, or their lack of thirst. 
	 * @return
	 */
	int getQuenchLevel() {
		return this.quench;
	}
	/**
	 * Get thirst level of creature, which is the inverse of thirst level.
	 * The higher the thirst, the thirstier the creature.
	 * @return
	 */
	int getThirstlevel() {
		return (100-this.quench);
	}
	/**
	 * sSets the quench level to quench.
	 * @return
	 */
	void setQuench(int quench) {
		this.quench=(quench>100)?100:(quench<0)?0:quench;
	}
	/**
	 * Sets the quench level to the inverse of thirst.
	 * @return
	 */
	void setThirst(int thirst) {
		this.quench = (thirst<0)?100:(thirst>100)?100:(100-thirst);
	}
	/**
	 * Modifies quench by quenchChange. Positive numbers increase quench and negative numbers decrease.
	 * @return
	 */
	void modifyQuenchLevel(int quenchChange) {
		this.quench+=quenchChange;
		if(this.quench > 100) {
			quench=100;
		} else if (this.quench < 0) {
			quench=0;
		}
	}
	/**
	 * Modifies quench by thirstChange. Positive numbers increase thirst and decrease quench, negative numbers do the inverse.
	 * @return
	 */
	void modifyThirstLevel(int thirstChange) {
		this.quench-=thirstChange;
		if(this.quench > 100) {
			quench=100;
		} else if (this.quench < 0) {
			quench=0;
		}
	}
	
	/**
	 * Returns the creature's long term energy.
	 * @return
	 */
	int getLongTermEnergy() {
		return this.energy_lt;
	}
	/**
	 * Sets the creature's long term energy to <code>longTermEnergy</code>.
	 * @param longTermEnergy
	 */
	void setLongTermEnergy(int longTermEnergy) {
		this.energy_lt=(longTermEnergy>100)?100:(longTermEnergy<0)?0:longTermEnergy;
	}
	/**
	 * Modifies the long term energy energy by <code>longTermEnergyChange</code>. Positive values increase and negative values decrease.
	 * @param longTermEnergyChange
	 */
	void modifyLongTermEnergy(int longTermEnergyChange) {
		this.energy_lt+=longTermEnergyChange;
		if (this.energy_lt>100) {
			this.energy_lt=100;
		} else if (this.energy_lt<0) {
			this.energy_lt=0;
		}
	}
	
	/**
	 * Returns the creature's short term energy.
	 * @return
	 */
	int getShortTermEnergy() {
		setShortTermEnergy(0);
		return this.energy_st;
	}
	/**
	 * Sets the creature's short term energy to <code>shortTermEnergy</code>.
	 * @param shortTermEnergy
	 */
	void setShortTermEnergy(int shortTermEnergy) {
		this.energy_st=(shortTermEnergy>100)?100:(shortTermEnergy<0)?0:shortTermEnergy;
	}
	/**
	 * Modifies the short term energy energy by <code>shortTermEnergyChange</code>. Positive values increase and negative values decrease.
	 * @param shortTermEnergyChange
	 */
	void modifyShortTermEnergy(int shortTermEnergyChange) {
		this.energy_st+=shortTermEnergyChange;
		if (this.energy_st>100) {
			this.energy_st=100;
		} else if (this.energy_st<0) {
			this.energy_st=0;
		}
	}
	
	
	/**
	 * Return the creature health.
	 * @return
	 */
	int getHealth() {
		return this.health;
	}
	/**
	 * Set the creature health.
	 * @param health
	 */
	void setHealth(int health) {
		this.health=health;
	}
	/**
	 * Increase or decrease creature health.
	 * @param healthChange
	 */
	void modifyHealth(int healthChange) {
		this.health+=healthChange;
		if (health>0) {
			this.health=0;
		} else if (health<maxHealth) {
			health=maxHealth;
		}
	}
	/**
	 * Return the creature MaxHealth.
	 * @return
	 */
	int getMaxHealth() {
		return this.maxHealth;
	}
	/**
	 * Set the creature MaxHealth.
	 * @param MaxHealth
	 */
	void setMaxHealth(int MaxHealth) {
		this.maxHealth=MaxHealth;
	}
	/**
	 * Increase or decrease creature MaxHealth.
	 * @param MaxHealthChange
	 */
	void modifyMaxHealth(int MaxHealthChange) {
		this.maxHealth+=MaxHealthChange;
		if (maxHealth>0) {
			this.maxHealth=0;
		}
	}
	
	/**
	 * Returns the creature's weight.
	 * @return
	 */
	double getWeight() {
		return this.weight;
	}
	/**
	 * Sets the creature's weight to weight.
	 * @param weight
	 */
	void setWeight(double weight) {
		this.weight=weight;
	}
	/**
	 * Modifies the creature weight by weightChange. Positives increase and negatives decrease.
	 * @param weightChange
	 */
	void modifyWeight(double weightChange) {
		this.weight+=weightChange;
	}
	
	/**
	 * Return the creatures sanity. Maximum is 100.
	 * @return
	 */
	int getSanity() {
		return this.sanity;
	}
	/**
	 * Sets the creature's sanity to sanity.
	 * @param sanity
	 */
	void setSanity(int sanity) {
		this.sanity = (sanity>100)?100:(sanity<0)?0:sanity;
	}
	/**
	 * Modifies the creature sanity by sanityChange. Positives increase and negatives decrease.
	 * @param sanityChange
	 */
	void modifySanity(int sanityChange) {
		this.sanity+=sanityChange;
		if (this.sanity>100) {
			this.sanity=100;
		} else if (this.sanity<0) {
			this.sanity=0;
		}
	}
	
	/**
	 * Return creature's humanity.
	 * @return
	 */
	int getHumanity() {
		return this.humanity;
	}
	/**
	 * Sets creature's humanity to humanity
	 * @param humanity
	 */
	void setHumanity(int humanity) {
		this.humanity = (humanity>100)?100:(humanity<0)?0:humanity;
	}
	/**
	 * Modifies the creature's humanity by humanityChange. Positives increase and negatives decrease.
	 * @param humanityChange
	 */
	void modifyHumanity(int humanityChange) {
		this.humanity+=humanityChange;
		if (this.humanity>100) {
			this.humanity=100;
		} else if (this.humanity<0) {
			this.humanity=0;
		}
	}
	
	/** Return creature's galvany.
	 * @return
	 */
	int getGalvany() {
		return this.galvany;
	}
	/**
	 * Sets creature's galvany to galvany
	 * @param humanity
	 */
	void setGalvany(int galvany) {
		this.galvany = (galvany>100)?100:(galvany<0)?0:galvany;
	}
	/**
	 * Modifies the creature's galvany by galvanyChange. Positives increase and negatives decrease. 
	 * @param humanityChange
	 */
	void modifyGalvany(int galvanyChange) {
		this.galvany+=galvanyChange;
		if (this.galvany>100) {
			this.galvany=100;
		} else if (this.galvany<0) {
			this.galvany=0;
		}
	}
	
	
	Coordinates getPosition() {
		return position;
	}
	void setPosition(Coordinates c) {
		position = c;
	}
}
