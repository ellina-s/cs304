package gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat.Field;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ManagerPanel extends JPanel {
	private JFrame mainFrame;
	private JButton dailySalesReport = new JButton("View Daily Sales Report");
	private JButton topSellingItems = new JButton("View Top Selling Items");
	private JButton addItems = new JButton("Add Items");
	private JButton processOrder = new JButton("Process Order");
	private JTable itemTable;
	private GridLayout buttonLayout = new GridLayout(4,1);
	private String[] columnNames = {
			"upc",
            "title",
            "type",
            "category",
            "company",
            "year",
            "price",
            "stock"};
	

	// Testing values
	private String[][] data = {
		    {"testupc", "testtitle",
		     "testtype", "typecategory", "testcompany", "testyear","testprice","teststock"}
		};
	
	
	private JScrollPane tablePanel;
	private JPanel actionPanel = new JPanel();
	private JPanel fieldPanel = new JPanel();
	
	
	//private JTable itemTable = new JTable();
	
	public ManagerPanel(JFrame mainFrame_) {
		mainFrame = mainFrame_;
		
		dailySalesReport.setFocusable(false);
		topSellingItems.setFocusable(false);
		addItems.setFocusable(false);
		processOrder.setFocusable(false);
		
		dailySalesReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}		
		});
		
		topSellingItems.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}		
		});
		
		addItems.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				setLayout(new GridLayout(2,1));
				
				mainFrame.setSize(500, 600);
				
				itemTable = new JTable(data, columnNames) {
					// Disable editing of table
			        private static final long serialVersionUID = 1L;
			        public boolean isCellEditable(int row, int column) {                
			                return false;               
			        };
			    };
				tablePanel = new JScrollPane(itemTable);
				
				//JLabel addItemLabel = new JLabel("Add Item");
				JLabel upcLabel = new JLabel("upc");
				JLabel titleLabel = new JLabel("title");
				JLabel typeLabel = new JLabel("type");
				JLabel categoryLabel = new JLabel("category");
				JLabel companyLabel = new JLabel("company");
				JLabel yearLabel = new JLabel("year");
				JLabel priceLabel = new JLabel("price");
				JLabel stockLabel = new JLabel("stock");
				JButton addItemButton = new JButton("Add Item");
				
				addItemButton.setFocusable(false);

				JTextField upcField = new JTextField("");
				JTextField titleField = new JTextField("");
				JTextField typeField = new JTextField("");
				JTextField categoryField = new JTextField("");
				JTextField companyField = new JTextField("");
				JTextField yearField = new JTextField("");
				JTextField priceField = new JTextField("");
				JTextField stockField = new JTextField("");

				fieldPanel.setLayout(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();

				c.gridx = 0;
				c.gridy = 0;
				c.ipadx = 5;
				fieldPanel.add(upcLabel, c);
				c.gridx = 1;
				c.gridy = 0;
				c.ipadx = 200;
				fieldPanel.add(upcField, c);
				c.gridx = 0;
				c.gridy = 1;
				c.ipadx = 5;
				fieldPanel.add(titleLabel, c);
				c.gridx = 1;
				c.gridy = 1;
				c.ipadx = 200;
				fieldPanel.add(titleField, c);
				c.gridx = 0;
				c.gridy = 2;
				c.ipadx = 5;
				fieldPanel.add(typeLabel, c);
				c.gridx = 1;
				c.gridy = 2;
				c.ipadx = 200;
				fieldPanel.add(typeField, c);
				c.gridx = 0;
				c.gridy = 3;
				c.ipadx = 5;
				fieldPanel.add(categoryLabel, c);
				c.gridx = 1;
				c.gridy = 3;
				c.ipadx = 200;
				fieldPanel.add(categoryField, c);
				c.gridx = 0;
				c.gridy = 4;
				c.ipadx = 5;
				fieldPanel.add(companyLabel, c);
				c.gridx = 1;
				c.gridy = 4;
				c.ipadx = 200;;
				fieldPanel.add(companyField, c);
				c.gridx = 0;
				c.gridy = 5;
				c.ipadx = 5;
				fieldPanel.add(yearLabel, c);
				c.gridx = 1;
				c.gridy = 5;
				c.ipadx = 200;
				fieldPanel.add(yearField, c);
				c.gridx = 0;
				c.gridy = 6;
				c.ipadx = 5;
				fieldPanel.add(priceLabel, c);
				c.gridx = 1;
				c.gridy = 6;
				c.ipadx = 200;
				fieldPanel.add(priceField, c);
				c.gridx = 0;
				c.gridy = 7;
				c.ipadx = 5;
				fieldPanel.add(stockLabel, c);
				c.gridx = 1;
				c.gridy = 7;
				c.ipadx = 200;
				fieldPanel.add(stockField, c);
				
				actionPanel.setLayout(new GridBagLayout());
				c.gridx = 0;
				c.gridy = 0;
				c.ipady = 5;
				actionPanel.add(fieldPanel, c);
				c.gridx = 0;
				c.gridy = 1;
				actionPanel.add(addItemButton, c);
				add(tablePanel);
				add(actionPanel);
				
				mainFrame.revalidate();
			}		
		});
		
		processOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}		
		});
		
		
		
		setLayout(buttonLayout);
		add(dailySalesReport);
		add(topSellingItems);
		add(addItems);
		add(processOrder);
	}
	
	
//	private void removeButtons() {
////		remove(dailySalesReport);
////		remove(topSellingItems);
////		remove(addItems);
////		remove(processOrder);
////		setLayout(new FlowLayout());
//		removeAll();
//	}

}
