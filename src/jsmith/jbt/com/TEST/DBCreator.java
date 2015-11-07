/**
 * 
 */
package jsmith.jbt.com.TEST;

import jsmith.jbt.com.ConnectionPool;
import jsmith.jbt.com.CouponDbHelper;
import jsmith.jbt.com.CouponSystemException;

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
