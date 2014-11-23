package transactions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

public class ManagerTransactions{
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	 private Connection con;
	 	 
	 public ManagerTransactions(Connection con) {
		 this.con = con;
	 }
	 
	 //SELECT I.upc, I.title, I.company, I.stock, SUM(PI.quantity) units
	 //FROM Item I, Purchase P, PurchaseItem PI
	 //WHERE I.upc = PI.upc and P.receiptId = PI.receiptId and P.date like '01/01/14'
	 //Group by upc
	 //Order by units DESC
	 //LIMIT 5;
	 public String[][] topSellingItems(String day, int n) {
			ArrayList<ArrayList<String>> table = null; 

			int upc;
			String company;
			int stock;
			int quantity;
		    	
		    Statement  stmt;
		    ResultSet  rs;
		    	    
		    String statement = "SELECT I.upc, I.company, I.stock, Sum(PI.quantity) units FROM Item I, Purchase P, PurchaseItem PI "
					+ "WHERE I.upc = PI.upc and P.receiptId = PI.receiptId and P.date like "  + "'" + day + "'" + " Group By category, upc "
		    		+ "Order by units DESC LIMIT " + Integer.toString(n);
		    
			try
			{
			  stmt = con.createStatement();
			  rs = stmt.executeQuery(statement);

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
			      company = rs.getString("company");
			      stock = rs.getInt("stock");
			      quantity = rs.getInt("units");
			      
			      table.get(0).add(Integer.toString(upc));
			      table.get(1).add(company);
			      table.get(2).add(Integer.toString(stock));
			      table.get(3).add(Integer.toString(quantity));
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
				return dataTransform(table);
			}
			else {
				return null;
			}	
	 }
	 
	
	 
	 public String[][] dailySalesReport(String day) {
		ArrayList<ArrayList<String>> table = null; 

		int upc;
		String category;
		float price;
		int quantity;
	    	
	    Statement  stmt;
	    ResultSet  rs;
	    	    
	    String statement = "SELECT I.upc, I.category, I.price, Sum(PI.quantity) units FROM Item I, Purchase P, PurchaseItem PI "
				+ "WHERE I.upc = PI.upc and P.receiptId = PI.receiptId and P.date like "  + "'" + day + "'" + " Group By category, upc";
	    
		try
		{
		  stmt = con.createStatement();
		  rs = stmt.executeQuery(statement);

		  // get info on ResultSet
		  ResultSetMetaData rsmd = rs.getMetaData();

		  // get number of columns
		  int numCols = rsmd.getColumnCount();
		  table = new ArrayList<ArrayList<String>> (numCols + 1);
		  
		  // display column names;
		  for (int i = 0; i < numCols; i++)
		  {
		      // get column name and print it
			  table.add(new ArrayList<String> ());
			  table.get(i).add(rsmd.getColumnName(i + 1));
		  }
		  
		  table.add(new ArrayList<String> ());
		  table.get(numCols).add("Total Value");

		  String previous = null;
		  float SumPrice = 0;
		  int SumQuantity = 0;
		  float TotalPrice = 0;
		  int TotalQuantity = 0;
		  
		  while(rs.next())
		  {
		      // for display purposes get everything from database 
		      // as a string

		      // simplified output formatting; truncation may occur
			  float tempPrice = 0;
			  
		      upc = rs.getInt("upc");
		      category = rs.getString("category");
		      price = rs.getFloat("price");
		      quantity = rs.getInt("units");
		      
		      if(!category.equals(previous) && previous != null) {
		    	  table.get(0).add("");
			      table.get(1).add("Total");
			      table.get(2).add("");
			      table.get(3).add(Integer.toString(SumQuantity));
			      table.get(4).add(Float.toString(SumPrice));
			      TotalPrice += SumPrice;
			      TotalQuantity += SumQuantity;
			      SumPrice = 0;
			      SumQuantity = 0;
		      }
		      
		      tempPrice = ((float) quantity) * price;
		      SumPrice += tempPrice;
		      SumQuantity += quantity;
		      
		      table.get(0).add(Integer.toString(upc));
		      table.get(1).add(category);
		      table.get(2).add(Float.toString(price));
		      table.get(3).add(Integer.toString(quantity));
		      table.get(4).add(Float.toString(tempPrice));
		      
		      previous = category;
		  }
		  
		  table.get(0).add("");
	      table.get(1).add("Total");
	      table.get(2).add("");
	      table.get(3).add(Integer.toString(SumQuantity));
	      table.get(4).add(Float.toString(SumPrice));
	      TotalPrice += SumPrice;
	      TotalQuantity += SumQuantity;
		  
		  table.get(0).add("");
		  table.get(1).add("Total Daily Sales");
		  table.get(2).add("");
		  table.get(3).add(Integer.toString(TotalQuantity));
		  table.get(4).add(Float.toString(TotalPrice));
	 
		  // close the statement; 
		  // the ResultSet will also be closed
		  stmt.close();
		}
		catch (SQLException ex)
		{
		    System.out.println("Message: " + ex.getMessage());
		}
		
		if(table != null) {
			return dataTransform(table);
		}
		else {
			return null;
		}	
	}
	 
	 public String[][] dataTransform(ArrayList<ArrayList<String>> table) {
		 String[][] result = new String[table.get(0).size()][table.size()];
			for(int i = 0; i < table.get(0).size(); i++) {
				for(int j = 0; j < table.size(); j++) {
					result[i][j] = table.get(j).get(i);
				}
			}
			
			return result;
	 }
}
