package tablestests;

import static org.junit.Assert.*;

import java.util.Random;

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

	/*
	 * Tests a successful insertion of a tuple in ITEM.
	 */
	@Test
	public void itemInsertTest(){

		// given
		// Connect to the database
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}
		con = (Connection) ams.getConnection();
		Item item = new Item(con);
		// when
		Random randomGenerator = new Random();
		int random_upc = randomGenerator.nextInt(100) * 5;
		boolean status = item.insertItem(random_upc, "RandomTestItem", "dvd", "country", "randomProduction", 2014, 10, 5);
		// then
		if(status == false){
			fail();
		}
		assertEquals(true, status);
	}

	/*
	 * Tests a successful deletion of a tuple from ITEM.
	 */
	@Test
	public void itemDeleteTest(){

		// given
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}
		con = (Connection) ams.getConnection();
		Item item = new Item(con);

		// when
		boolean status = item.insertItem(111, "Jungle", "dvd", "instrumental", "geo", 1996, 10, 40);
		if(status == false){
			fail();
		}
		assertEquals(true, status);

		// then
		boolean deleteStatus = item.deleteItem(111);
		if(deleteStatus == false){
			fail();
		}
		assertEquals(true, deleteStatus);
	}

	/*
	 * Tests a display of all tuple in ITEM.
	 */
	@Test
	public void displayItemsTest(){
		// given
		if(ams.connect("root", "cs304pwd")){
			System.out.println("You entered valid credentials.");
		}
		else{
			System.out.println("Unable to connect.");
		}

		// when
		con = (Connection) ams.getConnection();
		Item item = new Item(con);
		// then
		item.displayAllItems();
	}

}
