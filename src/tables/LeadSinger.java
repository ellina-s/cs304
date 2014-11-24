package tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

public class LeadSinger{
	private Connection con;

	/*
	 * Constructor for LeadSinger Table.
	 * Takes a database connection as a parameter.
	 */
	public LeadSinger(Connection con){
		this.con = con;
	}

	/*
	 * Inserts a tuple in LEADSINGER.
	 * Requires: an existing upc of a tuple from the ITEM table.
	 */
	public boolean insertLeadSinger( int upc, String name){

		PreparedStatement  ps;
		try {
			ps = con.prepareStatement("INSERT INTO leadsinger VALUES (?,?)");
			ps.setInt(1, upc);
			ps.setString(2, name);

			ps.executeUpdate();
			con.commit();
			System.out.println("LeadSinger ( " + upc + ", " + name + ") inserted successfully");
			ps.close();
			return true;

		} catch (SQLException e1) {			
			try {
				con.rollback();
				System.out.println("LeadSinger Insertion Error: " + e1.getMessage());
				return false;
			} catch(SQLException e2) {
				System.out.println("LeadSinger Rollback Error: " + e2.getMessage());
				System.exit(-1);
				return false;
			}
		}
	}

	/*
	 * Deletes a tuple from LeadSinger based on the item upc.
	 */
	public boolean deleteLeadSinger(int upc){

		try {
			PreparedStatement ps = con.prepareStatement("DELETE FROM LeadSinger WHERE upc = ?");
			ps.setInt(1, upc);
			int rowCount = ps.executeUpdate();
			if(rowCount == 0) {
				System.out.println("LeadSinger for item # " + upc + " does not exist.");
				ps.close();
				return false;
			}
			con.commit();
			System.out.println("LeadSinger for item # " + upc + " deleted successfully.");
			ps.close();
			return true;

		} catch (SQLException e) {
			System.out.println("LeadSinger Deletion Error: " + e.getMessage());
			try {
				con.rollback();
				return false;
			}
			catch (SQLException ex2)
			{
				System.out.println("LeadSinger Deletion Rollback Error:: " + ex2.getMessage());
				System.exit(-1);
				return false;
			}
		}
	}


	/*
	 * Displays all tuples from the LEADSINGER table.
	 */
	public void displayAllLeadSingers(){
		int upc;
		String name;

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM LEADSINGER");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();
			int[] formats = {10,20};

			System.out.println("-----------------------------------------------------");
			// display column names;
			for (int i = 0; i < numCols; i++){
				// get column name and print it

				System.out.printf("%-"+formats[i] +"s", rsmd.getColumnName(i+1));    
			}
			System.out.println(" ");
			
			while(rs.next()){
				upc = rs.getInt("upc");
				name = rs.getString("name");
				System.out.printf("%-10s%-20s\n", upc,name);
			}
			System.out.println("-----------------------------------------------------");
			stmt.close();

		} catch (SQLException e) {
			System.out.println("Message: " + e.getMessage());
		}

	}
	
	/*
	 * Returns a 2D array list of LeadSinger tuples.
	 */
	public String[][] getLeadSinger(){
		ArrayList<ArrayList<String>> table = null; 
		int upc;
		String name;
		
		try
		{
		  Statement stmt = con.createStatement();
		  ResultSet rs = stmt.executeQuery("SELECT * FROM LeadSinger");

		  // get info on ResultSet
		  ResultSetMetaData rsmd = rs.getMetaData();
		  
		  // get number of columns
		  int numCols = rsmd.getColumnCount();
		  table = new ArrayList<ArrayList<String>> (numCols);
		  
		  // display column names;
		  for (int i = 0; i < numCols; i++)
		  {
		      // get column name and print it
			  table.add(new ArrayList<String> ());
			  table.get(i).add(rsmd.getColumnName(i + 1));
		  }

		  while(rs.next())
		  {
		      // for display purposes get everything from database 
		      // as a string

		      // simplified output formatting; truncation may occur

		      upc = rs.getInt("upc");
		      table.get(0).add(Integer.toString(upc));
		      
		      name = rs.getString("name");
		      table.get(1).add(name);
		  }
	 
		  // close the statement; 
		  // the ResultSet will also be closed
		  stmt.close();
		}
		catch (SQLException ex)
		{
		    System.out.println("Message: " + ex.getMessage());
		}
		
		if(table != null) {
			String[][] result = new String[table.get(0).size()][table.size()];
			for(int i = 0; i < table.get(0).size(); i++) {
				for(int j = 0; j < table.size(); j++) {
					result[i][j] = table.get(j).get(i);
				}
			}
			
			return result;
		}
		else {
			return null;
		}	
		
		
	}

}
