/**
 * 
 */
package com.jbt.jsmith.FACADE;

import java.util.Collection;

import com.jbt.jsmith.ConnectionPool;
import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.CouponSystem.ClientType;
import com.jbt.jsmith.DAO.CompanyCouponDBDAO;
import com.jbt.jsmith.DAO.CompanyDBDAO;
import com.jbt.jsmith.DAO.CustomerCouponDBDAO;
import com.jbt.jsmith.DAO.CustomerDBDAO;
import com.jbt.jsmith.DTO.Company;
import com.jbt.jsmith.DTO.Customer;

/**
 * @author andrew
 *
 */
public class AdminFacade implements CouponClientFacade {
	
	private final CompanyDBDAO compDBDAO;
	private final CustomerDBDAO custDBDAO;
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
		this.custcouponDBDAO=new CustomerCouponDBDAO(cPool);
		this.compcouponDBDAO=new CompanyCouponDBDAO(cPool);
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.FACADE.CouponClientFacade#login(java.lang.String, java.lang.String, jsmith.jbt.com.FACADE.CouponSystem.ClientType)
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType type) throws CouponSystemException {
		//TODO seems to be a missfit so far with this CouponClientFacade interface
		if(name.equals("Admin") && password.equals("1234")) return this;
		else throw new CouponSystemException("Name or password not valid");
	}
	
	public void createCompany(Company comp) throws CouponSystemException {
		//no such name
		if(!compDBDAO.lookupByName(comp.getCOMP_NAME())) {
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
	
	public void updateCompany(Company comp) throws CouponSystemException {
		Company safeCopy=compDBDAO.read(comp.getID());
		
		//Cannot change company name
		if(!comp.getCOMP_NAME().equals(safeCopy.getCOMP_NAME())) throw new CouponSystemException("Cannot change company name");
		else compDBDAO.update(comp);		
	}
	
	public Company getCompany(long ID) throws CouponSystemException {
		return compDBDAO.read(ID);
	}
	
	public Collection<Company> getAllCompanies() throws CouponSystemException {
		return compDBDAO.readAll();
	}
	
	public void createCustomer(Customer cust) throws CouponSystemException {
		//no such name
		if(! custDBDAO.lookupByName(cust.getCUST_NAME())) {
			long id=custDBDAO.create(cust);
			cust.setID(id);
		}
		else throw new CouponSystemException("There is a customer with name "+cust.getCUST_NAME());
	}
	
	public void removeCustomer(Customer cust) throws CouponSystemException {
		//deleting from Customer Coupons
		custcouponDBDAO.deleteAll(cust.getID());
		//deleting from Customer
		custDBDAO.delete(cust);
	}
	
	public void updateCustomer(Customer cust) throws CouponSystemException {
		Customer safeCopy=custDBDAO.read(cust.getID());
		
		//Cannot change customer name
		if(!cust.getCUST_NAME().equals(safeCopy.getCUST_NAME())) throw new CouponSystemException("Cannot change customer name");
		else custDBDAO.update(cust);		
	}
	
	public Customer getCustomer(long ID) throws CouponSystemException {
		return custDBDAO.read(ID);
	}
	
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		return custDBDAO.readAll();
	}

}