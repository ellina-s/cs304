package transactions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

public class ClerkTransactions {
	
	 private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

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
	 
	 
	 /*public String returnItem(int receiptId, int[] upc, int[] quantity, String day) {
		 Statement  stmt;
		 ResultSet  rs;
		 PreparedStatement ps;
		 
		 int upcR;
		 int card_numR = 0;
		 int quantityR;
		 float priceR;
		 int count = 0;
		 float sum = 0;
		 
		 boolean isReturnable = isReturnable(receiptId, day);
		 if(isReturnable) {
			 String statement = "SELECT PI.upc, P.card_num, PI.quantity, I.price FROM Purchase P, PurchaseItem PI, Item I WHERE " + 
			"PI.receiptId = " + Integer.toString(receiptId) + " AND PI.upc = I.upc AND (";
			 
			 
			 for(int i = 0; i < upc.length; i++) {
				 if(i == 0) statement = statement + " PI.upc = " + Integer.toString(upc[i]);
				 else statement = statement + " OR PI.upc = " + Integer.toString(upc[i]);
			 }
			 statement = statement + " )";
			 			 
			 try {
				 stmt = con.createStatement();
				 rs = stmt.executeQuery(statement);
				 retId++;
				 
				 ps = con.prepareStatement("INSERT INTO Returned VALUES (?,?,?)");
				 ps.setInt(1, retId);
				 ps.setString(2, day);
				 ps.setInt(3, receiptId);
				 ps.executeUpdate();
				 con.commit();
				 ps.close();
				 
				 while(rs.next()) {
					 upcR = rs.getInt("upc");
					 card_numR = rs.getInt("card_num");
					 quantityR = rs.getInt("quantity");
					 priceR = rs.getFloat("price");
					 System.out.println(count);
					 if(count < upc.length && upc[count] == upcR) {
						 ps = con.prepareStatement("INSERT INTO ReturnItem VALUES (?,?,?)");
						 if(quantity[count] > quantityR) {
							 quantity[count] = quantityR; 
						 }
						 sum += priceR * quantity[count];
						 ps.setInt(1, retId);
						 ps.setInt(2, upc[count]);
						 ps.setInt(3, quantity[count]);
						 ps.executeUpdate();
						 con.commit();
						 ps.close();
					 }
					count++;
				 }
				 
				 stmt.close();
			 }
			 catch (SQLException ex)
			{
				 System.out.println("Message: " + ex.getMessage());
			}
			 return Float.toString(sum) + " returned to card number " + Integer.toString(card_numR);
		 }
		 else {
			 return "Item could not be returned";
		 }
	 }*/
	 
	 
	 //SELECT SUM(quantity) q
	 //FROM Returned R, ReturnItem RI
	 //WHERE R.receiptId = 3 AND R.retId = RI.retId
	 //Group by upc;
	 public String returnItem(int receiptId, int[] upc, int[] quantity, String day) {
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
		 
		 boolean isReturnable = isReturnable(receiptId, day);
		 if(isReturnable) {
			 try {
				String statement = "SELECT PI.upc, P.card_num, PI.quantity, I.price FROM Purchase P, PurchaseItem PI, Item I WHERE " + 
						"PI.receiptId = " + Integer.toString(receiptId) + " AND PI.receiptId = PI.receiptId AND PI.upc = I.upc AND PI.upc = ";
				String statement1 = "SELECT SUM(RI.quantity) quantity FROM ReturnItem RI, Returned R WHERE R.retId = RI.retId AND R.receiptId = " + Integer.toString(receiptId) + " AND RI.upc = ";
				for(int i = 0; i < upc.length; i++) {
					stmt = con.createStatement();
					 rs = stmt.executeQuery(statement + Integer.toString(upc[i]));
					 stmt = con.createStatement();
					 rs1 = stmt.executeQuery(statement1 + Integer.toString(upc[i]));
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
						 if(quantity[i] > q1 - q) {
							 quantity[i] = q1 - q;
						 }
						 if(quantity[i] != 0) {
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
							 sum += price * quantity[i];
							 ps.setInt(1, retId);
							 ps.setInt(2, upc[i]);
							 ps.setInt(3, quantity[i]);
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
			 
			 
			 return Float.toString(sum) + " returned to card number " + Integer.toString(card_num);
		 }
		 else {
			 return "Item could not be returned";
		 }
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
	
}
