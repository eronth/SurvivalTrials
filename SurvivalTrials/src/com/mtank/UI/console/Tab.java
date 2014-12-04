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
	
	Tab(int _maxLines, String _title) {
		currentLine = 0;
		title = _title;
		maxLines = _maxLines;
		lines = new LimitedList<String>(_maxLines);
	}
	
	public void write(String _line) {
		String temp = lines.getLast() + _line;
		lines.removeLast();
		lines.add(temp);
	}
	
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
