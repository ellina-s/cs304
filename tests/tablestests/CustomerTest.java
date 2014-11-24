package tablestests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import tables.Customer;
import transactions.CustomerSubTransactions;

import com.mysql.jdbc.Connection;

import connection.DatabaseConnection;

public class CustomerTest {

	
	private Connection con;
	// ams represents a connection to an MySQL database
	private DatabaseConnection ams = DatabaseConnection.getInstance();
	
	@Test
	public void CustomerInsertTest(){

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
		
		
		String [][] test = c.produceBill(upc, quantity);
		for(int i = 0; i < test.length; i++) {
			for(int j = 0; j < test[i].length; j++) {
			      System.out.printf("%-15.15s", test[i][j]);
			}
			System.out.println();
		}
	}
}
