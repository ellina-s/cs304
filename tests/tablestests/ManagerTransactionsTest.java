package tablestests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import tables.Item;
import tables.PurchaseItem;
import transactions.ManagerTransactions;

import com.mysql.jdbc.Connection;

import connection.DatabaseConnection;

public class ManagerTransactionsTest {
	private Connection con;
	// ams represents a connection to an MySQL database
	private DatabaseConnection ams = DatabaseConnection.getInstance();
	
	public void displayTable(String[][] test) {
		for(int i = 0; i < test.length; i++) {
			for(int j = 0; j < test[i].length; j++) {
			      System.out.printf("%-15.15s", test[i][j]);
			}
			System.out.println();
		}
	}
	
	
	//Need to insert an Item and a Purchase due to Foreign Key Constraints.
	@Test
	public void dailySalesReportTest(){

		// given
		// Connect to the database
		if(ams.connect("root", "(Ad727363)")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}

		con = (Connection) ams.getConnection();
		ManagerTransactions c = new ManagerTransactions(con);
		// when
		String[][] test = c.dailySalesReport("01/01/14");
		// then
		displayTable(test);
	}
	
	@Test
	public void topSellingItemsTest(){

		// given
		// Connect to the database
		if(ams.connect("root", "(Ad727363)")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}

		con = (Connection) ams.getConnection();
		ManagerTransactions c = new ManagerTransactions(con);
		// when
		String[][] test = c.topSellingItems("01/01/14", 2);
		// then
		displayTable(test);
	}
	
	@Test
	public void addItemsTest(){

		// given
		// Connect to the database
		if(ams.connect("root", "(Ad727363)")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}

		con = (Connection) ams.getConnection();
		ManagerTransactions c = new ManagerTransactions(con);
		Item i = new Item(con);
		
		// when
		System.out.println(c.addItems(1, 10, (float) 17.99));
		// then
		i.displayAllItems();
	}
	
}
