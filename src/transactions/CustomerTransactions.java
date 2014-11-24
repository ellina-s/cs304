package transactions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import tables.Customer;

import com.mysql.jdbc.Connection;

/**
 * Implements Customer transactions.
 */

public class CustomerTransactions{

	private Connection connection;

	/**
	 * Constructor.
	 * @param con - a database connection
	 */
	public CustomerTransactions(Connection con){
		this.connection = con;
	}

	/**
	 * Customers who access the store online for the first time,
	 * will be asked to register by providing their personal information,
	 * including their name, their address, phone number,  an id and a password.  
	 * If the id is already in the system, they will be asked to provide another one. 
	 * @return True if successfully registered a new customer, false otherwise.
	 */
	public boolean registerCustomer(int cid, String password, String name, String address, String phone){

		Statement  stmt;
		ResultSet  rs;
		int existing_cid;

		try
		{
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT cid FROM Customer");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			if(numCols != 1){
				System.err.println("Customer Registration Error: Failed to retrive exactly one column.");
				stmt.close();
				return false;
			}

			// Check for existing cid values
			while(rs.next())
			{
				existing_cid = rs.getInt("cid");
				//System.out.printf("Existing %-15.15s\n", existing_cid);

				if(cid == existing_cid){
					System.out.println("cid " + cid + " is already in the system. Please, provide a different cid.");
					stmt.close();
					return false;
				}
			}
			stmt.close();

			// Add a customer, since the given cid is not in the system.
			Customer c = new Customer(connection);
			boolean status = c.insertCustomer(cid, password, name, address, phone);

			if(status == true){
				System.out.println("Customer with cid " + cid +" registered.");
				return true;
			}
			else{
				System.out.println("Customer was not registered.");
				return false;
			}

		} catch (SQLException e) {	
			System.out.println("Customer Registration Error: " + e.getMessage());
			return false;
		}	
	}


	/*
	 * Online Purchase.
	 * 1) To buy online, customers have to identify themselves  by typing their customer id and password.
	 * Customers who access the store online for the first time have to register first.
	 * 2) To shop at the site, registered customers will be given a virtual shopping basket
	 *    and asked to specify the  item they want to buy.
	 * The customer will describe the item by providing the category, or the title,
	 * or the leading singer (or all of them),  and the quantity. --> CHECK THAT STOCK IS NOT ZERO? IF ZERO, THEN DON'T RETURN SUCH ITEM.
	 * -- >> LATER, IN STEP 4, CHECK THAT QUANTITY THAT THE CUSTOMER REQUESTED CAN BE SATISIFIED (COMPARE WITH STOCK).
	 *  If the information is not enough to define a single item,
	 * the system will display all the items that match the input and ask the customer to select one.
	 * 3) When an item is selected, it will be added to the customer shopping cart. --- > RECORD ITEM ID?
	 * Each time an item is selected the system has to make sure that there is enough quantity in the store to complete the purchase.
	 * Otherwise the system will ask the customer to accept the existing quantity. -- > COMPARE REQUESTED QUANTITY WITH STOCK
	 * The customer can repeat the same process for any number of items.
	 * 4) When the customer is ready to check-out, the system will produce a bill with the items and the total amount.
	 * 5) The client has to provide a credit card number and expiry date to complete the transaction.
	 *  After that,  the system will create a purchase for the store
	 *  and inform the customer about the number of days it will take to receive the goods.
	 * This number is estimated by the number of outstanding orders and the maximum number of orders that can be delivered in a day
	 * (which is fixed; you need to decide on this). 
	 */


	/**
	 * Skeleton method for online purchases.
	 */
	public void purchaseOnline(){

		// STEP 1.
		// In the gui, ask: "Are you a new or returning customer?" New -> Register, Returning -> Check customer password and ID.


	}

	/**
	 * Authenticates a customer.
	 * @param cid - customer id
	 * @param password - customer password
	 * @return True if successfully authenticated, false otherwise.
	 */
	public boolean authenticateCustomer(int cid, String password){

		Statement  stmt;
		ResultSet  rs;
		int existing_cid;
		String existing_password;

		try
		{
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT cid, password FROM Customer");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			if(numCols != 2){
				System.err.println("Customer Registration Error: Failed to retrive exactly two columns.");
				stmt.close();
				return false;
			}

			while(rs.next())
			{
				existing_cid = rs.getInt("cid");
				//System.out.printf("Existing %-15.15s  ", existing_cid);

				existing_password = rs.getString("password");
				//System.out.printf("Ehisting %-15.15s\n", existing_password);

				if(cid == existing_cid && password.equals(existing_password) ){
					System.out.println("Customer authenticated successfully.");
					stmt.close();
					return true;
				}
			}
			stmt.close();
			System.out.println("Invalid customer id or password. Please, try again.");
			return false;

		} catch (SQLException e) {	
			System.out.println("Customer Authentication Error: " + e.getMessage());
			return false;
		}
	}

	/*
	 * Specify category, or the title, or the leading singer (or all of them),  and the quantity.
	 * category, title - > ITEM - > record cid
	 * leading singer - > LeadSinger -> record cid
	 * quantity -> compare to stock in ITEM -> check stock of the given cid
	 */
	
	/**
	 * Searches for an item with the given category or title (case sensitive).
	 * If stock = 0, then item is out of stock.
	 * If quantity <= stock, then item is considered available. Otherwise, not enough.
	 */
	public boolean searchItem(String category, String title, int quantity){
		int existing_upc;
		int stock;
		String existing_category;
		String existing_title;

		String statement = "SELECT upc, category, title, stock FROM Item WHERE (category LIKE '" + category + "' OR title LIKE '" + title +"')";
		//System.out.println("Attempting: " + statement);

		if(quantity <= 0){
			System.err.println("Requested quantity cannot be zero or less. Please, try again.");
			return false;
		}
		
		try
		{
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(statement);

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			if(numCols != 4){
				System.err.println("Item search Error: Failed to retrive exactly four columns.");
				stmt.close();
				return false;
			}
			
			int[] formats = {15,15,15,15};
			
			System.out.println("-----------------------------------------------------");

			// display column names;
			for (int i = 0; i < numCols; i++){
				// get column name and print it
				System.out.printf("%-"+formats[i] +"s", rsmd.getColumnName(i+1));    
			}
			System.out.println(" ");

			while(rs.next())
			{
				existing_upc = rs.getInt("upc");
				System.out.printf("%-15.15s", existing_upc);

				existing_category = rs.getString("category");
				System.out.printf("%-15.15s", existing_category);

				existing_title = rs.getString("title");
				System.out.printf("%-15.15s", existing_title);
				
				stock = rs.getInt("stock");
				System.out.printf("%-15.15s\n", stock);
				
				if(category.equals(existing_category) && title.equals(existing_title)){
					System.out.println("UPC: " + existing_upc  + " Matching category: " + existing_category + " Matching title: " + existing_title);
					if(stock == 0){
						System.out.println("Sorry, item " + existing_upc + " is out of stock.");
						return false;
					}
					if(quantity <= stock){
						System.out.println("Requsted quantity is available");
					}
					else{
						System.out.println("Requsted quantity is not available. Available: " + stock);
					}
				}
				
				if(category.equals(existing_category)){
					System.out.println("UPC: " + existing_upc  + " Matching category: " + existing_category);
					if(stock == 0){
						System.out.println("Sorry, item " + existing_upc + " is out of stock.");
						return false;
					}
					if(quantity <= stock){
						System.out.println("Requsted quantity is available");
					}
					else{
						System.out.println("Requsted quantity is not available. Available: " + stock);
					}
				}
				
				if(title.equals(existing_title)){
					System.out.println("UPC: " + existing_upc  + " Matching title: " + existing_title);
					if(stock == 0){
						System.out.println("Sorry, item " + existing_upc + " is out of stock.");
						return false;
					}
					if(quantity <= stock){
						System.out.println("Requsted quantity is available");
					}
					else{
						System.out.println("Requsted quantity is not available. Available: " + stock);
					}
				}
			}
			return false;
		} catch (SQLException e) {
			try {
				connection.rollback();
				System.out.println("Search Item Error: " + e.getMessage());
				return false;
			} catch(SQLException e2) {
				System.out.println("Search Item Rollback Error: " + e2.getMessage());
				System.exit(-1);
				return false;
			}
		}
	}

}
