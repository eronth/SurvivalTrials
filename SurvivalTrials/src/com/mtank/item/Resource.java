package com.mtank.item;

import com.mtank.constants.TypeValue;

public class Resource extends Item {
	// Resource specific attributes
	int damageModifier=1;				// How much the damage of a weapon is modified. (Ammunition)
	boolean refinable=false;			// Can a resource be refined further.
	int resourceType;					// What type is the resource.
	
	// Ammo, logs, sticks, etc
	
	Resource() {
		resourceType=TypeValue.NONE;
	}

	/***
	 * Returns the resource's resource TypeValue.
	 * @return
	 */
	int getResourceType() {
		return resourceType;
	}
	/***
	 * Sets the resource's resource TypeValue.
	 * @param _resourceType
	 */
	void setResourceType(int _resourceType) {
		this.resourceType = _resourceType;
	}

	/***
	 * Returns the damage modifier of the resource. (Ammunition)
	 * @return
	 */
	int getDamageModifier() {
		return resourceType;
	}
	/***
	 * Sets the damage modifier of the resource. (Ammunition)
	 * @param _damageModifier
	 */
	void setDamageModifier(int _damageModifier) {
		this.damageModifier = _damageModifier;
	}
}
