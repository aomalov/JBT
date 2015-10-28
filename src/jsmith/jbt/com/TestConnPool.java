package jsmith.jbt.com;

import java.sql.Connection;
import java.sql.Date;

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
		// 
	
		CouponDbHelper.createTables(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl).getConnection());
		//CouponDbHelper.dropTables(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl).getConnection());
		
		CustomerDBDAO custDAO=new CustomerDBDAO(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl));
		Customer cust=new Customer(0, "Test", "pASSWORD");		
		long idCust=custDAO.create(cust);
		cust.setID(idCust);
		for (Customer c : custDAO.readAll()) {
			System.out.println(c);
		} 
		
		CompanyDBDAO compDAO=new CompanyDBDAO(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl));
		Company comp=new Company(0, "Test", "pASSWORD","email");		
		long idComp=compDAO.create(comp);
		comp.setID(idComp);
		comp.setCOMP_NAME("Real comp name");
		compDAO.update(comp);
		comp=compDAO.read(idComp);		
		System.out.println("New company "+comp);
		
		CouponDBDAO couponDAO=new CouponDBDAO(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl));
		Coupon coupon=new Coupon(0, CouponType.TRAVEL,"Title", "Message","Image",0,0.0,Date.valueOf("2015-10-01"),Date.valueOf("2015-12-01"));		
		long idCoupon=couponDAO.create(coupon);
		coupon.setID(idCoupon);
		System.out.println("New coupon "+coupon);
		
		CompanyCouponDBDAO compCoupon = new CompanyCouponDBDAO(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl));
		CustomerCouponDBDAO custCoupon = new CustomerCouponDBDAO(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl));
		
		compCoupon.create(idComp, idCoupon);
		custCoupon.create(idCust, idCoupon);
		
		for(long id: compCoupon.readAll(idComp)) System.out.println("coupon "+couponDAO.read(id)+" registered for company "+compDAO.read(idComp));
		
		for(long id: custCoupon.readAll(idCust)) System.out.println("coupon "+couponDAO.read(id)+" registered for customer "+custDAO.read(idCust));
		
	}

}
