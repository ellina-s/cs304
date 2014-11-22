package transactions;

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

			// display column names;
			//for (int i = 0; i < numCols; i++){
				// get column name and print it
				//System.out.printf("%s\n", rsmd.getColumnName(i+1));    
			//}

			// Check for existing cid values
			while(rs.next())
			{
				existing_cid = rs.getInt("cid");
				//System.out.printf("Exhisting %-15.15s\n", existing_cid);

				if(cid == existing_cid){
					System.out.println("cid " + cid + " is already in the system. Please, provide a different cid.");
					return false;
				}
			}
			stmt.close();
			
			// Add a customer, since the given cid is not in the system.
			Customer c = new Customer(connection);
			boolean status = c.insertCustomer(cid, password, name, address, phone);

			if(status == true){
				System.out.println("Customer registered.");
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

}
