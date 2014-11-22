package tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;

public class LeadSinger{
	private Connection con;
	
	/*
	 * Constructor for LeadSinger Table.
	 * Takes a database connection as a parameter.
	 */
	public LeadSinger(Connection con){
		this.con = con;
	}
	
	public boolean insertLeadSinger( int upc, String name){
		
		PreparedStatement  ps;
		try {
			ps = con.prepareStatement("INSERT INTO leadsinger VALUES (?,?)");
			ps.setInt(1, upc);
			ps.setString(2, name);
			
			ps.executeUpdate();
			con.commit();
			System.out.println("LeadSinger ( " + upc + ", " + name + ") inserted successfully");
			ps.close();
			return true;

		} catch (SQLException e1) {			
			try {
				con.rollback();
				System.out.println("LeadSinger Insertion Error: " + e1.getMessage());
				return false;
			} catch(SQLException e2) {
				System.out.println("LeadSinger Rollback Error: " + e2.getMessage());
				System.exit(-1);
				return false;
			}
		}
	}
	
}
