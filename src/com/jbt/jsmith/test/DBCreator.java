/**
 * 
 */
package com.jbt.jsmith.test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import com.jbt.jsmith.CouponSystem.ClientType;
import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.dao.ConnectionPool;
import com.jbt.jsmith.dao.CouponDbHelper;
import com.jbt.jsmith.dao.security.Owasp;

/**
 * @author andrew
 *
 *Creates necessary tables for the DB
 */
public class DBCreator {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		try {
			CouponDbHelper.createTables(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl).getConnection());
			Owasp.createTable(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl).getConnection());
			Owasp.createUser(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl).getConnection(), "Admin", "1234",ClientType.Admin.toString());
		} catch (CouponSystemException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
