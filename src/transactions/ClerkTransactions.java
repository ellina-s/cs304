package transactions;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.mysql.jdbc.Connection;

public class ClerkTransactions {
	
	 private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	 private Connection con;
	 
	 private int retId;
	 
	 public ClerkTransactions(Connection con) {
		 this.con = con;
		 
		 
	 }	
	 
	 public void returnItem(int receiptId, int upc, int quantity, String day) {
		 
	 }
	
}
