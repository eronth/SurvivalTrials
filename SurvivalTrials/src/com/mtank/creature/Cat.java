package com.mtank.creature;

import com.mtank.constants.TypeValue;

public class Cat extends Creature {

	Cat() {
		setCreatureType(TypeValue.Creature.CAT);
		setMaxWeight(21.3);
		setMinWeight(0.68);
	}
}
