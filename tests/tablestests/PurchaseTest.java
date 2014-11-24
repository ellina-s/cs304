package tablestests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import tables.Purchase;

import com.mysql.jdbc.Connection;

import connection.DatabaseConnection;

public class PurchaseTest {

	private Connection con;
	// ams represents a connection to an MySQL database
	private DatabaseConnection ams = DatabaseConnection.getInstance();
	
	
	//Need to insert an Item and a Purchase due to Foreign Key Constraints.
	@Test
	public void PurchaseInsertTest(){

		// given
		// Connect to the database
		if(ams.connect("root", "(Ad727363)")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}

		con = (Connection) ams.getConnection();
		Purchase c = new Purchase(con);
		// when
		boolean status = c.insertPurchase(100, "2014-01-01", 1, 50, "2014-01-01", "2014-01-01", "2014-01-01");
		
		// then
		//if(status == false){
		//	fail();
		//}
		//assertEquals(true, status);
		String[][] test = c.getPurchase();
		for(int i = 0; i < test.length; i++) {
			for(int j = 0; j < test[i].length; j++) {
			      System.out.printf("%-15.15s", test[i][j]);
			}
			System.out.println();
		}
	}
}
