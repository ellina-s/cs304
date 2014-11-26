package tablestests;

import java.util.ArrayList;

import org.junit.Test;

import tables.ReturnItem;
import transactions.ClerkTransactions;

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
		ArrayList<Integer> upc = new ArrayList<Integer> ();
		upc.add(1);
		upc.add(2);
		upc.add(3);
		upc.add(8);
		ArrayList<Integer> quantity = new ArrayList<Integer> ();
		quantity.add(1);
		quantity.add(1);
		quantity.add(1);
		quantity.add(-1);
		
		System.out.println();
		// then
		String[][] test = c.returnItem(26, upc, quantity, "2014-11-26");
		for(int i = 0; i < test.length; i++) {
			for(int j = 0; j < test[i].length; j++) {
			      System.out.printf("%-15.15s", test[i][j]);
			}
			System.out.println();
		}
	}
	
}
