/**
 * 
 */
package jsmith.jbt.com.FACADE;

import java.util.Collection;

import jsmith.jbt.com.ConnectionPool;
import jsmith.jbt.com.CouponDbHelper;
import jsmith.jbt.com.CouponSystemException;
import jsmith.jbt.com.DAO.CompanyCouponDBDAO;
import jsmith.jbt.com.DAO.CompanyDBDAO;
import jsmith.jbt.com.DAO.CouponDBDAO;
import jsmith.jbt.com.DAO.CustomerCouponDBDAO;
import jsmith.jbt.com.DAO.CustomerDBDAO;
import jsmith.jbt.com.DTO.Company;
import jsmith.jbt.com.CouponSystem.ClientType;

/**
 * @author andrew
 *
 */
public class AdminFacade implements CouponClientFacade {
	
	private final CompanyDBDAO compDBDAO;
	private final CustomerDBDAO custDBDAO;
	private final CouponDBDAO couponDBDAO;
	private final CustomerCouponDBDAO custcouponDBDAO;
	private final CompanyCouponDBDAO compcouponDBDAO;
	private final ConnectionPool cPool;
	

	/**
	 * @throws CouponSystemException 
	 * 
	 */
	public AdminFacade() throws CouponSystemException {
		
		this.cPool=ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl);
		this.compDBDAO=new CompanyDBDAO(cPool);
		this.custDBDAO=new CustomerDBDAO(cPool);
		this.couponDBDAO=new CouponDBDAO(cPool);
		this.custcouponDBDAO=new CustomerCouponDBDAO(cPool);
		this.compcouponDBDAO=new CompanyCouponDBDAO(cPool);
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.FACADE.CouponClientFacade#login(java.lang.String, java.lang.String, jsmith.jbt.com.FACADE.CouponSystem.ClientType)
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType type) throws CouponSystemException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void createCompany(Company comp) throws CouponSystemException {
		//no such name
		if(CouponDbHelper.getCountQueryResult("select count(*) as cnt from COMPANY where COMP_NAME='"+comp.getCOMP_NAME()+"'",cPool)==0) {
			long id=compDBDAO.create(comp);
			comp.setID(id);
		}
		else throw new CouponSystemException("There is a company with name "+comp.getCOMP_NAME());
	}
	
	public void removeCompany(Company comp) throws CouponSystemException {
		Collection<Long> compCoupons=compcouponDBDAO.readAll(comp.getID());
		//deleting from Customers
		for(Long aCoupon:compCoupons) custcouponDBDAO.deleteAllCoupons(aCoupon);
		//deleting from Company
		compcouponDBDAO.deleteAll(comp.getID());
		//deleting the Company itself
		compDBDAO.delete(comp);
	}

}
