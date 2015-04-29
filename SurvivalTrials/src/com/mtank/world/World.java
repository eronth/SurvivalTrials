package com.mtank.world;

import com.mtank.constants.TypeValue;
import com.mtank.creature.Creature;
import com.mtank.game.Coordinates;
import com.mtank.game.Game;
import com.mtank.game.Stringify;
import com.mtank.item.Item;
import com.mtank.structure.Structure;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class World {
    int landsToAdd = 5;
    int changedLands = 0;
	public int worldDimension=10;
	public Land[][] world;// = new Land[worldDimension][worldDimension];
    public ArrayList<ArrayList<Coordinates>> biomes;
	
	
	public World(int size){
		worldDimension=size;
		world = new Land[worldDimension][worldDimension]; 
		initializeWorldLand();
		int center=(world[0].length-1)/2;
		initializeIslandLand(center,center,center,center);
		smallWaterCleanup(); // Last touches to the water system.
		addSalt(0,0);
		enlargeLakes(); // Add much needed size to lakes. They tend to form pitifully small.
		initializeBiomes();
	}
	void initializeWorldLand(){
		//start by initializing the Land variable.
		for(int i=0;i<world.length;i++){
			for(int j=0;j<world[0].length;j++){
				world[i][j]=new Land(TypeValue.Land.WATER, i, j);
			}
		}
	}
	void initializeIslandLand(int x,int y,int xc,int yc){
		// Function works by setting the current spot to a non-SEAWATER value. It then possibly calls the function on neighboring squars, with a chance to fail based on an increasing number counter. 
		world[x][y].landType=TypeValue.NONE;
		int xlen=(xc>x?xc-x:x-xc);
		int ylen=(yc>y?yc-y:y-yc);
		double dist = (Math.sqrt( Math.pow(xlen,2) + Math.pow(ylen,2) ));
		
		
		
		// Values for inequalities were chosen to ensure the borders are always SALTWATER		
		if(x>1 && world[x-1][y].landType!=0/**/ && (Game.RAND.nextInt(2*worldDimension/3)-dist)>=-0) 
		{
			initializeIslandLand(x-1,y,xc,yc); 
		}
		if(y>1 && world[x][y-1].landType!=0/**/ && (Game.RAND.nextInt(2*worldDimension/3)-dist)>=-0)
		{ 
			initializeIslandLand(x,y-1,xc,yc); 
		}
		if(y<world.length-2 && world[x][y+1].landType!=0/**/ && (Game.RAND.nextInt(2*worldDimension/3)-dist)>=-0) 
		{ 
			initializeIslandLand(x,y+1,xc,yc); 
		}
		if(x<world.length-2 && world[x+1][y].landType!=0/**/ && (Game.RAND.nextInt(2*worldDimension/3)-dist)>=-0)
		{ 
			initializeIslandLand(x+1,y,xc,yc); 
		}
	}

	void smallWaterCleanup(){
		// The following code plugs up the little 1x1 holes of water.
		for(int i=1;i<world.length-1;i++){
			for(int j=1;j<world[0].length-1;j++){
				if(world[i][j].landType==TypeValue.Land.WATER && 
						world[i-1][j].landType==0 && world[i][j-1].landType==0 && world[i+1][j].landType==0 && world[i][j+1].landType==0){
					world[i][j]=new Land(0,i,j);
				}
			}
		}
	}
	void enlargeLakes(){
		for (int i=0;i<world.length;i++){
			for (int j=0;j<world[0].length;j++){
				if(world[i][j].landType==TypeValue.Land.WATER){
					world[i][j] = new Land(TypeValue.Land.STONE,i,j);	// Stone is acting as a temporary placeholder here.
				}
			}
		}
		for (int i=0;i<world.length;i++){
			for (int j=0;j<world[0].length;j++){
				if(world[i][j].landType==TypeValue.Land.STONE){
					fillLake(i,j); // Replace stone with lake water, additionally, expand! sort of a crappy function name for now.
				}
			}
		}
	}
	void fillLake(int x,int y){
		// fillLake spreads water outwards from the first viable source it finds... then moves 1 space further out to help enlarge the lake.
		int oldType=world[x][y].landType;
		world[x][y] = new Land(TypeValue.Land.WATER,x,y);
		if(oldType!=0 && oldType!=TypeValue.Land.SALTWATER){
			if((world[x+1][y].landType==0 || world[x][y].landType==TypeValue.Land.STONE) && !touchingSaltWater(x+1,y)){
				fillLake(x+1,y);
			}
			if((world[x][y+1].landType==0 || world[x][y].landType==TypeValue.Land.STONE) && !touchingSaltWater(x,y+1)){
				fillLake(x,y+1);
			}
			if((world[x-1][y].landType==0 || world[x][y].landType==TypeValue.Land.STONE) && !touchingSaltWater(x-1,y)){
				fillLake(x-1,y);
			}
			if((world[x][y-1].landType==0 || world[x][y].landType==TypeValue.Land.STONE) && !touchingSaltWater(x,y-1)){
				fillLake(x,y-1);
			}
			//TODO: Consider adding if statements for diagonals, possibly with only a % chance of success
		}
	}
	// == Takes in the x, y location of a piece of land. The land then returns true if it is touching SALTWATER vertically or horizontally. Diagonally is not included.
	boolean touchingSaltWater(int x, int y){
		return( world[x+1][y].landType==TypeValue.Land.SALTWATER || world[x][y+1].landType==TypeValue.Land.SALTWATER || world[x-1][y].landType==TypeValue.Land.SALTWATER || world[x][y-1].landType==TypeValue.Land.SALTWATER);
	}
	// == Takes in the x and y positions for a block of water. Turns the entire body of water including the initial square from WATER to SALTWATER. 
	private void addSalt(int i,int j){
		world[i][j] = new Land(TypeValue.Land.SALTWATER,i,j);
		if(i>0 && world[i-1][j].landType==TypeValue.Land.WATER){
			addSalt(i-1,j);
		}
		if(j>0 && world[i][j-1].landType==TypeValue.Land.WATER){
			addSalt(i,j-1);
		}
		if(i<world.length-1 && world[i+1][j].landType==TypeValue.Land.WATER){
			addSalt(i+1,j);
		}
		if(j<world[0].length-1 && world[i][j+1].landType==TypeValue.Land.WATER){
			addSalt(i,j+1);
		}
	}
	public String printWorld(){
		String ret="";
		for(int i=0; i<world.length;i++){
			for(int j=0;j<world[0].length;j++){
				ret+=" ";
				if (world[j][i].creature!=null && world[j][i].creature.creatureType!=0){
					ret += Stringify.creature(world[j][i].creature);
				}else if (world[j][i].structure != null && world[j][i].structure.structureType != 0){			
					ret += Stringify.structure(world[j][i].structure);
				}else if (world[j][i].item[0]!=0){
					ret += Stringify.item(world[j][i].item[0]);
				}else{
					ret += Stringify.land(world[j][i].landType);
				}
				ret+=" ";
			}
			ret+="\n";
		}
		System.out.print(ret);
		return ret;
	}

	private void initializeBiomes(){
        biomes = new ArrayList<ArrayList<Coordinates>>();
        //growlist is a list of biomes and their cells that need to grow.
        ArrayList<ArrayList<Coordinates>> growList = new ArrayList();

        //"Plains", "Forest", "Desert", "Dessert", "Ice"
        int[] biome = {TypeValue.Land.DIRT, TypeValue.Land.GRASS, TypeValue.Land.SAND, TypeValue.Land.SNOW, TypeValue.Land.ICE};
        shuffle(biome);
        int center=(world.length)/2;

        double angleRads = Math.toRadians(360.0 / (double) biome.length);
        Coordinates[] endpoints = new Coordinates[biome.length];
        Coordinates[] startpoints = new Coordinates[biome.length];

        //For each biome, generate a starting point between the island center and the beach,
        // going at an angle depending on the current iteration and # of biomes.
        for (int i = 0; i < biome.length; i++) {
            biomes.add(new ArrayList());
            double currentAngle = i*angleRads;
            float sine = (float) (5 * Math.sin(currentAngle));
            float cosine = (float) (5 * Math.cos(currentAngle));
            if(sine <1 && sine>=0)
                sine=1;
            else if(sine >1 && sine <0)
                sine=-1;
            if(cosine <1 && cosine>=0)
                cosine=1;
            else if(cosine >1 && cosine <0)
                cosine=-1;
            int yStep = Math.round(sine);
            int xStep = Math.round(cosine);

            for(int x = center, y = center, steps = 1; ; steps++) {

                if (world[x][y].landType == TypeValue.Land.SALTWATER) {
                    steps--;

                    endpoints[i] = new Coordinates(x - xStep, y - yStep);
                    int rand = Game.RAND.nextInt(steps) + 1;
                    startpoints[i] = new Coordinates(center + xStep * rand, center + yStep * rand);
                    break;
                }
                x+=xStep;
                y+=yStep;

            }
        }

        //Add starting points to growList, init the squares to be of their biome type.
        for(int i = 0; i < startpoints.length; i++) {
            world[startpoints[i].x][startpoints[i].y].landType = biome[i];
            biomes.get(i).add(startpoints[i]);
            growList.add(biomes.get(i));
        }

        //Fill in lands by following idea
        /**
         * 1) coordinates in growLands are pieces of land of a biome type that have LAND type lands surrounding them.
         * 2) Biomes take turns generating the next land piece, picks a random available grow spot, checks it's neighbors
         *      Of LAND type neighbors, one becomes that biome type and is added to the growList
         *      If no LAND type neighbors exist, it is removed from the list.
         * 3) For (I believe unnecessary) infinite loop prevention, if it goes 20 times without adding a land it quits.
         */
        int loopCount = 0;
        for(int i = 0; loopCount < 20 ;i++) {
            if(i == 5)
                i=0;
            if(growList.get(i).isEmpty()) {
                loopCount++;
                continue;
            }
            int growSpot = Game.RAND.nextInt(growList.get(i).size());
            Coordinates grewAt = grow(biome[i], growList.get(i).get(growSpot));
            if(grewAt == null) {
                growList.get(i).remove(growSpot);
            }
            else {
                growList.get(i).add(grewAt);
            }
            loopCount=0;
        }

        //fill un-filled spaces with stone?
        for(int i = 0; i < world.length;i++) {
            for(int j = 0; j < world.length; j++) {
                if(world[i][j].landType == TypeValue.NONE)
                    world[i][j].landType = TypeValue.Land.STONE;
            }
        }

        //(1 square) mountain in the center of the island
        world[center][center].landType = TypeValue.Land.STONE;

        //Add in beach
        int beachSize = Game.RAND.nextInt(4)+5;
        int beachLength = Game.RAND.nextInt(4)+3;
        for(int i = center-(beachSize/2);i< center+(beachSize/2);i++){
            int southShore = center-5;
            while(world[i][southShore].landType != TypeValue.Land.SALTWATER)
                southShore++;
            southShore--;
            for(int j = 0; j < beachLength;j++){
                world[i][southShore-j].landType = TypeValue.Land.SAND;
            }
        }
	}

    private static void shuffle(int[] array) {
        int n = array.length;
        for (int i = 0; i < array.length; i++) {
            // Get a random index of the array past i.
            int random = i + (int) (Math.random() * (n - i));
            // Swap the random element with the present element.
            int randomElement = array[random];
            array[random] = array[i];
            array[i] = randomElement;
        }
    }

    private Coordinates grow(int biome, Coordinates c) {
        ArrayList<Coordinates> newGrowth = new ArrayList();
        if(world[c.x+1][c.y].landType == TypeValue.NONE)
            newGrowth.add(new Coordinates(c.x+1,c.y));
        if(world[c.x-1][c.y].landType == TypeValue.NONE)
            newGrowth.add(new Coordinates(c.x-1,c.y));
        if(world[c.x][c.y+1].landType == TypeValue.NONE)
            newGrowth.add(new Coordinates(c.x,c.y+1));
        if(world[c.x][c.y-1].landType == TypeValue.NONE)
            newGrowth.add(new Coordinates(c.x,c.y-1));
        if(newGrowth.size()==0) {
            return null;
        }
        int spot = Game.RAND.nextInt(newGrowth.size());
        world[newGrowth.get(spot).x][newGrowth.get(spot).y].landType = biome;

        return newGrowth.get(spot);
    }
	
	
	void placeStructure(Structure structure, Coordinates c){
		world[c.x][c.y].structure=structure;
		structure.position.set(c);
	}
	public void placeCreature(Creature creature, Coordinates c){
		if(creature.position.x!=-1){
			world[creature.position.x][creature.position.y].creature=null;
		}
		world[c.x][c.y].creature=creature;
		creature.position.set(c); 
	}
	void placeItem(Item item, Coordinates c){
		int j;
		for(j=0; j<world[c.x][c.y].item.length; j++){ // creatureycreaturele through itemstructure that creaturean be on eacreatureh land and look for open structurepot
			if(world[c.x][c.y].item[j]==0){
				world[c.x][c.y].items[j]=item;
				world[c.x][c.y].item[j]=item.itemType;
				item.position.set(c);
			}
		}
	}
	
	
	
	
}
