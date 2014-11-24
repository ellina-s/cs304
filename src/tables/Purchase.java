package tables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
	 
	 //receiptId int not null,
	//	purchaseDate DATE not null,
	//	cid int not null,
	//	card_num int not null,
	//	expiryDate DATE not null,
	//	expectedDate DATE,
	//	deliveredDate DATE,
	 
	 public String[][] getPurchase() {
			ArrayList<ArrayList<String>> table = null; 
			
			int receiptId;
			String purchaseDate;
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
			  table = new ArrayList<ArrayList<String>> (numCols);
			  
			  // display column names;
			  for (int i = 0; i < numCols; i++)
			  {
			      // get column name and print it
				  table.add(new ArrayList<String> ());
				  //table.get(i).add(rsmd.getColumnName(i + 1));
			  }

			  while(rs.next())
			  {
			      // for display purposes get everything from database 
			      // as a string

			      // simplified output formatting; truncation may occur

			      receiptId = rs.getInt("receiptId");
			      table.get(0).add(Integer.toString(receiptId));
			      
			      purchaseDate = rs.getString("purchaseDate");
			      table.get(1).add(purchaseDate);

			      cid = rs.getInt("cid");
			      table.get(2).add(Integer.toString(cid));
			      
			      card_num = rs.getInt("card_num");
			      table.get(3).add(Integer.toString(card_num));
			      
			      expiryDate = rs.getString("expiryDate");
			      table.get(4).add(expiryDate);
			      
			      expectedDate = rs.getString("expectedDate");
			      table.get(5).add(expectedDate);
			      
			      deliveredDate = rs.getString("deliveredDate");
			      table.get(6).add(deliveredDate);
			      
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
