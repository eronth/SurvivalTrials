package com.mtank.UI.gamePanel;
import javax.swing.text.BadLocationException;

import com.mtank.game.mainClass;


public class GamepPanelUpdateThread extends Thread{

	GamePanel window;
   
	public GamepPanelUpdateThread() throws BadLocationException{
		window=new GamePanel();
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
				GamepPanelUpdateThread.sleep(900);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
