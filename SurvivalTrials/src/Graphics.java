import javax.swing.text.BadLocationException;


public class Graphics extends Thread{

	Display window;
   
	Graphics() throws BadLocationException{
		window=new Display();
		window.setVisible(true);
		window.initWorld(mainClass.island);
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
				Graphics.sleep(900);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
