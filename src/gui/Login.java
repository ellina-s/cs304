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

public class Login extends JPanel implements ActionListener
{
	// ams represents a connection to an MySQL database
	private DatabaseConnection ams = DatabaseConnection.getInstance();

	// user is allowed 3 login attempts
	private int loginAttempts = 0;

	// components of the login window
	private JFrame mainFrame;
	private JTextField usernameField = new JTextField(10);
	private JPasswordField passwordField = new JPasswordField(10);	   
	private JLabel usernameLabel = new JLabel("Enter username:  ");
	private JLabel passwordLabel = new JLabel("Enter password:  ");
	private JButton loginButton = new JButton("Log In");
	private JButton exitButton = new JButton("Exit");

	/*
	 * Default constructor. The login window is constructed here.
	 */
	public Login(JFrame mainFrame_) {
		mainFrame = mainFrame_;
		passwordField.setEchoChar('*');

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		setLayout(gb);
		setBorder(BorderFactory.createEmptyBorder(12, 12, 11, 11));

		// place the username label 
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(10, 10, 5, 0);
		gb.setConstraints(usernameLabel, c);
		add(usernameLabel);

		// place the text field for the username 
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 0, 5, 10);
		gb.setConstraints(usernameField, c);
		add(usernameField);

		// place password label
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 10, 10, 0);
		gb.setConstraints(passwordLabel, c);
		add(passwordLabel);

		// place the password field 
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 10, 10);
		gb.setConstraints(passwordField, c);
		add(passwordField);

		// place the login button
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(10, 5, 5, 5);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(loginButton, c);
		add(loginButton);
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 10, 5, 10);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(exitButton, c);
		add(exitButton);
		
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}		
		});

		// end of layout

		// Register password field and OK button with action event handler.
		// An action event is generated when the return key is pressed while 
		// the cursor is in the password field or when the OK button is pressed.
		passwordField.addActionListener(this);
		loginButton.addActionListener(this);

		// initially, place the cursor in the username text field
		usernameField.grabFocus(); 
	}


	/*
	 * event handler for password field and OK button
	 */ 	    
	public void actionPerformed(ActionEvent e) {
		if (ams.connect(usernameField.getText(), String.valueOf(passwordField.getPassword()))) {
			
			mainFrame.remove(this);
			mainFrame.revalidate();


			System.out.println("You entered valid credentials.");
		}
	}
}

