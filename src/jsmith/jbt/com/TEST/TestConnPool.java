package jsmith.jbt.com.TEST;

import java.sql.Connection;
import java.sql.Date;

import jsmith.jbt.com.ConnectionPool;
import jsmith.jbt.com.CouponDbHelper;
import jsmith.jbt.com.CouponSystemException;
import jsmith.jbt.com.DAO.CompanyCouponDBDAO;
import jsmith.jbt.com.DAO.CompanyDBDAO;
import jsmith.jbt.com.DAO.CouponDBDAO;
import jsmith.jbt.com.DAO.CustomerCouponDBDAO;
import jsmith.jbt.com.DAO.CustomerDBDAO;
import jsmith.jbt.com.DTO.Company;
import jsmith.jbt.com.DTO.Coupon;
import jsmith.jbt.com.DTO.Customer;
import jsmith.jbt.com.DTO.Coupon.CouponType;

/**
 * @author andrewm
 *
 *Test class for table creation and thread pool running
 */
public class TestConnPool extends Thread {
	
	@Override
	public void run() {
		try {
			ConnectionPool cPool=ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl);
			Connection conn=cPool.getConnection();
			System.out.println(getName()+" got connection");
			Thread.sleep(1000);
			cPool.returnConnection(conn);
			System.out.println(getName()+" released connection");
		} catch (CouponSystemException e) {
			// 
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TestConnPool(String name) {
		super(name);
		// 
	}
	
	/**
	 * Test procedure for blocking Connection get and return for the Thread Pool
	 */
	public static void TestMultyThreadingConnAquiring() {
		for(int k=0;k<15;k++){
			TestConnPool testThread=new TestConnPool("thread "+(k+1));
			testThread.start();
		}
	}

	public static void main(String[] args) throws CouponSystemException {
		TestMultyThreadingConnAquiring();
	}

}
