package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import transactions.ClerkTransactions;

import com.mysql.jdbc.Connection;

import connection.DatabaseConnection;

public class ClerkPanel extends JPanel {
	private JFrame mainFrame;
	
	private JLabel processRefunds = new JLabel("Process Refunds");
	private JLabel receiptID = new JLabel("receiptID");
	private JLabel upc = new JLabel("upc");
	private JLabel quantity = new JLabel("quantity");
	
	private JTextField receiptIDField = new JTextField("");
	private JTextField upcField0 = new JTextField("");
	private JTextField upcField1 = new JTextField("");
	private JTextField upcField2 = new JTextField("");
	private JTextField upcField3 = new JTextField("");
	private JTextField upcField4 = new JTextField("");
	private JTextField upcField5 = new JTextField("");
	private JTextField upcField6 = new JTextField("");
	private JTextField upcField7 = new JTextField("");
	private JTextField upcField8 = new JTextField("");
	private JTextField upcField9 = new JTextField("");
	private JTextField quantityField0 = new JTextField("");
	private JTextField quantityField1 = new JTextField("");
	private JTextField quantityField2 = new JTextField("");
	private JTextField quantityField3 = new JTextField("");
	private JTextField quantityField4 = new JTextField("");
	private JTextField quantityField5 = new JTextField("");
	private JTextField quantityField6 = new JTextField("");
	private JTextField quantityField7 = new JTextField("");
	private JTextField quantityField8 = new JTextField("");
	private JTextField quantityField9 = new JTextField("");
	
	private JTable returnTable;
	private String[][] data;
	private DefaultTableModel dtm;
	private JScrollPane tablePanel;
	private String[] returnColumnNames = {"upc","quantity","price","total"};
	
	private JButton processRefundButton = new JButton("Process Refund");
	
	private DatabaseConnection ams = DatabaseConnection.getInstance();
	private Connection con = (Connection) ams.getConnection();
	
	public ClerkPanel(JFrame mainFrame_) {
		removeAll();
		mainFrame = mainFrame_;

		mainFrame.setSize(800, 600);
		processRefundButton.setFocusable(false);
		
		Object[][] emptyData = {};

		dtm = new DefaultTableModel(emptyData,returnColumnNames);
		
		returnTable = new JTable(dtm) {
			// Disable editing of table
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {                
				return false;               
			};
		};
		
		tablePanel = new JScrollPane(returnTable);
		tablePanel.setPreferredSize(new Dimension(500, 200));

		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		add(tablePanel,c);
		
		receiptIDField.setColumns(10);
		upcField0.setColumns(10);
		upcField1.setColumns(10);
		upcField2.setColumns(10);
		upcField3.setColumns(10);
		upcField4.setColumns(10);
		upcField5.setColumns(10);
		upcField6.setColumns(10);
		upcField7.setColumns(10);
		upcField8.setColumns(10);
		upcField9.setColumns(10);
		quantityField0.setColumns(10);
		quantityField1.setColumns(10);
		quantityField2.setColumns(10);
		quantityField3.setColumns(10);
		quantityField4.setColumns(10);
		quantityField5.setColumns(10);
		quantityField6.setColumns(10);
		quantityField7.setColumns(10);
		quantityField8.setColumns(10);
		quantityField9.setColumns(10);
		
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 2;
		c.ipady = 30;
		add(processRefunds,c);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.ipady = 0;
		add(receiptID,c);
		c.gridx = 2;
		c.gridy = 2;
		add(receiptIDField,c);

		c.ipady = 10;
		c.gridx = 1;
		c.gridy = 3;
		add(upc,c);
		c.gridx = 2;
		c.gridy = 3;
		add(quantity,c);

		c.ipady = 0;
		c.gridx = 1;
		c.gridy = 4;
		add(upcField0,c);
		c.gridx = 1;
		c.gridy = 5;
		add(upcField1,c);
		c.gridx = 1;
		c.gridy = 6;
		add(upcField2,c);
		c.gridx = 1;
		c.gridy = 7;
		add(upcField3,c);
		c.gridx = 1;
		c.gridy = 8;
		add(upcField4,c);
		c.gridx = 1;
		c.gridy = 9;
		add(upcField5,c);
		c.gridx = 1;
		c.gridy = 10;
		add(upcField6,c);
		c.gridx = 1;
		c.gridy = 11;
		add(upcField7,c);
		c.gridx = 1;
		c.gridy = 12;
		add(upcField8,c);
		c.gridx = 1;
		c.gridy = 13;
		add(upcField9,c);
		
		c.gridx = 2;
		c.gridy = 4;
		add(quantityField0,c);
		c.gridx = 2;
		c.gridy = 5;
		add(quantityField1,c);
		c.gridx = 2;
		c.gridy = 6;
		add(quantityField2,c);
		c.gridx = 2;
		c.gridy = 7;
		add(quantityField3,c);
		c.gridx = 2;
		c.gridy = 8;
		add(quantityField4,c);
		c.gridx = 2;
		c.gridy = 9;
		add(quantityField5,c);
		c.gridx = 2;
		c.gridy = 10;
		add(quantityField6,c);
		c.gridx = 2;
		c.gridy = 11;
		add(quantityField7,c);
		c.gridx = 2;
		c.gridy = 12;
		add(quantityField8,c);
		c.gridx = 2;
		c.gridy = 13;
		add(quantityField9,c);
		
		c.gridx = 1;
		c.gridy = 14;
		c.gridwidth = 2;
		c.insets = new Insets(10,10,10,10);
		add(processRefundButton,c);
		
		
		processRefundButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				processRefund();
			}});
		

	}
	
	private void processRefund() {
		ClerkTransactions ct = new ClerkTransactions(con);
		
		try {
			int receiptId = Integer.parseInt(receiptIDField.getText());
			ArrayList<Integer> upcList = new ArrayList<Integer>();
			ArrayList<Integer> quantityList = new ArrayList<Integer>();
			int year = Calendar.getInstance().get(Calendar.YEAR);
			int month = Calendar.getInstance().get(Calendar.MONTH);
			int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
			String date = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);

			
			if (!upcField0.getText().equals("")) {
				upcList.add(Integer.parseInt(upcField0.getText()));
			}
			if (!upcField1.getText().equals("")) {
				upcList.add(Integer.parseInt(upcField1.getText()));
			}
			if (!upcField2.getText().equals("")) {
				upcList.add(Integer.parseInt(upcField2.getText()));
			}
			if (!upcField3.getText().equals("")) {
				upcList.add(Integer.parseInt(upcField3.getText()));
			}
			if (!upcField4.getText().equals("")) {
				upcList.add(Integer.parseInt(upcField4.getText()));
			}
			if (!upcField5.getText().equals("")) {
				upcList.add(Integer.parseInt(upcField5.getText()));
			}
			if (!upcField6.getText().equals("")) {
				upcList.add(Integer.parseInt(upcField6.getText()));
			}
			if (!upcField7.getText().equals("")) {
				upcList.add(Integer.parseInt(upcField7.getText()));
			}
			if (!upcField8.getText().equals("")) {
				upcList.add(Integer.parseInt(upcField8.getText()));
			}
			if (!upcField9.getText().equals("")) {
				upcList.add(Integer.parseInt(upcField9.getText()));
			}
			
			if (!quantityField0.getText().equals("")) {
				quantityList.add(Integer.parseInt(quantityField0.getText()));
			}
			if (!quantityField1.getText().equals("")) {
				quantityList.add(Integer.parseInt(quantityField1.getText()));
			}
			if (!quantityField2.getText().equals("")) {
				quantityList.add(Integer.parseInt(quantityField2.getText()));
			}
			if (!quantityField3.getText().equals("")) {
				quantityList.add(Integer.parseInt(quantityField3.getText()));
			}
			if (!quantityField4.getText().equals("")) {
				quantityList.add(Integer.parseInt(quantityField4.getText()));
			}
			if (!quantityField5.getText().equals("")) {
				quantityList.add(Integer.parseInt(quantityField5.getText()));
			}
			if (!quantityField6.getText().equals("")) {
				quantityList.add(Integer.parseInt(quantityField6.getText()));
			}
			if (!quantityField7.getText().equals("")) {
				quantityList.add(Integer.parseInt(quantityField7.getText()));
			}
			if (!quantityField8.getText().equals("")) {
				quantityList.add(Integer.parseInt(quantityField8.getText()));
			}
			if (!quantityField9.getText().equals("")) {
				quantityList.add(Integer.parseInt(quantityField9.getText()));
			}
			
			data = ct.returnItem(receiptId, upcList, quantityList, date);
			
	
			for (int row = dtm.getRowCount() - 1; row >= 0; row--) {
				dtm.removeRow(row);
			}
			
			if (data.length > 0) {
				for (int i = 0; i < data.length; i++) {
					dtm.addRow(data[i]);	
				}
			}
			
			JOptionPane.showMessageDialog(mainFrame,"Returns processed.");
			
		} catch (NumberFormatException e) {
			//System.out.println(receiptIDField.getText());
			JOptionPane.showMessageDialog(mainFrame,"One or more fields are invalid.");
		}

	}
}
