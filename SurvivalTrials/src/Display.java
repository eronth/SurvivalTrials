import java.awt.*;

import javax.swing.*;
import javax.swing.text.*;


public class Display extends JFrame {

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
    
  //TODO REWRITE TO MAKE FASTER
  public void display(World test) throws BadLocationException {
	  	MapDisplay.setText("");
	    Document temp = MapDisplay.getDocument();
	    Document blank = new DefaultStyledDocument();
	    MapDisplay.setDocument(blank);
        for (int i = 0; i < test.world.length; i++) {
            for (int j = 0; j < test.world[0].length; j++) {
            	addSpace(temp, " ");
                if (test.world[j][i].creature != null && test.world[j][i].creature.creatureType != 0) {
                    appendCreature(temp, D.stringifyCreature(test.world[j][i].creature), test.world[j][i].creature.creatureType);
                } else if (test.world[j][i].structure != 0) {
                    appendStructure(D.stringifyStructure(test.world[j][i].structure), 0);
                } else if (test.world[j][i].item[0] != 0) {
                    appendItem(D.stringifyItem(test.world[j][i].item[0]), 0);
                } else {
                	appendLand(temp, D.stringifyLand(test.world[j][i].landType), test.world[j][i].landType);
                }
                addSpace(temp, " ");
            }
            addSpace(temp, "\n");
        }
	    MapDisplay.setDocument(temp);
    }

    public void appendLand(Document tempDoc, String str, int type) throws BadLocationException {
        StyledDocument document = (StyledDocument) MapDisplay.getDocument();
        Style blank = MapDisplay.addStyle("blank", null);
        StyleConstants.setForeground(blank, Color.white);
        Style saltwater = MapDisplay.addStyle("saltwater", null);
        StyleConstants.setForeground(saltwater, Color.blue);
        Color waterC = new Color(156, 245, 245);
        Style water = MapDisplay.addStyle("water", null);
        StyleConstants.setForeground(water, waterC);
        Style land = MapDisplay.addStyle("land", null);
        //Color dkGreen = Color.green.darker();
        StyleConstants.setForeground(land, Color.green.darker());
        
        switch (type) {
            case D.SALTWATER:
            	tempDoc.insertString(tempDoc.getLength(), str, saltwater);
                break;
            case D.NONE:
            	tempDoc.insertString(tempDoc.getLength(), str, land);
                break;
            case D.WATER:
            	tempDoc.insertString(tempDoc.getLength(), str, water);
                break;
            default:
            	tempDoc.insertString(tempDoc.getLength(), str, null);
                break;
        }
    }
    
    public void appendCreature(Document tempDoc, String str, int type) throws BadLocationException {
        StyledDocument document = (StyledDocument) MapDisplay.getDocument();
        Style blank = MapDisplay.addStyle("blank", null);
        StyleConstants.setForeground(blank, Color.white);
        
        switch (type) {
            case D.NONE:
            	tempDoc.insertString(tempDoc.getLength(), str, blank);
                break;
            default:
            	tempDoc.insertString(tempDoc.getLength(), str, null);
                break;
        }
    }
    
    public void appendStructure(String str, int type) throws BadLocationException {
        StyledDocument document = (StyledDocument) MapDisplay.getDocument();
        Style blank = MapDisplay.addStyle("blank", null);
        StyleConstants.setForeground(blank, Color.white);
        
        switch (type) {
            case D.NONE:
                document.insertString(document.getLength(), str, blank);
                break;
            default:
                document.insertString(document.getLength(), str, null);
                break;
        }
    }
    
    public void appendItem(String str, int type) throws BadLocationException {
        StyledDocument document = (StyledDocument) MapDisplay.getDocument();
        Style blank = MapDisplay.addStyle("blank", null);
        StyleConstants.setForeground(blank, Color.white);
        
        switch (type) {
            case D.NONE:
                document.insertString(document.getLength(), str, blank);
                break;
            default:
                document.insertString(document.getLength(), str, null);
                break;
        }
    }
    
    public void addSpace(Document tempDoc, String str) throws BadLocationException {
        StyledDocument document = (StyledDocument) MapDisplay.getDocument(); 
        tempDoc.insertString(tempDoc.getLength(), str, null);
    }
}
