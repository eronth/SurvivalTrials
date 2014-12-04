
import javax.swing.text.BadLocationException;


public class Graphics extends Thread{

	Display window;
	int threadUpdateDelay;
   
	Graphics() throws BadLocationException{
		window=new Display();
		window.setVisible(true);
		window.initWorld(mainClass.island);
		threadUpdateDelay = 300;
	}
	public void run() {
		while (true) {
			try {
				window.display(mainClass.island);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Graphics.sleep(threadUpdateDelay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
