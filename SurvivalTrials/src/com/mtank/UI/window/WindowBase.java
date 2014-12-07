package com.mtank.UI.window;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.text.BadLocationException;

import com.mtank.UI.console.Console;
import com.mtank.UI.gamePanel.GamePanel;
import com.mtank.game.mainClass;

public class WindowBase {

	private JFrame frame;
	private Console tConsole;
	static GamePanel gameWorldDisplay;

	/**
	 * Launch the application.
	 * This will become the new main game launcher
	 * Each task becomes a new runnable.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowBase window = new WindowBase();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gameWorldDisplay.display(mainClass.island);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// Game loop?
			}
		});
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
		topLeftPanel.add(gameWorldDisplay);
		
		//Properly Align the splitpanes
		verticalSplitPane.setResizeWeight(.95);
		horizontalSplitPane.setResizeWeight(.85);
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
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * TEMPRORARY
	 * Print a simple message to the debug screen.
	 * TEMPRORARY
	 */
	public void print(String msg) {
		tConsole.writeln("Debug", msg);
	}

	/**
	 * TEMPRORARY
	 * Print a simple message to the debug screen.
	 * TEMPRORARY
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
	}

}
