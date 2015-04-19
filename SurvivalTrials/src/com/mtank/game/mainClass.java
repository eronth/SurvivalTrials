package com.mtank.game;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Timer;
import javax.swing.text.BadLocationException;

import com.mtank.UI.window.LWJGL_Display;
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
	static LinkedList<Creature> people=new LinkedList<Creature>();
	static WindowBase gameWindow;
	//Testing
	static int n=10;
	static int maxn=n;
	long mspt=(long) (.2*1000);
	long startTime,endTime,elapsedTime;
	
	
	public static void main(String arg[]) throws BadLocationException{
		Game.seedRand();
		System.out.print("Main begins here\n======================\n\nWaterworld\n");
		
		// 120 is max advisable size.
		island=new World(100);
		System.out.println("\nFinal World Generation using :"+Game.getSeed());
		island.printWorld();
		// WindowBase gameWindow;
		//*** Testing new LWGJL/OGL display
		LWJGL_Display Display = new LWJGL_Display();
		/*WindowBase w1 = new WindowBase();
		WindowBase w2 = new WindowBase();
		w1.setFontSize(5);
		w2.setFontSize(5);
		w1.message="left";
		w2.message="right";
		w1.delay=100;
		w2.delay=500;
		w1.start();
		w2.start();*/
		Display.execute();
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {/**/
				/*try {
					gameWindow = new WindowBase();
					gameWindow.setFontSize(5);
					gameWindow.start();
				} catch (Exception e) {
					e.printStackTrace();
				}*/
		/*	}
		});/**/
		 
		// Code in place for crappy initialization purposes.
		/*people.add(new Creature(island,"Jack","MeHoff",1,70.0,50,2,50,100,100,100));
		people.add(new Creature(island,"Jill","MeHoff",1,70,50,2,50,100,100,100));
		 
		Coordinates c=new Coordinates(island.worldDimension/2,island.worldDimension/2);
		island.placeCreature(people.get(0),c);
		c=new Coordinates(island.worldDimension/2+2, island.worldDimension/2);
		island.placeCreature(people.get(1),c);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Timer GameLoop = new Timer(300, new ActionListener() {
						long mspt=(long) (.2*1000);//mspt = milliseconds per turn //should run at .2*1000 or .3*1000
						long startTime,endTime;
						public void actionPerformed(ActionEvent evt) {
							if(n > 0) {
								startTime=System.currentTimeMillis();
								
								for(int i=0;i<people.size();i++){
									// TODO: ACTION GOES HERE.
									people.get(i).action(island);
								}
								
								endTime=System.currentTimeMillis();
								System.out.println("Turn "+(maxn-n)+" Elapsed Time:"+(endTime-startTime)+" mspt:"+mspt);
								//gameWindow.print("Turn "+(maxn-n)+" Elapsed Time:"+elapsedTime+" mspt:"+mspt);
								//gameWindow.updateWorldDisplay();
								n--;
							}
						}
					});
					GameLoop.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
	}
	
	String availableChars () {
		StringBuilder sb = new StringBuilder();
		for(char tst=0;tst<=35100+1;tst++){
			sb.append("\t"+(int)tst+":"+tst);
			if(tst%15 == 0){
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}
