/**
 * 
 */
package com.jbt.jsmith.facade;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.CouponSystem.ClientType;
import com.jbt.jsmith.dao.CouponDBDAO;
import com.jbt.jsmith.dao.CouponDbHelper;
import com.jbt.jsmith.dao.CustomerCouponDBDAO;
import com.jbt.jsmith.dao.CustomerDBDAO;
import com.jbt.jsmith.dao.security.Owasp;
import com.jbt.jsmith.dto.Coupon;
import com.jbt.jsmith.dto.Customer;
import com.jbt.jsmith.dto.Coupon.CouponType;

/**
 * @author andrew
 *
 */
public class CustomerFacade implements CouponClientFacade {

	private final CustomerDBDAO customerDBDAO;
	private final CustomerCouponDBDAO customerCouponDBDAO;
	private final CouponDBDAO couponDBDAO;
	private Customer innerCustomer=null;
	
	
	public CustomerFacade() throws CouponSystemException{
		this.customerDBDAO=new CustomerDBDAO();
		this.customerCouponDBDAO=new CustomerCouponDBDAO();
		this.couponDBDAO=new CouponDBDAO();
	}
	
	/* (non-Javadoc)
	 * @see jsmith.jbt.com.FACADE.CouponClientFacade#login(java.lang.String, java.lang.String, jsmith.jbt.com.FACADE.CouponSystem.ClientType)
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType type) throws CouponSystemException {
		//name lookup
		long ID=CouponDbHelper.getLoginLookup(ClientType.Client, "ID",name);
		if(ID>0) innerCustomer=customerDBDAO.read(ID);
		else throw new CouponSystemException("Customer name at login is not valid");
		try {
			if(Owasp.authenticate(name, password))	
				return this;
			else throw new CouponSystemException("password not valid");
		} catch (NoSuchAlgorithmException | SQLException e) {
			// TODO Auto-generated catch block
			throw new CouponSystemException("Error during login procedure");
		}
	}

	public void purchaseCoupon(Coupon aCoupon) throws CouponSystemException {
	// TODO cannot buy an outdated coupon
		if(customerCouponDBDAO.lookupPair(innerCustomer.getID(), aCoupon.getID())) throw new CouponSystemException("cannot purchase coupon twice");
		
		Coupon safeCopy=couponDBDAO.read(aCoupon.getID());
		if(safeCopy.getAMOUNT()>0) {
			customerCouponDBDAO.create(innerCustomer.getID(), aCoupon.getID());
			//Update the number of coupons available at the company resourse stock
			safeCopy.setAMOUNT(aCoupon.getAMOUNT()-1);
			couponDBDAO.update(safeCopy);			
		}
	}
	
	public Collection<Coupon> getAllPurchased() throws CouponSystemException {
		Collection<Coupon> res=new ArrayList<>();
		for(long couponID: customerCouponDBDAO.readAll(innerCustomer.getID())){
			res.add(couponDBDAO.read(couponID));
		}
		return res; 
	}
	
	public Collection<Coupon> getAllPurchasedByType(CouponType type) throws CouponSystemException {
		Collection<Coupon> res=new ArrayList<>();
		for(long couponID: customerCouponDBDAO.readAll(innerCustomer.getID())){
			if(couponDBDAO.read(couponID).getTYPE().equals(type)) res.add(couponDBDAO.read(couponID));
		}
		return res; 
	}
	
	public Collection<Coupon> getAllPurchasedByPrice(Double price) throws CouponSystemException {
		Collection<Coupon> res=new ArrayList<>();
		for(long couponID: customerCouponDBDAO.readAll(innerCustomer.getID())){
			if(couponDBDAO.read(couponID).getPRICE()<=price) res.add(couponDBDAO.read(couponID));
		}
		return res; 
	}
	
	
	
	
}
