package transactions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

public class ClerkTransactions {
	
	 private Connection con;
	 
	 private int retId;
	 
	 public ClerkTransactions(Connection con) {
		 this.con = con;
		 retId = 0;
		    	
		 Statement  stmt;
		 ResultSet  rs;
		    	    
		 String statement = "SELECT Max(R.retId) max_retId FROM Returned R";
		    
		 try {
			  stmt = con.createStatement();
			  rs = stmt.executeQuery(statement);
			  if(rs.next()) {
			  	retId = rs.getInt("max_retId");
			  }
			  // close the statement; 
			  // the ResultSet will also be closed
			  stmt.close();
			}
			catch (SQLException ex)
			{
			    System.out.println("Message: " + ex.getMessage());
			    retId = 0;
			}
	 }
	 

	 public String[][] returnItem(int receiptId, ArrayList<Integer> upc, ArrayList<Integer> quantity, String day) {
		 Statement  stmt = null;
		 ResultSet  rs;
		 ResultSet rs1;
		 PreparedStatement ps;
		 int q = 0;
		 int card_num = 0;
		 int q1 = 0;
		 int price = 0;
		 float sum = 0;
		 boolean returnedItem = false;
		 ManagerTransactions m = new ManagerTransactions(con);
		 
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>> ();
		
		for(int i = 0; i < 4; i++) {
			table.add(new ArrayList<String>());
		}

		 boolean isValidReceipt = isValidReceipt(receiptId);
		 if(!isValidReceipt) {
			 table.get(0).add("Not a valid receipt");
			 table.get(1).add("");
			 table.get(2).add("");
			 table.get(3).add("");
			 return dataTransform(table);
		 }
		 
		 boolean isReturnable = isReturnable(receiptId, day);
		 if(!isReturnable) {
			 table.get(0).add("Days passed >= 15");
			 table.get(1).add(" purchase date");
			 table.get(2).add("");
			 table.get(3).add("");
			 return dataTransform(table);
		 }
		 
		 if(isReturnable && isValidReceipt) {
			 try {
				String statement = "SELECT PI.upc, P.card_num, PI.quantity, I.price FROM Purchase P, PurchaseItem PI, Item I WHERE " + 
						"PI.receiptId = " + Integer.toString(receiptId) + " AND PI.receiptId = P.receiptId AND PI.upc = I.upc AND PI.upc = ";
				String statement1 = "SELECT SUM(RI.quantity) quantity FROM ReturnItem RI, Returned R WHERE R.retId = RI.retId AND R.receiptId = " + Integer.toString(receiptId) + " AND RI.upc = ";
				for(int i = 0; i < upc.size(); i++) {
					stmt = con.createStatement();
					 rs = stmt.executeQuery(statement + Integer.toString(upc.get(i)));
					 stmt = con.createStatement();
					 rs1 = stmt.executeQuery(statement1 + Integer.toString(upc.get(i)));
					 if(rs1.next()) {
						 q = rs1.getInt("quantity");
					 }
					 else {
						 q = 0;
					 }
					 if(rs.next()) {
						 q1 = rs.getInt("quantity");
						 card_num = rs.getInt("card_num");
						 price = rs.getInt("price");
						 if(quantity.get(i) > q1 - q) {
							 quantity.remove(i);
							 quantity.add(i, q1 - q);
						 }
						 if(quantity.get(i) > 0) {
							 if(!returnedItem) {
								ps = con.prepareStatement("INSERT INTO Returned VALUES (?,?,?)");
								retId++;
								ps.setInt(1, retId);
								ps.setString(2, day);
								ps.setInt(3, receiptId);
								ps.executeUpdate();
								con.commit();
								ps.close();
								returnedItem = true;
							 }
							 ps = con.prepareStatement("INSERT INTO ReturnItem VALUES (?,?,?)");
							 sum += price * quantity.get(i);
							 ps.setInt(1, retId);
							 ps.setInt(2, upc.get(i));
							 ps.setInt(3, quantity.get(i));
							 table.get(0).add(Integer.toString(upc.get(i)));
							 table.get(1).add(Integer.toString(quantity.get(i)));
							 table.get(2).add(Float.toString(price));
							 table.get(3).add(Float.toString(price * quantity.get(i)));
							 m.addItems(upc.get(i), quantity.get(i), -1);
							 ps.executeUpdate();
							 con.commit();
							 ps.close();
						 }
					 }
				}
				
				stmt.close();
			} catch (SQLException ex) {
				 System.out.println("Message: " + ex.getMessage());
			}
			 if(returnedItem) {
				 table.get(0).add("");
				 table.get(1).add("Refunded to ");
				 table.get(2).add("card number " + Integer.toString(card_num));
				 table.get(3).add(Float.toString(sum));
			 }
			 else {
				 table.get(0).add("Cannot return items");
				 table.get(1).add("");
				 table.get(2).add("");
				 table.get(3).add("");
			 }
		 }
		 
		 return dataTransform(table);
	 }
	 
	 private boolean isValidReceipt(int receiptId) {
		 String statement = "SELECT * FROM Purchase P WHERE receiptId = " + Integer.toString(receiptId);
		 Statement stmt;
		 ResultSet rs;
		 boolean isReturnable = false;
		 
		 try {
			 stmt = con.createStatement();
			 rs = stmt.executeQuery(statement);
			 isReturnable =  rs.next();
			 stmt.close();
		 }
		 catch (SQLException ex) {
			 System.out.println("Message: " + ex.getMessage());
			 isReturnable = false;
		}

		 return isReturnable;
	 }
	 
	 private boolean isReturnable(int receiptId, String day) {
		 String statement = "SELECT * FROM Purchase P WHERE receiptId = " + Integer.toString(receiptId) + " AND purchaseDate >= Date_SUB('" + day + "', INTERVAL 15 DAY)" ;
		 Statement stmt;
		 ResultSet rs;
		 boolean isReturnable = false;
		 
		 try {
			 stmt = con.createStatement();
			 rs = stmt.executeQuery(statement);
			 isReturnable =  rs.next();
			 stmt.close();
		 }
		 catch (SQLException ex) {
			 System.out.println("Message: " + ex.getMessage());
			 isReturnable = false;
		}

		 return isReturnable;
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
