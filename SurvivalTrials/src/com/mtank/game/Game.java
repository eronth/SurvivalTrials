package com.mtank.game;

import java.util.Random;

public class Game {
	public static Random RAND=new Random();
	private static long seed;
	public static void seedRand(){
		seed=System.currentTimeMillis();
		RAND.setSeed(seed);
	}
	
	static Long getSeed() {
		return seed;
	}
	static void setSeed(long newSeed) {
		seed = newSeed;
	}
}
