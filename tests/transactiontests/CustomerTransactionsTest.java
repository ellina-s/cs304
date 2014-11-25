package transactiontests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

import com.mysql.jdbc.Connection;

import tables.Customer;
import tables.Item;
import tables.LeadSinger;
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
		//Item item = new Item(con);
		//item.displayAllItems();

		// when
		CustomerTransactions customer = new CustomerTransactions(con);
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
		CustomerTransactions customer = new CustomerTransactions(con);
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
		CustomerTransactions customer = new CustomerTransactions(con);
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
		CustomerTransactions customer = new CustomerTransactions(con);
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
		//LeadSinger leadSinger = new LeadSinger(con);
		//leadSinger.displayAllLeadSingers();

		// when
		CustomerTransactions customer = new CustomerTransactions(con);
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
		CustomerTransactions customer = new CustomerTransactions(con);
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
		CustomerTransactions customer = new CustomerTransactions(con);
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
		CustomerTransactions customer = new CustomerTransactions(con);
		boolean status = customer.searchSingerHelper("Roza");
		// then
		if(status == true){
			fail();
		}
		assertEquals(false, status);
	}

	@Test
	public void preciseSearchTest(){
		
		System.out.println(" ");
		System.out.println("***** PRECISE SEARCH TEST *****");
		
		// given
		// Connect to the database
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}
		con = (Connection) ams.getConnection();
		ArrayList<Integer> found_upcs = new ArrayList<Integer>();
		// when
		CustomerTransactions customer = new CustomerTransactions(con);
		found_upcs = customer.preciseSearch("drama", "shine", 2, "Maroon5");

		if(found_upcs == null){
			fail();
		}
		
		System.out.println("--------------------- TEST --------------------------------------");
		for(int i = 0; i < found_upcs.size(); i++){
			System.out.println(found_upcs.get(i));
		}
		System.out.println(" ");
		
	}
	
	
	@Test
	public void genericSearchTest(){
		
		System.out.println(" ");
		System.out.println("***** GENERIC SEARCH TEST *****");
		
		//System.out.println(" ");
		//System.out.println("***** Category OR title SEARCH TEST *****");
		
		
		// given
		// Connect to the database
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}
		con = (Connection) ams.getConnection();
		ArrayList<Integer> found_upcs = new ArrayList<Integer>();
		// when
		CustomerTransactions customer = new CustomerTransactions(con);
		//found_upcs = customer.genericSearch("drama", "RandomTestTr", 4, "Maroon5");
		found_upcs = customer.genericSearch("rock", "Story of my life", 4, "Maroon5"); // good to test out of stock items. No precise if found,
		//found_upcs = customer.genericSearch("drama", "Shine", 2, "Maroon5"); // good to test precise items
		//found_upcs = customer.genericSearch("pop", "jay", 160, "Dan"); // good to test no items found
		
		if(found_upcs == null){
			fail();
		}
		
		System.out.println("--------------------- TEST --------------------------------------");
		for(int i = 0; i < found_upcs.size(); i++){
			System.out.println(found_upcs.get(i));
		}
		System.out.println(" ");
		
	}
}
