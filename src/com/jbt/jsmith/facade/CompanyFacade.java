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
import com.jbt.jsmith.dao.CompanyCouponDBDAO;
import com.jbt.jsmith.dao.CompanyDBDAO;
import com.jbt.jsmith.dao.CouponDBDAO;
import com.jbt.jsmith.dao.CouponDbHelper;
import com.jbt.jsmith.dao.CustomerCouponDBDAO;
import com.jbt.jsmith.dao.security.Owasp;
import com.jbt.jsmith.dto.Company;
import com.jbt.jsmith.dto.Coupon;
import com.jbt.jsmith.dto.Coupon.CouponType;

/**
 * @author andrew
 *
 */
public class CompanyFacade implements CouponClientFacade {

	private final CompanyDBDAO companyDBDAO;
	private final CustomerCouponDBDAO customerCouponDBDAO;
	private final CompanyCouponDBDAO companyCouponDBDAO;
	private Company innerCompany=null;
	private CouponDBDAO couponDBDAO;
	
	
	public CompanyFacade() throws CouponSystemException{
		this.companyDBDAO=new CompanyDBDAO();
		this.customerCouponDBDAO=new CustomerCouponDBDAO();
		this.companyCouponDBDAO=new CompanyCouponDBDAO();
		this.couponDBDAO=new CouponDBDAO();
	}
	
	/* (non-Javadoc)
	 * @see jsmith.jbt.com.FACADE.CouponClientFacade#login(java.lang.String, java.lang.String, jsmith.jbt.com.FACADE.CouponSystem.ClientType)
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType type) throws CouponSystemException {
		//name lookup
		long ID=CouponDbHelper.getLoginLookup(ClientType.Company, "ID",name);
		if(ID>0) innerCompany=companyDBDAO.read(ID);
		else throw new CouponSystemException("Company name at login is not valid");
		try {
			if(Owasp.authenticate(companyDBDAO.getConnectionPool().getConnection(), name, password))	
				return this;
			else 
				throw new CouponSystemException("password not valid");
		} catch (NoSuchAlgorithmException | SQLException e) {
			// TODO Auto-generated catch block
			throw new CouponSystemException("Error during login procedure");
		}
	}
	
	public void createCoupon(Coupon aCoupon) throws CouponSystemException {
		//no such name
		if(!couponDBDAO.lookupByName(aCoupon.getTITLE())) {
			long id=couponDBDAO.create(aCoupon); //Create entity table record
			aCoupon.setID(id);
			companyCouponDBDAO.create(innerCompany.getID(), id); //Link a coupon to the company
		}
		else throw new CouponSystemException("There is a coupon with title "+aCoupon.getTITLE());
	}
	
	public void removeCoupon(Coupon aCoupon) throws CouponSystemException {
		//deleting from Customers
		customerCouponDBDAO.deleteAllCoupons(aCoupon.getID());
		//deleting from Company
		companyCouponDBDAO.delete(innerCompany.getID(), aCoupon.getID());
	}
	
	public void updateCoupon(Coupon aCoupon) throws CouponSystemException {
		Coupon safeCopy=couponDBDAO.read(aCoupon.getID());
		//Allow to change only price and validity date
		safeCopy.setPRICE(aCoupon.getPRICE());
		safeCopy.setEND_DATE(aCoupon.getEND_DATE());
		couponDBDAO.update(safeCopy);		
	}
	
	public Coupon getCoupon(long ID) throws CouponSystemException {
		return couponDBDAO.read(ID);
	}
	
	public Collection<Coupon> getAllCoupons() throws CouponSystemException {
		Collection<Coupon> res=new ArrayList<>();
		for(long couponID: companyCouponDBDAO.readAll(innerCompany.getID())){
			res.add(couponDBDAO.read(couponID));		
		}
		return res;
	}

	public Collection<Coupon> getCouponByType(CouponType type) throws CouponSystemException {
		Collection<Coupon> res=new ArrayList<>();
		for(long couponID: companyCouponDBDAO.readAll(innerCompany.getID())){
			if(couponDBDAO.read(couponID).getTYPE().equals(type)) res.add(couponDBDAO.read(couponID));		
		}
		return res;
	}

	public Collection<Coupon> getCouponByPrice(Double price) throws CouponSystemException {
		Collection<Coupon> res=new ArrayList<>();
		for(long couponID: companyCouponDBDAO.readAll(innerCompany.getID())){
			if(couponDBDAO.read(couponID).getPRICE()<=price) res.add(couponDBDAO.read(couponID));		
		}
		return res;
	}
	
	//TODO getCouponByDate

}
