package tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class Item{

	private Connection connection;

	/*
	 * Constructor for Item Table.
	 * Takes a database connection as a parameter.
	 */
	public Item(Connection con){
		this.connection = con;

		/*
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 */
	}


	/**
	 * Inserts an Item in the ITEM table.
	 * Returns true if successful, false otherwise.
	 */
	public boolean insertItem( int upc, String title, String type, String category, String company, int year, float price, int stock){

		PreparedStatement  ps;

		try {
			ps = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?,?,?,?,?)");
			ps.setInt(1, upc);
			ps.setString(2, title);
			ps.setString(3, type);
			ps.setString(4, category);
			ps.setString(5, company);
			ps.setInt(6, year);
			ps.setFloat(7, price);
			ps.setInt(8, stock);
			ps.executeUpdate();
			connection.commit();
			System.out.println("Item ( " + upc + ", " + title +  ", " + type + ", " + 
					category + ", " + company + ", " + year + ", " + price + ", " + stock + ") inserted successfully");
			ps.close();
			return true;

		} catch (SQLException e1) {			
			try {
				connection.rollback();
				System.out.println("Item Insertion Error: " + e1.getMessage());
				return false;
			} catch(SQLException e2) {
				System.out.println("Item Rollback Error: " + e2.getMessage());
				System.exit(-1);
				return false;
			}
		}
	}
	
	/*
	 * Deletes a tuple from the ITEM table.
	 */
	public boolean deleteItem(){
		
		return false;
	}

}
