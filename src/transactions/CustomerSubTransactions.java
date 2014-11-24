package transactions;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

public class CustomerSubTransactions {

	
	private Connection con;

	public CustomerSubTransactions(Connection con){
		this.con = con;
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
		float price;
		float sum = 0;
		
		Statement stmt;
		ResultSet rs;
		
		String statement = "SELECT I.title, I.type, I.category, I.company, I.price From Item I WHERE I.upc = ";
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

			stmt.close();
		} catch (SQLException ex)
		{
		    System.out.println("Message: " + ex.getMessage());
		    return null;
		}
		
		return dataTransform(table);
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
