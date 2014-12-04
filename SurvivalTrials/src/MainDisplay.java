import guiConsole.Console;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.BoxLayout;

import java.awt.GridLayout;
import java.awt.Component;


@SuppressWarnings("serial")
public class MainDisplay extends JFrame {

	private JPanel contentPane;
	

	//Testing Tabbed console display
    private JTabbedPane consolePanel;
    private JPanel mainPanel;
    private JPanel outputPanel;
    private JPanel errorsPanel;
    static private Console tConsole;

	/**
	 * Launch the application.
	 */
	//TODO: Determine if still useful
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//MainDisplay frame = new MainDisplay();
					//frame.setVisible(true);
					new MainDisplay().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public MainDisplay() {
		setForeground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 937, 698);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3);
		
		consolePanel = new JTabbedPane();
		addTabs(consolePanel);
		panel_2.add(consolePanel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panel_2.add(panel_1);
		
		tConsole = new Console();
		panel_3.add(tConsole);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		
		printTest();
	}
	
	private void addTabs(JTabbedPane tabbedPanel) {
		mainPanel = new JPanel();
		JTextArea mainTextArea = new JTextArea(5,50);
		outputPanel = new JPanel();
		JTextArea outputTextArea = new JTextArea(5,50);
		errorsPanel = new JPanel();
		JTextArea errorTextArea = new JTextArea(5,50);
		tabbedPanel.add("Main", mainPanel);
        //PrintStream outputStream = new PrintStream(new CustomOutputStream(mainTextArea));
        //System.setOut(outputStream);
		mainTextArea.setEditable(false);
        mainPanel.add( new JScrollPane( mainTextArea ) );
		tabbedPanel.add("Output", outputPanel);
        outputPanel.add( new JScrollPane( outputTextArea ) );
		tabbedPanel.add("Errors", errorsPanel);
        //PrintStream errorStream = new PrintStream(new CustomOutputStream(errorTextArea));
        //System.setErr(errorStream);
        errorTextArea.setEditable(false);
        errorsPanel.add( new JScrollPane( errorTextArea ) );
	}
	private void printTest() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
            	int i = 0;
                while (true) {
                    tConsole.writeln("Debug", "Debugging.. " + i++);

					//tConsole.writeln("Errors", "Error found.. " + i++ +"/100000");
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
