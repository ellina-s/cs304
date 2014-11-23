package tablestests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import tables.ReturnItem;

import com.mysql.jdbc.Connection;

import connection.DatabaseConnection;

public class ReturnItemTest {
	private Connection con;
	// ams represents a connection to an MySQL database
	private DatabaseConnection ams = DatabaseConnection.getInstance();
	
	
	//Need to insert an Item and a Purchase due to Foreign Key Constraints.
	@Test
	public void ReturnedInsertTest(){

		// given
		// Connect to the database
		if(ams.connect("root", "(Ad727363)")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}

		con = (Connection) ams.getConnection();
		ReturnItem c = new ReturnItem(con);
		// when
		boolean status = c.insertReturnItem(1, 2, 1);
		
		// then
		if(status == false){
			fail();
		}
		assertEquals(true, status);
		c.showReturnItem();
		c.deleteReturnItem(1, 2);
		c.showReturnItem();
	}
}
