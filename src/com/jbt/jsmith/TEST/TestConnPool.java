package com.jbt.jsmith.TEST;

import java.sql.Connection;
import java.sql.Date;

import com.jbt.jsmith.ConnectionPool;
import com.jbt.jsmith.CouponDbHelper;
import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.DAO.CompanyCouponDBDAO;
import com.jbt.jsmith.DAO.CompanyDBDAO;
import com.jbt.jsmith.DAO.CouponDBDAO;
import com.jbt.jsmith.DAO.CustomerCouponDBDAO;
import com.jbt.jsmith.DAO.CustomerDBDAO;
import com.jbt.jsmith.DTO.Company;
import com.jbt.jsmith.DTO.Coupon;
import com.jbt.jsmith.DTO.Customer;
import com.jbt.jsmith.DTO.Coupon.CouponType;

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
