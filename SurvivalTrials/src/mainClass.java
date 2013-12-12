import javax.swing.JLabel;
import javax.swing.text.BadLocationException;

/*
 * @Authors
 * Nic Pereira
 */

// NOTICE: All general notices for this project will be kept here.
// 1.	All pushes should be both compilable and have any added data-structures tested for reliability.
// 2.	Use the D class for common data types. Any instances that require data change will be made much easier this way.
// 3.	In as many cases as possible, the value 0 will be used as "neutral" or "none". Example: Land with a landType value of 0 will be treated as typless or non-existant land.
// 4.	Update your code with adequate commenting before pushing. 

public class mainClass {
	static World island;
        static Display window;
	
	
	 public static void main(String arg[]) throws BadLocationException{
		System.out.print("Main begins here\n======================\n\nWaterworld\n");
		D.seedRand();
                window=new Display();
                window.setVisible(true);
		island=new World(70);
                window.display(island);
		System.out.println("\nFinal World Generation using :"+D.seed);
		island.printWorld();
	}
}
