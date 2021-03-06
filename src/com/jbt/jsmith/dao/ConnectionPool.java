package com.jbt.jsmith.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import com.jbt.jsmith.CouponSystemException;


/**
 * @author andrew
 * ConnectionPool class - introduces a singleton routine DB helper class
 * 
 * the pool returns a connection quickly - based on the queue data structure
 * 
 */

public class ConnectionPool {
	
	private final Set<Connection> connections;
	private final Queue<Connection> freeConnections;
	private static ConnectionPool instance;
	private static int maxConnections; 
	
	public static String defDriverName ="org.apache.derby.jdbc.ClientDriver40";
	public static String defDbUrl="jdbc:derby://localhost:1527/CouponDB;create=true";
	
	/**
	 * @author andrew
	 * @return a ConnectionPool singleton class object
	 * @category default inside param Number of connections = 10
	 * 
	 */
	
	public static synchronized ConnectionPool getInstance(String driverName, String dbUrl) throws CouponSystemException {
		if(instance==null) instance=new ConnectionPool(driverName, dbUrl,10);
		return instance;
	}
	
	private ConnectionPool(String driverName, String dbUrl,int maxConn) throws CouponSystemException {
		connections=new HashSet<>();
		freeConnections=new LinkedList<>();
		maxConnections=maxConn;

		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e1) {
			throw new CouponSystemException("No db driver found."); 
		}

		for(int k=0;k<maxConnections;k++){
			Connection newConn;
			try {
				newConn = DriverManager.getConnection(dbUrl);
			} catch (SQLException e) {
				// 
				throw new CouponSystemException("Could not obtain db connection in Conn Pool."); 
			}
			connections.add(newConn);
		}					
		freeConnections.addAll(connections);
	}
	
	
	public synchronized Connection getConnection() throws CouponSystemException{
		
		while(freeConnections.isEmpty())
			try {
				wait();
				//System.out.println("no empty conns");
			} catch (InterruptedException e) {
				// 
				throw new CouponSystemException("Couldn't provide free connection from the pool");
			}
//		System.out.println("gave 1 conn from " + freeConnections.size());
		return freeConnections.poll();
	}
	
	public synchronized void returnConnection(Connection conn)
	{
		freeConnections.add(conn);
		notifyAll();
	}
	
	public void closeAllConnections() throws CouponSystemException{
		for (Connection conn : instance.connections) {
			try {
				conn.close();
			} catch (SQLException e) {
				// 
				throw new CouponSystemException("Couldn't close connection in Connection Pool");
			}
		}
	}
	
	/**
	 * Utility function - used only for local testing
	 * 
	 * @param conn
	 * @param sql
	 * @throws SQLException
	 */
	public static void showQuery(Connection conn,String sql) throws SQLException
	{
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery(sql);
		ResultSetMetaData md=rs.getMetaData();
		while(rs.next())
		{
			for(int k=1;k<=md.getColumnCount();k++)
			{
				System.out.print(rs.getObject(k)+ "  ");
			}
			System.out.print("\n");
		}
	}
	
}
