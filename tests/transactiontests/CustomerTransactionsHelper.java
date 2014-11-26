package transactiontests;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

public class CustomerTransactionsHelper{
	
	private Connection connection;

	/**
	 * Constructor.
	 * @param con - a database connection
	 */
	public CustomerTransactionsHelper(Connection con){
		this.connection = con;
	}
	
	
	/**
	 * Searches for an item with the given category or title.
	 * If stock = 0, then item is out of stock.
	 * If quantity <= stock, then item is considered available. Otherwise, not enough.
	 */
	public boolean searchItemHelper(String category, String title, int quantity){
		int existing_upc;
		int stock;
		String existing_category;
		String existing_title;
		ArrayList<Integer> upcs_list = new ArrayList<Integer>();


		if(quantity <= 0){
			System.err.println("Requested quantity cannot be zero or less. Please, try again.");
			return false;
		}


		String statement = "SELECT upc, category, title, stock FROM Item WHERE (category LIKE '" + category + "' OR title LIKE '" + title +"')";
		System.out.println("Attempting: " + statement);

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

				if(stock == 0){
					System.err.println("Sorry, item " + existing_upc + " is out of stock.");
				}
				if(quantity <= stock){
					System.out.println("Requsted quantity is available");
					upcs_list.add(existing_upc);
				}
				else{
					System.out.println("Requsted quantity is not available. Available: " + stock);
					upcs_list.add(existing_upc);
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
	
	/**
	 * Searches for a Singer in the LeadSinger table given a singer name.
	 */
	public boolean searchSingerHelper(String name){

		int existing_upc;
		String existing_name;

		if(name == "" || name == null){
			System.err.println("Name cannot be null or empty. Please, try again.");
			return false;
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
				return false;
			}

			//int[] formats = {15,15};
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

				existing_name = rs.getString("name");
				//System.out.printf("%-15.15s\n", existing_name);

				if(name.equals(existing_name)){
					System.out.println("UPC: " + existing_upc  + " Matching name: " + existing_name);
					return true;
				}
			}

			System.err.println("No such singer was found.");
			return false;
		} catch (SQLException e) {
			try {
				connection.rollback();
				System.out.println("Search Singer Error: " + e.getMessage());
				return false;
			} catch(SQLException e2) {
				System.out.println("Search Singer Rollback Error: " + e2.getMessage());
				System.exit(-1);
				return false;
			}
		}

	}
	
	
	
}
