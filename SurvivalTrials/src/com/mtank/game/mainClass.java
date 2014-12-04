package com.mtank.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Timer;
import javax.swing.text.BadLocationException;

import com.mtank.UI.gamePanel.GamepPanelUpdateThread;
import com.mtank.UI.window.WindowBase;
import com.mtank.creature.Creature;
import com.mtank.world.World;

/*
 * @Authors
 * Nic Pereira
 * Tim Kinealy
 * Austin Davis
 **/

/*
*    F*CK KENDRICK     F*CK KENDRICK     F*CK KENDRICK     F*CK KENDRICK     
*    F*CK KENDRICK     F*CK KENDRICK     F*CK KENDRICK     F*CK KENDRICK     
*    F*CK KENDRICK     F*CK KENDRICK     F*CK KENDRICK     F*CK KENDRICK     
*    F*CK KENDRICK     F*CK KENDRICK     F*CK KENDRICK     F*CK KENDRICK     
*    F*CK KENDRICK     F*CK KENDRICK     F*CK KENDRICK     F*CK KENDRICK     
*    F*CK KENDRICK     F*CK KENDRICK     F*CK KENDRICK     F*CK KENDRICK     
*
* TO DO: BLACKMAIL KENDRICK/GET HIM FIRED
**/

// NOTICE: All general notices for this project will be kept here.
// 1.	All pushes should be both compilable and have any added data-structures tested for reliability.
// 2.	Use the D class for common data types. Any instances that require data change will be made much easier this way.
// 3.	In as many cases as possible, the value 0 will be used as "neutral" or "none". Example: Land with a landType value of 0 will be treated as typless or non-existent land.
// 4.	Update your code with adequate commenting before pushing. 
// 5.	Character attributes are being designed such that an average human will start with one stat at 50 and two stats at 30, dependent on his skillset.

public class mainClass {
	public static World island;
	static LinkedList<Creature> person=new LinkedList<Creature>();
	
	
	public static void main(String arg[]) throws BadLocationException{
		Game.seedRand();
		System.out.print("Main begins here\n======================\n\nWaterworld\n");
		island=new World(50);
		System.out.println("\nFinal World Generation using :"+Game.getSeed());
		island.printWorld();
		
		final WindowBase gameWindow = new WindowBase();
		
		//GamepPanelUpdateThread g = new GamepPanelUpdateThread();
		//g.start();
		 
		// Code in place for crappy initialization purposes.
		person.add(new Creature(island,"Jack","MeHoff",1,70.0,50,2,50,100,100,100));
		person.add(new Creature(island,"Jill","MeHoff",1,70,50,2,50,100,100,100));
		 
		Coordinates c=new Coordinates(30,30);
		island.placeCreature(person.get(0),c);
		c=new Coordinates(island.worldDimension/2+2, island.worldDimension/2);
		island.placeCreature(person.get(1),c);
		
		
		
		 
		 /*person[0].pathfind.generatePath(person[0].position,person[0].target);
		 person[0].pathSet=0;
		 String ass="";
		 for(int i=0;i<person[0].pathfind.path.length;i++){
			 ass=" "+person[0].pathfind.path[i];
		 }
		 System.out.println("Path! "+ass);*/
		 
		 
		// ==================================================================================================
		// Rudamentary game loop starts here. int n is used to iterate the number of turns you'd like to run.
		// This loop will eventually be infinite until user selects to end game.
		int n=200;
		int maxn=n;
		long mspt=(long) (.2*1000);//mspt = milliseconds per turn //should run at .2*1000 or .3*1000
		long startTime,endTime,elapsedTime;
		gameWindow.initiate();
		Timer windowUpdate = new Timer(300, new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {    
		        gameWindow.updateWorldDisplay();
		    }
		});
		windowUpdate.start();
		while(n!=0){
			startTime=System.currentTimeMillis();
			for(int i=0;i<person.size();i++){
				// TODO: ACTION GOES HERE.
				person.get(i).action(island);// TODO: revert this to cycling everyone.
			}
			
			endTime=System.currentTimeMillis();
			elapsedTime=endTime-startTime;
			System.out.println("Turn "+(maxn-n)+" Elapsed Time:"+elapsedTime+" mspt:"+mspt);
			gameWindow.print("Turn "+(maxn-n)+" Elapsed Time:"+elapsedTime+" mspt:"+mspt);
			if(mspt>elapsedTime){
				try {
					Thread.sleep(mspt-elapsedTime);
				} catch(InterruptedException ex){
					Thread.currentThread().interrupt();
				}
			}
			n--;
		}/**/
			
			
		// TODO: remove all of the following code
		// testing facility follows. I'm looking at all possible chars with this, just to see what symbols can be used.
		/*for(char tst=0;tst<=35100+1;tst++){
			System.out.print("\t"+(int)tst+":"+tst);
			if(tst%15 == 0){
				System.out.print("\n");
			}
		}/**/
	 }
}
