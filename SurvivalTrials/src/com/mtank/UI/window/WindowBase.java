package com.mtank.UI.window;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;

import com.mtank.UI.console.Console;
import com.mtank.UI.gamePanel.GameBoard;
import com.mtank.UI.gamePanel.GamePanel;
import com.mtank.UI.gamePanel.LandBoard;
import com.mtank.game.mainClass;

public class WindowBase extends Thread {

	private JFrame frame;
	private Console tConsole;
	static GamePanel gameWorldDisplay;
	static LandBoard gameLandBoard;
	static GameBoard gameBoard;
	public int delay;
	public String message = "";

	/**
	 * Runner
	 */
	public void run() {
		Timer displayLoop = new Timer(0, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					Thread.sleep(100);
					//updateWorldDisplay();
					System.out.println("Display! "+message+System.currentTimeMillis());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		displayLoop.start();
	}
	
	/**
	 * Create the application.
	 */
	public WindowBase() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * Handles splitting the GUI
	 * Adds command console
	 * Adds display window
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane horizontalSplitPane = new JSplitPane();
		frame.getContentPane().add(horizontalSplitPane, BorderLayout.CENTER);
		
		JSplitPane verticalSplitPane = new JSplitPane();
		verticalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		horizontalSplitPane.setLeftComponent(verticalSplitPane);
		
		JPanel topLeftPanel = new JPanel();
		//verticalSplitPane.setLeftComponent(topLeftPanel);
		verticalSplitPane.setTopComponent(topLeftPanel);
		
		JPanel bottomLeftPanel = new JPanel();
		//verticalSplitPane.setRightComponent(bottomLeftPanel);
		verticalSplitPane.setBottomComponent(bottomLeftPanel);
		
		JPanel rightPanel = new JPanel();
		horizontalSplitPane.setRightComponent(rightPanel);
		//bottomLeftPanel.setLayout(new BorderLayout(0, 0));
		
		tConsole = new Console();
		//bottomLeftPanel.add(tConsole, BorderLayout.CENTER);
		bottomLeftPanel.add(tConsole);
		
		gameWorldDisplay = new GamePanel();
		gameLandBoard = new LandBoard();
		gameBoard = new GameBoard();
		
		JLayeredPane testLayers = new JLayeredPane();
		testLayers.add(gameLandBoard, new Integer(0),0);
		testLayers.add(gameBoard, new Integer(1), 0);
		
		
		//topLeftPanel.add(gameWorldDisplay);
		//topLeftPanel.add(gameLandBoard);
		//topLeftPanel.add(gameBoard);
		//topLeftPanel.add(testLayers);
		
		//Properly Align the splitpanes
		verticalSplitPane.setResizeWeight(.95);
		horizontalSplitPane.setResizeWeight(.85);
		frame.pack();
		makeVisible();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					updateWorldDisplay();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * TEMPRORARY
	 * Set the mainGUI to be visible.
	 * TEMPRORARY
	 */
	public void makeVisible() {
		this.frame.setVisible(true);
	}

	/**
	 * TEMPRORARY
	 * Manually repaint the display window based on the world.
	 * TEMPRORARY
	 */
	public void updateWorldDisplay() {
		try {
			gameWorldDisplay.display(mainClass.island);
			gameLandBoard.updateLandMap(mainClass.island);
			gameBoard.updateGameMap(mainClass.island);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * TEMPRORARY
	 * Print a simple message to the debug screen.
	 * TEMPRORARY
	 * @param msg - The String message you want displayed to the error console in the WindowBase.
	 */
	public void print(String msg) {
		tConsole.writeln("Debug", msg);
	}

	/**
	 * TEMPRORARY
	 * Print a simple message to the debug screen.
	 * TEMPRORARY
	 * @param msg - The String message you want displayed to the error console in the WindowBase.
	 */
	public void printError(String msg) {
		tConsole.writeln("Errors", msg);
	}

	/**
	 * TEMPRORARY
	 * Temporary pass through to panel to update the font size.
	 * TEMPRORARY
	 */
	public void setFontSize(int _size) {
		gameWorldDisplay.setFontSize(_size);
		gameLandBoard.setFontSize(_size);
	}

}
