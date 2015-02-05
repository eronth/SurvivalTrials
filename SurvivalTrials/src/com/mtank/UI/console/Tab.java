package com.mtank.UI.console;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Tab {
	
	private int maxLines;
	private int currentLine;
	public String title;
	public LimitedList<String> lines;
	public JPanel tabPanel;
	public JTextArea tabTextArea;
	public static boolean textAreaInUse = false;
	
	/**
	 * Create a new instance of a tab.
	 * @param _maxLines - Int maximum number of lines to store and show.
	 * @param _title - String title of the new tab instance.
	 */
	Tab(int _maxLines, String _title) {
		currentLine = 0;
		title = _title;
		maxLines = _maxLines;
		lines = new LimitedList<String>(_maxLines);
	}
	
	/**
	 * Add a new line to the individual tab.
	 * @param _line - String message that will be added, stored, and displayed. Will not make a new line and will 
	 * be added to the previous line.
	 */
	public void write(String _line) {
		String temp = lines.getLast() + _line;
		lines.removeLast();
		lines.add(temp);
	}
	
	/**
	 * Add a new line to the individual tab.
	 * @param _line - String message that will be added, stored, and displayed. Will create each _line on a new line with an endline.
	 */
	public void writeln(String _line) {
		lines.add(_line);
		if(currentLine < maxLines){
			currentLine++;
		}
	}
	
	public int getMaxLines() {
		return maxLines;
	}
}
