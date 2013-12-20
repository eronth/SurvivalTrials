import javax.swing.text.BadLocationException;

/*
 * @Authors
 * Nic Pereira
 * Tim Kinealy
 * Austin Davis
 */

// NOTICE: All general notices for this project will be kept here.
// 1.	All pushes should be both compilable and have any added data-structures tested for reliability.
// 2.	Use the D class for common data types. Any instances that require data change will be made much easier this way.
// 3.	In as many cases as possible, the value 0 will be used as "neutral" or "none". Example: Land with a landType value of 0 will be treated as typless or non-existant land.
// 4.	Update your code with adequate commenting before pushing. 
// 5.	Character attributes are being desiged such that an average human will start with one stat at 50 and two stats at 30, dependant on his skillset.

public class mainClass {
	static World island;
	static Display window;
	static Creature[] person=new Creature[2];
	
	
	 public static void main(String arg[]) throws BadLocationException{
		 D.seedRand();
		 System.out.print("Main begins here\n======================\n\nWaterworld\n");
		 window=new Display();
		 window.setVisible(true);
		 island=new World(50);
		 window.display(island);
		 System.out.println("\nFinal World Generation using :"+D.seed);
		 island.printWorld();
		 
		 
		 // Code in place for crappy initialization purposes.
		 for(int i=0;i<person.length;i++){
			 person[i]=new Creature("Jack","MeHoff",1,70,50,50,50,100,100,100);
			 person[i].actionChoice=i;
		 }
		 person[1].firstName="Jill";
		
		 island.placeCreature(person[0], island.worldDimension/2+1, island.worldDimension/2);
		 island.placeCreature(person[1], island.worldDimension/2+2, island.worldDimension/2);
			
		 // ==================================================================================================
		 // Rudamentary game loop starts here. int n is used to iterate the number of turns you'd like to run.
		 // This loop will eventually be infinite until user selects to end game.
		 int n=200;
		 int maxn=n;
		 long mspt=(long) (.3*1000);//mspt = milliseconds per turn
		 long startTime,endTime,elapsedTime;
		 while(n!=0){
			 startTime=System.currentTimeMillis();
			 for(int i=0;i<person.length;i++){
				 person[i].action(island);
			 }
			 
			 //island.printWorld();
			window.display(island);
			 endTime=System.currentTimeMillis();
			 elapsedTime=endTime-startTime;
			 System.out.println("Turn "+(maxn-n)+" Elapsed Time:"+elapsedTime+" mspt:"+mspt);
			 if(mspt>elapsedTime){
				 try {
					 Thread.sleep(mspt-elapsedTime);
				 }catch(InterruptedException ex){
					 Thread.currentThread().interrupt();
				 }
			 }
			 n--;
		 }
			
			
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
