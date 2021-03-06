/**
 * 
 */
package com.jbt.jsmith.test;

import java.sql.Date;

import com.jbt.jsmith.CouponSystem;
import com.jbt.jsmith.CouponSystem.ClientType;
import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.dao.ConnectionPool;
import com.jbt.jsmith.dao.CouponDbHelper;
import com.jbt.jsmith.dto.Company;
import com.jbt.jsmith.dto.Coupon;
import com.jbt.jsmith.dto.Customer;
import com.jbt.jsmith.dto.Coupon.CouponType;
import com.jbt.jsmith.facade.AdminFacade;
import com.jbt.jsmith.facade.CompanyFacade;
import com.jbt.jsmith.facade.CustomerFacade;

/**
 * @author andrew
 *
 *Global testing routine for PHASE 1 of the project Coupons
 *
 *ALL facades are tested in a END 2 END scenario
 *
 */
public class TestCouponSystem {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		try {
			ConnectionPool cPool=ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl);
			
			//CouponDbHelper.dropTables(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl).getConnection());
			//CouponDbHelper.createTables(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl).getConnection());
			
			
			long identity_cnt=CouponDbHelper.getQueryResultLong("select count(*)+1 as cnt from COMPANY", "cnt", cPool);
			CouponSystem theCouponius=CouponSystem.getInstance();
			
			AdminFacade admin=(AdminFacade)theCouponius.login("Admin", "1234",  ClientType.Admin);
			Company testComp=new Company(0, "Company "+identity_cnt, "1234", "comp"+identity_cnt+"@companies.com");
			admin.createCompany(testComp);
			System.out.println("Created company # "+testComp.getID());
			
			Customer testCust=new Customer(0, "Customer "+identity_cnt, "1234");
			admin.createCustomer(testCust);
			System.out.println("Created customer #"+testCust.getID());
//			testCust.setPASSWORD("12345");
//			admin.updateCustomer(testCust);
//			testCust.setCUST_NAME("new Name");
//			admin.updateCustomer(testCust);
			
			CompanyFacade compFacade=(CompanyFacade)theCouponius.login("Company "+identity_cnt, "1234", ClientType.Company);
			Coupon testCoupon=new Coupon(0, CouponType.HEALTHCARE, "Coupon "+identity_cnt, "msg", "iMAGE", 2, 15.0, Date.valueOf("2015-1-1"), Date.valueOf("2016-12-1"));
			
			compFacade.createCoupon(testCoupon);
			System.out.println("Created coupon #"+testCoupon.getID());
			System.out.println("All coupons for company "+testComp.getCOMP_NAME());
			for(Coupon c:compFacade.getCouponByType(CouponType.HEALTHCARE)) System.out.println(c);

			CustomerFacade custFacade=(CustomerFacade)theCouponius.login("Customer "+identity_cnt, "1234", ClientType.Client);
			custFacade.purchaseCoupon(testCoupon);
			System.out.println("All coupons for customer "+testCust.getCUST_NAME());			
			for(Coupon c:custFacade.getAllPurchased()) System.out.println(c);
			System.out.println("All coupons available to purchase for customer "+testCust.getCUST_NAME());			
			for(Coupon c:custFacade.getCouponsOnSale(null)) System.out.println(c);
			
			Thread.sleep(1500);
			theCouponius.shutdown();
			
		} catch (CouponSystemException | InterruptedException e) {
			e.printStackTrace();
		}
		

	}

}
