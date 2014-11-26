package transactiontests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import com.mysql.jdbc.Connection;
import connection.DatabaseConnection;

/**
 * JUnit tests for Customer Transactions Helper class.
 */


public class CustomerTransactionsHelperTest{


	private Connection con;
	// ams represents a connection to an MySQL database
	private DatabaseConnection ams = DatabaseConnection.getInstance();

	/**
	 * Tests searching for an item, given existing title or category and non-zero quantity.
	 */
	@Test
	public void searchItemHelperTest(){
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
		CustomerTransactionsHelper customer = new CustomerTransactionsHelper(con);
		boolean status = customer.searchItemHelper("drama", "RandomTestTr", 4);
		// then
		if(status == false){
			fail();
		}
		assertEquals(true, status);
	}

	/**
	 * Tests searching for an item given a zero quantity.
	 */
	@Test
	public void zeroQuantityHelperTest(){
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
		CustomerTransactionsHelper customer = new CustomerTransactionsHelper(con);
		boolean status = customer.searchItemHelper("newWave", "Rain", 0);
		// then
		if(status == true){
			fail();
		}
		assertEquals(false, status);
	}

	/**
	 * Tests searching for an item given a negative quantity.
	 */
	@Test
	public void negativeQuantityHelperTest(){
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
		CustomerTransactionsHelper customer = new CustomerTransactionsHelper(con);
		boolean status = customer.searchItemHelper("newWave", "Rain", -1);
		// then
		if(status == true){
			fail();
		}
		assertEquals(false, status);
	}

	@Test
	public void zeroStockHelperTest(){
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
		CustomerTransactionsHelper customer = new CustomerTransactionsHelper(con);
		boolean status = customer.searchItemHelper("rock", "Rain", 11);
		// then
		if(status == true){
			fail();
		}
		assertEquals(false, status);
	}

	/**
	 * Tests searching for a Singer given a valid name.
	 */
	@Test
	public void successfulSearchSingerHelperTest(){
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
		CustomerTransactionsHelper customer = new CustomerTransactionsHelper(con);
		boolean status = customer.searchSingerHelper("Elvis");
		// then
		if(status == false){
			fail();
		}
		assertEquals(true, status);
	}


	/**
	 * Tests searching for a Singer given a null name.
	 */
	@Test
	public void nullSearchSingerHelperTest(){
		// given
		// Connect to the database
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}
		con = (Connection) ams.getConnection();
		//LeadSinger leadSinger = new LeadSinger(con);
		//leadSinger.displayAllLeadSingers();

		// when
		CustomerTransactionsHelper customer = new CustomerTransactionsHelper(con);
		boolean status = customer.searchSingerHelper(null);
		// then
		if(status == true){
			fail();
		}
		assertEquals(false, status);
	}

	/**
	 * Tests searching for a Singer given an empty name.
	 */
	@Test
	public void emptySearchSingerHelperTest(){
		// given
		// Connect to the database
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}
		con = (Connection) ams.getConnection();
		//LeadSinger leadSinger = new LeadSinger(con);
		//leadSinger.displayAllLeadSingers();

		// when
		CustomerTransactionsHelper customer = new CustomerTransactionsHelper(con);
		boolean status = customer.searchSingerHelper("");
		// then
		if(status == true){
			fail();
		}
		assertEquals(false, status);
	}

	/**
	 * Tests searching for a Singer given a non-existing name.
	 */
	@Test
	public void failedSearchSingerHelperTest(){
		// given
		// Connect to the database
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}
		con = (Connection) ams.getConnection();
		//LeadSinger leadSinger = new LeadSinger(con);
		//leadSinger.displayAllLeadSingers();

		// when
		CustomerTransactionsHelper customer = new CustomerTransactionsHelper(con);
		boolean status = customer.searchSingerHelper("Roza");
		// then
		if(status == true){
			fail();
		}
		assertEquals(false, status);
	}

}
