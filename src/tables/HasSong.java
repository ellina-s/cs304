package tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

/*
 * Basic operations for the HASSONG table.
 */
public class HasSong{
	
	private Connection con;
	
	/*
	 * Constructor for HASSONG Table.
	 * Takes a database connection as a parameter.
	 */
	public HasSong(Connection con){
		this.con = con;
	}
	
	/*
	 * Inserts a tuple in HASSONG.
	 * Requires: an existing upc of a tuple from the ITEM table.
	 */
	public boolean insertHasSong( int upc, String title){
		
		PreparedStatement  ps;
		try {
			ps = con.prepareStatement("INSERT INTO HASSONG VALUES (?,?)");
			ps.setInt(1, upc);
			ps.setString(2, title);

			ps.executeUpdate();
			con.commit();
			System.out.println("HasSong ( " + upc + ", " + title + ") inserted successfully");
			ps.close();
			return true;

		} catch (SQLException e1) {			
			try {
				con.rollback();
				System.out.println("HasSong Insertion Error: " + e1.getMessage());
				return false;
			} catch(SQLException e2) {
				System.out.println("HasSong Rollback Error: " + e2.getMessage());
				System.exit(-1);
				return false;
			}
		}
	}
	
}
