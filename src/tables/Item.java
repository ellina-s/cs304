package tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	 * Inserts a tuple in the ITEM table.
	 * Returns true if successful, false otherwise.
	 */
	public boolean insertItem( int upc, String title, String type, String category, String company, int year, float price, int stock){

		if(!type.equals("dvd") && !type.equals("cd")){
			System.out.println("Type of Item can be cd or dvd only. Tuple rejected.");
			return false;
		}
		
		if(!category.equals("rock") && !category.equals("pop") && !category.equals("rap") && !category.equals("country") && !category.equals("classical") && !category.equals("new age") && !category.equals("instrumental")){
			System.out.println("Category of Item can be only : rock, pop, rap, country, classical, new age, instrumental. Tuple rejected.");
			return false;
		}
		
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
	public boolean deleteItem(int upc){

		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM Item WHERE upc = ?");
			ps.setInt(1, upc);
			int rowCount = ps.executeUpdate();
			if(rowCount == 0) {
				System.out.println("Item # " + upc + " does not exist.");
				ps.close();
				return false;
			}
			connection.commit();
			System.out.println("Item # " + upc + " deleted successfully.");
			ps.close();
			return true;

		} catch (SQLException e) {
			System.out.println("Item Deletion Error: " + e.getMessage());
			try {
				connection.rollback();
				return false;
			}
			catch (SQLException ex2)
			{
				System.out.println("Item Deletion Rollback Error:: " + ex2.getMessage());
				System.exit(-1);
				return false;
			}
		}

	}

	/*
	 * Displays all tuples from the ITEM table.
	 */
	public void displayAllItems(){

		int upc, year, stock;
		float price;
		String title, type, category, company;

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM item");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();
			int[] formats = {10,20,10,10,20,10,10,10};
		
			System.out.println("-----------------------------------------------------");

			// display column names;
			for (int i = 0; i < numCols; i++){
				// get column name and print it

				System.out.printf("%-"+formats[i] +"s", rsmd.getColumnName(i+1));    
			}
			System.out.println(" ");
			
			while(rs.next()){
				upc = rs.getInt("upc");
				year = rs.getInt("year");
				stock = rs.getInt("stock");
				price = rs.getFloat("price");
				title = rs.getString("title");
				type = rs.getString("type");
				category = rs.getString("category");
				company = rs.getString("company");
				
				System.out.printf("%-10s%-20s%-10s%-10s%-20s%-10s%-10s%-10s\n", 
						upc,title,type,category,company,year,price,stock);
				
			}
			System.out.println("-----------------------------------------------------");
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




		
	}
	
	 public String[][] getItem() {
		ArrayList<ArrayList<String>> table = null; 
		
		int upc;
		String title;
		String type;
		String category;
		String company;
		int year;
		float price;
		int stock;
	    	
	    Statement  stmt;
	    ResultSet  rs;
		   
		try
		{
		  stmt = connection.createStatement();

		  rs = stmt.executeQuery("SELECT * FROM Item");

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
		      
		      title = rs.getString("title");
		      table.get(1).add(title);

		      type = rs.getString("type");
		      table.get(2).add(type);
		      
		      category = rs.getString("category");
		      table.get(3).add(category);
		      
		      company = rs.getString("company");
		      table.get(4).add(company);
		      
		      year = rs.getInt("year");
		      table.get(5).add(Integer.toString(year));
		      
		      price = rs.getFloat("price");
		      table.get(6).add(Float.toString(price));
		      
		      stock = rs.getInt("stock");
		      table.get(7).add(Integer.toString(stock));
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
