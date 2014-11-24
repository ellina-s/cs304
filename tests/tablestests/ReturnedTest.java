package tablestests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import tables.Returned;

import com.mysql.jdbc.Connection;

import connection.DatabaseConnection;

public class ReturnedTest {

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
		Returned c = new Returned(con);
		// when
		boolean status = c.insertReturned(5, "2015-01-01", 11);
		
		// then
		if(status == false){
			fail();
		}
		assertEquals(true, status);
		c.showReturned();
		c.deleteReturned(5);
		c.showReturned();
	}
	
}
