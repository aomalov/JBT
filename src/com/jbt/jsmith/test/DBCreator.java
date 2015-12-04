/**
 * 
 */
package com.jbt.jsmith.test;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.dao.ConnectionPool;
import com.jbt.jsmith.dao.CouponDbHelper;

/**
 * @author andrew
 *
 *Creates necessary tables for the DB
 */
public class DBCreator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			CouponDbHelper.createTables(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl).getConnection());
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
