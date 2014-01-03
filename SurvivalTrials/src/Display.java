import java.awt.*;

import javax.swing.*;
import javax.swing.text.*;


public class Display extends JFrame {

	//Added to make Eclipse Happy
	private static final long serialVersionUID = 1L;
	
	private JTextPane MapDisplay;
	private StyledDocument altDisplay=new DefaultStyledDocument();
	private StyledDocument current=new DefaultStyledDocument();
    
    public Display() {
    	// Set up main display window
		MapDisplay = new JTextPane();
		
		// Set up frame to house display
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setBackground(Color.white);
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Unsure if needed
		setSize(1100,900);
		
		Font monoSpace=new Font("Monospaced", Font.PLAIN, 12);
		//Font cNew=new Font("Courier New", 0, 12);
		// Set display background and font
		MapDisplay.setBackground(Color.black); // Set background color. Black seems to work well
		MapDisplay.setFont(monoSpace); // Set the font for grid display. Must be monospaced
		
		add(MapDisplay);
    }
    
	//TODO REWRITE TO MAKE EVEN FASTER
    
    // Called to display an image.  Takes in a World class.
    public void display(World canvas) throws BadLocationException {    	
    	// Create a document for editing
    	current=MapDisplay.getStyledDocument();
		
		// Clear the current document in preparation of update
    	current.remove(0, current.getLength()); //setText("");
		MapDisplay.setDocument(altDisplay);
		
		// Cycle through and world array and first display land
		for (int i = 0; i < canvas.world.length; i++) {
			for (int j = 0; j < canvas.world[0].length; j++) {
				addSpace(current, " ");
				// if there is a creature,structure, or item, they take precedence over land.
				if (canvas.world[j][i].creature != null && canvas.world[j][i].creature.creatureType != 0) {
					drawCreature(current, D.stringifyCreature(canvas.world[j][i].creature), canvas.world[j][i].creature.creatureType);
				} else if (canvas.world[j][i].structure != null && canvas.world[j][i].structure.structureType != 0) {
					drawStructure(current, D.stringifyStructure(canvas.world[j][i].structure), 0);
				} else if (canvas.world[j][i].item[0] != 0) {
					drawItem(current, D.stringifyItem(canvas.world[j][i].item[0]), 0);
				} else {
					drawLand(current, canvas, D.stringifyLand(canvas.world[j][i].landType), canvas.world[j][i].landType);
				}
				addSpace(current, " ");
			}
			addSpace(current, "\n");
		}
		// Once update is complete, set display to updated document
		MapDisplay.setDocument(current);
    }
    
    // Set up the 'blank' document that we will be stitching behind the real one
    public void makeAlt(World canvas) throws BadLocationException{
    	for (int i = 0; i < canvas.world.length; i++) {
			for (int j = 0; j < canvas.world[0].length; j++) {
				addSpace(altDisplay, " ");
				drawLand(altDisplay, canvas, D.stringifyLand(canvas.world[j][i].landType), canvas.world[j][i].landType);
				addSpace(altDisplay, " ");
			}
			addSpace(altDisplay, "\n");
		}
    }

    //TODO Use highlight to paint land and water in background.
	public void drawLand(StyledDocument workingCanvas,World canvas, String str, int type) throws BadLocationException {
		
		// Set up text styles for landtypes.  Also set up highlights for possible use later
		Style blank = MapDisplay.addStyle("blank", null);
		StyleConstants.setForeground(blank, Color.white);
		//DefaultHighlighter.DefaultHighlightPainter blankH=new DefaultHighlighter.DefaultHighlightPainter(Color.white);
		Style saltwater = MapDisplay.addStyle("saltwater", null);
		StyleConstants.setForeground(saltwater, Color.blue);
		//DefaultHighlighter.DefaultHighlightPainter saltwaterH=new DefaultHighlighter.DefaultHighlightPainter(Color.blue);
		Color waterC = new Color(156, 245, 245);
		Style water = MapDisplay.addStyle("water", null);
		StyleConstants.setForeground(water, waterC);
		//DefaultHighlightPainter waterH=new DefaultHighlighter.DefaultHighlightPainter(waterC);
		Color landC = Color.green.darker();	
		Style land = MapDisplay.addStyle("land", null);
		StyleConstants.setForeground(land, landC);
		//DefaultHighlighter.DefaultHighlightPainter landH=new DefaultHighlighter.DefaultHighlightPainter(landC);
		
		// Get the current index of the cursor for inputing next character
		int index=workingCanvas.getLength();
		switch (type) {
			case D.SALTWATER:
				workingCanvas.insertString(index, str, saltwater);
				//workingCanvas.insertString(index, " ", saltwater); // Used for highlighting 
				//MapDisplay.getHighlighter().addHighlight(index, index+1, saltwaterH); // Used for highlighting 
				break;
			case D.NONE:
				workingCanvas.insertString(index, str, land);
				//workingCanvas.insertString(index, "  ", land); // Used for highlighting 
			    //MapDisplay.getHighlighter().addHighlight(index, index+1, landH); // Used for highlighting 
				break;
			case D.WATER:
				workingCanvas.insertString(index, str, water);
				//workingCanvas.insertString(index, " ", water); // Used for highlighting 
			    //MapDisplay.getHighlighter().addHighlight(index, index+1, waterH); // Used for highlighting 
				break;
			default:
				workingCanvas.insertString(index, str, null);
				//workingCanvas.insertString(index, " ", null); // Used for highlighting 
			    //MapDisplay.getHighlighter().addHighlight(index, index+1, blankH); // Used for highlighting 
				break;
		}
	}

	// Function to draw creature. Will pull and compare with data class
	public void drawCreature(StyledDocument workingCanvas, String str, int type) throws BadLocationException {
		Style blank = MapDisplay.addStyle("blank", null);
		StyleConstants.setForeground(blank, Color.WHITE);
		
		// Switch will be based off of data class once implemented to determine color
		switch (type) {
			case 1:
		    	workingCanvas.insertString(workingCanvas.getLength(), str, blank);
		        break;
		    default:
		    	workingCanvas.insertString(workingCanvas.getLength(), str, null);
		        break;
		}
	}

	// Function to draw structure. Will pull and compare with data class
	public void drawStructure(StyledDocument workingCanvas, String str, int type) throws BadLocationException {
		Style blank = MapDisplay.addStyle("blank", null);
		StyleConstants.setForeground(blank, Color.white);
		
		// Switch will be based off of data class once implemented to determine color
		switch (type) {
		    case 1:
		        workingCanvas.insertString(workingCanvas.getLength(), str, blank);
		        break;
		    default:
		    	workingCanvas.insertString(workingCanvas.getLength(), str, null);
		        break;
		}
	}

	// Function to draw first item on land from item array. Will pull and compare with data class
	public void drawItem(StyledDocument workingCanvas, String str, int type) throws BadLocationException {
		Style blank = MapDisplay.addStyle("blank", null);
		StyleConstants.setForeground(blank, Color.white);
		
		// Switch will be based off of data class once implemented to determine color
		switch (type) {
			case D.NONE:
				workingCanvas.insertString(workingCanvas.getLength(), str, blank);
				break;
		    default:
		    	workingCanvas.insertString(workingCanvas.getLength(), str, null);
		        break;
		}
	}

	// Function to draw a space. Used for spacing
	public void addSpace(StyledDocument workingCanvas, String str) throws BadLocationException {
		workingCanvas.insertString(workingCanvas.getLength(), str, null);
	}
}
