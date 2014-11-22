package connection;

import java.sql.*; 

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
	
}
