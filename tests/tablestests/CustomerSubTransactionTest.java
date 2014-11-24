package tablestests;

import java.util.ArrayList;

import org.junit.Test;

import tables.PurchaseItem;
import transactions.CustomerSubTransactions;

import com.mysql.jdbc.Connection;

import connection.DatabaseConnection;

public class CustomerSubTransactionTest {

	
	private Connection con;
	// ams represents a connection to an MySQL database
	private DatabaseConnection ams = DatabaseConnection.getInstance();
	
	@Test
	public void produceBillTest() {
		if(ams.connect("root", "(Ad727363)")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}

		con = (Connection) ams.getConnection();
		CustomerSubTransactions c = new CustomerSubTransactions(con);
		// when
		ArrayList<Integer> upc = new ArrayList<Integer> ();
		upc.add(1);
		upc.add(2);
		upc.add(3);
		upc.add(8);
		ArrayList<Integer> quantity = new ArrayList<Integer> ();
		quantity.add(1);
		quantity.add(1);
		quantity.add(1);
		quantity.add(5);
		
		String[][] test = c.produceBill(upc, quantity);
		for(int i = 0; i < test.length; i++) {
			for(int j = 0; j < test[i].length; j++) {
			      System.out.printf("%-15.15s", test[i][j]);
			}
			System.out.println();
		}
	}
	
	@Test
	public void checkOutTest() {

		// given
		// Connect to the database
		if(ams.connect("root", "(Ad727363)")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}

		con = (Connection) ams.getConnection();
		CustomerSubTransactions c = new CustomerSubTransactions(con);
		PurchaseItem p = new PurchaseItem(con);
		// when
		ArrayList<Integer> upc = new ArrayList<Integer> ();
		upc.add(1);
		upc.add(2);
		upc.add(3);
		upc.add(8);
		ArrayList<Integer> quantity = new ArrayList<Integer> ();
		quantity.add(1);
		quantity.add(1);
		quantity.add(1);
		quantity.add(5);
		
		System.out.println(c.checkOut(upc, quantity, 1, 100, "2014-01-01", "2014-01-01"));
		// then
		p.showPurchaseItem();
	}
	
	
}
