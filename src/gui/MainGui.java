package gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Contains main() to run the program.
 * Creates a the main gui frame and login dialog.
 */

public class MainGui extends JFrame{

	private static MainGui gui;

	/*
	 * Constructor.
	 */
	public MainGui(){
	}

	public static void initGui(){

		gui.setTitle("Allegro Music Store");
		gui.setSize(500, 500);

		// Closing the window
		gui.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});

		// Center the frame
		Dimension d = gui.getToolkit().getScreenSize();
		Rectangle r = gui.getBounds();
		gui.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
		
		gui.setVisible(true);

	}


	public static void main(String[] args) {

		gui = new MainGui();
		initGui();

		// create the login window
		Login lw = new Login();

	}
}
