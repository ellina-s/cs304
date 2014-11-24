package gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat.Field;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;

import connection.DatabaseConnection;
import tables.Item;
import transactions.ManagerTransactions;

public class ManagerPanel extends JPanel {
	private JFrame mainFrame;
	private JButton dailySalesReport = new JButton("View Daily Sales Report");
	private JButton topSellingItems = new JButton("View Top Selling Items");
	private JButton addItems = new JButton("Add Items");
	private JButton processOrder = new JButton("Process Order");
	private JButton topSellingButton = new JButton("Show Top Selling Items");
	private JButton backButtonTopSelling = new JButton("Back");
	private JButton backButtonSalesReport = new JButton("Back");
	private JButton generateReportButton = new JButton("Generate Report");
	private JTable itemTable;
	private JTable salesReportTable;
	private JTable topSellingTable;
	private JTextField dateField = new JTextField("");
	private JTextField topSellingField = new JTextField("");
	private JTextField upcField = new JTextField("");
	private JTextField priceField = new JTextField("");
	private JTextField stockField = new JTextField("");
	private GridLayout buttonLayout = new GridLayout(4,1);
	private String[] itemColumnNames = {"upc","title","type","category","company","year","price","stock"};
	private String[] salesReportColumnNames = {"UPC","Category","Unit Price","Units","Total Value"};
	private String[] topSellingColumnNames = {"title","company","current stock","copies sold"};

	private JLabel enterDate = new JLabel("Enter Date (MM/DD/YY)");
	private JLabel top = new JLabel("Top");
	private JLabel sellingItems = new JLabel("Selling Items");
	private JLabel upcLabel = new JLabel("upc");
	private JLabel priceLabel = new JLabel("price (optional)");
	private JLabel stockLabel = new JLabel("stock");

	private DatabaseConnection ams = DatabaseConnection.getInstance();
	private Connection con = (Connection) ams.getConnection();

	Item item = new Item(con);
	private String[][] data;
	private DefaultTableModel dtm;

	private JScrollPane tablePanel;
	private JPanel actionPanel = new JPanel();
	private JPanel fieldPanel = new JPanel();

	private ActionListener showTopSelling = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			showTopSellingItems();
		}};

		private ActionListener salesReport = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				generateReport();
			}
		};

		public ManagerPanel(JFrame mainFrame_) {
			mainFrame = mainFrame_;

			dailySalesReport.setFocusable(false);
			topSellingItems.setFocusable(false);
			addItems.setFocusable(false);
			processOrder.setFocusable(false);

			dailySalesReport.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					removeAll();
					setLayout(new GridBagLayout());
					GridBagConstraints c = new GridBagConstraints();

					mainFrame.setSize(800, 600);

					Object[][] emptyData = {};

					dtm = new DefaultTableModel(emptyData,salesReportColumnNames);

					salesReportTable = new JTable(dtm) {
						// Disable editing of table
						private static final long serialVersionUID = 1L;
						public boolean isCellEditable(int row, int column) {                
							return false;               
						};
					};

					salesReportTable.getColumnModel().getColumn(1).setPreferredWidth(105);

					tablePanel = new JScrollPane(salesReportTable);

					dateField.setColumns(10);
					dateField.addActionListener(salesReport);

					generateReportButton.addActionListener(salesReport);

					generateReportButton.setFocusable(false);

					//				backButtonSalesReport.addActionListener(new ActionListener(){
					//					@Override
					//					public void actionPerformed(ActionEvent arg0) {
					//						dateField.removeActionListener(salesReport);
					//						generateReportButton.removeActionListener(salesReport);
					//						removeAll();
					//
					//						mainFrame.setSize(500, 500);
					//						setLayout(buttonLayout);
					//						add(dailySalesReport);
					//						add(topSellingItems);
					//						add(addItems);
					//						add(processOrder);
					//						
					//						mainFrame.revalidate();
					//						mainFrame.repaint();
					//						
					//					}});

					actionPanel.add(enterDate);
					actionPanel.add(dateField);
					actionPanel.add(generateReportButton);
					//actionPanel.add(backButtonSalesReport);

					c.gridx = 0;
					c.gridy = 0;
					add(tablePanel,c);
					c.gridx = 0;
					c.gridy = 1;
					c.ipadx = 5;
					add(actionPanel,c);

					mainFrame.revalidate();
				}		
			});

			topSellingItems.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					removeAll();
					setLayout(new GridBagLayout());
					GridBagConstraints c = new GridBagConstraints();

					mainFrame.setSize(800, 600);

					Object[][] emptyData = {};

					dtm = new DefaultTableModel(emptyData,topSellingColumnNames);


					topSellingTable = new JTable(dtm) {
						// Disable editing of table
						private static final long serialVersionUID = 1L;
						public boolean isCellEditable(int row, int column) {                
							return false;               
						};
					};

					tablePanel = new JScrollPane(topSellingTable);

					dateField.setColumns(10);
					topSellingField.setColumns(4);

					dateField.addActionListener(showTopSelling);
					topSellingField.addActionListener(showTopSelling);

					topSellingButton.addActionListener(showTopSelling);
					topSellingButton.setFocusable(false);

					//				backButtonTopSelling.addActionListener(new ActionListener(){
					//					@Override
					//					public void actionPerformed(ActionEvent arg0) {
					//						dateField.removeActionListener(showTopSelling);
					//						topSellingField.removeActionListener(showTopSelling);
					//						topSellingButton.removeActionListener(showTopSelling);
					//						removeAll();
					//
					//						mainFrame.setSize(500, 500);
					//						setLayout(buttonLayout);
					//						add(dailySalesReport);
					//						add(topSellingItems);
					//						add(addItems);
					//						add(processOrder);
					//						
					//						mainFrame.revalidate();
					//						mainFrame.repaint();
					//						
					//					}});

					actionPanel.add(enterDate);
					actionPanel.add(dateField);
					actionPanel.add(top);
					actionPanel.add(topSellingField);
					actionPanel.add(sellingItems);
					actionPanel.add(topSellingButton);
					//actionPanel.add(backButtonTopSelling);

					c.gridx = 0;
					c.gridy = 0;
					add(tablePanel,c);
					c.gridx = 0;
					c.gridy = 1;
					c.ipadx = 5;
					add(actionPanel,c);

					mainFrame.revalidate();
					mainFrame.repaint();

				}		
			});

			addItems.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					removeAll();
					setLayout(new GridLayout(2,1));

					mainFrame.setSize(800, 600);

					data = item.getItem();
					dtm = new DefaultTableModel(data,itemColumnNames);
					itemTable = new JTable(dtm) {
						// Disable editing of table
						private static final long serialVersionUID = 1L;
						public boolean isCellEditable(int row, int column) {                
							return false;               
						};
					};
					tablePanel = new JScrollPane(itemTable);






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
					fieldPanel.add(priceLabel, c);
					c.gridx = 1;
					c.gridy = 1;
					c.ipadx = 200;
					fieldPanel.add(priceField, c);
					c.gridx = 0;
					c.gridy = 2;
					c.ipadx = 5;
					fieldPanel.add(stockLabel, c);
					c.gridx = 1;
					c.gridy = 2;
					c.ipadx = 200;
					fieldPanel.add(stockField, c);


					JButton addItemButton = new JButton("Add Item");
					addItemButton.setFocusable(false);

					addItemButton.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							addItems();
						}});

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
					mainFrame.repaint();
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

		private void generateReport() {
			// Test data
			//		String[][] data = {
			//				{"2555","classical","10.50","10","105.00"},
			//				{"2555","classical","5.00","5","25.00"},
			//				{"3455","classical","20.00","1","20.00"},
			//				{"1255","pop","20.00","5","100.00"},
			//				{"5666","pop","10.00","10","100.00"},
			//				{"1244","rock","2.50","20","50.00"},
			//				{"8844","rock","5.00","50","250.00"}
			//		};

			// Clear the table
			for (int row = dtm.getRowCount() - 1; row >= 0; row--) {
				dtm.removeRow(row);
			}

			ManagerTransactions mt = new ManagerTransactions(con);
			data = mt.dailySalesReport(dateField.getText());
			Object[] separator = {"","","","","",""};

			if (data.length == 0) {
				JOptionPane.showMessageDialog(mainFrame,"No data found for specified date.");
			}

			//rock, pop, rap, country, classical, new age and instrumental.
			else if (data.length > 0) {
				int classicalTotalUnits = 0;
				int countryTotalUnits = 0;
				int instrumentalTotalUnits = 0;
				int newAgeTotalUnits = 0;
				int popTotalUnits = 0;
				int rapTotalUnits = 0;
				int rockTotalUnits = 0;
				int totalDailyUnits = 0;

				float classicalTotalValue = 0;
				float countryTotalValue = 0;
				float instrumentalTotalValue = 0;
				float newAgeTotalValue = 0;
				float popTotalValue = 0;
				float rapTotalValue = 0;
				float rockTotalValue = 0;
				float totalDailyValue = 0;

				// Calculate classical totals
				for (int i = 0; i < data.length; i++) {
					if (data[i][1].equals("classical")) {
						dtm.addRow(data[i]);
						classicalTotalUnits += Integer.parseInt(data[i][3]);
						classicalTotalValue += Float.parseFloat(data[i][4]);
					}
				}
				if (classicalTotalUnits > 0) {
					totalDailyUnits += classicalTotalUnits;
					totalDailyValue += classicalTotalValue;
					Object[] classicalTotal = {"","Total (classical)", "",classicalTotalUnits,classicalTotalValue};
					dtm.addRow(classicalTotal);
					dtm.addRow(separator);
				}

				// Calculate country totals
				for (int i = 0; i < data.length; i++) {
					if (data[i][1].equals("country")) {
						dtm.addRow(data[i]);
						countryTotalUnits += Integer.parseInt(data[i][3]);
						countryTotalValue += Float.parseFloat(data[i][4]);
					}
				}
				if (countryTotalUnits > 0) {
					totalDailyUnits += countryTotalUnits;
					totalDailyValue += countryTotalValue;
					Object[] countryTotal = {"","Total (country)", "",countryTotalUnits,countryTotalValue};
					dtm.addRow(countryTotal);
					dtm.addRow(separator);
				}

				// Calculate instrumental totals
				for (int i = 0; i < data.length; i++) {
					if (data[i][1].equals("instrumental")) {
						dtm.addRow(data[i]);
						instrumentalTotalUnits += Integer.parseInt(data[i][3]);
						instrumentalTotalValue += Float.parseFloat(data[i][4]);
					}
				}
				if (instrumentalTotalUnits > 0) {
					totalDailyUnits += instrumentalTotalUnits;
					totalDailyValue += instrumentalTotalValue;
					Object[] instrumentalTotal = {"","Total (instrumental)", "",instrumentalTotalUnits,instrumentalTotalValue};
					dtm.addRow(instrumentalTotal);
					dtm.addRow(separator);
				}

				// Calculate new age totals
				for (int i = 0; i < data.length; i++) {
					if (data[i][1].equals("new age")) {
						dtm.addRow(data[i]);
						newAgeTotalUnits += Integer.parseInt(data[i][3]);
						newAgeTotalValue += Float.parseFloat(data[i][4]);
					}
				}
				if (newAgeTotalUnits > 0) {
					totalDailyUnits += newAgeTotalUnits;
					totalDailyValue += newAgeTotalValue;
					Object[] newAgeTotal = {"","Total (newAge)", "",newAgeTotalUnits,newAgeTotalValue};
					dtm.addRow(newAgeTotal);
					dtm.addRow(separator);
				}

				// Calculate pop totals
				for (int i = 0; i < data.length; i++) {
					if (data[i][1].equals("pop")) {
						dtm.addRow(data[i]);
						popTotalUnits += Integer.parseInt(data[i][3]);
						popTotalValue += Float.parseFloat(data[i][4]);
					}
				}
				if (popTotalUnits > 0) {
					totalDailyUnits += popTotalUnits;
					totalDailyValue += popTotalValue;
					Object[] popTotal = {"","Total (pop)", "",popTotalUnits,popTotalValue};
					dtm.addRow(popTotal);
					dtm.addRow(separator);
				}

				// Calculate rap totals
				for (int i = 0; i < data.length; i++) {
					if (data[i][1].equals("rap")) {
						dtm.addRow(data[i]);
						rapTotalUnits += Integer.parseInt(data[i][3]);
						rapTotalValue += Float.parseFloat(data[i][4]);
					}
				}
				if (rapTotalUnits > 0) {
					totalDailyUnits += rapTotalUnits;
					totalDailyValue += rapTotalValue;
					Object[] rapTotal = {"","Total (rap)", "",rapTotalUnits,rapTotalValue};
					dtm.addRow(rapTotal);
					dtm.addRow(separator);
				}

				// Calculate rock totals
				for (int i = 0; i < data.length; i++) {
					if (data[i][1].equals("rock")) {
						dtm.addRow(data[i]);
						rockTotalUnits += Integer.parseInt(data[i][3]);
						rockTotalValue += Float.parseFloat(data[i][4]);
					}
				}
				if (rockTotalUnits > 0) {
					totalDailyUnits += rockTotalUnits;
					totalDailyValue += rockTotalValue;
					Object[] rockTotal = {"","Total (rock)", "",rockTotalUnits,rockTotalValue};
					dtm.addRow(rockTotal);
					dtm.addRow(separator);
				}

				if (totalDailyUnits > 0) {

					Object[] totalDailySales = {"","Total Daily Sales", "",totalDailyUnits,totalDailyValue};
					dtm.addRow(totalDailySales);
				}

			}
		}


		private void showTopSellingItems() {
			// Test data
			//		String[][] data = {
			//				{"testtitle0","testcompany0","teststock0","testsold0"},
			//				{"testtitle1","testcompany1","teststock1","testsold1"},
			//				{"testtitle2","testcompany2","teststock2","testsold2"},
			//				{"testtitle3","testcompany3","teststock3","testsold3"},
			//				{"testtitle4","testcompany4","teststock4","testsold4"},
			//				{"testtitle5","testcompany5","teststock5","testsold5"},
			//				{"testtitle6","testcompany6","teststock6","testsold6"}
			//		};

			for (int row = dtm.getRowCount() - 1; row >= 0; row--) {
				dtm.removeRow(row);
			}
			try {
				int top = Integer.parseInt(topSellingField.getText());
				ManagerTransactions mt = new ManagerTransactions(con);
				data = mt.topSellingItems(dateField.getText(), top);

				if (data.length == 0) {
					JOptionPane.showMessageDialog(mainFrame,"No data found for specified date.");
				}

				else if (data.length > 0) {
					for (int i = 0; i < data.length; i++) {
						dtm.addRow(data[i]);	
					}
				}

			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(mainFrame,"Please enter a valid integer in the top selling field.");
			}





		}

		private boolean upcExists(String upc) {
			if (data.length > 0) {
				for (int i = 0; i < data.length; i++) {
					if (upc.equals(data[i][0])) {
						return true;
					}
				}
				return false;
			}
			return false;
		}
		


		private void addItems() {
			try {
				int upc = Integer.parseInt(upcField.getText());
				int stock = Integer.parseInt(stockField.getText());
				float price = 0;

				if (!upcExists(upcField.getText())) {
					JOptionPane.showMessageDialog(mainFrame,"upc not found.");
				} else {

					if (priceField.getText().equals("")) {
						if (data.length > 0) {
							for (int i = 0; i < data.length; i++) {
								if (upcField.getText().equals(data[i][0])) {
									price = Float.parseFloat(data[i][6]);
								}
							}
						}
						
						ManagerTransactions mt = new ManagerTransactions(con);
						mt.addItems(upc, stock, price);
						
						for (int row = dtm.getRowCount() - 1; row >= 0; row--) {
							dtm.removeRow(row);
						}
						
						data = item.getItem();
						
						if (data.length > 0) {
							for (int i = 0; i < data.length; i++) {
								dtm.addRow(data[i]);	
							}
						}
						JOptionPane.showMessageDialog(mainFrame,"Added items to stock.");
						
						
					} else {
						price = Float.parseFloat(priceField.getText());
						
						ManagerTransactions mt = new ManagerTransactions(con);
						mt.addItems(upc, stock, price);
						
						for (int row = dtm.getRowCount() - 1; row >= 0; row--) {
							dtm.removeRow(row);
						}
						
						data = item.getItem();
						
						if (data.length > 0) {
							for (int i = 0; i < data.length; i++) {
								dtm.addRow(data[i]);	
							}
						}
						
						JOptionPane.showMessageDialog(mainFrame,"Added items to stock.");
					}
				}
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(mainFrame,"One or more fields are invalid.");
			}

		}
}
