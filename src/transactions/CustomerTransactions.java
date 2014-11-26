package transactions;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import tables.Customer;

import com.mysql.jdbc.Connection;

/**
 * Implements Customer transactions.
 */

public class CustomerTransactions{

	private Connection connection;
	private final static int COL_NUM = 5;

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
	SEARCH ALGORITHM

	Step 1. Try to find an precise item that matches the category, title, and singer name by calling preciseSearch().
			If such item(s) is found, then return its upc. End of search.
			If no precise item is found, then
	Step 2. Call searchItem(category, title, quantity) to search by category or title.
			If there are any items found with such title or category, then add them to the resulting array list.
			If no item is found, return nothing. 
	Step 3.  Call searchSinger(name) to search the singer by name.
			If no singer is found, return nothing.
			If a singer or singers are found, then return UPCs, and add them to the resulting array list.

	At the end, check if the resulting array list contains no elements (aka no items or singer found), then return null.
	Note that all these steps are done by the genericSearch() method.
	You don't need to call searchItem(), or searchSinger(), or preciseSearch().
	 */

	// TODO

	/**
	 * Searches for items.
	 * @return An array list of all UPCs that have been found.
	 */
	public String[][] genericSearch(String category, String title, int quantity, String name){

		ArrayList<Integer> upcs = new ArrayList<Integer>();
		ArrayList<Integer> precise_found_upcs = new ArrayList<Integer>();
		ArrayList<Integer> categoryOrTitleUpcs = new ArrayList<Integer>();
		ArrayList<Integer> singers_upcs = new ArrayList<Integer>();

		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>> ();
		for(int i = 0; i < COL_NUM; i++) {
			table.add(new ArrayList<String>());
		}

		if(quantity <= 0){
			System.err.println("Requested quantity cannot be zero or less. Please, try again.");
			return null;
		}
		
		if( name == null){
			System.err.println("Singer name cannot be null. Please, try again.");
			return null;
		}

		// STEP 1: Precise Search

		System.out.println(" ");
		System.out.println("Searching for a precise item...");
		
		precise_found_upcs = preciseSearch(category, title, quantity, name);

		if(precise_found_upcs == null){
			System.out.println("Problem in searching a precise item...");
		}
		else{
			if(precise_found_upcs.size() == 0){
				System.out.println("No precise items were found.");
			}
			else{
				//System.out.println("Found exact item(s) with category, title, and singer name.");
				//System.out.println("----------------------- Generic Search------------------------------------");

				//for(int i = 0; i < precise_found_upcs.size(); i++){
					//System.out.println(precise_found_upcs.get(i));
				//}
				//System.out.println(" ");

				ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>> ();
				result = populateReturnTable(precise_found_upcs, table, name);
				return dataTransform(result);
			}
		}


		// STEP 2: Search by category or title

		System.out.println(" ");
		System.out.println("Searching by category or title...");

		categoryOrTitleUpcs = searchItem(category, title, quantity);

		if(categoryOrTitleUpcs == null){
			System.out.println("Problem in searching item by category or title...");
		}
		else{
			if(categoryOrTitleUpcs.size() == 0){
				System.out.println("No items were found with category " + category + " or title " + title);
			}
			else{

				//System.out.println("Found item(s) with category or title");
				//System.out.println("----------------------- Generic Search------------------------------------");

				//for(int i = 0; i < categoryOrTitleUpcs.size(); i++){
					//System.out.println(categoryOrTitleUpcs.get(i));
				//}
				//System.out.println(" ");

				// Copy into upcs
				for(int i = 0; i < categoryOrTitleUpcs.size(); i++){

					if(!upcs.contains(categoryOrTitleUpcs.get(i))){
						upcs.add(categoryOrTitleUpcs.get(i));
					}
					//else{
						//System.out.println("Contains duplicate "  + categoryOrTitleUpcs.get(i));
					//}
				}
			}
		}


		// STEP 3: Search by Singer name

		System.out.println(" ");
		System.out.println("Searching by singer name...");

		singers_upcs = searchSinger(name, quantity);

		if(singers_upcs == null){
			System.out.println("Problem in searching by singer name...");
		}
		else{

			if(singers_upcs.size() == 0){
				System.out.println("No items were found with singer " + name);
			}
			else{

				//System.out.println("Found item(s) by singer");
				//System.out.println("----------------------- Generic Search------------------------------------");

				//for(int i = 0; i < singers_upcs.size(); i++){
					//System.out.println(singers_upcs.get(i));
				//}
				//System.out.println(" ");

				// Copy into upcs
				for(int i = 0; i < singers_upcs.size(); i++){

					if(!upcs.contains(singers_upcs.get(i))){
						upcs.add(singers_upcs.get(i));
					}
					//else{
						//System.out.println("Contains dulicate "  + singers_upcs.get(i));
					//}
				}
			}
		}

		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>> ();
		result = populateReturnTable(upcs, table, name);
		return dataTransform(result);
	}

	// TODO

	/**
	 * Populates a 2D array list with all the data that is to be displayed in GUI.
	 * @param upcs Array list of UPCs
	 * @param table 2D Arraylist that will be populated with data.
	 */
	private ArrayList<ArrayList<String>> populateReturnTable(ArrayList<Integer> upcs, ArrayList<ArrayList<String>> table, String singername){

		// Step 1. Retrieve the data from the Item table

		int stock;
		String title, category, name;
		Statement stmt;
		ResultSet rs;

		String statement = "SELECT title, category, stock FROM Item Where upc = ";

		try
		{
			stmt = connection.createStatement();

			//System.out.println("---------------------- POPULATING TABLE-------------------------------");
			//System.out.println("title               category       stock");

			for(int i = 0; i < upcs.size(); i++) {

				rs = stmt.executeQuery(statement + Integer.toString(upcs.get(i)));
				//ResultSetMetaData rsmd = rs.getMetaData();

				if(rs.next()) {
					title = rs.getString("title");
					category = rs.getString("category");
					stock = rs.getInt("stock");

					table.get(0).add(Integer.toString(upcs.get(i)));
					table.get(1).add(title);
					table.get(2).add(category);
					table.get(4).add(Integer.toString(stock));

					//System.out.printf("%-20s%-15s%-10s", title,category,stock);
					//System.out.println("  upc " + upcs.get(i));
				}
			}

			// close the statement
			stmt.close();
		}
		catch (SQLException ex){
			System.out.println("Populate Message: " + ex.getMessage());
		}

		// Step 2. Retrieve the data from the Singer table

		String singerStatement = "SELECT name FROM LeadSinger Where upc =  ";

		try
		{
			stmt = connection.createStatement();

			//System.out.println("---------------------- POPULATING SINGERs-------------------------------");

			for(int i = 0; i < upcs.size(); i++) {

				rs = stmt.executeQuery(singerStatement + Integer.toString(upcs.get(i)));
				//ResultSetMetaData rsmd = rs.getMetaData();

				if(rs.next()) {
					name = rs.getString("name");
					
					if(singername.equals(name)){
						table.get(3).add(name);
						//System.out.println("singer name: " + name + " upc " + upcs.get(i));
					}
					else{

						while(rs.next()){
							String anothername = rs.getString("name");
							
							if(singername.equals(anothername)){
								table.get(3).add(anothername);
								//System.out.println("singer name: " + anothername + " upc " + upcs.get(i));
							}
						}
					}
				}
				else{
					table.get(3).add("N/A");
					//System.out.println(" * No singer found for upc: " +  upcs.get(i));
				}
			}

			// close the statement
			stmt.close();
		}
		catch (SQLException ex){
			System.out.println("Populate Message: " + ex.getMessage());
		}
		return table;
	}

	// TODO

	/**
	 * Transforms a 2D array list into 2D string array.
	 */
	private String[][] dataTransform(ArrayList<ArrayList<String>> table) {
		
		//System.out.println(" *** Table 0th item size (number of UPCs): " + table.get(0).size());
		//System.out.println(" *** Table size: " + table.size());
		
		String[][] result = new String[table.get(0).size()][table.size()];
		for(int i = 0; i < table.get(0).size(); i++) {
			for(int j = 0; j < table.size(); j++) {
				result[i][j] = table.get(j).get(i);
			}
		}	
		return result;
	}


	// TODO

	/**
	 * Searches for a precise item.
	 * @return An array list of UPCs of the items that have been found.
	 */
	public ArrayList<Integer> preciseSearch(String category, String title, int quantity, String name){
		int existing_upc;
		int stock;
		String existing_category;
		String existing_title;
		String existing_name;
		ArrayList<Integer> precise_upcs = new ArrayList<Integer>();

		String statement = "SELECT I.upc, category, title, stock, S.name FROM Item I, LeadSinger S WHERE I.upc = S.upc AND (I.category LIKE '" + category + "' AND I.title LIKE '" + title +"' AND S.name LIKE '" + name +"')";
		//System.out.println("Attempting: " + statement);

		try
		{
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(statement);

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			if(numCols != 5){
				System.err.println("Item search Error: Failed to retrive exactly five columns.");
				stmt.close();
				return null;
			}

			//int[] formats = {15,15,15,15,15};

			//System.out.println("-----------------------------------------------------");

			// display column names;
			//for (int i = 0; i < numCols; i++){
				// get column name and print it
				//System.out.printf("%-"+formats[i] +"s", rsmd.getColumnName(i+1));    
			//}
			//System.out.println(" ");

			while(rs.next())
			{
				existing_upc = rs.getInt("upc");
				//System.out.printf("%-15.15s", existing_upc);

				existing_category = rs.getString("category");
				//System.out.printf("%-15.15s", existing_category);

				existing_title = rs.getString("title");
				//System.out.printf("%-15.15s", existing_title);

				stock = rs.getInt("stock");
				//System.out.printf("%-15.15s", stock);

				existing_name = rs.getString("name");
				//System.out.printf("%-15.15s\n", existing_name);

				if(stock == 0){
					System.err.println("Sorry, item " + existing_upc + " is out of stock.");
				}
				if(quantity <= stock){
					//System.out.println("Requsted quantity is available");
					precise_upcs.add(existing_upc);
				}
				else{
					if(stock > 0){
						//System.out.println("Requsted quantity is not available. Available: " + stock);
						precise_upcs.add(existing_upc);
					}
				}

			}
			return precise_upcs;

		} catch (SQLException e) {
			try {
				connection.rollback();
				System.out.println("Precise Item Search Error: " + e.getMessage());
				return null;
			} catch(SQLException e2) {
				System.out.println("Precise Item Search Rollback Error: " + e2.getMessage());
				System.exit(-1);
				return null;
			}
		}
	}


	// TODO

	/**
	 * Searches for an item with the given category or title.
	 * If stock = 0, then item is out of stock.
	 * If quantity <= stock, then item is considered available. Otherwise, not enough.
	 * @return An array list of UPCs that have been found.
	 */
	public ArrayList<Integer> searchItem(String category, String title, int quantity){
		int existing_upc;
		int stock;
		String existing_category;
		String existing_title;
		ArrayList<Integer> upcs_list = new ArrayList<Integer>();

		String statement = "SELECT upc, category, title, stock FROM Item WHERE (category LIKE '" + category + "' OR title LIKE '" + title +"')";
		//System.out.println("Attempting: " + statement);

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
				return null;
			}

			//int[] formats = {15,15,15,15};

			//System.out.println("-----------------------------------------------------");

			// display column names;
			//for (int i = 0; i < numCols; i++){
				// get column name and print it
				//System.out.printf("%-"+formats[i] +"s", rsmd.getColumnName(i+1));    
			//}
			//System.out.println(" ");

			while(rs.next())
			{
				existing_upc = rs.getInt("upc");
				//System.out.printf("%-15.15s", existing_upc);

				existing_category = rs.getString("category");
				//System.out.printf("%-15.15s", existing_category);

				existing_title = rs.getString("title");
				//System.out.printf("%-15.15s", existing_title);

				stock = rs.getInt("stock");
				//System.out.printf("%-15.15s\n", stock);

				if(stock == 0){
					System.err.println("Sorry, item " + existing_upc + " is out of stock.");
				}
				if(quantity <= stock){
					//System.out.println("Requsted quantity is available");
					upcs_list.add(existing_upc);
				}
				else{
					if(stock > 0){
						//System.out.println("Requsted quantity is not available. Available: " + stock);
						upcs_list.add(existing_upc);
					}
				}
			}

			return upcs_list;

		} catch (SQLException e) {
			try {
				connection.rollback();
				System.out.println("Search Item Error: " + e.getMessage());
				return null;
			} catch(SQLException e2) {
				System.out.println("Search Item Rollback Error: " + e2.getMessage());
				System.exit(-1);
				return null;
			}
		}
	}

	// TODO


	/**
	 * Searches for a Singer in the LeadSinger table given a singer name.
	 * @return An array list of UPCs of the tuples that have been found.
	 */
	public ArrayList<Integer> searchSinger(String name, int quantity){

		int existing_upc;
		int stock;
		//String existing_name;
		ArrayList<Integer> singer_upcs_list = new ArrayList<Integer>();
		ArrayList<Integer> upcs_list = new ArrayList<Integer>();

		if( name == null){
			System.err.println("Singer name cannot be null. Please, try again.");
			return null;
		}

		String statement = "SELECT upc, name FROM LeadSinger WHERE (name LIKE '" + name + "')";
		//System.out.println("Attempting: " + statement);

		try
		{
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(statement);

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			if(numCols != 2){
				System.err.println("Singer search error: Failed to retrive exactly two columns.");
				stmt.close();
				return null;
			}

			//int[] formats = {15,15};
			// display column names;
			//for (int i = 0; i < numCols; i++){
				//System.out.printf("%-"+formats[i] +"s", rsmd.getColumnName(i+1));    
			//}
			//System.out.println(" ");

			while(rs.next())
			{
				existing_upc = rs.getInt("upc");
				//System.out.printf("%-15.15s", existing_upc);

				//existing_name = rs.getString("name");
				//System.out.printf("%-15.15s\n", existing_name);

				singer_upcs_list.add(existing_upc);
			}

			stmt.close();

		} catch (SQLException e) {
			try {
				connection.rollback();
				System.out.println("Search Singer Error: " + e.getMessage());
				return null;
			} catch(SQLException e2) {
				System.out.println("Search Singer Rollback Error: " + e2.getMessage());
				System.exit(-1);
				return null;
			}
		}

		// CHECK FOR QUANTITY

		String itemStatement = "SELECT stock FROM Item Where upc = ";

		try
		{
			Statement stmt = connection.createStatement();
			for(int i = 0; i < singer_upcs_list.size(); i++) {
				ResultSet rs = stmt.executeQuery(itemStatement + Integer.toString(singer_upcs_list.get(i)));
				if(rs.next()) {
					stock = rs.getInt("stock");

					if(stock == 0){
						System.err.println("Sorry, item " + singer_upcs_list.get(i) + " is out of stock.");
					}
					if(quantity <= stock){
						//System.out.println("Requsted quantity is available");
						upcs_list.add(singer_upcs_list.get(i));
					}
					else{
						if(stock > 0){
							//System.out.println("Requsted quantity is not available. Available: " + stock);
							upcs_list.add(singer_upcs_list.get(i));
						}
					}

				}
			}

			// close the statement
			stmt.close();
		}
		catch (SQLException ex){
			System.out.println("Singer Search Message: " + ex.getMessage());
		}
		
		return upcs_list;
	}

}
