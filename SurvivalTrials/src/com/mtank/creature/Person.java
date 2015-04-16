package com.mtank.creature;

public class Person {
	// Base Mind Status
	private int sanity=100;			// How much gruesome and horrid things you've seen. Affects your ability to make decisions.
	private int humanity=100;		// How opposed to murder you are.
	private int galvany=100;		// How happy and optimistic you are.
	
	
	
	
	
	
	
	
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
}
