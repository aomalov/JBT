/**
 * 
 */
package com.jbt.jsmith.test;

import java.sql.SQLException;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.dao.ConnectionPool;
import com.jbt.jsmith.dao.CouponDbHelper;
import com.jbt.jsmith.dao.security.Owasp;

/**
 * @author andrew
 *
 *Cleanes the DB - drops all tables
 */
public class DBCleaner {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		try {
			CouponDbHelper.dropTables(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl).getConnection());
			Owasp.dropTable(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl).getConnection());
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
