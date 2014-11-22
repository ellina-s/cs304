package tablestests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import tables.Customer;
import tables.PurchaseItem;

import com.mysql.jdbc.Connection;

import connection.DatabaseConnection;

public class PurchaseItemTest {

	private Connection con;
	// ams represents a connection to an MySQL database
	private DatabaseConnection ams = DatabaseConnection.getInstance();
	
	
	//Need to insert an Item and a Purchase due to Foreign Key Constraints.
	@Test
	public void CustomerInsertTest(){

		// given
		// Connect to the database
		if(ams.connect("root", "(Ad727363)")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}

		con = (Connection) ams.getConnection();
		PurchaseItem c = new PurchaseItem(con);
		// when
		boolean status = c.insertPurchaseItem(1, 2, 3);
		// then
		if(status == false){
			fail();
		}
		assertEquals(true, status);
		c.showPurchaseItem();
		c.deletePurchaseItem(1, 3);
		c.showPurchaseItem();
	}
	
}
