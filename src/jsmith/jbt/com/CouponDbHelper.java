package jsmith.jbt.com;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author andrew
 * 
 * Helper utility class for different DB related routines
 *
 */
public class CouponDbHelper {
	
	private CouponDbHelper (){
		
	}
	
	/**
	 * @param date1
	 * @param date2
	 * @return Map of TimeUnits  - difference between 2 dates in seconds, hours, etc
	 * 
	 * @author http://stackoverflow.com/questions/1555262/calculating-the-difference-between-two-java-date-instances
	 */
	public static Map<TimeUnit,Long> computeDetailedDateDiff(Date date1, Date date2) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
	    Collections.reverse(units);
	    Map<TimeUnit,Long> result = new LinkedHashMap<TimeUnit,Long>();
	    long milliesRest = diffInMillies;
	    for ( TimeUnit unit : units ) {
	        long diff = unit.convert(milliesRest,TimeUnit.MILLISECONDS);
	        long diffInMilliesForUnit = unit.toMillis(diff);
	        milliesRest = milliesRest - diffInMilliesForUnit;
	        result.put(unit,diff);
	    }
	    return result;
	}
	
	public static void createTables(Connection conn) throws CouponSystemException {;
		
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("CREATE TABLE Company(ID INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY, COMP_NAME VARCHAR(80), PASSWORD VARCHAR(30), EMAIL VARCHAR(30))");
			stmt.executeUpdate("CREATE TABLE Customer(ID INT NOT NULL GENERATED ALWAYS AS IDENTITY  PRIMARY KEY, CUST_NAME VARCHAR(80), PASSWORD VARCHAR(30))");
			stmt.executeUpdate("CREATE TABLE Coupon(ID INT NOT NULL GENERATED ALWAYS AS IDENTITY  PRIMARY KEY, TITLE VARCHAR(80), MESSAGE VARCHAR(100), IMAGE VARCHAR(100), TYPE VARCHAR(100), "
													+ "AMOUNT INT, PRICE NUMERIC(5,2), START_DATE DATE, END_DATE DATE)");			
			stmt.executeUpdate("CREATE TABLE Company_Coupon(COMP_ID INT,COUPON_ID INT, PRIMARY KEY(COMP_ID,COUPON_ID) ,"
					+ "FOREIGN KEY(COMP_ID) REFERENCES Company(ID) , FOREIGN KEY(COUPON_ID) REFERENCES Coupon(ID))");
			stmt.executeUpdate("CREATE TABLE Customer_Coupon(CUST_ID INT,COUPON_ID INT, PRIMARY KEY(CUST_ID,COUPON_ID) ,"
					+ "FOREIGN KEY(CUST_ID) REFERENCES Customer(ID) , FOREIGN KEY(COUPON_ID) REFERENCES Coupon(ID))");
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't create db table");
		}
	}
	
	public static void dropTables(Connection conn) throws CouponSystemException{
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE Company_Coupon");
			stmt.executeUpdate("DROP TABLE Customer_Coupon");
			stmt.executeUpdate("DROP TABLE Company");
			stmt.executeUpdate("DROP TABLE Customer");
			stmt.executeUpdate("DROP TABLE Coupon");
			
		} catch (SQLException e) {
			// 
			throw new CouponSystemException("Couldn't drop db table");
		}
		
		
	}
	
	
	/**
	 * @param stmt
	 * @return IDENTITY field after insertion
	 * @throws SQLException
	 */
	public static long getDBIdentityField(PreparedStatement stmt) throws SQLException{
		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		return rs.getInt(1);
	}
	
	/**
	 * @param stmt
	 * @return Cnt field from the prepared query
	 * @throws SQLException
	 * @throws CouponSystemException 
	 */
	public static long getQueryResultLong(String sqlQuery,String fieldName,ConnectionPool cPool) throws CouponSystemException {
		long res=0;
		
		PreparedStatement pstmt;
		try {
			pstmt = cPool.getConnection().prepareStatement(sqlQuery);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) res=rs.getLong(fieldName);
		} catch (SQLException e) {
			// 
			throw new CouponSystemException("Error in DB query "+sqlQuery);
		}
	
		return res;
	}

}
