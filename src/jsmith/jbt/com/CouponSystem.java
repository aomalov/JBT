/**
 * 
 */
package jsmith.jbt.com;

import java.util.concurrent.TimeUnit;

import jsmith.jbt.com.FACADE.AdminFacade;
import jsmith.jbt.com.FACADE.CompanyFacade;
import jsmith.jbt.com.FACADE.CouponClientFacade;
import jsmith.jbt.com.FACADE.CustomerFacade;

/**
 * @author andrew
 * 
 * Generic singleton class to provide for a client-type facade , enforced with business logic and data-access layer
 *
 */
public class CouponSystem {
	
	private static CouponSystem instance;
	private DailyCouponExpirationTask cleanUpTask;
	private Thread cleanupTaskThread;
	
	public enum ClientType {
		Admin, Company, Client
	}
	
	/**
	 * @return the CouponSystem singleton method
	 * @throws CouponSystemException 
	 */
	public static CouponSystem getInstance() throws CouponSystemException{
		if(instance==null) instance=new CouponSystem();
		return instance;
	}
	
	private CouponSystem() throws CouponSystemException{
		this.cleanUpTask=new DailyCouponExpirationTask(TimeUnit.MINUTES,5); //Every 5 minutes clean outdated coupons
		this.cleanupTaskThread=new Thread(this.cleanUpTask);
		this.cleanupTaskThread.start();
	}
	
	/**
	 * @param name
	 * @param password
	 * @param type						Admin, Company, Client
	 * @throws CouponSystemException
	 * @return ClientCouponFacade
	 */
	public CouponClientFacade login(String name,String password,ClientType type) throws CouponSystemException {
		CouponClientFacade res=null;
		
		switch(type) {
		case Client: 
			res=new CustomerFacade();
			break;
		case Company:
			res=new CompanyFacade();
			break;
		case Admin:
			res=new AdminFacade();
			break;
		}
		return res.login(name, password, type);
	}
	
	/**
	 * @throws CouponSystemException
	 */
	public void shutdown() throws CouponSystemException{
		this.cleanUpTask.stopTask();
		ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl).closeAllConnections();
	}

}
