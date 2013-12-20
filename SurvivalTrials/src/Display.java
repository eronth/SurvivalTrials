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
		setBackground(new Color(255, 255, 255));
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Unsure if needed
		setSize(1100,900);
		
		Font monoSpace=new Font("Monospaced", Font.PLAIN, 12);
		Font cNew=new Font("Courier New", 0, 12);
		// Set display background and font
		MapDisplay.setBackground(new java.awt.Color(1, 1, 1)); // Set background color. Black seems to work well
		//MapDisplay.setFont(cNew); // Need a monospaced typeface and size to match
		MapDisplay.setFont(monoSpace);
		
		add(MapDisplay);
    }
    
	//TODO REWRITE TO MAKE EVEN FASTER
    
    // Called to display an image.  Takes in a World class.
    public void display(World canvas) throws BadLocationException {    	
    	// Create a document for editing
    	current=(StyledDocument) MapDisplay.getDocument();
		
		// Clear the current document in preparation of update
    	current.remove(0, current.getLength()); //setText("");
		MapDisplay.setDocument(altDisplay);
		
		// Cycle through and world array and first display land
		for (int i = 0; i < canvas.world.length; i++) {
			for (int j = 0; j < canvas.world[0].length; j++) {
				addSpace(current, " ");
				drawLand(current, canvas, D.stringifyLand(canvas.world[j][i].landType), canvas.world[j][i].landType, j, i);
				addSpace(current, " ");
			}
			addSpace(current, "\n");
		}
		// Once update is complete, set display to updated document
		MapDisplay.setDocument(current);
    }
    
    // Set up the 'blank' document that we will be stitching behind the real one
    public void makeAlt(World canvas) throws BadLocationException{
		altDisplay=new DefaultStyledDocument();
    	for (int i = 0; i < canvas.world.length; i++) {
			for (int j = 0; j < canvas.world[0].length; j++) {
				addSpace(altDisplay, " ");
				drawAltLand(altDisplay, canvas, D.stringifyLand(canvas.world[j][i].landType), canvas.world[j][i].landType, j, i);
				addSpace(altDisplay, " ");
			}
			addSpace(altDisplay, "\n");
		}
    }

    //TODO Use highlight to paint land and water in background.
	public void drawLand(Document workingCanvas,World canvas, String str, int type, int xpos, int ypos) throws BadLocationException {
		
		// Set up text styles for landtypes.  Also set up highlights for possible use later
		Style blank = MapDisplay.addStyle("blank", null);
		StyleConstants.setForeground(blank, Color.white);
		DefaultHighlighter.DefaultHighlightPainter blankH=new DefaultHighlighter.DefaultHighlightPainter(Color.white);
		Style saltwater = MapDisplay.addStyle("saltwater", null);
		StyleConstants.setForeground(saltwater, Color.blue);
		DefaultHighlighter.DefaultHighlightPainter saltwaterH=new DefaultHighlighter.DefaultHighlightPainter(Color.blue);
		Color waterC = new Color(156, 245, 245);
		Style water = MapDisplay.addStyle("water", null);
		StyleConstants.setForeground(water, waterC);
		DefaultHighlighter.DefaultHighlightPainter waterH=new DefaultHighlighter.DefaultHighlightPainter(waterC);
		Style land = MapDisplay.addStyle("land", null);
		Color landC = Color.green.darker();
		StyleConstants.setForeground(land, landC);
		DefaultHighlighter.DefaultHighlightPainter landH=new DefaultHighlighter.DefaultHighlightPainter(landC);
		
		// Get the current index of the cursor for inputing next character
		int index=workingCanvas.getLength();

		// if we are inputting a creature, item, or structure, do that logic
		if (canvas.world[xpos][ypos].creature != null && canvas.world[xpos][ypos].creature.creatureType != 0) {
			drawCreature(workingCanvas, D.stringifyCreature(canvas.world[xpos][ypos].creature), canvas.world[xpos][ypos].creature.creatureType);
		} else if (canvas.world[xpos][ypos].structure != null && canvas.world[xpos][ypos].structure.structureType != 0) {
			appendStructure(workingCanvas, D.stringifyStructure(canvas.world[xpos][ypos].structure), 0);
		} else if (canvas.world[xpos][ypos].item[0] != 0) {
			drawItem(workingCanvas, D.stringifyItem(canvas.world[xpos][ypos].item[0]), 0);
		} else {
			// If no special item, compare land values with data class
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
	}
	
	// Secondary draw land for 'blank' document.  Rewrite to have only one function
	public void drawAltLand(Document workingCanvas,World canvas, String str, int type, int xpos, int ypos) throws BadLocationException {
		
		// Set up text styles for landtypes.  Also set up highlights for possible use later
		Style blank = MapDisplay.addStyle("blank", null);
		StyleConstants.setForeground(blank, Color.white);
		DefaultHighlighter.DefaultHighlightPainter blankH=new DefaultHighlighter.DefaultHighlightPainter(Color.white);
		Style saltwater = MapDisplay.addStyle("saltwater", null);
		StyleConstants.setForeground(saltwater, Color.blue);
		DefaultHighlighter.DefaultHighlightPainter saltwaterH=new DefaultHighlighter.DefaultHighlightPainter(Color.blue);
		Color waterC = new Color(156, 245, 245);
		Style water = MapDisplay.addStyle("water", null);
		StyleConstants.setForeground(water, waterC);
		DefaultHighlighter.DefaultHighlightPainter waterH=new DefaultHighlighter.DefaultHighlightPainter(waterC);
		Style land = MapDisplay.addStyle("land", null);
		Color landC = Color.green.darker();
		StyleConstants.setForeground(land, landC);
		DefaultHighlighter.DefaultHighlightPainter landH=new DefaultHighlighter.DefaultHighlightPainter(landC);
		
		// Get the current index of the cursor for inputing next character
		int index=workingCanvas.getLength();

		// Compare land values with data class
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
	public void drawCreature(Document workingCanvas, String str, int type) throws BadLocationException {
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
	public void appendStructure(Document workingCanvas, String str, int type) throws BadLocationException {
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

	// Function to draw item. Will pull and compare with data class
	public void drawItem(Document workingCanvas, String str, int type) throws BadLocationException {
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
	public void addSpace(Document workingCanvas, String str) throws BadLocationException {
		workingCanvas.insertString(workingCanvas.getLength(), str, null);
	}
}
