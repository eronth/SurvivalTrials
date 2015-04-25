package com.mtank.structure;
import java.util.LinkedList;

import com.mtank.constants.TypeValue;
import com.mtank.game.Coordinates;
import com.mtank.item.Item;


public class Structure {
	public Coordinates position = new Coordinates();
	public LinkedList<Item> structureParts = new LinkedList<Item>();
	public LinkedList<Item> resource = new LinkedList<Item>();
	public int structureType = 0; // Type of structure (e.g. Wall, Door, Fire pit, tree, etc)
	int maxHealth;
	int currentHealth;
	// Used to determine which tools are better at dismantling the structure.
	public LinkedList<Integer> dismantledBy = new LinkedList<Integer>();
	// Used to determine what tool or damage type is highly effective against this structure.
	public LinkedList<Integer> weakAgainst = new LinkedList<Integer>();
	public boolean inUse = false;	// Move this to a furniture subclass of Structures.

	
	Structure(){	
	}
	// Initialize structure based on given _structureType. In some cases, _materialType is used to deterimined what it's made of. 
	public Structure(int _structureType) {
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
			initWall();
			break;
		case TypeValue.Structure.DOOR:
			initDoor();
			break;
		case TypeValue.Structure.FIREPIT:
			initFirepit();
			break;
		}
	}
	Structure(int structureType, Coordinates c) {
		this.structureType=structureType;
		this.position.set(c);
	}
	
	
	// The following is a series of initialization functions that lead into the generic one.
	private void initTree() {
		initGeneric(TypeValue.Structure.TREE,60);
	}
	private void initFruittree() {
		initGeneric(TypeValue.Structure.FRUITTREE,60);
	}
	private void initCactus() {
		initGeneric(TypeValue.Structure.CACTUS,100);
	}
	private void initBoulder() {
		initGeneric(TypeValue.Structure.BOULDER,100);
	}
	private void initWall() {
		initGeneric(TypeValue.Structure.WALL,100);
	}
	private void initDoor() {
		initGeneric(TypeValue.Structure.DOOR,70);
	}
	private void initFirepit() {
		initGeneric(TypeValue.Structure.FIREPIT,20);		
	}
	// Used to ensure all initializations are on par with one another.
	private void initGeneric(int _structureType, int _maxHealth){
		structureType = _structureType;
		maxHealth = currentHealth = _maxHealth;
	}
	
	
	/** Returns the maximum possible health for the structure. */
	public int getMaxHealth() {
		return maxHealth;
	}
	/** Sets the maximum possible health for the structure. */
	public void setMaxHealth(int health) {
		maxHealth=health;
	}
	/** Returns the current health of the structure. */
	public int getHealth() {
		return currentHealth;
	}
	/** Sets the current health of the structure. This cannot go above max health or under 0. */
	public void setHealth(int health) {
		if (health>maxHealth) {
			currentHealth = maxHealth;
		} else if (health<0) {
			currentHealth = 0;
		} else {
			currentHealth = health;
		}
	}
	/** 
	 * Modify the current health by the passed in value. This will not increase over max nor under 0.
	 */
	public void modifyHealth(int health) {
		currentHealth+=health;
		if (currentHealth>maxHealth) {
			currentHealth = maxHealth;
		} else if (currentHealth<0) {
			currentHealth = 0;
		}
	}
	/**
	 * Decreases the health by the damage taken.
	 * @param damage
	 */
	public void damaged(int damage) {
		modifyHealth(-damage);
	}
	/**
	 * Increases the health by the healing or repairs applied.
	 * @param health
	 */
	public void repaired(int health) {
		modifyHealth(health);
	}
}
