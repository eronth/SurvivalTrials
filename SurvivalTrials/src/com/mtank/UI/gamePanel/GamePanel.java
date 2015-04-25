package com.mtank.UI.gamePanel;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.mtank.constants.GraphicColor;
import com.mtank.constants.TypeValue;
import com.mtank.game.Stringify;
import com.mtank.world.World;


public class GamePanel extends JPanel {
	//TODO:
	/*
	 * Rewrite GamePanel
	 * Stack character panel over land only panel
	 * Each write statement will be  █ █ █
	 * 
	 */

	//Added to make Eclipse Happy
	private static final long serialVersionUID = 1L;
	
	private JTextPane WorldDisplay;
	private StyledDocument landMap = new DefaultStyledDocument();
	private StyledDocument current = new DefaultStyledDocument();
	private StyledDocument buffered = new DefaultStyledDocument();  //New for triple buffered
	
	private File fontFile = new File("fonts/DejaVuSansMono.ttf");
	private Font monoSpacedFont;
	
	private int fontSize = 12;
	
	boolean switchDisplay = false;
	
	/*
	 * STYLE CONSTANTS:
	 * Used for painting the land.
	 * Initialized in initialize() so that it is not recreated each time draw land is called.
	 */
	Style blank;
	DefaultHighlighter.DefaultHighlightPainter blankHighlighter;
	Style saltwater;
	DefaultHighlighter.DefaultHighlightPainter saltwaterHighlighter;
	Style water;
	DefaultHighlighter.DefaultHighlightPainter waterHighlighter;
	Style land;
	DefaultHighlighter.DefaultHighlightPainter landHighlighter;
	//-------------------------------------------------------------
	// BIOMES STYLES
	//-------------------------------------------------------------
	Style dirt;
	DefaultHighlighter.DefaultHighlightPainter dirtHighlighter;
	Style grass;
	DefaultHighlighter.DefaultHighlightPainter grassHighlighter;
	Style stone;
	DefaultHighlighter.DefaultHighlightPainter stoneHighlighter;
	Style sand;
	DefaultHighlighter.DefaultHighlightPainter sandHighlighter;
    
    public GamePanel() {
    	// Set up main display window
		WorldDisplay = new JTextPane();
		

		WorldDisplay.setFocusable(false);
		
		// Initialize the styles and 
		blank = WorldDisplay.addStyle("blank", null);
		StyleConstants.setForeground(blank, GraphicColor.BLANK);
		blankHighlighter = new DefaultHighlighter.DefaultHighlightPainter(GraphicColor.BLANK);
		saltwater = WorldDisplay.addStyle("saltwater", null);
		StyleConstants.setForeground(saltwater, GraphicColor.SALTWATER);
		saltwaterHighlighter = new DefaultHighlighter.DefaultHighlightPainter(GraphicColor.SALTWATER);
		water = WorldDisplay.addStyle("water", null);
		StyleConstants.setForeground(water, GraphicColor.WATER);
		waterHighlighter = new DefaultHighlighter.DefaultHighlightPainter(GraphicColor.WATER);
		land = WorldDisplay.addStyle("land", null);
		StyleConstants.setForeground(land, GraphicColor.LAND);
		landHighlighter = new DefaultHighlighter.DefaultHighlightPainter(GraphicColor.LAND);
		//-------------------------------------------------------------
		// BIOMES STYLES
		//-------------------------------------------------------------
		dirt = WorldDisplay.addStyle("dirt", null);
		StyleConstants.setForeground(dirt, GraphicColor.DIRT);
		dirtHighlighter = new DefaultHighlighter.DefaultHighlightPainter(GraphicColor.DIRT);
		grass = WorldDisplay.addStyle("grass", null);
		StyleConstants.setForeground(grass, GraphicColor.GRASS);
		grassHighlighter = new DefaultHighlighter.DefaultHighlightPainter(GraphicColor.GRASS);
		stone = WorldDisplay.addStyle("stone", null);
		StyleConstants.setForeground(stone, GraphicColor.STONE);
		stoneHighlighter = new DefaultHighlighter.DefaultHighlightPainter(GraphicColor.STONE);
		sand = WorldDisplay.addStyle("sand", null);
		StyleConstants.setForeground(sand, GraphicColor.SAND);
		sandHighlighter = new DefaultHighlighter.DefaultHighlightPainter(GraphicColor.SAND);
		
		// Set display background and font
		setFontSize(fontSize);
		WorldDisplay.setBackground(GraphicColor.BACKGROUND); // Set background color. Black seems to work well
		
		add(WorldDisplay);
    }
    
    /**
     * Updates the font size for the display.
     * @param _size - The new size for the font. Must be greater than or eqal to 1.
     */
    public void setFontSize(int _size){
    	if(_size >= 1){
    		fontSize = _size;
    		try {
    			monoSpacedFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, fontSize);
    		} catch (FontFormatException e) {
    			//In the event custom font cannot be created, fall back to Monospaced
    			monoSpacedFont = new Font("Monospaced", Font.PLAIN, fontSize);
    		} catch (IOException e) {
    			//In the event custom font cannot be loaded, fall back to Monospaced
    			monoSpacedFont = new Font("Monospaced", Font.PLAIN, fontSize);
    		}
    		WorldDisplay.setFont(monoSpacedFont);
    	}
    }
    
	// Testing
    public void display (World canvas) throws BadLocationException {
    	if( switchDisplay ) {
    		displayToggled( canvas, current, buffered );
    	} else {
    		displayToggled( canvas, buffered, current );
    	}
    	switchDisplay = !switchDisplay;
    }
    
    
    /*
    
    ***//*** Keep for reference 
    
    // Called to display an image.  Takes in a World class.
    public void display1(World canvas) throws BadLocationException {    	
    	// Create a document for editing
    	current=WorldDisplay.getStyledDocument();
		
		// Clear the current document in preparation of update
    	current.remove(0, current.getLength()); //setText("");
		WorldDisplay.setDocument(altDisplay); // WorldDisplay.setDocument(altDisplay);
		
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
		WorldDisplay.setDocument(current);
    }
    
     ***//*** Keep for reference 
    
    */
    
 // Called to display an image.  Takes in a World class.
    public void displayToggled(World canvas, StyledDocument workingCanvas, StyledDocument altCanvas) throws BadLocationException {
		// Clear the current document in preparation of update
    	altCanvas = WorldDisplay.getStyledDocument();
    	workingCanvas = new DefaultStyledDocument();
		WorldDisplay.setDocument(altCanvas);
		
		/*Used to adjust the line height/spacing
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setLineSpacing(sas, -0.2f);
		workingCanvas.setParagraphAttributes(0, 1, sas, false);*/
		
		// Cycle through and world array and first display land
		for (int i = 0; i < canvas.world.length; i++) {
			for (int j = 0; j < canvas.world[0].length; j++) {
				int _landType = canvas.world[j][i].landType;
				addSpace(workingCanvas, " ");
				// if there is a creature,structure, or item, they take precedence over land.
				if (canvas.world[j][i].creature != null && canvas.world[j][i].creature.creatureType != 0) {
					drawCreature(workingCanvas, Stringify.creature(canvas.world[j][i].creature), canvas.world[j][i].creature.creatureType, _landType);
				} else if (canvas.world[j][i].structure != null && canvas.world[j][i].structure.structureType != 0) {
					drawStructure(workingCanvas, Stringify.structure(canvas.world[j][i].structure), 0, _landType);
				} else if (canvas.world[j][i].item[0] != 0) {
					drawItem(workingCanvas, Stringify.item(canvas.world[j][i].item[0]), 0, _landType);
				} else {
					drawLand(workingCanvas, canvas, Stringify.land(canvas.world[j][i].landType), _landType);
				}
				addSpace(workingCanvas, " ");
			}
			addSpace(workingCanvas, "\n");
		}
		
		// Once update is complete, set display to updated document
		WorldDisplay.setDocument(workingCanvas);
    }
    
    // Set up the 'blank' document that we will be stitching behind the real one
    public void makeLandMap(World canvas) throws BadLocationException{
    	for (int i = 0; i < canvas.world.length; i++) {
			for (int j = 0; j < canvas.world[0].length; j++) {
				addSpace(landMap, " ");
				drawLand(landMap, canvas, Stringify.land(canvas.world[j][i].landType), canvas.world[j][i].landType);
				addSpace(landMap, " ");
			}
			addSpace(landMap, "\n");
		}
    }
    
 // Called to display an image.  Takes in a World class.
    public void initWorld(World canvas) throws BadLocationException {
    	makeLandMap(canvas);
    	StyledDocument something=new DefaultStyledDocument();
		
		// Clear the current document in preparation of update
    	something.remove(0, something.getLength()); //setText("");
		
		// Cycle through and world array and first display land
		for (int i = 0; i < canvas.world.length; i++) {
			for (int j = 0; j < canvas.world[0].length; j++) {
				int _landType = canvas.world[j][i].landType;
				addSpace(something, " ");
				// if there is a creature,structure, or item, they take precedence over land.
				if (canvas.world[j][i].creature != null && canvas.world[j][i].creature.creatureType != 0) {
					drawCreature(something, Stringify.creature(canvas.world[j][i].creature), canvas.world[j][i].creature.creatureType, _landType);
				} else if (canvas.world[j][i].structure != null && canvas.world[j][i].structure.structureType != 0) {
					drawStructure(something, Stringify.structure(canvas.world[j][i].structure), 0,_landType);
				} else if (canvas.world[j][i].item[0] != 0) {
					drawItem(something, Stringify.item(canvas.world[j][i].item[0]), 0,_landType);
				} else {
					drawLand(something, canvas, Stringify.land(canvas.world[j][i].landType), _landType);
				}
				addSpace(something, " ");
			}
			addSpace(something, "\n");
		}
		buffered = something;
		current = something;
    }

	/**
	 * Calls insertString to input a LAND with into workingCanvas
	 * @param workingCanvas - A StyledDocument that will be inserted into
	 * @param str - String character that will be inserted into workingCanvas. Single character
	 * @param type - The type of the LAND used to determine the graphic to be inserted
	 * @throws BadLocationException
	 */
	public void drawLand(StyledDocument workingCanvas,World canvas, String str, int type) throws BadLocationException {
		Style selectedStyle = WorldDisplay.addStyle("selectedStyle", null);
		DefaultHighlighter.DefaultHighlightPainter selectedHighlighter = blankHighlighter;
		
		// Get the current index of the cursor for inputing next character
		int index = workingCanvas.getLength();
		switch (type) {
			case TypeValue.Land.SALTWATER:
				selectedStyle = saltwater;
				selectedHighlighter = saltwaterHighlighter;
				break;
			case TypeValue.NONE:
				selectedStyle = land;
				selectedHighlighter = landHighlighter;
				break;
			case TypeValue.Land.WATER:
				selectedStyle = water;
				selectedHighlighter = waterHighlighter;
				break;
			case TypeValue.Land.DIRT:
				selectedStyle = dirt;
				selectedHighlighter = dirtHighlighter;
				break;
			case TypeValue.Land.SAND:
				selectedStyle = sand;
				selectedHighlighter = sandHighlighter;
				break;
			case TypeValue.Land.GRASS:
				selectedStyle = grass;
				selectedHighlighter = grassHighlighter;
				break;
			case TypeValue.Land.STONE:
				selectedStyle = stone;
				selectedHighlighter = stoneHighlighter;
				break;
			default:
				selectedStyle = blank;
				selectedHighlighter = blankHighlighter;
				break;
		}
		workingCanvas.insertString(index, " ", selectedStyle);
		WorldDisplay.getHighlighter().addHighlight(index-1, index+2, selectedHighlighter);
	}
	
	/**
	 * Return a DefaultHighlighter.DefaultHighlightPainter based off of an input land type.
	 * Used for drawItem, drawCreature, and drawStructure
	 * @param landType - the landtype of the given land an item/creature/structure is on.
	 * @return
	 */
	private DefaultHighlighter.DefaultHighlightPainter getLandHighlighter(int landType) {
		DefaultHighlighter.DefaultHighlightPainter selectedHighlighter = blankHighlighter;
		switch (landType) {
			case TypeValue.Land.SALTWATER:
				selectedHighlighter = saltwaterHighlighter;
				break;
			case TypeValue.NONE:
				selectedHighlighter = landHighlighter;
				break;
			case TypeValue.Land.WATER:
				selectedHighlighter = waterHighlighter;
				break;
			case TypeValue.Land.DIRT:
				selectedHighlighter = dirtHighlighter;
				break;
			case TypeValue.Land.SAND:
				selectedHighlighter = sandHighlighter;
				break;
			case TypeValue.Land.GRASS:
				selectedHighlighter = grassHighlighter;
				break;
			case TypeValue.Land.STONE:
				selectedHighlighter = stoneHighlighter;
				break;
			default:
				selectedHighlighter = blankHighlighter;
				break;
		}
		return selectedHighlighter;
	}

	/**
	 * Calls insertString to input a CREATURE with into workingCanvas
	 * @param workingCanvas - A StyledDocument that will be inserted into
	 * @param str - String character that will be inserted into workingCanvas. Single character
	 * @param type - The type of the CREATURE used to determine the graphic to be inserted
	 * @param landType - Int of the land that the CREATURE sits on. Used to draw land under it.
	 * @throws BadLocationException
	 */
	public void drawCreature(StyledDocument workingCanvas, String str, int type, int landType) throws BadLocationException {
		int index = workingCanvas.getLength();
		// Switch will be based off of data class once implemented to determine color
		switch (type) {
			case 1:
		    	workingCanvas.insertString(workingCanvas.getLength(), str, blank);
		        break;
		    default:
		    	workingCanvas.insertString(workingCanvas.getLength(), str, null);
		        break;
		}
		DefaultHighlighter.DefaultHighlightPainter currentLandHighlighter = getLandHighlighter(landType);
		WorldDisplay.getHighlighter().addHighlight(index-1, index+2, currentLandHighlighter);
	}

	/**
	 * Calls insertString to input a STRUCTURE with into workingCanvas
	 * @param workingCanvas - A StyledDocument that will be inserted into
	 * @param str - String character that will be inserted into workingCanvas. Single character
	 * @param type - The type of the STRUCTURE used to determine the graphic to be inserted
	 * @param landType - Int of the land that the STRUCTURE sits on. Used to draw land under it.
	 * @throws BadLocationException
	 */
	public void drawStructure(StyledDocument workingCanvas, String str, int type, int landType) throws BadLocationException {
		int index = workingCanvas.getLength();
		// Switch will be based off of data class once implemented to determine color
		switch (type) {
		    case 1:
		        workingCanvas.insertString(workingCanvas.getLength(), str, blank);
		        break;
		    default:
		    	workingCanvas.insertString(workingCanvas.getLength(), str, null);
		        break;
		}
		DefaultHighlighter.DefaultHighlightPainter currentLandHighlighter = getLandHighlighter(landType);
		WorldDisplay.getHighlighter().addHighlight(index-1, index+2, currentLandHighlighter);
	}

	/**
	 * Calls insertString to input an ITEM with into workingCanvas
	 * @param workingCanvas - A StyledDocument that will be inserted into
	 * @param str - String character that will be inserted into workingCanvas. Single character
	 * @param type - The type of the ITEM used to determine the graphic to be inserted
	 * @param landType - Int of the land that the ITEM sits on. Used to 
	 * @throws BadLocationException
	 */
	public void drawItem(StyledDocument workingCanvas, String str, int type, int landType) throws BadLocationException {
		int index = workingCanvas.getLength();
		// Switch will be based off of data class once implemented to determine color
		switch (type) {
			case TypeValue.NONE:
				workingCanvas.insertString(workingCanvas.getLength(), str, blank);
				break;
		    default:
		    	workingCanvas.insertString(workingCanvas.getLength(), str, null);
		        break;
		}
		DefaultHighlighter.DefaultHighlightPainter currentLandHighlighter = getLandHighlighter(landType);
		WorldDisplay.getHighlighter().addHighlight(index-1, index+2, currentLandHighlighter);
	}

	/**
	 * Calls insertString to input a BLANK_SPACE into workingCanvas
	 * @param workingCanvas - A StyledDocument that will be inserted into
	 * @param str - String character that will be inserted into workingCanvas. Single character
	 * @throws BadLocationException
	 */
	public void addSpace(StyledDocument workingCanvas, String str) throws BadLocationException {
		workingCanvas.insertString(workingCanvas.getLength(), str, null);
	}
}
