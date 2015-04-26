package com.mtank.item;

public class Tool extends Item {
	//  Weapon/Tool attributes
	int damage=0;
	int range=0;
	int attackSpeed=0;
	boolean twoHanded=false;
	float MAXDURABILITY=100f;
	float durability;
	
	Tool(){
		durability=MAXDURABILITY;
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
	
	/***
	 * Returns the durability value of the tool/weapon.
	 * @return
	 */
	float getDurability(){
		return durability;
	}
	/***
	 * Sets the durability of the weapon/tool.
	 * @param _durability
	 */
	void setDurability(float _durability){
		this.durability=_durability;
	}
	/**
	 * Modifies the items durability. Positives increase and negatives decrease.
	 * @param durabilityChange
	 */
	void modifyHumanity(int durabilityChange) {
		this.durability+=durabilityChange;
		if (this.durability>MAXDURABILITY) {
			this.durability=MAXDURABILITY;
		} else if (this.durability<0) {
			this.durability=0;
		}
	}
	
	/***
	 * Returns the maximum durability value of the tool/weapon.
	 * @return
	 */
	float getMaxDurability(){
		return MAXDURABILITY;
	}
	/***
	 * Sets the maximum durability of the weapon/tool.
	 * @param _durability
	 */
	void setMaxDurability(float _durability){
		this.MAXDURABILITY=_durability;
	}

}
