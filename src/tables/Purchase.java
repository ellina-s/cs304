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

public class Purchase{
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	 private Connection con;
	 
	 public Purchase(Connection con) {
		 this.con = con;
	 }
	 
	 public void deletePurchase(int receiptId) {
			PreparedStatement ps;
			
			try {
				ps = con.prepareStatement("DELETE FROM Purchase WHERE receiptId = ?");
				ps.setInt(1, receiptId);
				
				int rowCount = ps.executeUpdate();

				if (rowCount == 0)
				{
					System.out.println("\nPurchase " + receiptId + " does not exist!");
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
	 
	 public void deletePurchase() {
		 	int receiptId;
			PreparedStatement ps;
			
			try {
				ps = con.prepareStatement("DELETE FROM Purchase WHERE receiptId = ?");
				System.out.println("ReceiptId: ");
				receiptId = in.read();
				ps.setInt(1, receiptId);
				
				int rowCount = ps.executeUpdate();

				if (rowCount == 0)
				{
					System.out.println("\nPurchase " + receiptId +  " does not exist!");
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
	 
	 public boolean insertPurchase(int receiptId, String purchaseDate, int cid, int card_num, String expiryDate, String expectedDate, String deliveredDate) {
			PreparedStatement ps;
			boolean inserted = true;
			
			try {
				ps = con.prepareStatement("INSERT INTO Purchase VALUES (?,?,?,?,?,?,?)");
				ps.setInt(1, receiptId);
				ps.setString(2, purchaseDate);
				ps.setInt(3, cid);
				ps.setInt(4, card_num);
				ps.setString(5, expiryDate);
				ps.setString(6, expectedDate);
				ps.setString(7, deliveredDate);
				
				
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

	
	 public boolean insertPurchase() {
		int receiptId;
		String date;
		int cid;
		int card_num;
		String expiryDate;
		String expectedDate;
		String deliveredDate;
		boolean inserted = true;
		
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement("INSERT INTO Purchase VALUES (?,?,?,?,?,?,?)");
			System.out.println("ReceiptId: ");
			receiptId = in.read();
			ps.setInt(1, receiptId);
			
			System.out.println("date: ");
			date = in.readLine();
			ps.setString(2, date);
			
			System.out.println("cid: ");
			cid = in.read();
			ps.setInt(3, cid);
			
			System.out.println("card_num: ");
			card_num = in.read();
			ps.setInt(4, card_num);
			
			System.out.println("expiryDate: ");
			expiryDate = in.readLine();
			ps.setString(5, expiryDate);
			
			System.out.println("expectedDate: ");
			expectedDate = in.readLine();
			if (expectedDate.length() == 0) {
				ps.setString(6, null);
			}
			else {
				ps.setString(6, expectedDate);
			}
			
			System.out.println("deliveredDate: ");
			deliveredDate = in.readLine();
			if (deliveredDate.length() == 0) {
				ps.setString(7, null);
			}
			else {
				ps.setString(7, deliveredDate);
			}
			
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
	 
	 
	 
	 
	 public void showPurchase() {
		int receiptId;
		String date;
		int cid;
		int card_num;
		String expiryDate;
		String expectedDate;
		String deliveredDate;
	    	
	    Statement  stmt;
	    ResultSet  rs;
		   
		try
		{
		  stmt = con.createStatement();

		  rs = stmt.executeQuery("SELECT * FROM Purchase");

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

		      receiptId = rs.getInt("receiptId");
		      System.out.printf("%-15.15s", receiptId);
		      
		      date = rs.getString("purchaseDate");
		      System.out.printf("%-15.15s", date);
		      
		      cid = rs.getInt("cid");
		      System.out.printf("%-15.15s", cid);
		      
		      card_num = rs.getInt("card_num");
		      System.out.printf("%-15.15s", card_num);
		      
		      expiryDate = rs.getString("expiryDate");
		      System.out.printf("%-15.15s", expiryDate);
		      
		      expectedDate = rs.getString("expectedDate");
		      System.out.printf("%-15.15s", expectedDate);
		      
		      deliveredDate = rs.getString("deliveredDate");
		      System.out.printf("%-15.15s\n", deliveredDate);
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
