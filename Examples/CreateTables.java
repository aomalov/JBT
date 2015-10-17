package com.jbt.b.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;

public class CreateTables {

	public static void main(String[] args) {

		String driverName = "org.apache.derby.jdbc.ClientDriver40";
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		System.out.println("driver class loaded");

		String url = "jdbc:derby://localhost:1527/CouponDb;create=true";

		try (Connection con = DriverManager.getConnection(url);) {

			System.out.println("connection to db1 established");
			//createBookTable(con);
//			
//			Book b=new Book(5, "title new", "author new 2", 123, new Date(new GregorianCalendar().getTimeInMillis()));
//			for(int k=1;k<100;k++)
//			{
//				b.setId(k+10);
//				b.setPrice(k+100);
//				BookDAO.createPrep(b, con);
//			}
			//BookDAO.delete(b, con);
			// deleteBookTable(con);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("connection to db1 closed");

	}

	public static void createBookTable(Connection con) throws SQLException {
		String sql = "CREATE TABLE books(ID INT PRIMARY KEY, title VARCHAR(30), author VARCHAR(30), price DOUBLE, release_date DATE)";
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql);
		System.out.println("success: " + sql);
	}

	public static void deleteBookTable(Connection con) throws SQLException {
		String sql = "DROP TABLE books";
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql);
		System.out.println("success: " + sql);

	}

}
