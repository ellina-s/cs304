package tablestests;

import static org.junit.Assert.*;
import com.mysql.jdbc.Connection;
import org.junit.Test;
import tables.LeadSinger;
import connection.DatabaseConnection;


/**
 * Tests for the LEADSINGER table basic operations.
 */
public class leadSingerTest{

	private Connection con;
	// ams represents a connection to an MySQL database
	private DatabaseConnection ams = DatabaseConnection.getInstance();
	private final static int ITEM_UPC = 105;

	/*
	 * Tests insertion of a tuple in the LEADSINGER table.
	 * Requires: ITEM_UPC be an existing upc in the ITEM table.
	 */
	@Test
	public void insertLeadSingerTest(){
		// given
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}
		con = (Connection) ams.getConnection();
		LeadSinger leadSinger = new LeadSinger(con);

		// when
		boolean status = leadSinger.insertLeadSinger(ITEM_UPC, "Sinatra");
		// then
		if(status == false){
			fail();
		}
		assertEquals(true, status);
	}
	

	/*
	 * Tests deleting a tuple from LeadSinger.
	 * Requires: upc should be an upc of an existing item.
	 */
	@Test
	public void deleteLeadSinger(){
		// given
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}
		con = (Connection) ams.getConnection();
		LeadSinger leadSinger = new LeadSinger(con);
		
		// when
		boolean status = leadSinger.deleteLeadSinger(105);
		// then
		if(status == false){
			fail();
		}
		assertEquals(true, status);
	}

}