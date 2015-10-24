/**
 * 
 */
package jsmith.jbt.com;

import java.sql.Date;

import jsmith.jbt.com.CouponSystem.ClientType;
import jsmith.jbt.com.DTO.Company;
import jsmith.jbt.com.DTO.Coupon;
import jsmith.jbt.com.DTO.Coupon.CouponType;
import jsmith.jbt.com.DTO.Customer;
import jsmith.jbt.com.FACADE.AdminFacade;
import jsmith.jbt.com.FACADE.CompanyFacade;
import jsmith.jbt.com.FACADE.CustomerFacade;

/**
 * @author andrew
 *
 */
public class TestCouponSystem {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CouponSystem theCouponius=CouponSystem.getInstance();
			AdminFacade admin=(AdminFacade)theCouponius.login("Admin", "1234", ClientType.Admin);
			Company testComp=new Company(0, "Company 4", "1234", "comp1@comp1.com");
			//admin.createCompany(testComp);
			System.out.println(testComp.getID());
			
			Customer testCust=new Customer(0, "Customer 3", "1234");
			//admin.createCustomer(testCust);
			System.out.println(testCust.getID());
			
			CompanyFacade compFacade=(CompanyFacade)theCouponius.login("Company 1", "1234", ClientType.Company);
			Coupon testCoupon=new Coupon(0, CouponType.HEALTHCARE, "Coupon 3", "msg", "iMAGE", 2, 15.0, Date.valueOf("2015-1-1"), Date.valueOf("2015-12-1"));
			
			//TODO:  coupon record is deleted by derby
			compFacade.createCoupon(testCoupon);
			//testCoupon=compFacade.getCoupon(7);
			System.out.println(testCoupon.getID());
			for(Coupon c:compFacade.getCouponByType(CouponType.HEALTHCARE)) System.out.println(c);
			
			CustomerFacade custFacade=(CustomerFacade)theCouponius.login("Customer 3", "1234", ClientType.Client);
			custFacade.purchaseCoupon(testCoupon);
			for(Coupon c:custFacade.getAllPurchased()) System.out.println(c);
			
			//theCouponius.shutdown();
			
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
