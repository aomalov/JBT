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
 *Cleanes the DB - drops all tables
 */
public class DBCleaner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			CouponDbHelper.dropTables(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl).getConnection());
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
