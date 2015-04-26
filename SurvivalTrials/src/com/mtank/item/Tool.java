package com.mtank.item;

import com.mtank.constants.TypeValue;

public class Tool extends Item {
	// Tool/Weapon specific attributes
	int damage=0;
	int range=0;
	int attackSpeed=0;
	boolean twoHanded=false;
	int ammunitionType=TypeValue.NONE;
	
	Tool(){
	}
	
	/***
	 * Returns the tools damage value.
	 * @return
	 */
	int getDamage(){
		return damage;
	}
	/***
	 * Sets the tools range value.
	 * @param int _damage
	 */
	void setDamage(int _damage){
		this.damage=_damage;
	}
	
	/***
	 * Returns the tools range value;
	 * @return
	 */
	int getRange(){
		return range;
	}
	/***
	 * Sets the tools range value;
	 * @param _range
	 */
	void setRange(int _range){
		this.range=_range;
	}
	
	/***
	 * Returns the tools speed value.
	 * @return
	 */
	int getSpeed(){
		return attackSpeed;
	}
	/***
	 * Sets the tools speed value.
	 * @param _attackSpeed
	 */
	void setSpeed(int _attackSpeed){
		this.attackSpeed=_attackSpeed;
	}
	
	/***
	 * Returns the type of ammunition this weapon uses.
	 * @return
	 */
	int getAmmoType(){
		return ammunitionType;
	}
	/***
	 * Sets the type of ammunition this weapon uses.
	 * @param _ammunitionType
	 */
	void setAmmoType(int _ammunitionType){
		this.ammunitionType=_ammunitionType;
	}
	
	/***
	 * Returns true if the tool requires two hands.
	 * @return
	 */
	boolean isTwoHanded(){
		return twoHanded;
	}
	/***
	 * Sets whether or not the tool requires two hands.
	 * @param _twoHanded
	 */
	void setTwoHanded(boolean _twoHanded){
		this.twoHanded=_twoHanded;
	}
}
