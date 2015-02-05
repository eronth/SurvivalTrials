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
import com.mtank.game.mainClass;
import com.mtank.world.World;

/**
 * This is a panel that will display the land-mass, water, and lakes.
 * Meant to be under a GameBoard.
 * @author Tim
 *
 */
public class LandBoard extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextPane landBoardTextPane;
	private StyledDocument landBoardDocument;
	private StyledDocument bufferedLandBoardDocument;
	
	private File fontFile = new File("fonts/DejaVuSansMono.ttf");
	private Font monoSpacedFont;
	
	private int fontSize = 12;
	
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

    
    /**
     * Create a new LandBoard. Is a panel that contains a JTextPane that holds a graphical 
     * Representation of the island.
     */
	public LandBoard() {
    	// Set up main display window
		landBoardTextPane = new JTextPane();
		
		initilize();
		// Try to import custom font. Set to font or backup font.
		setFontSize(fontSize);
		
		add(landBoardTextPane);
    }
	
	/**
	 * Create the landBoardDocument StyledDocument and the bufferedLandBoardDocument StyledDocument.
	 * Initializes the styles and highlighters used to draw the land.
	 */
	private void initilize() {
		landBoardDocument = new DefaultStyledDocument();
		bufferedLandBoardDocument = new DefaultStyledDocument();

		landBoardTextPane.setFocusable(false);
		landBoardTextPane.setOpaque(false);
		// Initialize the styles and highlighters
		blank = landBoardTextPane.addStyle("blank", null);
		StyleConstants.setForeground(blank, GraphicColor.BLANK);
		blankHighlighter = new DefaultHighlighter.DefaultHighlightPainter(GraphicColor.BLANK);
		saltwater = landBoardTextPane.addStyle("saltwater", null);
		StyleConstants.setForeground(saltwater, GraphicColor.SALTWATER);
		saltwaterHighlighter = new DefaultHighlighter.DefaultHighlightPainter(GraphicColor.SALTWATER);
		water = landBoardTextPane.addStyle("water", null);
		StyleConstants.setForeground(water, GraphicColor.WATER);
		waterHighlighter = new DefaultHighlighter.DefaultHighlightPainter(GraphicColor.WATER);
		land = landBoardTextPane.addStyle("land", null);
		StyleConstants.setForeground(land, GraphicColor.LAND);
		landHighlighter = new DefaultHighlighter.DefaultHighlightPainter(GraphicColor.LAND);
		//-------------------------------------------------------------
		// BIOMES STYLES
		//-------------------------------------------------------------
		dirt = landBoardTextPane.addStyle("dirt", null);
		StyleConstants.setForeground(dirt, GraphicColor.DIRT);
		dirtHighlighter = new DefaultHighlighter.DefaultHighlightPainter(GraphicColor.DIRT);
		grass = landBoardTextPane.addStyle("grass", null);
		StyleConstants.setForeground(grass, GraphicColor.GRASS);
		grassHighlighter = new DefaultHighlighter.DefaultHighlightPainter(GraphicColor.GRASS);
		stone = landBoardTextPane.addStyle("stone", null);
		StyleConstants.setForeground(stone, GraphicColor.STONE);
		stoneHighlighter = new DefaultHighlighter.DefaultHighlightPainter(GraphicColor.STONE);
		sand = landBoardTextPane.addStyle("sand", null);
		StyleConstants.setForeground(sand, GraphicColor.SAND);
		sandHighlighter = new DefaultHighlighter.DefaultHighlightPainter(GraphicColor.SAND);
		
		try {
			makeLandMap(mainClass.island);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("FAILED MAKING");
		}
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
    		landBoardTextPane.setFont(monoSpacedFont);
    	}
    }
    
    /**
     * Initially create and draw the land mass of the island.
     * @param canvas - World that the display is to be based off of.
     * @throws BadLocationException
     */
    public void makeLandMap(World canvas) throws BadLocationException{
		long startTime=System.currentTimeMillis(),elapsedTime = 0;
    	for (int i = 0; i < canvas.world.length; i++) {
			for (int j = 0; j < canvas.world[0].length; j++) {
				addSpace(landBoardDocument, " ");
				drawLand(landBoardDocument, canvas, Stringify.land(canvas.world[j][i].landType), canvas.world[j][i].landType);
				addSpace(landBoardDocument, " ");
			}
			addSpace(landBoardDocument, "\n");
		}
		bufferedLandBoardDocument = landBoardDocument;
		elapsedTime=System.currentTimeMillis()-startTime;
		System.out.println("Finished initial land paint - "+"Elapsed Time:"+elapsedTime);
    }
    
    /**
     * Redraw the current land mass and paint it to the panel.
     * @param canvas - World that the display is to be based off of.
     * @throws BadLocationException
     */
    public void updateLandMap(World canvas) throws BadLocationException{
		long startTime=System.currentTimeMillis(),elapsedTime = 0;
    	landBoardTextPane.setDocument(bufferedLandBoardDocument);
    	
		for (int i = 0; i < canvas.world.length; i++) {
			for (int j = 0; j < canvas.world[0].length; j++) {
				addSpace(landBoardDocument, " ");
				drawLand(landBoardDocument, canvas, Stringify.land(canvas.world[j][i].landType), canvas.world[j][i].landType);
				addSpace(landBoardDocument, " ");
			}
			addSpace(landBoardDocument, "\n");
		}
		landBoardTextPane.setDocument(landBoardDocument);
		bufferedLandBoardDocument = landBoardDocument;
		elapsedTime=System.currentTimeMillis()-startTime;
		System.out.println("Finished updating land paint - "+"Elapsed Time:"+elapsedTime);
    }

	/**
	 * Calls insertString to input a LAND with into workingCanvas
	 * @param workingCanvas - A StyledDocument that will be inserted into
	 * @param str - String character that will be inserted into workingCanvas. Single character
	 * @param type - The type of the LAND used to determine the graphic to be inserted
	 * @throws BadLocationException
	 */
	public void drawLand(StyledDocument workingCanvas,World canvas, String str, int type) throws BadLocationException {
		Style selectedStyle = landBoardTextPane.addStyle("selectedStyle", null);
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
		landBoardTextPane.getHighlighter().addHighlight(index-1, index+2, selectedHighlighter);
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
