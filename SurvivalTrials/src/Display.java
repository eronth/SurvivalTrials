import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;


public class Display extends JFrame {

	//Added to make Eclipse Happy
	private static final long serialVersionUID = 1L;
	
	private JTextPane MapDisplay;
    
    public Display() {
		MapDisplay = new JTextPane();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setBackground(new Color(255, 255, 255));
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		setSize(1100,800);
		
		MapDisplay.setBackground(new java.awt.Color(1, 1, 1));
		MapDisplay.setFont(new Font("Courier New", 0, 12));
		
		add(MapDisplay);
    }
    
	//TODO REWRITE TO MAKE EVEN FASTER
    public void display(World canvas) throws BadLocationException {
    	Document current = MapDisplay.getDocument();
		Document blank = new DefaultStyledDocument();
    	current.remove(0, current.getLength()); //setText("");
		MapDisplay.setDocument(blank);
		for (int i = 0; i < canvas.world.length; i++) {
			for (int j = 0; j < canvas.world[0].length; j++) {
				addSpace(current, " ");
				drawLand(current, canvas, D.stringifyLand(canvas.world[j][i].landType), canvas.world[j][i].landType, j, i);
				addSpace(current, " ");
			}
			addSpace(current, "\n");
		}
		MapDisplay.setDocument(current);
    }

    //TODO Use highlight to paint land and water in background.
	public void drawLand(Document workingCanvas,World canvas, String str, int type, int xpos, int ypos) throws BadLocationException {
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
		
		int index=workingCanvas.getLength();

		if (canvas.world[xpos][ypos].creature != null && canvas.world[xpos][ypos].creature.creatureType != 0) {
			drawCreature(workingCanvas, D.stringifyCreature(canvas.world[xpos][ypos].creature), canvas.world[xpos][ypos].creature.creatureType);
		} else if (canvas.world[xpos][ypos].structure != null && canvas.world[xpos][ypos].structure.structureType != 0) {
			appendStructure(workingCanvas, D.stringifyStructure(canvas.world[xpos][ypos].structure), 0);
		} else if (canvas.world[xpos][ypos].item[0] != 0) {
			drawItem(workingCanvas, D.stringifyItem(canvas.world[xpos][ypos].item[0]), 0);
		} else {
			switch (type) {
				case D.SALTWATER:
					workingCanvas.insertString(index, str, saltwater);
					//workingCanvas.insertString(index, " ", saltwater);
					//MapDisplay.getHighlighter().addHighlight(index, index+1, saltwaterH);
					break;
				case D.NONE:
					workingCanvas.insertString(index, str, land);
					//workingCanvas.insertString(index, "  ", land);
				    //MapDisplay.getHighlighter().addHighlight(index, index+1, landH);
					break;
				case D.WATER:
					workingCanvas.insertString(index, str, water);
					//workingCanvas.insertString(index, " ", water);
				    //MapDisplay.getHighlighter().addHighlight(index, index+1, waterH);
					break;
				default:
					workingCanvas.insertString(index, str, null);
					//workingCanvas.insertString(index, " ", null);
				    //MapDisplay.getHighlighter().addHighlight(index, index+1, blankH);
					break;
			}
		}
	}

	public void drawCreature(Document workingCanvas, String str, int type) throws BadLocationException {
		Style blank = MapDisplay.addStyle("blank", null);
		StyleConstants.setForeground(blank, Color.WHITE);
		
		switch (type) {
			case 1:
		    	workingCanvas.insertString(workingCanvas.getLength(), str, blank);
		        break;
		    default:
		    	workingCanvas.insertString(workingCanvas.getLength(), str, null);
		        break;
		}
	}

	public void appendStructure(Document workingCanvas, String str, int type) throws BadLocationException {
		Style blank = MapDisplay.addStyle("blank", null);
		StyleConstants.setForeground(blank, Color.white);
		
		switch (type) {
		    case D.NONE:
		        workingCanvas.insertString(workingCanvas.getLength(), str, blank);
		        break;
		    default:
		    	workingCanvas.insertString(workingCanvas.getLength(), str, null);
		        break;
		}
	}

	public void drawItem(Document workingCanvas, String str, int type) throws BadLocationException {
		Style blank = MapDisplay.addStyle("blank", null);
		StyleConstants.setForeground(blank, Color.white);
		
		switch (type) {
			case D.NONE:
				workingCanvas.insertString(workingCanvas.getLength(), str, blank);
				break;
		    default:
		    	workingCanvas.insertString(workingCanvas.getLength(), str, null);
		        break;
		}
	}

	public void addSpace(Document workingCanvas, String str) throws BadLocationException {
		workingCanvas.insertString(workingCanvas.getLength(), str, null);
	}
}
