package tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

/*
 * Basic operations for the HASSONG table.
 */
public class HasSong{

	private Connection con;

	/*
	 * Constructor for HASSONG Table.
	 * Takes a database connection as a parameter.
	 */
	public HasSong(Connection con){
		this.con = con;
	}

	/*
	 * Inserts a tuple in HASSONG.
	 * Requires: an existing upc of a tuple from the ITEM table.
	 */
	public boolean insertHasSong( int upc, String title){

		PreparedStatement  ps;
		try {
			ps = con.prepareStatement("INSERT INTO HASSONG VALUES (?,?)");
			ps.setInt(1, upc);
			ps.setString(2, title);

			ps.executeUpdate();
			con.commit();
			System.out.println("HasSong ( " + upc + ", " + title + ") inserted successfully");
			ps.close();
			return true;

		} catch (SQLException e1) {			
			try {
				con.rollback();
				System.out.println("HasSong Insertion Error: " + e1.getMessage());
				return false;
			} catch(SQLException e2) {
				System.out.println("HasSong Rollback Error: " + e2.getMessage());
				System.exit(-1);
				return false;
			}
		}
	}

	/**
	 * Deletes a tuple from HASSONG.
	 * Requires a upc of an existing item.
	 */
	public boolean deleteHasSong(int upc){
		try {
			PreparedStatement ps = con.prepareStatement("DELETE FROM HasSong WHERE upc = ?");
			ps.setInt(1, upc);
			int rowCount = ps.executeUpdate();
			if(rowCount == 0) {
				System.out.println("HasSong for item # " + upc + " does not exist.");
				ps.close();
				return false;
			}
			con.commit();
			System.out.println("HasSong for item # " + upc + " deleted successfully.");
			ps.close();
			return true;

		} catch (SQLException e) {
			System.out.println("HasSong Deletion Error: " + e.getMessage());
			try {
				con.rollback();
				return false;
			}
			catch (SQLException ex2)
			{
				System.out.println("HasSong Deletion Rollback Error:: " + ex2.getMessage());
				System.exit(-1);
				return false;
			}
		}
	}

	/**
	 * Displays all tuples from HasSong.
	 */
	public void displayAllHasSong(){
		int upc;
		String title;

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HASSONG");

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
				title = rs.getString("title");
				System.out.printf("%-10s%-20s\n", upc,title);
			}
			System.out.println("-----------------------------------------------------");
			stmt.close();

		} catch (SQLException e) {
			System.out.println("Message: " + e.getMessage());
		}


	}

	/**
	 * Returns a 2D array list of HasSong tuples.
	 */
	public String[][] getHasSong(){
		ArrayList<ArrayList<String>> table = null; 
		int upc;
		String title;

		try
		{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HASSONG");

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
				// for display purposes get everything from database as a string
				// simplified output formatting; truncation may occur

				upc = rs.getInt("upc");
				table.get(0).add(Integer.toString(upc));

				title = rs.getString("title");
				table.get(1).add(title);
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
