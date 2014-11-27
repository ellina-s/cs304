package transactions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

public class CustomerSubTransactions {

	
	private Connection con;
	
	private int receiptId;
	
	private final int MAXDELIVERIES = 5;
	
	private static float sum;

	public CustomerSubTransactions(Connection con){
		this.con = con;
		
		receiptId = 0;
    	
		Statement  stmt;
		ResultSet  rs;
		    	    
		String statement = "SELECT Max(P.receiptId) max_receiptId FROM Purchase P";
		    
		try {
			  stmt = con.createStatement();
			  rs = stmt.executeQuery(statement);
			  if(rs.next()) {
			  	receiptId = rs.getInt("max_receiptId");
			  }
			  // close the statement; 
			  // the ResultSet will also be closed
			  stmt.close();
			}
			catch (SQLException ex)
			{
			    System.out.println("Message: " + ex.getMessage());
			    receiptId = 0;
			}
	}
	
	
	public String[][] produceBill(ArrayList<Integer> upc, ArrayList<Integer> quantity) {
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>> ();
		for(int i = 0; i < 8; i++) {
			table.add(new ArrayList<String>());
		}
		
		
		String title;
		String type;
		String category;
		String company;
		int stock;
		float price;
		float sum = 0;
		
		Statement stmt;
		ResultSet rs;
		
		String statement = "SELECT I.title, I.type, I.category, I.company, I.price, I.stock From Item I WHERE I.upc = ";
		try {
			stmt = con.createStatement();
			for(int i = 0; i < upc.size(); i++) {
				rs = stmt.executeQuery(statement + Integer.toString(upc.get(i)));
				if(rs.next()) {
					title = rs.getString("title");
					type = rs.getString("type");
					category = rs.getString("category");
					company = rs.getString("company");
					price = rs.getFloat("price");
					stock = rs.getInt("stock");
					if(quantity.get(i) > stock) {
						quantity.remove(i);
						quantity.add(i, stock);
					}
					sum += price * quantity.get(i);
					
					table.get(0).add(Integer.toString(upc.get(i)));
					table.get(1).add(title);
					table.get(2).add(type);
					table.get(3).add(category);
					table.get(4).add(company);
					table.get(5).add(Integer.toString(quantity.get(i)));
					table.get(6).add(Float.toString(price));
					table.get(7).add(Float.toString(price * quantity.get(i)));
				}
			}
			
			table.get(0).add("");
			table.get(1).add("");
			table.get(2).add("");
			table.get(3).add("");
			table.get(4).add("");
			table.get(5).add("");
			table.get(6).add("Total");
			table.get(7).add(Float.toString(sum));
			
			CustomerSubTransactions.sum = sum;

			stmt.close();
		} catch (SQLException ex)
		{
		    System.out.println("Message: " + ex.getMessage());
		    return null;
		}
		
		return dataTransform(table);
	}
	
	public String checkOut(ArrayList<Integer> upc, ArrayList<Integer> quantity, int cid, int card_num, String purchaseDate, String expiryDate) {
		PreparedStatement ps;
		Statement  stmt = null;
		ResultSet  rs;
		String expectedDeliveryDate = null;
		String statement = "SELECT I.stock From Item I WHERE I.upc = ";
		int p = Integer.decode(purchaseDate.substring(0, 4) + purchaseDate.substring(5,7) + purchaseDate.substring(8));		
		int e = Integer.decode(expiryDate.substring(0, 4) + expiryDate.substring(5,7) + expiryDate.substring(8));
		
		if(p <= e) {
			expectedDeliveryDate = expectedDeliveryDate(Integer.decode(purchaseDate.substring(0,4)), Integer.decode(purchaseDate.substring(5,7)), Integer.decode(purchaseDate.substring(8)));
			try {
				ps = con.prepareStatement("INSERT INTO Purchase VALUES (?,?,?,?,?,?,?)");
				receiptId++;
				ps.setInt(1, receiptId);
				ps.setString(2, purchaseDate);
				ps.setInt(3, cid);
				ps.setInt(4, card_num);
				ps.setString(5, expiryDate);
				ps.setString(6, expectedDeliveryDate);
				ps.setString(7, null);
				ps.executeUpdate();
				con.commit();
				ps.close();
				
				for(int i = 0; i < upc.size(); i++) {
					ps = con.prepareStatement("INSERT INTO PurchaseItem VALUES (?, ?, ?)");
					ps.setInt(1, receiptId);
					ps.setInt(2, upc.get(i));
					ps.setInt(3, quantity.get(i));
					ps.executeUpdate();
					con.commit();
					ps.close();
					
					ps = con.prepareStatement("UPDATE Item Set stock = ? WHERE upc = ?");
					stmt = con.createStatement();
					rs = stmt.executeQuery(statement + Integer.toString(upc.get(i)));
					if(rs.next()) {
						ps.setInt(1, rs.getInt("stock") - quantity.get(i));
						ps.setInt(2, upc.get(i));
						ps.executeUpdate();
						con.commit();
						ps.close();
					}
					stmt.close();
				}
				
			} catch (SQLException ex) {
				 System.out.println("Message: " + ex.getMessage());
				 return "Could not purchase";
			}
		}
		else {
			return "Credit card passed expiry date";
		}
		
		return (Float.toString(sum) + " charged to card number " + Integer.toString(card_num) + ".  Your receipt ID is " + Integer.toString(receiptId) + ".  Expected delivery date is " + expectedDeliveryDate + ".");
		
	}
	
	private String expectedDeliveryDate(int year, int month, int day) {
		String statement = "SELECT Count(*) num_dates From Purchase P WHERE P.expectedDate = ";
		Statement  stmt = null;
		ResultSet  rs;
		int total = MAXDELIVERIES + 1;
		
		try {
			while(total >= MAXDELIVERIES) {
				stmt = con.createStatement();
				day++;
				if((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && day > 31) {
					day = 1;
					month++;
				}
				if(month == 2 && year % 4 == 0 && day > 29) {
					month++;
					day = 1;
				}
				if((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
					day = 1;
					month ++;
				}
				if(month > 12) {
					month = 1;
					year++;
				}
				rs = stmt.executeQuery((statement + "'"+ Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day) + "'"));
				if(rs.next()) {
					total = rs.getInt("num_dates");
				}
				else {
					total = 0;
				}
				stmt.close();
			}
		} catch (SQLException ex) {
			 System.out.println("Message: " + ex.getMessage());
			 return null;
		}
		
		
		return (Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day));
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
