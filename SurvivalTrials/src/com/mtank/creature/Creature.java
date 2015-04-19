//test add

package com.mtank.creature;

import java.util.LinkedList;

import com.mtank.ai.PathFindingWorld;
import com.mtank.constants.Action;
import com.mtank.constants.Direction;
import com.mtank.constants.Role;
import com.mtank.game.Coordinates;
import com.mtank.game.Game;
import com.mtank.game.mainClass;
import com.mtank.item.Item;
import com.mtank.structure.Structure;



public class Creature {
	// Basic creature info.
	String firstName="Phillip";
	String lastName="Fry";
	public int creatureType=0;
	private int role = Role.NONE;
	// Action values and info.
	private LinkedList<Integer> 	queuedAction = new LinkedList<Integer>();
	private LinkedList<Coordinates> queuedTarget = new LinkedList<Coordinates>();
	private LinkedList<Structure> 	queuedStructure = new LinkedList<Structure>();
	private LinkedList<Item>		queuedItem = new LinkedList<Item>();
	private int acdt = 0;			// acdt stands for the Action Count Down Timer. It counts down until an action is to be executed.
	private boolean isActing = false;
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
	private double weight=70;		// Weight is done in kg. Max is 140 min is 40 for humans.
	private double maxWeight = 200;
	private double minWeight = 0;
	// Variables for determining interaction with other creatures.
	int fight;
	int flee;
	char disposition = 0; //1 is agressive, 0 is neutral, -1 is timid.
	// Useful pathfinding data structures
	private PathFindingWorld pathfind;
	private boolean pathSet = false;
	// Spacial related info.
	public Coordinates position = new Coordinates();
	private int facingDirection = 0;
	// Equipment
	private LinkedList<Item> inventory = new LinkedList<Item>();
	Item head = null;
	Item chest = null;
	Item legs = null;
	Item feet = null;
	Item leftHand = null;
	Item rightHand = null;
	

	
	
	/**
	 * Initialize a blank creature object.
	 */
	Creature() {
	}
	
	/**
	 * Initialize a creature with a first name of <b>fn</b>, last name of <b>ln</b> and type of <b>type</b>. All other stats are also initialized here.
	 */
	public Creature(String fn,String ln,int type, double weight, int enduranceStart, int strengthStart, int intelligenceStart) {
		firstName=fn;
		lastName=ln;
		creatureType=type;
		baseEndurance=enduranceStart;
		baseStrength=strengthStart;
		baseIntelligence = intelligenceStart;
		pathfind=new PathFindingWorld(mainClass.island);
		queuedAction.add(0);
	}
	
	/**
	 * Finds the ideal path between creatures current location and <b>target</b>.
	 */
	public void generatePath(Coordinates target) {
		pathfind.targetCoords = new Coordinates(target);
		pathfind.generatePath(position, 0);
	}
	
	/**
	 *  This is the switch case for which action the creature will be performing this turn.
	 */
	public void action(){
		switch (queuedAction.get(0)) {
		case Action.ATTACK:
			performAttackAction();
			break;
		case Action.BUILD:
			performBuildAction();
			break;
		case Action.GRAB:
			performGrabAction();
			break;
		case Action.DEFEND:
			performDefendAction();
			break;
		case Action.DESTROY:
			performDestroyAction();
			break;
		case Action.DISMANTLE:
			performDismantleAction();
			break;
		case Action.GATHER:
			performGatherAction();
			break;
		case Action.IDLE:
			performIdleAction();
			break;
		case Action.SLEEP:
			performSleepAction();
			break;
		case Action.WANDER:
			performWanderAction();
			break;
		case Action.WALK:
			performWalkAction();
			break;
		default:
			break;
		}
	}
	
	
	/**
	 * THIS IS THE LIST OF ALL AddAction()s. 
	 * CHECK BELOW THIS LINE TO ADD, REMOVE, OR MODIFY.
	 * |			|			|			|
	 * ATTACK		BUILD		GRAB		DEFEND
	 * DESTROY		DISMANTLE	GATHER		IDLE
	 * SLEEP		WALK		WANDER
	 */
	/**
	 * Adds the attack action to list of actions.
	 */
	public void addAttackAction() {
		// TODO no clue how to build this yet.
	}
	public void performAttackAction() {
		// XXX
	}
	/**
	 * Adds build structure to the list of actions.
	 */
	public void addBuildAction(Structure s, Coordinates c) {
		// Add all the necessary actions in order.
		// First, we must walk to the required location.
		queuedAction.add(Action.WALK);
		queuedTarget.add(c);
		// Second, add the structure building requirement.
		queuedAction.add(Action.BUILD);
		queuedStructure.add(s);
		queuedTarget.add(c);
	}
	public void performBuildAction() {
		// XXX
	}
	/**
	 * Adds the grab item action to the list of actions.
	 */
	public void addGrabAction(Item i, Coordinates c) {
		// Add all necessary actions in order.
		// First, we must walk to the item.
		queuedAction.add(Action.WALK);
		queuedTarget.add(c);
		// Second, add the actual item obtainment.
		queuedAction.add(Action.GRAB);
		queuedItem.add(i);
	}
	public void performGrabAction() {
		inventory.add(queuedItem.get(0));
		queuedItem.remove();
		queuedAction.remove();
	}
	/**
	 * Adds the defend action to the list of actions.
	 */
	public void addDefendAction() {
		// TODO figure out combat rules before writing this.
	}
	public void performDefendAction() {
		// XXX
	}
	/**
	 * Adds an action to destroy a structure. 
	 * Destroy removes a structure without leaving materials behind. If you wish to harvest materials, use "dismantle".
	 * @param s
	 * @param c
	 */
	public void addDestroyAction(Structure s, Coordinates c) {
		// Add all necessary actions in order.
		// First we walk to the structure.
		queuedAction.add(Action.WALK);
		queuedTarget.add(c);
		// Second, add the actual structure destruction.
		queuedAction.add(Action.DESTROY);
		queuedStructure.add(s);
	}
	public void performDestroyAction() {
		if (acdt>0) {
			acdt--;
		} else if(isActing) {
			boolean usingProperWeapon = false;
			for (Integer tool : queuedStructure.getFirst().weakAgainst) {
				if ((rightHand!=null && rightHand.itemType == tool)
						|| (leftHand!=null && leftHand.itemType == tool) ) {
					usingProperWeapon=true;
				}
			}
			int damage = (int) ((usingProperWeapon)?1.2*getDamage():getDamage());
			queuedStructure.getFirst().damaged(damage);
		}
		if ( !(isActing && acdt>0) ) {
			isActing = true;
			acdt = 15 - getSpeed()/20;
		}
		if (queuedStructure.getFirst().getHealth()==0) {
			queuedStructure.removeFirst();
			queuedAction.removeFirst();
		}
	}
	/**
	 * Adds an action to dismantle a structure.
	 * Dismantle takes a structure apart, leaving behind some of the original material. If you wish to simply remove the structure, use "destroy".
	 */
	public void addDismantleAction(Structure s, Coordinates c) {
		// Add all necessary actions in order.
		// First we walk to the structure.
		queuedAction.add(Action.WALK);
		queuedTarget.add(c);
		// Second, add the actual structure dismantle.
		queuedAction.add(Action.DISMANTLE);
		queuedStructure.add(s);
	}
	public void performDismantleAction() {
		if ( !queuedStructure.getFirst().resource.isEmpty() ) {
			// If there are still resources left to gather, gather them first then dismantle.
			queuedAction.add(0, Action.GATHER);
			queuedStructure.add(0, queuedStructure.getFirst());
		} else/**/ if (!isActing) {
			isActing=true;
			acdt = 0;
		} else if (acdt > 0) {
			acdt--;
		} else {
			if ( !queuedStructure.getFirst().structureParts.isEmpty() ) {
				inventory.add(queuedStructure.getFirst().structureParts.getFirst());
				queuedStructure.getFirst().structureParts.removeFirst();
			} 
			if (queuedStructure.getFirst().structureParts.isEmpty() && queuedStructure.getFirst().resource.isEmpty()) {
				queuedStructure.removeFirst();
				queuedAction.removeFirst();
				isActing = false;
			}
		}
		if ( !(isActing && acdt>0) ) {
			boolean usingProperTool = false;
			for (Integer tool : queuedStructure.getFirst().dismantledBy) {
				if ((rightHand!=null && rightHand.itemType == tool)
						|| (leftHand!=null && leftHand.itemType == tool) ) {
					usingProperTool=true;
				}
			}
			acdt = ((usingProperTool)?80:100)*queuedStructure.getFirst().getMaxHealth()/getStrength();
		}
	}
	/**
	 * Adds an action to gather resources.
	 */
	public void addGatherAction(Structure s, Coordinates c) {
		// Add all necessary actions in order.
		// First we walk to the structure.
		queuedAction.add(Action.WALK);
		queuedTarget.add(c);
		// Next, gather.
		queuedAction.add(Action.GATHER);
		queuedStructure.add(s);
	}
	public void performGatherAction() {
		if (!isActing) {
			acdt = 0;
			isActing = true;
		} else if (acdt > 0) {
			acdt--;
		} else {
			if ( !queuedStructure.getFirst().resource.isEmpty() ) {
				inventory.add(queuedStructure.getFirst().resource.getFirst());
				queuedStructure.getFirst().resource.removeFirst();
			}
			if ( queuedStructure.getFirst().resource.isEmpty() ) {
				queuedStructure.removeFirst();
				queuedAction.removeFirst();
				isActing = false;
			}
		}
		if ( !(isActing && acdt>0) ) {
			acdt = role==Role.PILGRIM?2:3;				
		}
	}
	/**
	 * Adds an Idle action. Action will repeat itself until worn out or a new action is selected.
	 * Creature should wander in a square area marked by diagonal corners c0 and c1.
	 */
	public void addIdleAction() {
		queuedAction.add(Action.IDLE);
	}
	public void performIdleAction() {
		if (isActing) {
			acdt = 0;
			isActing = false;
		}
		if (queuedAction.size() > 1 && queuedAction.get(1) != null) {
			queuedAction.removeFirst();
		}
	}
	/**
	 * Add a sleep action.
	 */
	public void addSleepAction(Structure s, Coordinates c) {
		// Add all necessary actions in order.
		// First, move to location (hopefully bed);
		queuedAction.add(Action.WALK);
		queuedTarget.add(c);
		// Next, mark bed as used if it exists.
		queuedAction.add(Action.SLEEP);
		queuedStructure.add(s);
	}
	public void performSleepAction() {
		if ( !queuedStructure.getFirst().inUse ) {
			queuedStructure.getFirst().inUse = true;
			isActing = true;
			acdt=10;
		}
		if (isActing && (energy_lt<100 || energy_st<100)) {
			if (energy_lt<100) {
				if (acdt>0) {
					acdt--;
				} else {
					acdt = 10;
					energy_lt++;
				}
			}
			if (energy_st<100) {
				energy_st++;
			}
		} else {
			queuedAction.removeFirst();
			queuedStructure.removeFirst();
			isActing = false;
			acdt=0;
		}
	}
	/**
	 * Adds a walk to location c action.
	 */
	public void addWalkAction(Coordinates c) {
		queuedAction.add(Action.WALK);
		System.out.println("coord check " + c);
		queuedTarget.add(new Coordinates(c));
	}
	public void addImmediateWalkAction(Coordinates c) {
		queuedAction.add(0, Action.WALK);
		queuedTarget.add(0, c);
	}
	public void performWalkAction() {
		if (pathSet) {
			// TODO all actual walking related stuff here.				
			// Initialize timer, and countdown.
			if (acdt <= 0) {
				// Upon countdown completion, take a step, then check if we made it to target location.
				// If at location end walk.
				// If not at location, generate new path, start again.
				// if at target location, isActing is false, acdt is 0 and we exit.
				acdt = 200/getSpeed();
			} else {
				acdt--;
			}
		} else {
			pathfind.setTargetCoords(queuedTarget.get(0));
			System.out.println("What the fuck is wrong? " +queuedTarget.get(0));
			pathfind.generatePath(position, 0);
			pathSet = true;
			isActing = true;
			acdt = 200/getSpeed(); // Initialize timer.
		}
		// XXX
	}
	/**
	 * Adds an action to wander around.
	 * @param c0
	 * @param c1
	 */
	public void addWanderAction(Coordinates c0, Coordinates c1) {
		// Ensure c0 is upper left corner and c1 is lower right.
		if (c0.x>c1.x) {
			int tmp = c0.x;
			c0.x=c1.x;
			c1.x=tmp;
		}
		if (c0.y>c1.y) {
			int tmp = c0.y;
			c0.y=c1.y;
			c1.y=tmp;
		}
		// Adds two corners for the area within which they should wander.
		queuedTarget.add(c0);
		queuedTarget.add(c1);
	}
	public void performWanderAction() {
		Coordinates target = null;
		// We'll only try this loop <tries> times before assuming it's impossible to avoid stalling forever..
		int tries = 15;
		do {
			// Returns a value between 0 and 99.
			int directionPercent = Game.RANDY.nextInt(100);
			if (directionPercent<1) {
				// turn around and walk backwards
				target = position.directionalCoord(Direction.invert(facingDirection));
			} else if (directionPercent<11) {
				// face nearly backwards
				boolean left = (0==Game.RANDY.nextInt(2));
				if (left) {
					target = position.directionalCoord(Direction.invert(Direction.slightLeft(facingDirection)));
				} else {
					target = position.directionalCoord(Direction.invert(Direction.slightRight(facingDirection)));
				}
			} else if (directionPercent<31) {
				// turn 90 degrees
				boolean left = (0==Game.RANDY.nextInt(2));
				if (left) {
					target = position.directionalCoord(Direction.slightLeft(Direction.slightLeft(facingDirection)));
				} else {
					target = position.directionalCoord(Direction.slightRight(Direction.slightRight(facingDirection)));
				}
			} else if (directionPercent<61) {
				//  turn slightly
				boolean left = (0==Game.RANDY.nextInt(2));
				if (left) {
					target = position.directionalCoord(Direction.slightLeft(facingDirection));
				} else {
					target = position.directionalCoord(Direction.slightRight(facingDirection));
				}
			} else { // This should be, in theory, the 61-100 range or 39% of the time.
				// take a step foward
				target = position.directionalCoord(facingDirection);
			}
			tries--;
		} while (tries>0 
				|| target.x<queuedTarget.get(0).x 
				|| target.y<queuedTarget.get(0).y
				|| target.x>queuedTarget.get(1).x 
				|| target.y>queuedTarget.get(1).y);
		addImmediateWalkAction(target);
	}
	
	
	
	
	/**
	 * Returns the speed of the creature based on many factors, including their strength, weight, energy.
	 * Returns a value between 0 and 100.
	 * @return
	 */
	int getSpeed() {
		return (int)(70+((getStrength()-(getWeight()-70))*(getLongTermEnergy())/200) ); // Move at a speed based on weight and energy
	}
	/**
	 * Returns the base damage a creature deals, 
	 * before taking into account the appropriate tools or opponents armor.
	 */
	int getDamage() {
		return (int)(getStrength()+getWeight()-40)/10;
	}
	
	/**
	 * Return creature's endurance.
	 */
	int getEndourance() {
		return getBaseEndurance();//TODO*modifier + increase
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
		return getBaseStrength();//TODO*modifier +bonus
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
		return getBaseIntelligence();//TODO*modifier +bonus
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
		if (weight>maxWeight) {
			weight=maxWeight;
		} else if (weight<minWeight) {
			weight=minWeight;
		}
	}
	/**
	 * Modifies the creature weight by weightChange. Positives increase and negatives decrease.
	 * @param weightChange
	 */
	void modifyWeight(double weightChange) {
		this.weight+=weightChange;
		if (weight>maxWeight) {
			weight=maxWeight;
		} else if (weight<minWeight) {
			weight=minWeight;
		}
	}
	/**
	 * Sets the max weight of this type of creature.
	 * @param weight
	 */
	void setMaxWeight(double weight) {
		this.maxWeight = weight;
	}
	/**
	 * Sets the minimum weight of this type of creature.
	 * @return
	 */
	void setMinWeight(double weight) {
		this.minWeight = weight;
	}
	
	/**
	 * Returns the creature's creature type.
	 * @return
	 */
	int getCreatureType() {
		return creatureType;
	}
	/**
	 * Sets the creature's creature type.
	 * @return
	 */
	void setCreatureType(int type) {
		this.creatureType = type;
	}
	
	/**
	 * returns the fight variable value.
	 */
	int getFight() {
		// TODO improve fight/flee responses involving hunger and strength
		return fight;
	}
	/**
	 * Sets the fight variable. Forces values under 0 to 0 and values over 40 to 40.
	 * @return
	 */
	void setFight(int fight) {
		if (fight>40) {
			this.fight = 40;
		} else if (fight<5) {
			this.fight = 5;
		} else {
			this.fight = fight;			
		}
	}
	/**
	 * Returns the flee variable value.
	 * @return
	 */
	int getFlee() {
		// TODO improve fight/flee responses involving hunger and strength
		return flee;
	}
	/**
	 * Sets the flee variable. Forces values under 0 to 0 and values over 40 to 40.
	 * @param flee
	 * @return
	 */
	void setFlee(int flee) {
		if (flee>40) {
			this.flee= 40;
		} else if (flee<5) {
			this.flee = 5;
		} else {
			this.flee = flee;			
		}
	}
	/**
	 * Determines the appropriate interaction with other creatures.
	 * @return
	 */
	void determineDisposition(Creature c) {
		int primaryRange = 0;
		int secondaryRange = 0;
		if (c.creatureType == this.creatureType) {
			primaryRange = 5;
			secondaryRange = 2;
		} else if (disposition != 0) {
			if (disposition == 1) {
				primaryRange = getFight();
				secondaryRange = getFlee();
			} else if (disposition == -1) {
				primaryRange = getFlee();
				secondaryRange = getFight();
			}
			primaryRange += 40;
			secondaryRange /= 2;
			secondaryRange = secondaryRange - 100;
			if(secondaryRange<5) {
				secondaryRange= 5;
			}
			if (secondaryRange - primaryRange< 10) {
				secondaryRange = primaryRange+10;
			}
		}
		int decision = Game.RANDY.nextInt(100);
		if (decision < primaryRange) {
			if (disposition > 1){
				// TODO make me flee
			} else {
				// TODO ATTACK!
			}
		} else if (decision < secondaryRange) {
			// TODO be cool.
		} else {
			if (disposition > 1){
				// TODO ATTACK!
			} else {
				// TODO make me flee
			}
		}
	}
	
	
	Coordinates getPosition() {
		return position;
	}
	void setPosition(Coordinates c) {
		position = c;
	}
}
