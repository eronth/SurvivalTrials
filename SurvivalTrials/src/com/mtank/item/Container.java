package com.mtank.item;

import java.util.ArrayList;

public class Container extends Item {
	// Container specific attributes
	float maxVolume=1.0f;			// Max volume of items that can be contained.
	float currentVolume=0f;			// Current volume of all items in the container.
	ArrayList<Item> Items;
	
	Container() {
		Items  = new ArrayList<Item>();
	}
	
	boolean addItem(Item toAdd) {
		boolean retval = true;
		if( ( currentVolume+toAdd.getVolume() ) <= maxVolume) {
			Items.add(toAdd);
			currentVolume+=toAdd.getVolume();
		} else
			retval = false;
		return retval;
	}
	
	boolean removeItem(Item toRemove) {
		currentVolume-=toRemove.getVolume();
		return Items.remove(toRemove);
	}
	
	/***
	 * Gets the number of items currently in the container.
	 * @return
	 */
	int getSize() {
		return Items.size();
	}
	
	/***
	 * Returns the maximum storage capacity of the container in cubic meters.
	 * @return
	 */
	float getCapacity() {
		return maxVolume;
	}
}
