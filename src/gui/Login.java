package gui;

import javax.swing.*;

import connection.DatabaseConnection;

import java.awt.*;
import java.awt.event.*;

/**
 * The login window.
 * Based on the CPSC 304 Tutorial.
 * Reference: http://www.cs.ubc.ca/~laks/cpsc304/Swing/jdbc_swing.html
 */

public class Login extends JDialog implements ActionListener
{
	// MvbOracleConnection represents a connection to an Oracle database
	private DatabaseConnection ams = DatabaseConnection.getInstance();

	// user is allowed 3 login attempts
	private int loginAttempts = 0;

	// components of the login window
	private JTextField usernameField = new JTextField(10);
	private JPasswordField passwordField = new JPasswordField(10);	   
	private JLabel usernameLabel = new JLabel("Enter username:  ");
	private JLabel passwordLabel = new JLabel("Enter password:  ");
	private JButton loginButton = new JButton("Log In");
	private JFrame mainFrame; // ***


	/*
	 * Default constructor. The login window is constructed here.
	 */
	public Login()
	{
		//super(parent, "User Login", true);
		//setResizable(false);

		mainFrame = new JFrame("User Login"); // ***

		passwordField.setEchoChar('*');

		// content pane for the login window
		JPanel loginPane = new JPanel();
		mainFrame.setContentPane(loginPane); // ***


		/*
		 * layout components using the GridBag layout manager
		 */ 

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		loginPane.setLayout(gb);
		loginPane.setBorder(BorderFactory.createEmptyBorder(12, 12, 11, 11));

		// place the username label 
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(10, 10, 5, 0);
		gb.setConstraints(usernameLabel, c);
		loginPane.add(usernameLabel);

		// place the text field for the username 
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 0, 5, 10);
		gb.setConstraints(usernameField, c);
		loginPane.add(usernameField);

		// place password label
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 10, 10, 0);
		gb.setConstraints(passwordLabel, c);
		loginPane.add(passwordLabel);

		// place the password field 
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 10, 10);
		gb.setConstraints(passwordField, c);
		loginPane.add(passwordField);

		// place the login button
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 10, 5, 10);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(loginButton, c);
		loginPane.add(loginButton);

		// end of layout

		// Register password field and OK button with action event handler.
		// An action event is generated when the return key is pressed while 
		// the cursor is in the password field or when the OK button is pressed.
		passwordField.addActionListener(this);
		loginButton.addActionListener(this);

		// anonymous inner class for closing the window
		addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{ 
				System.exit(0); 
			}
		});


		// size the window to obtain a best fit for the components
		mainFrame.pack();

		// center the frame
		Dimension d = mainFrame.getToolkit().getScreenSize();
		Rectangle r = mainFrame.getBounds();
		mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		// make the window visible
		mainFrame.setVisible(true);

		// initially, place the cursor in the username text field
		usernameField.requestFocus();	  
	}


	/*
	 * event handler for password field and OK button
	 */ 	    
	public void actionPerformed(ActionEvent e) 
	{
		if (ams.connect(usernameField.getText(), 
				String.valueOf(passwordField.getPassword())))
		{
			// if the username and password are valid, 
			// get rid of the login window
			mainFrame.dispose();

			System.out.println("You entered valid credentials.");
		}
		else
		{
			loginAttempts++;

			if (loginAttempts >= 3)
			{
				dispose();
				System.exit(0);
			}
			else
			{
				// clear the password
				passwordField.setText("");
			}
		}  
	}     
}

