package transactiontests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import com.mysql.jdbc.Connection;
import tables.Customer;
import transactions.CustomerTransactions;
import connection.DatabaseConnection;

/**
 * JUnit tests for Customer Transactions.
 */
public class CustomerTransactionsTest{


	private Connection con;
	// ams represents a connection to an MySQL database
	private DatabaseConnection ams = DatabaseConnection.getInstance();

	/**
	 * Tests registering a customer.
	 */
	@Test
	public void registerCustomerTest(){

		// given
		// Connect to the database
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}
		con = (Connection) ams.getConnection();
		
		// when
		//Customer c = new Customer(con);
		//c.insertCustomer(105, "arjan", "ad", "ubc", "604 - 9394");
		//c.insertCustomer(550, "pass50", "user50", "ubc", "604 - 9390");
		//c.insertCustomer(551, "pass51", "user52", "ubc", "604 - 9391");
		//c.insertCustomer(552, "pass52", "user52", "ubc", "604 - 9392");
		
		CustomerTransactions customer = new CustomerTransactions(con);
		// when
		boolean status = customer.registerCustomer(554, "pass553=4", "testuser554", "Vancouver", "46872125");

		// then
		if(status == false){
			fail();
		}
		assertEquals(true, status);
	}

}

