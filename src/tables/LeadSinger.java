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
	
	/*
	 * Inserts a tuple in LEADSINGER.
	 * Requires: an existing upc of a tuple from the ITEM table.
	 */
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
	
	/*
	 * Deletes a tuple from LeadSinger based on the item upc.
	 */
	public boolean deleteLeadSinger(int upc){
		
		try {
			PreparedStatement ps = con.prepareStatement("DELETE FROM LeadSinger WHERE upc = ?");
			ps.setInt(1, upc);
			int rowCount = ps.executeUpdate();
			if(rowCount == 0) {
				System.out.println("LeadSinger for item # " + upc + " does not exist.");
				ps.close();
				return false;
			}
			con.commit();
			System.out.println("LeadSinger for item # " + upc + " deleted successfully.");
			ps.close();
			return true;

		} catch (SQLException e) {
			System.out.println("LeadSinger Deletion Error: " + e.getMessage());
			try {
				con.rollback();
				return false;
			}
			catch (SQLException ex2)
			{
				System.out.println("LeadSinger Deletion Rollback Error:: " + ex2.getMessage());
				System.exit(-1);
				return false;
			}
		}
	}
	
}
