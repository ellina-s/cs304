package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ManagerPanel extends JPanel {
	private JFrame mainFrame;
	private JButton dailySalesReport = new JButton("View Daily Sales Report");
	private JButton topSellingItems = new JButton("View Top Selling Items");
	private JButton addItems = new JButton("Add Items");
	private JButton processOrder = new JButton("Process Order");
	
	
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
				System.exit(0);
			}		
		});
		
		processOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}		
		});
		
		
		
		setLayout(new GridLayout(4,1));
		add(dailySalesReport);
		add(topSellingItems);
		add(addItems);
		add(processOrder);
	}
	

}
