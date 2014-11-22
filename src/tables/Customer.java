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

public class Customer{
	
	 private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	 private Connection con;
	 
	 public Customer(Connection con) {
		 this.con = con;
	 }
	 
	 public void deleteCustomer(int cid) {
			PreparedStatement ps;
			
			try {
				ps = con.prepareStatement("DELETE FROM Customer WHERE cid = ?");
				ps.setInt(1, cid);
				
				int rowCount = ps.executeUpdate();

				if (rowCount == 0)
				{
					System.out.println("\nCustomer " + cid + " does not exist!");
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
	 
	 public void deleteCustomer() {
		 	int cid;
			PreparedStatement ps;
			
			try {
				ps = con.prepareStatement("DELETE FROM Customer WHERE cid = ?");
				System.out.println("Customer ID: ");
				cid = in.read();
				ps.setInt(1, cid);
				
				int rowCount = ps.executeUpdate();

				if (rowCount == 0)
				{
					System.out.println("\nCustomer " + cid + " does not exist!");
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
	 
	 public boolean insertCustomer(int cid, String password, String name, String address, String phone) {
			PreparedStatement ps;
			boolean inserted = true;
			
			try {
				ps = con.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?,?)");
				ps.setInt(1, cid);
				ps.setString(2, password);
				ps.setString(3, name);
				ps.setString(4, address);
				ps.setString(5, phone);
				
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

	
	 public boolean insertCustomer() {
		int cid;
		String password;
		String name;
		String address;
		String phone;
		boolean inserted = true;
		
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?,?)");
			System.out.println("Customer ID: ");
			cid = in.read();
			ps.setInt(1, cid);
			
			System.out.println("Customer Password: ");
			password = in.readLine();
			ps.setString(2, password);
			
			System.out.println("Customer Name: ");
			name = in.readLine();
			ps.setString(3, name);
			
			System.out.println("Customer Address: ");
			address = in.readLine();
			ps.setString(4, address);
			
			System.out.println("Customer Phone: ");
			phone = in.readLine();
			ps.setString(5, phone);
			
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
	 
	 
	 
	 
	 public void showCustomer() {
		int cid;
		String password;
		String name;
		String address;
		String phone;
	    	
	    Statement  stmt;
	    ResultSet  rs;
		   
		try
		{
		  stmt = con.createStatement();

		  rs = stmt.executeQuery("SELECT * FROM Customer");

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

		      cid = rs.getInt("cid");
		      System.out.printf("%-15.15s", cid);
		      
		      password = rs.getString("password");
		      System.out.printf("%-15.15s", password);

		      name = rs.getString("name");
		      System.out.printf("%-15.15s", name);
		      
		      address = rs.getString("address");
		      System.out.printf("%-15.15s", address);
		      
		      phone = rs.getString("phone");
		      System.out.printf("%-15.15s", phone);
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
