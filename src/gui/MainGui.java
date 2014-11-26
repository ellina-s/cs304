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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import connection.DatabaseConnection;

/**
 * Contains main() to run the program.
 * Creates a the main gui frame and login dialog.
 */

public class MainGui extends JFrame{

	private static JFrame mainFrame;
	private static JPanel mainPanel;
	private static Login loginUI;
	private static ClerkPanel clerkPanel;
	private static ManagerPanel managerPanel;
	private static CustomerPanel customerPanel;
	private DatabaseConnection ams;
	
	static JButton clerkButton;
	static JButton customerButton;
	static JButton managerButton;
	
	static int WIDTH = 500;
	static int HEIGHT = 500;
	
	

	/*
	 * Constructor.
	 */
	public MainGui() {
		initGui();
		
		mainFrame.setResizable(false);
		clerkButton = new JButton("Clerks");
		customerButton = new JButton("Customers");
		managerButton = new JButton("Managers");
		
		clerkButton.setFocusable(false);
		customerButton.setFocusable(false);
		managerButton.setFocusable(false);
		
		clerkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.getContentPane().removeAll();
				clerkPanel = new ClerkPanel(mainFrame);
				mainFrame.add(clerkPanel);
				
				
				mainFrame.revalidate();
			}		
		});
		
		customerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.getContentPane().removeAll();
				customerPanel = new CustomerPanel(mainFrame);
				mainFrame.add(customerPanel);
				
				
				mainFrame.revalidate();
			}		
		});
		
		managerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.getContentPane().removeAll();
				managerPanel = new ManagerPanel(mainFrame);
				mainFrame.add(managerPanel);
				
				
				mainFrame.revalidate();
			}		
		});
	}

	public static void initGui(){
		mainFrame = new JFrame();
		mainPanel = new JPanel();

		mainFrame.setTitle("Allegro Music Store");
		mainFrame.setSize(WIDTH, HEIGHT);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Center the frame
		Dimension d = mainFrame.getToolkit().getScreenSize();
		Rectangle r = mainFrame.getBounds();
		mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
		
		mainFrame.setVisible(true);
	}
	


	public static void main(String[] args) {
		// Uncomment this try/catch block to change the Look and Feel of the GUI.
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedLookAndFeelException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		new MainGui();

		// create the login window
		loginUI = new Login(mainFrame);
		loginUI.addWindowListener(new WindowAdapter() { 
		    @Override public void windowClosing(WindowEvent e) { 
		    	System.out.println("reached");
			      System.exit(0);
			    }
			  });
		Dimension d = mainFrame.getToolkit().getScreenSize();
		Rectangle r = mainFrame.getBounds();
		loginUI.pack();
		loginUI.setLocation( (d.width - r.width)/2 + WIDTH/2 - loginUI.getWidth()/2, (d.height - r.height)/2 + HEIGHT/2 - loginUI.getHeight()/2) ;
		loginUI.setVisible(true);
		//mainFrame.add(loginUI);
		//mainFrame.setVisible(true);
	
		
		mainPanel.setLayout(new GridLayout(3,1));
		mainPanel.add(clerkButton);
		mainPanel.add(customerButton);
		mainPanel.add(managerButton);
		
		mainFrame.add(mainPanel);
	
	}
}