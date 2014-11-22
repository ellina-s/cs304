package tables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

public class Returned{
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	 private Connection con;
	 
	 public Returned(Connection con) {
		 this.con = con;
	 }
	 
	 public void deleteReturned(int retid) {
			PreparedStatement ps;
			
			try {
				ps = con.prepareStatement("DELETE FROM Returned WHERE retid = ?");
				ps.setInt(1, retid);
				
				int rowCount = ps.executeUpdate();

				if (rowCount == 0)
				{
					System.out.println("\nReturned " + retid + " " + " does not exist!");
				}

				con.commit();

				ps.close();
			}
			catch (SQLException ex) {
			    System.out.println("Message: " + ex.getMessage());
			    try {
				// undo the insert
				con.rollback();	
			    }
			    catch (SQLException ex2) {
			    	System.out.println("Message: " + ex2.getMessage());
			    	System.exit(-1);
			    }
			}
		}
	 
	 public void deleteReturned() {
		 	int retid;
			PreparedStatement ps;
			
			try {
				ps = con.prepareStatement("DELETE FROM Returned WHERE retid = ?");
				System.out.println("retid: ");
				retid = in.read();
				ps.setInt(1, retid);
				
				
				int rowCount = ps.executeUpdate();

				if (rowCount == 0)
				{
					System.out.println("\nReturned " + retid + " does not exist!");
				}

				con.commit();

				ps.close();
			} catch (IOException e) {
			    System.out.println("IOException!");
			}
			catch (SQLException ex) {
			    System.out.println("Message: " + ex.getMessage());
			    try {
				// undo the insert
				con.rollback();	
			    }
			    catch (SQLException ex2) {
			    	System.out.println("Message: " + ex2.getMessage());
			    	System.exit(-1);
			    }
			}
		}
	 
	 public boolean insertReturned(int retid, String date, int receiptId) {
			PreparedStatement ps;
			boolean inserted = true;
			
			try {
				ps = con.prepareStatement("INSERT INTO Returned VALUES (?,?,?)");
				ps.setInt(1, retid);
				ps.setString(2, date);
				ps.setInt(3, receiptId);
				
				ps.executeUpdate();

				// commit work 
				con.commit();

				ps.close();
			}
			catch (SQLException ex) {
			    System.out.println("Message: " + ex.getMessage());
			    inserted = false;
			    try {
			    	// undo the insert
			    	con.rollback();	
			    }
			    catch (SQLException ex2) {
			    	inserted = false;
			    	System.out.println("Message: " + ex2.getMessage());
			    	System.exit(-1);
			    }
			}
			
			return inserted;
		}

	
	 public boolean insertReturned() {
		int retid;
		String date;
		int receiptId;
		boolean inserted = true;
		
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement("INSERT INTO ReturnItem VALUES (?,?,?)");
			System.out.println("retid: ");
			retid = in.read();
			ps.setInt(1, retid);
			
			System.out.println("date: ");
			date = in.readLine();
			ps.setString(2, date);
			
			System.out.println("receiptId: ");
			receiptId = in.read();
			ps.setInt(3, receiptId);
			
			ps.executeUpdate();

			  // commit work 
			con.commit();

			ps.close();
		}
		catch (IOException e) {
		    System.out.println("IOException!");
		    inserted = false;
		}
		catch (SQLException ex) {
		    System.out.println("Message: " + ex.getMessage());
		    inserted = false;
		    try {
		    	// undo the insert
		    	con.rollback();	
		    }
		    catch (SQLException ex2) {
		    	inserted = false;
		    	System.out.println("Message: " + ex2.getMessage());
		    	System.exit(-1);
		    }
		}
		return inserted;
	}
	 
	 
	 
	 
	 public void showReturned() {
		int retid;
		String date;
		int receiptId;
	    	
	    Statement  stmt;
	    ResultSet  rs;
		   
		try
		{
		  stmt = con.createStatement();

		  rs = stmt.executeQuery("SELECT * FROM ReturnItem");

		  // get info on ResultSet
		  ResultSetMetaData rsmd = rs.getMetaData();

		  // get number of columns
		  int numCols = rsmd.getColumnCount();

		  System.out.println(" ");
		  
		  // display column names;
		  for (int i = 0; i < numCols; i++)
		  {
		      // get column name and print it

		      System.out.printf("%-15s", rsmd.getColumnName(i+1));    
		  }

		  System.out.println(" ");

		  while(rs.next())
		  {
		      // for display purposes get everything from database 
		      // as a string

		      // simplified output formatting; truncation may occur

		      retid = rs.getInt("receiptId");
		      System.out.printf("%-15.15s", retid);
		      
		      date = rs.getString("date");
		      System.out.printf("%-15.15s", date);

		      receiptId = rs.getInt("receiptId");
		      System.out.printf("%-15.15s\n", receiptId);
		  }
	 
		  // close the statement; 
		  // the ResultSet will also be closed
		  stmt.close();
		}
		catch (SQLException ex)
		{
		    System.out.println("Message: " + ex.getMessage());
		}	
	}
}
