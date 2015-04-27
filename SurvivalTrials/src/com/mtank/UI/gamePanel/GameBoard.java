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
 * This is a panel that will display all characters, structures, and items.
 * Meant to be overlaid over a LandBoard
 * @author Tim
 *
 */
public class GameBoard extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextPane gameBoardTextPane;
	private StyledDocument gameBoardDocument;
	private StyledDocument bufferedGameBoardDocument;
	
	private File fontFile = new File("fonts/DejaVuSansMono.ttf");
	private Font monoSpacedFont;
	
	private int fontSize = 12;
	
	/*
	 * STYLE CONSTANTS:
	 * Used for painting the creatures and characters.
	 * Initialized in initialize() so that it is not recreated each time drawItem/drawStructure/drawCreature is called.
	 */
	Style blank;
	DefaultHighlighter.DefaultHighlightPainter blankHighlighter;

    
    /**
     * Create a new GameBoard. Is a panel that contains a JTextPane that holds a graphical 
     * Representation of the characters and creatures.
     */
	public GameBoard() {
    	// Set up main display window
		gameBoardTextPane = new JTextPane();
		
		initilize();
		// Try to import custom font. Set to font or backup font.
		setFontSize(fontSize);
		
		add(gameBoardTextPane);
    }
	
	/**
	 * Create the gameBoardDocument StyledDocument and the bufferedGameBoardDocument StyledDocument.
	 * Initializes the styles and highlighters used to draw the land.
	 */
	private void initilize() {
		gameBoardDocument = new DefaultStyledDocument();
		bufferedGameBoardDocument = new DefaultStyledDocument();

		gameBoardTextPane.setFocusable(false);
		gameBoardTextPane.setOpaque(false);
		// Initialize the styles
		blank = gameBoardDocument.addStyle("blank", null);
		StyleConstants.setForeground(blank, GraphicColor.BLANK);
		blankHighlighter = new DefaultHighlighter.DefaultHighlightPainter(GraphicColor.BLANK);
		
		try {
			updateGameMap(mainClass.island);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("FAILED MAKING");
		}
	}
    
    /**
     * Updates the font size for the display.
     */
    public void setFontSize(int _size){
    	if(_size >= 1){
    		fontSize = _size;
    		try {
    			monoSpacedFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.BOLD, fontSize);
    		} catch (FontFormatException e) {
    			//In the event custom font cannot be created, fall back to Monospaced
    			monoSpacedFont = new Font("Monospaced", Font.BOLD, fontSize);
    		} catch (IOException e) {
    			//In the event custom font cannot be loaded, fall back to Monospaced
    			monoSpacedFont = new Font("Monospaced", Font.BOLD, fontSize);
    		}
    		gameBoardTextPane.setFont(monoSpacedFont);
    	}
    }
    
    /**
     * Updates the font size for the display.
     */
    public void setFontSize(int _size, boolean bold){
    	int style = (bold) ? Font.BOLD : Font.PLAIN;
    	if(_size >= 1){
    		fontSize = _size;
    		try {
    			monoSpacedFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(style, fontSize);
    		} catch (FontFormatException e) {
    			//In the event custom font cannot be created, fall back to Monospaced
    			monoSpacedFont = new Font("Monospaced", style, fontSize);
    		} catch (IOException e) {
    			//In the event custom font cannot be loaded, fall back to Monospaced
    			monoSpacedFont = new Font("Monospaced", style, fontSize);
    		}
    		gameBoardTextPane.setFont(monoSpacedFont);
    	}
    }
    
    /**
     * Redraw the current land mass and paint it to the panel.
     * @param canvas - World that the display is to be based off of.
     * @throws BadLocationException
     */
    public void updateGameMap(World canvas) throws BadLocationException{
		long startTime=System.currentTimeMillis(),elapsedTime = 0;
    	gameBoardTextPane.setDocument(bufferedGameBoardDocument);
		for (int i = 0; i < canvas.world.length; i++) {
			for (int j = 0; j < canvas.world[0].length; j++) {
				int _landType = canvas.world[j][i].landType;
				addSpace(gameBoardDocument, " ");
				setFontSize(fontSize, true);
				// if there is a creature,structure, or item, they take precedence over land.
				if (canvas.world[j][i].creature != null && canvas.world[j][i].creature.creatureType != 0) {
					drawCreature(gameBoardDocument, Stringify.creature(canvas.world[j][i].creature), canvas.world[j][i].creature.creatureType, _landType);
				} else if (canvas.world[j][i].structure != null && canvas.world[j][i].structure.structureType != 0) {
					drawStructure(gameBoardDocument, Stringify.structure(canvas.world[j][i].structure), 0,_landType);
				} else if (canvas.world[j][i].item.get(0).itemType != 0) {
					drawItem(gameBoardDocument, Stringify.item(canvas.world[j][i].item.get(0)), 0,_landType);
				} else {
					addSpace(gameBoardDocument, " ");
				}
				setFontSize(fontSize, false);
				addSpace(gameBoardDocument, " ");
			}
			addSpace(gameBoardDocument, "\n");
		}
		gameBoardTextPane.setDocument(gameBoardDocument);
		bufferedGameBoardDocument = gameBoardDocument;
		elapsedTime=System.currentTimeMillis()-startTime;
		System.out.println("Finished updating game paint - "+"Elapsed Time:"+elapsedTime);
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

	/**
	 * Calls insertString to input a STRUCTURE with into workingCanvas
	 * @param workingCanvas - A StyledDocument that will be inserted into
	 * @param str - String character that will be inserted into workingCanvas. Single character
	 * @param type - The type of the STRUCTURE used to determine the graphic to be inserted
	 * @param landType - Int of the land that the STRUCTURE sits on. Used to draw land under it.
	 * @throws BadLocationException
	 */
	public void drawStructure(StyledDocument workingCanvas, String str, int type, int landType) throws BadLocationException {
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

	/**
	 * Calls insertString to input an ITEM with into workingCanvas
	 * @param workingCanvas - A StyledDocument that will be inserted into
	 * @param str - String character that will be inserted into workingCanvas. Single character
	 * @param type - The type of the ITEM used to determine the graphic to be inserted
	 * @param landType - Int of the land that the ITEM sits on. Used to 
	 * @throws BadLocationException
	 */
	public void drawItem(StyledDocument workingCanvas, String str, int type, int landType) throws BadLocationException {
		// Switch will be based off of data class once implemented to determine color
		switch (type) {
			case TypeValue.NONE:
				workingCanvas.insertString(workingCanvas.getLength(), str, blank);
				break;
		    default:
		    	workingCanvas.insertString(workingCanvas.getLength(), str, null);
		        break;
		}
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
