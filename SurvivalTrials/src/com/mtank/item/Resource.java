package com.mtank.item;

import com.mtank.constants.TypeValue;

public class Resource extends Item {
	
	boolean refinable=false;
	int resourceType;
	
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
	void setItemType(int _resourceType) {
		this.resourceType = _resourceType;
	}
}
