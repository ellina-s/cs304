package tablestests;

import static org.junit.Assert.*;

import com.mysql.jdbc.Connection;

import org.junit.Test;

import tables.HasSong;
import tables.LeadSinger;
import connection.DatabaseConnection;

/**
 * Tests for the HASSONG table basic operations.
 */
public class hasSongTest{

	private Connection con;
	// ams represents a connection to an MySQL database
	private DatabaseConnection ams = DatabaseConnection.getInstance();

	/**
	 * Tests inserting a tuple in the HasSong table.
	 */
	@Test
	public void insertHasSongTest(){
		// given
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}
		con = (Connection) ams.getConnection();
		HasSong hasSong = new HasSong(con);
		// when
		boolean status = hasSong.insertHasSong(100, "Only You (song)");
		// then
		if(status == false){
			fail();
		}
		assertEquals(true, status);
	}

	/**
	 * Tests deleting a tuple from the HasSong table.
	 */
	@Test
	public void deleteHasSongTest(){
		// given
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}
		con = (Connection) ams.getConnection();
		HasSong hasSong = new HasSong(con);
		// when
		boolean status = hasSong.deleteHasSong(100);
		// then
		if(status == false){
			fail();
		}
		assertEquals(true, status);
	}

	/**
	 * Tests displaying all tuples from HasSong.
	 */
	@Test
	public void displayHasSongTest(){
		// given
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}
		// when
		con = (Connection) ams.getConnection();
		HasSong hasSong = new HasSong(con);
		// then
		hasSong.displayAllHasSong();
	}


}