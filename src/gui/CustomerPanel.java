package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tables.Customer;

import com.mysql.jdbc.Connection;

import connection.DatabaseConnection;

public class CustomerPanel extends JPanel {
	private JFrame mainFrame;

	private JButton loginButton = new JButton("Login");
	private JButton registerButton = new JButton("Register");

	private GridLayout buttonLayout = new GridLayout(2,1);


	private DatabaseConnection ams = DatabaseConnection.getInstance();
	private Connection con = (Connection) ams.getConnection();
	private Customer customer = new Customer(con);
	private String[][] data;

	public CustomerPanel(JFrame mainFrame_) {
		mainFrame = mainFrame_;

		loginButton.setFocusable(false);
		registerButton.setFocusable(false);

		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();

				setLayout(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();

				JLabel id = new JLabel("ID:");
				JLabel password = new JLabel("Password:");
				JLabel confirmPassword = new JLabel("Confirm Password:");
				JLabel name = new JLabel("Name:");
				JLabel address = new JLabel("Address:");
				JLabel phoneNumber = new JLabel("Phone Number:");

				final JTextField idField = new JTextField("");
				final JTextField passwordField = new JTextField("");
				final JTextField confirmPasswordField = new JTextField("");
				final JTextField nameField = new JTextField("");
				final JTextField addressField = new JTextField("");
				final JTextField phoneNumberField = new JTextField("");

				JButton registerAccountButton = new JButton("Register Account");
				JButton backButton = new JButton("Back");

				registerAccountButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						data = customer.getCustomer();
						
						if (passwordField.getText().length() > 40 || passwordField.getText().length() < 1) {
							JOptionPane.showMessageDialog(mainFrame,"Password must be between 1 and 40 characters long.");
						}
						else if (nameField.getText().length() > 40 || nameField.getText().length() < 1) {
							JOptionPane.showMessageDialog(mainFrame,"Name must be between 1 and 40 characters long.");
						}
						else if (addressField.getText().length() > 40 || addressField.getText().length() < 1) {
							JOptionPane.showMessageDialog(mainFrame,"Address must be between 1 and 40 characters long.");
						}
						else if (phoneNumberField.getText().length() > 40 || phoneNumberField.getText().length() < 1) {
							JOptionPane.showMessageDialog(mainFrame,"Phone Number must be between 1 and 40 characters long.");
						}
						else if (!passwordField.getText().equals(confirmPasswordField.getText())) {
							JOptionPane.showMessageDialog(mainFrame,"Password fields do not match!");
						}
						else
							try {
								Integer.parseInt(idField.getText());
								
								if (idExists(idField.getText())) {
									JOptionPane.showMessageDialog(mainFrame,"ID already exists, please use a different ID");
								}
								else {
									customer.insertCustomer(Integer.parseInt(idField.getText()),
															passwordField.getText(),
															nameField.getText(),
															addressField.getText(),
															phoneNumberField.getText());
									idField.setText("");
									passwordField.setText("");
									confirmPasswordField.setText("");
									nameField.setText("");
									addressField.setText("");
									phoneNumberField.setText("");
								
								JOptionPane.showMessageDialog(mainFrame,"Account successfully created.");
								}
								
								
							} catch (NumberFormatException exception) {
								JOptionPane.showMessageDialog(mainFrame,"ID must be an integer.");
							}
					}		
				});
				
				
				backButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						removeAll();
						setLayout(buttonLayout);
						add(loginButton);
						add(registerButton);
						
						mainFrame.revalidate();
						mainFrame.repaint();
					}		
				});

				JLabel topLabel = new JLabel("Please enter the fields below.");

				c.gridx = 0;
				c.gridy = 0;
				c.insets = new Insets(10,0,0,10);
				add(topLabel,c);

				c.gridx = 0;
				c.gridy = 1;
				c.insets = new Insets(0,0,0,0);
				c.anchor = GridBagConstraints.WEST;
				add(id,c);
				c.gridx = 0;
				c.gridy = 3;
				c.anchor = GridBagConstraints.WEST;
				add(password,c);
				c.gridx = 0;
				c.gridy = 5;
				c.anchor = GridBagConstraints.WEST;
				add(confirmPassword,c);
				c.gridx = 0;
				c.gridy = 7;
				c.anchor = GridBagConstraints.WEST;
				add(name,c);
				c.gridx = 0;
				c.gridy = 9;
				c.anchor = GridBagConstraints.WEST;
				add(address,c);
				c.gridx = 0;
				c.gridy = 11;
				c.anchor = GridBagConstraints.WEST;
				add(phoneNumber,c);

				c.gridx = 0;
				c.gridy = 2;
				c.ipadx = 200;
				c.anchor = GridBagConstraints.WEST;
				add(idField,c);
				c.gridx = 0;
				c.gridy = 4;
				c.ipadx = 200;
				c.anchor = GridBagConstraints.WEST;
				add(passwordField,c);
				c.gridx = 0;
				c.gridy = 6;
				c.ipadx = 200;
				c.anchor = GridBagConstraints.WEST;
				add(confirmPasswordField,c);
				c.gridx = 0;
				c.gridy = 8;
				c.ipadx = 200;
				c.anchor = GridBagConstraints.WEST;
				add(nameField,c);
				c.gridx = 0;
				c.gridy = 10;
				c.ipadx = 200;
				c.anchor = GridBagConstraints.WEST;
				add(addressField,c);
				c.gridx = 0;
				c.gridy = 12;
				c.ipadx = 200;
				c.anchor = GridBagConstraints.WEST;
				add(phoneNumberField,c);

				c.gridx = 0;
				c.gridy = 13;
				c.ipadx = 0;
				c.insets = new Insets(10,0,0,0);
				c.anchor = GridBagConstraints.CENTER;
				add(registerAccountButton,c);
				
				c.gridx = 0;
				c.gridy = 14;
				c.ipadx = 0;
				c.insets = new Insets(10,0,0,0);
				c.anchor = GridBagConstraints.CENTER;
				add(backButton,c);


				mainFrame.revalidate();
				mainFrame.repaint();

			}		
		});


		setLayout(buttonLayout);
		add(loginButton);
		add(registerButton);
	}

	private boolean idExists(String id) {
		for (int i = 0; i < data.length; i++) {
			if (id.equals(data[i][0])) {
				return true;
			}
		}
		return false;
	}

}
