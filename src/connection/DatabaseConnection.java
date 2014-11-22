package connection;

import java.sql.*; 
import java.io.*;

/**
 * Based on the CPSC 304 Tutorial.
 * Reference: http://www.cs.ubc.ca/~laks/cpsc304/Swing/jdbc_swing.html
 */


/*
 * This class is a singleton class that provides methods 
 * to connect to a MySQL database, return the connection, 
 * set the connection, and determine whether or not the MySQL
 * JDBC driver has been loaded. To obtain a reference to an
 * instance of this class, use the getInstance() method.
 * 
 * ams stands for Allegro Music Store.
 */ 
public class DatabaseConnection
{
	private static DatabaseConnection _ams = null;
	protected Connection con = null;
	protected boolean driverLoaded = false;
	
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	/*
	 * The constructor is declared protected so that only subclasses
	 * can access it.
	 */ 
	protected DatabaseConnection(){
		// empty
	}


	/*
	 * Returns an instance of DatabaseConnection
	 */ 
	public static DatabaseConnection getInstance(){
		if (_ams == null){
			_ams = new DatabaseConnection(); 
		}
		return _ams;
	}


	/* 
	 * Loads the MySQL JDBC driver and connects to the database using 
	 * the given username and password.
	 * Database name: csmusicstore
	 * Returns true if the connection is successful; false otherwise.
	 */ 
	public boolean connect(String username, String password){

		//MySQL URL for HOME
		String connectURL = "jdbc:mysql://localhost/csmusicstore"; 
		try {
			if (!driverLoaded){
				// Load the MySQL JDBC driver
				DriverManager.registerDriver(new com.mysql.jdbc.Driver());
				driverLoaded = true; 
			}
			con = DriverManager.getConnection(connectURL,username,password);
			System.out.println("\nConnected to Database!");
			con.setAutoCommit(false);
			return true; 
		} catch (SQLException e) {
			System.out.println("Message: " + e.getMessage());
			return false;
		}
	}


	/*
	 * Returns the connection
	 */
	public Connection getConnection(){
		return con; 
	}


	/*
	 * Sets the connection
	 */
	public void setConnection(Connection connect){
		con = connect; 
	}


	/*
	 * Returns true if the driver is loaded; false otherwise
	 */ 
	public boolean isDriverLoaded(){
		return driverLoaded; 
	}


	/*
	 * This method allows members of this class to clean up after itself 
	 * before it is garbage collected. It is called by the garbage collector.
	 */ 
	protected void finalize() throws Throwable{		
		if (con != null){
			con.close();
		}

		// finalize() must call super.finalize() as the last thing it does
		super.finalize();	
	}


	private void insertItem() {
		String upc;
		String title;
		String type;
		String category;
		String company;
		String year;
		String price;
		String stock;
		
		PreparedStatement ps;
		  
		try
		{
		  ps = con.prepareStatement("INSERT INTO item VALUES (?,?,?,?,?,?,?,?)");
		
		  System.out.print("Item UPC: ");
		  upc = in.readLine();
		  ps.setString(1, upc);

		  System.out.print("Item title: ");
		  title = in.readLine();
		  ps.setString(2, title);
		  
		  System.out.print("Item Type: ");
		  type = in.readLine();
		  ps.setString(3, type);
		  
		  System.out.print("Item Category: ");
		  category = in.readLine();
		  ps.setString(4, category);
		  
		  System.out.print("Item Company: ");
		  company = in.readLine();
		  ps.setString(5, company);
		  
		  System.out.print("Item Year: ");
		  year = in.readLine();
		  ps.setString(6, year);
		  
		  System.out.print("Item Price: ");
		  price = in.readLine();
		  ps.setString(7, price);

		  System.out.print("Item Stock: ");
		  stock = in.readLine();
		  ps.setString(8, stock);

		  ps.executeUpdate();

		  // commit work 
		  con.commit();

		  ps.close();
		}
		catch (IOException e)
		{
		    System.out.println("IOException!");
		}
		catch (SQLException ex)
		{
		    System.out.println("Message: " + ex.getMessage());
		    try 
		    {
			// undo the insert
			con.rollback();	
		    }
		    catch (SQLException ex2)
		    {
			System.out.println("Message: " + ex2.getMessage());
			System.exit(-1);
		    }
		}
	    }
	
	
	
	// Simple text interface
	public void showMenu() {
		int choice;
		boolean quit;

		quit = false;

		try 
		{
			// disable auto commit mode
			con.setAutoCommit(false);

			while (!quit)
			{
				System.out.print("\n\nPlease choose one of the following: \n");
				System.out.print("1.  Insert Item\n");
				System.out.print("2.  Quit\n>> ");

				choice = Integer.parseInt(in.readLine());

				//		System.out.println(" ");

				switch(choice)
				{
				case 1:  insertItem(); break;
				case 2:  quit = true;
				}
			}

			con.close();
			in.close();
			System.out.println("\nGood Bye!\n\n");
			System.exit(0);
		}
		catch (IOException e)
		{
			System.out.println("IOException!");

			try
			{
				con.close();
				System.exit(-1);
			}
			catch (SQLException ex)
			{
				System.out.println("Message: " + ex.getMessage());
			}
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}
	}

}
