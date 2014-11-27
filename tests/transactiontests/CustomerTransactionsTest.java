package transactiontests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import com.mysql.jdbc.Connection;
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


	

	/*
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
	*/


	// TODO
	
	
	/**
	 * Tests the genericSearch() function.
	 */
	@Test
	public void genericSearchTest(){

		System.out.println(" ");
		System.out.println("***** GENERIC SEARCH TEST *****");

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
		String [][] twoDArray = customer.genericSearch("drama", "RandomTestTr", 4, "Maroon5");;

		System.out.println(" ");
		System.out.println("--------------------- TEST Data --------------------------------------");
		for(int i = 0; i < twoDArray.length; i++){
			int subArrayLength = twoDArray[i].length;		
			for(int j = 0; j < subArrayLength; j++){
				System.out.print(twoDArray[i][j] + " ");
			}
			System.out.println(" ");
		}
	}
	

	/**
	 * Tests searching when no items are found.
	 * Input: some non-existing parameters.
	 * Expected output: no tuples are found.
	 */
	@Test
	public void noItemsFoundgenericSearchTest(){

		System.out.println(" ");
		System.out.println("***** No items SEARCH TEST *****");

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
		String [][] twoDArray = customer.genericSearch("pop", "jay", 160, "Dan");

		// then
		System.out.println(" ");
		System.out.println("--------------------- TEST Data --------------------------------------");
		for(int i = 0; i < twoDArray.length; i++){
			int subArrayLength = twoDArray[i].length;		
			for(int j = 0; j < subArrayLength; j++){
				System.out.print(twoDArray[i][j] + " ");
			}
			System.out.println(" ");
		}
		
		System.out.println(" ");	
	}
	

	/**
	 * Tests raising an error in searching by null singer name.
	 * Input: null singer name.
	 * Expected output: genericSearch() should return null.
	 */
	@Test
	public void singerErrorSearchTest(){

		System.out.println(" ");
		System.out.println("***** Singer Error SEARCH TEST *****");

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
		String [][] twoDArray = customer.genericSearch("drama", "RandomTestItem", 412, null);
		
		// then
		if(twoDArray != null){
			fail();
		}
	}
	
	
	// TODO
	
	/**
	 * Tests searching for a precise item.
	 * Input: "drama", "Shine", 9800, "Maroon5"
	 * Expected output: a item that exactly satisfies the input.
	 */
	@Test
	public void preciseItemSearchTest(){

		System.out.println(" ");
		System.out.println("***** Precise SEARCH TEST *****");

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

		String [][] twoDArray = customer.genericSearch("drama", "Shine", 9800, "Maroon5");
		//String [][] twoDArray = customer.genericSearch("", "", 50, "Pink");
		

		System.out.println(" ");
		System.out.println("--------------------- TEST Data --------------------------------------");
		for(int i = 0; i < twoDArray.length; i++){
			int subArrayLength = twoDArray[i].length;		
			for(int j = 0; j < subArrayLength; j++){
				System.out.print(twoDArray[i][j] + " ");
			}
			System.out.println(" ");
		}
	}
	
	
	/**
	 * Tests searching for multiple items with the same singer.
	 * Input: a singer name.
	 * Expected output: items that have the input singer.
	 * 					Returned items should be also not out of stock.
	 */
	@Test
	public void multipleItemSearchTest(){

		System.out.println(" ");
		System.out.println("***** Multiple item SEARCH TEST *****");

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
		String [][] twoDArray = customer.genericSearch("", "", 50, "Pink");
		
		// then
		System.out.println(" ");
		System.out.println("--------------------- TEST Data --------------------------------------");
		for(int i = 0; i < twoDArray.length; i++){
			int subArrayLength = twoDArray[i].length;		
			for(int j = 0; j < subArrayLength; j++){
				System.out.print(twoDArray[i][j] + " ");
			}
			System.out.println(" ");
		}
	}
	 

	// TODO
	
	/**
	 * Tests searching for items that are out of stock.
	 * Such items should not be returned.
	 */
	@Test
	public void outOfStockSearchTest(){

		System.out.println(" ");
		System.out.println("***** Out Of Stock SEARCH TEST *****");

		// given
		// Connect to the database
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}
		con = (Connection) ams.getConnection();
		CustomerTransactions customer = new CustomerTransactions(con);
		// when
		String [][] twoDArray = customer.genericSearch("rock", "Story of my life", 4, "Ed");
		// then
		System.out.println(" ");
		System.out.println("--------------------- TEST Data --------------------------------------");
		for(int i = 0; i < twoDArray.length; i++){
			int subArrayLength = twoDArray[i].length;		
			for(int j = 0; j < subArrayLength; j++){
				System.out.print(twoDArray[i][j] + " ");
			}
			System.out.println(" ");
		}
	}
	
	
	/**
	 * Tests an empty singer search string.
	 * Expected output: items that have been found by parameters other then singer name.
	 */
	@Test
	public void emptySingerStringSearchTest(){

		System.out.println(" ");
		System.out.println("***** Empty Singer SEARCH TEST *****");

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
		String [][] twoDArray = customer.genericSearch("rock", "Story of my life", 4, "");

		System.out.println(" ");
		System.out.println("--------------------- TEST Data --------------------------------------");
		for(int i = 0; i < twoDArray.length; i++){
			int subArrayLength = twoDArray[i].length;		
			for(int j = 0; j < subArrayLength; j++){
				System.out.print(twoDArray[i][j] + " ");
			}
			System.out.println(" ");
		}
	}
	

}
