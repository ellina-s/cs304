package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Contains main() to run the program.
 * Creates a the main gui frame and login dialog.
 */

public class MainGui extends JFrame{

	private static JFrame mainFrame;
	private static JPanel mainPanel;
	private static Login loginUI;
	private static ManagerPanel managerPanel;

	/*
	 * Constructor.
	 */
	public MainGui() {
		initGui();
	}

	public static void initGui(){
		mainFrame = new JFrame();
		mainPanel = new JPanel();

		mainFrame.setTitle("Allegro Music Store");
		mainFrame.setSize(500, 500);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Center the frame
		Dimension d = mainFrame.getToolkit().getScreenSize();
		Rectangle r = mainFrame.getBounds();
		mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
		
		mainFrame.setVisible(true);
	}


	public static void main(String[] args) {
		new MainGui();

		// create the login window
		loginUI = new Login(mainFrame);
		mainFrame.add(loginUI);
		mainFrame.setVisible(true);
		
		// Buttons to access the three different user interfaces
		JButton clerkButton = new JButton("Clerks");
		JButton customerButton = new JButton("Customers");
		JButton managerButton = new JButton("Managers");
		clerkButton.setFocusable(false);
		customerButton.setFocusable(false);
		managerButton.setFocusable(false);
		
		managerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.getContentPane().removeAll();
				managerPanel = new ManagerPanel(mainFrame);
				mainFrame.add(managerPanel);
				
				
				mainFrame.revalidate();
			}		
		});
		
		mainPanel.setLayout(new GridLayout(3,1));
		mainPanel.add(clerkButton);
		mainPanel.add(customerButton);
		mainPanel.add(managerButton);
		
		//mainFrame.add(new JLabel("Test"));
		mainFrame.add(mainPanel);
	
	}
}