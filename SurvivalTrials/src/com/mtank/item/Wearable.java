package com.mtank.item;

import com.mtank.constants.TypeValue;

public class Wearable extends Item {
	// Wearable specific attributes
	int armorValue=0;					//  Amount of defense provided.
	int wearSlot=TypeValue.NONE;		//  Armorslot that wearable will be worn in.
	
	/***
	 * Returns the armor value of the wearable;
	 * @return
	 */
	int getArmorValue(){
		return armorValue;
	}
	/***
	 * Sets the armor value of the wearable.
	 * @param _armorValue
	 */
	void setArmorValue(int _armorValue){
		this.armorValue=_armorValue;
	}

}
