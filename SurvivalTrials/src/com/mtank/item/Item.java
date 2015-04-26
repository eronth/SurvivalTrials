package com.mtank.item;

import com.mtank.constants.TypeValue;
import com.mtank.game.Coordinates;


// ITEM Types/Subclasses
//  Resource
//  Container
//  Wearable
//  Tool/Weapon

public class Item {
	// Default name/description.
	final String undfName="[UNDEFINED_NAME]";
	final String undefDescr="[UNDEFINED_DESCRIPTION]";
	
	public Coordinates position = new Coordinates();
	
	// Base Attributes
	public int itemType;								// starts typeless
	String name="[UNDEFINED_NAME]";						// Item name/identifying text.
	String description="[UNDEFINED_DESCRIPTION]";		// Used for flavor text or unique items.
	float weight=0f;									// Weight is done in kg.
	float volume=0f;									// Might later remove.
	int materialType=TypeValue.NONE; 					// Material the item is made of. Used to determine what it is effective against or how strong it is.
	
	// Advanced Attributes
	boolean stackable;									// Can this item be stored in stacks.
	int quantity=0;										// For stacks of items, mostly resources.
	boolean held=false; 								// Is item held or placed on the ground.
	
	// Create a blank item object
	Item(){
	}

	Item(int type, String _name, String _description, int _materialType, float _weight, int _volume, int _quantity, Coordinates c){
		initGeneric(type, _name, _description, _materialType, _weight, _volume,  _quantity, c);
	}
	
	private void initGeneric(int type, String _name, String _description, int _materialType, float _weight, int _volume, int _quantity, Coordinates c){
		itemType=type;
		name=_name;
		description=_description;
		materialType=_materialType;
		weight=_weight;
		volume=_volume;
		quantity=_quantity;
		this.position.set(c);
	}
	
	//TODO: Getters and setters for all attributes
	/***
	 * Returns the item's item TypeValue.
	 * @return
	 */
	int getItemType() {
		return itemType;
	}
	/***
	 * Sets the item's item TypeValue.
	 * @param _itemType
	 */
	void setItemType(int _itemType) {
		this.itemType = _itemType;
	}

	/***
	 * Returns the item's name.
	 * @return
	 */
	String getName() {
		return name;
	}
	/***
	 * Sets the item's name.
	 * @param _name
	 */
	void setName(String _name) {
		this.name = _name;
	}

	/***
	 * Returns the item's description.
	 * @return
	 */
	String getDescription() {
		return description;
	}
	/***
	 * Sets the item's description.
	 * @param _description
	 */
	void setDescription(String _description) {
		this.description = _description;
	}

	/***
	 * Returns the item's weight.
	 * @return
	 */
	float getWeight() {
		return weight;
	}
	/***
	 * Sets the item's weight.
	 * @param _weight
	 */
	void setWeight(float _weight) {
		this.weight = _weight;
	}

	/***
	 * Returns the item's volume.
	 * @return
	 */
	float getVolume() {
		return volume;
	}
	/***
	 * Sets the item's volume.
	 * @param _volume
	 */
	void setVolume(float _volume) {
		this.volume = _volume;
	}

	/***
	 * Returns the item's materialType.
	 * @return
	 */
	int getMaterialType() {
		return materialType;
	}
	/***
	 * Sets the item's materialType.
	 * @param _materialType
	 */
	void setMaterialType(int _materialType) {
		this.materialType = _materialType;
	}

	/***
	 * Returns true if the item is able to be stacked.
	 * @return
	 */
	boolean isStackable() {
		return stackable;
	}
	/***
	 * Sets whether or not the item can be stacked.
	 * @param _stackable
	 */
	void setStackable(boolean _stackable) {
		this.stackable = _stackable;
	}

	/***
	 * Returns true if the item is currently held.
	 * @return
	 */
	boolean isHeld() {
		return held;
	}
	/***
	 * Sets whether or not the item is currently held.
	 * @param _stackable
	 */
	void setHeld(boolean _held) {
		this.held = _held;
	}
}