package tablestests;

import static org.junit.Assert.*;
import com.mysql.jdbc.Connection;
import org.junit.Test;
import tables.Item;
import connection.DatabaseConnection;


/**
 * Tests for the ITEM table basic operations.
 */

public class itemTest{
	
	private Connection con;
	// ams represents a connection to an MySQL database
	private DatabaseConnection ams = DatabaseConnection.getInstance();
	
	@Test
	public void itemInsertTest(){

		// given
		// Connect to the database
		if(ams.connect("", "")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}

		con = (Connection) ams.getConnection();
		Item item = new Item(con);
		// when
		boolean status = item.insertItem(101, "Rain", "dvd", "drama", "ubc", 2014, 40, 10000);
		// then
		if(status == false){
			fail();
		}
		assertEquals(true, status);
		
	}
	
}
