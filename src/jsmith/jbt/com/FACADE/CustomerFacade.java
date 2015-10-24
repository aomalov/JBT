/**
 * 
 */
package jsmith.jbt.com.FACADE;

import jsmith.jbt.com.CouponSystemException;

import java.util.ArrayList;
import java.util.Collection;

import jsmith.jbt.com.ConnectionPool;
import jsmith.jbt.com.CouponDbHelper;
import jsmith.jbt.com.CouponSystem.ClientType;
import jsmith.jbt.com.DAO.CompanyCouponDBDAO;
import jsmith.jbt.com.DAO.CompanyDBDAO;
import jsmith.jbt.com.DAO.CouponDBDAO;
import jsmith.jbt.com.DAO.CustomerCouponDBDAO;
import jsmith.jbt.com.DAO.CustomerDBDAO;
import jsmith.jbt.com.DTO.Coupon;
import jsmith.jbt.com.DTO.Coupon.CouponType;
import jsmith.jbt.com.DTO.Customer;

/**
 * @author andrew
 *
 */
public class CustomerFacade implements CouponClientFacade {

	private final CompanyDBDAO compDBDAO;
	private final CustomerDBDAO custDBDAO;
	private final CustomerCouponDBDAO custcouponDBDAO;
	private final CompanyCouponDBDAO compcouponDBDAO;
	private final CouponDBDAO couponDBDAO;
	private final ConnectionPool cPool;
	private Customer innerCustomer=null;
	
	
	public CustomerFacade() throws CouponSystemException{
		this.cPool=ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl);
		this.compDBDAO=new CompanyDBDAO(cPool);
		this.custDBDAO=new CustomerDBDAO(cPool);
		this.custcouponDBDAO=new CustomerCouponDBDAO(cPool);
		this.compcouponDBDAO=new CompanyCouponDBDAO(cPool);
		this.couponDBDAO=new CouponDBDAO(cPool);
	}
	
	/* (non-Javadoc)
	 * @see jsmith.jbt.com.FACADE.CouponClientFacade#login(java.lang.String, java.lang.String, jsmith.jbt.com.FACADE.CouponSystem.ClientType)
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType type) throws CouponSystemException {
		//name lookup
		long ID=CouponDbHelper.getQueryResultLong("select ID from CUSTOMER where CUST_NAME'"+name+"'", "ID", cPool);
		if(ID>0) innerCustomer=custDBDAO.read(ID);
		else throw new CouponSystemException("User name not valid");
		if(password.equals(innerCustomer.getPASSWORD())) return this;
		else throw new CouponSystemException("password not valid");
	}

	public void purchaseCoupon(Coupon aCoupon) throws CouponSystemException {
	// TODO cannot buy an outdated coupon
		long cnt=CouponDbHelper.getQueryResultLong("select count(COUPON_ID) as cnt from CUSTOMER_COUPON where CUST_ID="+innerCustomer.getID()+" and COUPON_ID="+aCoupon.getID(), "cnt", cPool);
		if(cnt>0) throw new CouponSystemException("cannot purchase coupon twice");
		
		custcouponDBDAO.create(innerCustomer.getID(), aCoupon.getID());
	}
	
	public Collection<Coupon> getAllPurchased() throws CouponSystemException {
		Collection<Coupon> res=new ArrayList<>();
		for(long couponID: custcouponDBDAO.readAll(innerCustomer.getID())){
			res.add(couponDBDAO.read(couponID));
		}
		return res; 
	}
	
	public Collection<Coupon> getAllPurchasedByType(CouponType type) throws CouponSystemException {
		Collection<Coupon> res=new ArrayList<>();
		for(long couponID: custcouponDBDAO.readAll(innerCustomer.getID())){
			if(couponDBDAO.read(couponID).getTYPE().equals(type)) res.add(couponDBDAO.read(couponID));
		}
		return res; 
	}
	
	public Collection<Coupon> getAllPurchasedByPrice(Double price) throws CouponSystemException {
		Collection<Coupon> res=new ArrayList<>();
		for(long couponID: custcouponDBDAO.readAll(innerCustomer.getID())){
			if(couponDBDAO.read(couponID).getPRICE()<=price) res.add(couponDBDAO.read(couponID));
		}
		return res; 
	}
	
	
	
	
}
