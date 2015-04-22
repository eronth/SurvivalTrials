package com.mtank.game;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Timer;
import javax.swing.text.BadLocationException;

import com.mtank.UI.window.LWJGL_Display;
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
	static int n=20;
	static int maxn=n;
	static LinkedList<Creature> person=new LinkedList<Creature>();
	
	
	public static void main(String arg[]) throws BadLocationException{
		Game.seedRand();
		System.out.print("Main begins here\n======================\n\nWaterworld\n");
		island=new World(100);
		System.out.println("\nFinal World Generation using :"+Game.getSeed());
		island.printWorld();
		 
		// Code in place for crappy initialization purposes.
		person.add(new Creature("Jack","MeHoff",1,70.0,50,2,50));
		person.add(new Creature("Jill","MeHoff",1,70,50,2,50));
		 
		Coordinates c=new Coordinates(30,30);
		island.placeCreature(person.get(0),c);
		c=new Coordinates(island.worldDimension/2+2, island.worldDimension/2);
		island.placeCreature(person.get(1),c);
		
		person.get(0).addWalkAction(new Coordinates(20,20));
		//person.get(0).addSleepAction(new Structure(1), new Coordinates(1,1));
		//person.get(0).addBuildAction(new Structure(2), new Coordinates(2,2));
		
		 
		 /*person[0].pathfind.generatePath(person[0].position,person[0].target);
		 person[0].pathSet=0;
		 String ass="";
		 for(int i=0;i<person[0].pathfind.path.length;i++){
			 ass=" "+person[0].pathfind.path[i];
		 }
		 System.out.println("Path! "+ass);*/
		 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Timer GameLoop = new Timer(300, new ActionListener() {
						long mspt=(long) (.2*1000);//mspt = milliseconds per turn //should run at .2*1000 or .3*1000
						long startTime,endTime;
						public void actionPerformed(ActionEvent evt) {
							if(n > 0) {
								startTime=System.currentTimeMillis();
								for(int i=0;i<person.size();i++){
									// TODO: ACTION GOES HERE.
									Coordinates c = new Coordinates(person.get(i).getPosition().x, person.get(i).getPosition().y+1);
									island.world[c.x][c.y].creature=person.get(i);
									island.world[c.x][c.y-1].creature=null;
									person.get(i).setPosition(c);
								}
								
								endTime=System.currentTimeMillis();
								System.out.println("Turn "+(maxn-n)+" Elapsed Time:"+(endTime-startTime)+" mspt:"+mspt);
								System.out.println("Person 1: " + person.get(0).position.toString());
								System.out.println("Person 2: " + person.get(1).position.toString());
								n--;
							}
						}
					});
					GameLoop.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		LWJGL_Display Display = new LWJGL_Display();
		Display.execute();
	 }
}
