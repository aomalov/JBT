/**
 * 
 */
package com.jbt.jsmith.facade;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Collection;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.CouponSystem.ClientType;
import com.jbt.jsmith.dao.CompanyCouponDBDAO;
import com.jbt.jsmith.dao.CompanyDBDAO;
import com.jbt.jsmith.dao.CustomerCouponDBDAO;
import com.jbt.jsmith.dao.CustomerDBDAO;
import com.jbt.jsmith.dao.security.Owasp;
import com.jbt.jsmith.dto.Company;
import com.jbt.jsmith.dto.Customer;

/**
 * @author andrew
 *
 */
public class AdminFacade implements CouponClientFacade {
	
	private final CompanyDBDAO companyDBDAO;
	private final CustomerDBDAO customerDBDAO;
	private final CustomerCouponDBDAO customerCouponDBDAO;
	private final CompanyCouponDBDAO companyCouponDBDAO;
	

	/**
	 * @throws CouponSystemException 
	 * 
	 */
	public AdminFacade() throws CouponSystemException {
		this.companyDBDAO=new CompanyDBDAO();
		this.customerDBDAO=new CustomerDBDAO();
		this.customerCouponDBDAO=new CustomerCouponDBDAO();
		this.companyCouponDBDAO=new CompanyCouponDBDAO();
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.FACADE.CouponClientFacade#login(java.lang.String, java.lang.String, jsmith.jbt.com.FACADE.CouponSystem.ClientType)
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType type) throws CouponSystemException {
		//TODO seems to be a missfit so far with this CouponClientFacade interface
		try {
			if(Owasp.authenticate(companyDBDAO.getConnectionPool().getConnection(), name, password)) 
				return this;
			else 
				throw new CouponSystemException("Name or password not valid");
		} catch (NoSuchAlgorithmException | SQLException e) {
			// TODO Auto-generated catch block
			throw new CouponSystemException("Error during login procedure");
		}
	}
	
	public void createCompany(Company comp) throws CouponSystemException {
		//no such name
		if(!companyDBDAO.lookupByName(comp.getCOMP_NAME())) {
			long id=companyDBDAO.create(comp);
			comp.setID(id);
		}
		else throw new CouponSystemException("There is a company with name "+comp.getCOMP_NAME());
	}
	
	public void removeCompany(Company comp) throws CouponSystemException {
		Collection<Long> compCoupons=companyCouponDBDAO.readAll(comp.getID());
		//deleting from Customers
		for(Long aCoupon:compCoupons) customerCouponDBDAO.deleteAllCoupons(aCoupon);
		//deleting from Company
		companyCouponDBDAO.deleteAll(comp.getID());
		//deleting the Company itself
		companyDBDAO.delete(comp);
	}
	
	public void updateCompany(Company comp) throws CouponSystemException {
		Company safeCopy=companyDBDAO.read(comp.getID());
		
		//Cannot change company name
		if(!comp.getCOMP_NAME().equals(safeCopy.getCOMP_NAME())) throw new CouponSystemException("Cannot change company name");
		else companyDBDAO.update(comp);		
	}
	
	public Company getCompany(long ID) throws CouponSystemException {
		return companyDBDAO.read(ID);
	}
	
	public Collection<Company> getAllCompanies() throws CouponSystemException {
		return companyDBDAO.readAll();
	}
	
	public void createCustomer(Customer cust) throws CouponSystemException {
		//no such name
		if(! customerDBDAO.lookupByName(cust.getCUST_NAME())) {
			long id=customerDBDAO.create(cust);
			cust.setID(id);
		}
		else throw new CouponSystemException("There is a customer with name "+cust.getCUST_NAME());
	}
	
	public void removeCustomer(Customer cust) throws CouponSystemException {
		//deleting from Customer Coupons
		customerCouponDBDAO.deleteAll(cust.getID());
		//deleting from Customer
		customerDBDAO.delete(cust);
	}
	
	public void updateCustomer(Customer cust) throws CouponSystemException {
		Customer safeCopy=customerDBDAO.read(cust.getID());
		
		//Cannot change customer name
		if(!cust.getCUST_NAME().equals(safeCopy.getCUST_NAME())) throw new CouponSystemException("Cannot change customer name");
		else customerDBDAO.update(cust);		
	}
	
	public Customer getCustomer(long ID) throws CouponSystemException {
		return customerDBDAO.read(ID);
	}
	
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		return customerDBDAO.readAll();
	}

}
