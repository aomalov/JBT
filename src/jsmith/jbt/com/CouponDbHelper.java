package jsmith.jbt.com;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CouponDbHelper {
	
	private CouponDbHelper (){
		
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
			// TODO Auto-generated catch block
			throw new CouponSystemException("Couldn't drop db table");
		}
		
		
	}

}
