package tablestests;

import org.junit.Test;

import tables.ReturnItem;
import transactions.ClerkTransactions;
import transactions.ManagerTransactions;

import com.mysql.jdbc.Connection;

import connection.DatabaseConnection;

public class ClerkTransactionsTest {
	private Connection con;
	// ams represents a connection to an MySQL database
	private DatabaseConnection ams = DatabaseConnection.getInstance();
	
	@Test
	public void returnItemTest() {

		// given
		// Connect to the database
		if(ams.connect("root", "(Ad727363)")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}

		con = (Connection) ams.getConnection();
		ClerkTransactions c = new ClerkTransactions(con);
		ReturnItem r = new ReturnItem(con);
		
		// when
		int[] upc = new int[4];
		upc[0] = 1;
		upc[1] = 2;
		upc[2] = 3;
		upc[3] = 8;
		int[] quantity = new int[4];
		quantity[0] = 1;
		quantity[1] = 1;
		quantity[2] = 1;
		quantity[3] = 5;
		
		System.out.println(c.returnItem(1, upc, quantity, "2014-01-01"));
		// then
		r.showReturnItem();
	}
	
}
