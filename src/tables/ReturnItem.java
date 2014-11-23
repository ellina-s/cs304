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

public class ReturnItem{
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	 private Connection con;
	 
	 public ReturnItem(Connection con) {
		 this.con = con;
	 }
	 
	 public void deleteReturnItem(int retid, int upc) {
			PreparedStatement ps;
			
			try {
				ps = con.prepareStatement("DELETE FROM ReturnItem WHERE retid = ? AND upc = ?");
				ps.setInt(1, retid);
				ps.setInt(2, upc);
				
				int rowCount = ps.executeUpdate();

				if (rowCount == 0)
				{
					System.out.println("\nReturnItem " + retid + " " + upc + " does not exist!");
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
	 
	 public void deleteReturnItem() {
		 	int retid;
		 	int upc;
			PreparedStatement ps;
			
			try {
				ps = con.prepareStatement("DELETE FROM ReturnItem WHERE retid = ? AND upc = ?");
				System.out.println("retid: ");
				retid = in.read();
				ps.setInt(1, retid);
				
				System.out.println("upc: ");
				upc = in.read();
				ps.setInt(2, upc);
				
				int rowCount = ps.executeUpdate();

				if (rowCount == 0)
				{
					System.out.println("\nReturnItem " + retid + " " + upc + " does not exist!");
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
	 
	 public boolean insertReturnItem(int retid, int upc, int quantity) {
			PreparedStatement ps;
			boolean inserted = true;
			
			try {
				ps = con.prepareStatement("INSERT INTO ReturnItem VALUES (?,?,?)");
				ps.setInt(1, retid);
				ps.setInt(2, upc);
				ps.setInt(3, quantity);
				
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

	
	 public boolean insertReturnItem() {
		int retid;
		int upc;
		int quantity;
		boolean inserted = true;
		
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement("INSERT INTO ReturnItem VALUES (?,?,?)");
			System.out.println("retid: ");
			retid = in.read();
			ps.setInt(1, retid);
			
			System.out.println("upc: ");
			upc = in.read();
			ps.setInt(2, upc);
			
			System.out.println("quantity: ");
			quantity = in.read();
			ps.setInt(3, quantity);
			
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
	 
	 
	 
	 
	 public void showReturnItem() {
		int retid;
		int upc;
		int quantity;
	    	
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

		      retid = rs.getInt("retid");
		      System.out.printf("%-15.15s", retid);
		      
		      upc = rs.getInt("upc");
		      System.out.printf("%-15.15s", upc);

		      quantity = rs.getInt("quantity");
		      System.out.printf("%-15.15s\n", quantity);
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
