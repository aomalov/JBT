/**
 * 
 */
package com.jbt.jsmith.TEST;

import com.jbt.jsmith.ConnectionPool;
import com.jbt.jsmith.CouponDbHelper;
import com.jbt.jsmith.CouponSystemException;

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
