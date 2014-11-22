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
	 * Tests registering a new customer.
	 */
	/*
	@Test
	public void registerNewCustomerTest(){

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
		CustomerTransactions customer = new CustomerTransactions(con);
		boolean status = customer.registerCustomer(5566, "pass556", "testuser556", "North Van", "987654");

		// then
		if(status == false){
			fail();
		}
		assertEquals(true, status);
	}
	*/


	/**
	 * Tests registering an existing customer.
	 * Input: A customer that is already in the Customer table.
	 * Expected output: Customer was not registered.
	 */
	/*
	@Test
	public void registerExistingCustomerTest(){

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
		CustomerTransactions customer = new CustomerTransactions(con);
		boolean status = customer.registerCustomer(554, "pass553=4", "testuser554", "Vancouver", "46872125");

		// then
		if(status == true){
			fail();
		}
		assertEquals(false, status);
	}
	*/

	/**
	 * Tests authenticating an existing customer.
	 * Input: Valid cid and password.
	 * Expected output: Customer is successfully authenticated.
	 */
	@Test
	public void successfulAuthenticationTest(){
		// given
		// Connect to the database
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}
		con = (Connection) ams.getConnection();

		/*
		Existing 105              Existing arjan          
		Existing 550              Existing pass50         
		Existing 551              Existing pass51         
		Existing 552              Existing pass52         
		Existing 553              Existing pass553        
		Existing 554              Existing pass553=4      
		Existing 555              Existing pass555
		*/
	
		// when
		CustomerTransactions customer = new CustomerTransactions(con);
		boolean status = customer.authenticateCustomer(555, "pass555");
		// then
		if(status == false){
			fail();
		}
		assertEquals(true, status);
	}
	
	/**
	 * Tests authenticating a customer given invalid cid.
	 * Input: Invalid cid and a valid password.
	 * Expected output: Customer is not authenticated.
	 */
	@Test
	public void invalidCidAuthenticationTest(){
		// given
		// Connect to the database
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}
		con = (Connection) ams.getConnection();

		/*
		Existing 105              Existing arjan          
		Existing 550              Existing pass50         
		Existing 551              Existing pass51         
		Existing 552              Existing pass52         
		Existing 553              Existing pass553        
		Existing 554              Existing pass553=4      
		Existing 555              Existing pass555
		*/

		// when
		CustomerTransactions customer = new CustomerTransactions(con);
		boolean status = customer.authenticateCustomer(599, "pass555");
		// then
		if(status == true){
			fail();
		}
		assertEquals(false, status);
	}



}

