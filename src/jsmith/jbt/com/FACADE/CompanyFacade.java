/**
 * 
 */
package jsmith.jbt.com.FACADE;

import java.util.ArrayList;
import java.util.Collection;

import jsmith.jbt.com.ConnectionPool;
import jsmith.jbt.com.CouponDbHelper;
import jsmith.jbt.com.CouponSystemException;
import jsmith.jbt.com.CouponSystem.ClientType;
import jsmith.jbt.com.DAO.CompanyCouponDBDAO;
import jsmith.jbt.com.DAO.CompanyDBDAO;
import jsmith.jbt.com.DAO.CouponDBDAO;
import jsmith.jbt.com.DAO.CustomerCouponDBDAO;
import jsmith.jbt.com.DAO.CustomerDBDAO;
import jsmith.jbt.com.DTO.Company;
import jsmith.jbt.com.DTO.Coupon;
import jsmith.jbt.com.DTO.Coupon.CouponType;
import jsmith.jbt.com.DTO.Customer;

/**
 * @author andrew
 *
 */
public class CompanyFacade implements CouponClientFacade {

	private final CompanyDBDAO compDBDAO;
	private final CustomerDBDAO custDBDAO;
	private final CustomerCouponDBDAO custcouponDBDAO;
	private final CompanyCouponDBDAO compcouponDBDAO;
	private final ConnectionPool cPool;
	private Company innerCompany=null;
	private CouponDBDAO couponDBDAO;
	
	
	public CompanyFacade() throws CouponSystemException{
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
		long ID=CouponDbHelper.getQueryResultLong("select ID from COMPANY where COMP_NAME='"+name+"'", "ID", cPool);
		if(ID>0) innerCompany=compDBDAO.read(ID);
		else throw new CouponSystemException("Company User name not valid");
		if(password.equals(innerCompany.getPASSWORD())) return this;
		else throw new CouponSystemException("password not valid");
	}
	
	public void createCoupon(Coupon aCoupon) throws CouponSystemException {
		//no such name
		if(CouponDbHelper.getQueryResultLong("select count(*) as cnt from COUPON where TITLE='"+aCoupon.getTITLE()+"'","cnt",cPool)==0) {
			long id=couponDBDAO.create(aCoupon); //Create entity table record
			aCoupon.setID(id);
			compcouponDBDAO.create(innerCompany.getID(), id); //Link a coupon to the company
		}
		else throw new CouponSystemException("There is a coupon with title "+aCoupon.getTITLE());
	}
	
	public void removeCoupon(Coupon aCoupon) throws CouponSystemException {
		//deleting from Customers
		custcouponDBDAO.deleteAllCoupons(aCoupon.getID());
		//deleting from Company
		compcouponDBDAO.delete(innerCompany.getID(), aCoupon.getID());
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
		for(long couponID: compcouponDBDAO.readAll(innerCompany.getID())){
			res.add(couponDBDAO.read(couponID));		
		}
		return res;
	}

	public Collection<Coupon> getCouponByType(CouponType type) throws CouponSystemException {
		Collection<Coupon> res=new ArrayList<>();
		for(long couponID: compcouponDBDAO.readAll(innerCompany.getID())){
			if(couponDBDAO.read(couponID).getTYPE().equals(type)) res.add(couponDBDAO.read(couponID));		
		}
		return res;
	}

	public Collection<Coupon> getCouponByPrice(Double price) throws CouponSystemException {
		Collection<Coupon> res=new ArrayList<>();
		for(long couponID: compcouponDBDAO.readAll(innerCompany.getID())){
			if(couponDBDAO.read(couponID).getPRICE()<=price) res.add(couponDBDAO.read(couponID));		
		}
		return res;
	}
	
	//TODO getCouponByDate

}
