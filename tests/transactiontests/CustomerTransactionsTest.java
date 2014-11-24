package transactiontests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Test;

import com.mysql.jdbc.Connection;

import tables.Customer;
import tables.Item;
import transactions.CustomerTransactions;
import connection.DatabaseConnection;

/**
 * JUnit tests for Customer Transactions.
 */

/*
 * Some tests are based on the following customer credentials:
 * 
 *  --------------------------------------------
 * 	cid						  password
 *  --------------------------------------------
	Existing 105              Existing arjan          
	Existing 550              Existing pass50         
	Existing 551              Existing pass51         
	Existing 552              Existing pass52         
	Existing 553              Existing pass553        
	Existing 554              Existing pass553=4      
	Existing 555              Existing pass555
	--------------------------------------------
 */
public class CustomerTransactionsTest{


	private Connection con;
	// ams represents a connection to an MySQL database
	private DatabaseConnection ams = DatabaseConnection.getInstance();

	/**
	 * Tests registering a new customer.
	 */
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
			//fail();
		}
		assertEquals(true, status);
	}


	/**
	 * Tests registering an existing customer.
	 * Input: A customer that is already in the Customer table.
	 * Expected output: Customer was not registered.
	 */
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

		// when
		CustomerTransactions customer = new CustomerTransactions(con);
		boolean status = customer.authenticateCustomer(599, "pass555");
		// then
		if(status == true){
			fail();
		}
		assertEquals(false, status);
	}

	/**
	 * Tests authenticating a customer given invalid password.
	 * Input: Valid cid and invalid password.
	 * Expected output: Customer is not authenticated.
	 */
	@Test
	public void invalidPasswordAuthenticationTest(){
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
		boolean status = customer.authenticateCustomer(555, "pass55");
		// then
		if(status == true){
			fail();
		}
		assertEquals(false, status);
	}

	/**
	 * Tests authenticating a customer given invalid credentials.
	 * Input: Invalid cid and invalid password.
	 * Expected output: Customer is not authenticated.
	 */
	@Test
	public void invalidPasswordAndCidAuthenticationTest(){
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
		boolean status = customer.authenticateCustomer(553, "pss553");
		// then
		if(status == true){
			fail();
		}
		assertEquals(false, status);
	}


	/*-----------------------------------------------------
	upc       title               type      category  company             year      price     stock      
	100       Rain                dvd       drama     ubc                 2014      40.0      10000     
	101       Rain                dvd       drama     ubc                 2014      40.0      10000     
	102       Shower              dvd       drama     ubc                 2014      40.0      10000     
	103       Snow                dvd       drama     ubc                 2014      40.0      10000     
	104       Shine               dvd       drama     ubc                 2014      40.0      10000     
	105       Glow                dvd       drama     ubc                 2014      40.0      10000     
	135       RandomTestItem      cd        drama     randomProduction    2014      10.0      5         
	195       RandomTestItem      cd        drama     randomProduction    2014      10.0      5         
	225       RandomTestItem      cd        drama     randomProduction    2014      10.0      5         
	230       RandomTestItem      dvd       rock      randomProduction    2014      10.0      5         
	285       RandomTestItem      cd        drama     randomProduction    2014      10.0      5         
	300       Strike              dvd       drama     weather             2014      40.0      10000     
	335       RandomTestItem      dvd       rock      randomProduction    2014      10.0      5         
	400       RandomTestItem      dvd       new wave  randomProduction    2014      10.0      5         
	405       RandomTestItem      cd        newWave   randomProduction    2014      10.0      5         
	420       RandomTestItem      cd        drama     randomProduction    2014      10.0      5         
	465       RandomTestItem      cd        drama     randomProduction    2014      10.0      5         
	475       RandomTestItem      cd        drama     randomProduction    2014      10.0      5         
	480       RandomTestItem      dvd       country   randomProduction    2014      10.0      5         
	490       RandomTestItem      dvd       new age   randomProduction    2014      10.0      5         
	12345     Story of my life    dvd       drama     nopublisher         2012      90.99     15        
	-----------------------------------------------------*/

	/**
	 * Tests searching for an item, given existing title or category and non-zero quantity.
	 */
	@Test
	public void searchItemTest(){
		// given
		// Connect to the database
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}
		con = (Connection) ams.getConnection();

		//Item item = new Item(con);
		//item.displayAllItems();

		//Item item = new Item(con);
		//Random randomGenerator = new Random();
		//int random_upc = randomGenerator.nextInt(100) * 5;
		//item.insertItem(random_upc, "RandomTestTr", "cd", "rock", "randomProduction", 1999, (float) 5.99, 0);
		
		// when
		CustomerTransactions customer = new CustomerTransactions(con);
		boolean status = customer.searchItem("newWave", "Rain", 4);
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
	public void zeroQuantityTest(){
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
		boolean status = customer.searchItem("newWave", "Rain", 0);
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
	public void negativeQuantityTest(){
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
		boolean status = customer.searchItem("newWave", "Rain", -1);
		// then
		if(status == true){
			fail();
		}
		assertEquals(false, status);
	}

}
