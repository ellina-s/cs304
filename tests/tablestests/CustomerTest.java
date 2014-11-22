package tablestests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import tables.Customer;

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
		Customer c = new Customer(con);
		// when
		boolean status = c.insertCustomer(105, "arjan", "ad", "ubc", "604 - 9394");
		// then
		if(status == false){
			fail();
		}
		assertEquals(true, status);
		c.showCustomer();
		c.deleteCustomer(102);
		c.deleteCustomer(105);
		String [][] test = c.getCustomer();
		for(int i = 0; i < test.length; i++) {
			for(int j = 0; j < test[i].length; j++) {
			      System.out.printf("%-15.15s", test[i][j]);
			}
			System.out.println();
		}
	}
}
