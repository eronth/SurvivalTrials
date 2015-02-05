package com.mtank.UI.console;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Console extends JPanel {

	Vector<Tab> tabs;
	private JTabbedPane consoleTabPane;
	
	public Console() {
		consoleTabPane = new JTabbedPane();
		tabs = new Vector<Tab>();
		addTab(200, "Debug");
		addTab(200, "Errors");
		add(consoleTabPane);
		this.setVisible(true);
	}
	
	/**
	 * Add a new tab to the internal JTabbedPane
	 * @param _size - The maximum number of saved messages.
	 * @param _title - String title of new tab that will display on its tab.
	 */
	public void addTab(int _size, String _title) {
		Tab newTab = new Tab(_size, _title);
		tabs.add(newTab);
		addTabPanel(consoleTabPane, _title, newTab);
	}
	
	/**
	 * Create a new JPanel and add it to the specific Tab of the JTabbedPane
	 * @param _tabPane - A JTabbedPane you want to add a new tab to
	 * @param _title - String title of new tab that will display on its tab.
	 * @param _tab - The individual tab we are adding to.
	 */
	private void addTabPanel(JTabbedPane _tabPane, String _title, Tab _tab) {
		_tab.tabPanel = new JPanel();
		_tab.tabTextArea = new JTextArea(10, 50);
		_tab.tabTextArea.setEditable(false);
		_tabPane.add(_title, _tab.tabPanel);
		_tab.tabPanel.add( new JScrollPane( _tab.tabTextArea ) );
		updateTab(tabs.size()-1);
	}
	
	/**
	 * Write a single line of text without an endline.  This will add it to the previous line of text.
	 * @param _tab - The String name of the tab you want to add to.
	 * @param _line - The String message you want to append.
	 */
	public void write(String _tab, String _line) {
		for(int i = 0; i < tabs.size(); i++) {
			if(tabs.get(i).title == _tab) {
				tabs.get(i).write(_line);
			}
		}
	}
	
	/**
	 * Write a single line of text without with an endline.
	 * @param _tab - The String name of the tab you want to add to.
	 * @param _line - The String message you want to append.
	 */
	public void writeln(String _tab, String _line) {
		for(int i = 0; i < tabs.size(); i++) {
			if(tabs.get(i).title.toString().equals(_tab.toString())) {
				tabs.get(i).writeln(_line);
			}
		}
	}
	
	/**
	 * Creates a new thread that will update the tab with the newest messages to display.
	 * @param i - the index number of the tab we are updating.
	 */
	private void updateTab(final int i) {
		Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                	tabs.get(i).tabTextArea.setText("");
                	if( !tabs.get(i).lines.isEmpty() ) {
                		Iterator<String> li = tabs.get(i).lines.listIterator();
                		while(li.hasNext()) {
                			try {
                    			tabs.get(i).tabTextArea.append(li.next() + "\n");
                    			tabs.get(i).tabTextArea.setCaretPosition(tabs.get(i).tabTextArea.getDocument().getLength());
                			} catch (ConcurrentModificationException e) {
                				//System.out.println("Error in " + tabs.get(i).title + " at " + li.next());
                				e.printStackTrace();
                			}
                		}
                	}
                	try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        thread.start();
	}
}
