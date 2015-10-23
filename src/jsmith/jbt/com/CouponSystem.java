/**
 * 
 */
package jsmith.jbt.com;

import jsmith.jbt.com.FACADE.CouponClientFacade;

/**
 * @author andrew
 * 
 * Generic singleton class to provide for a clien-type facade , enforced with business logic and data-access layer
 *
 */
public class CouponSystem {
	
	private static CouponSystem instance;
	
	public enum ClientType {
		Admin, Company, Client
	}
	
	public static CouponSystem getInstance(){
		if(instance==null) instance=new CouponSystem();
		return instance;
	}
	
	private CouponSystem(){
	}
	
	/**
	 * @param name
	 * @param password
	 * @param type						Admin, Company, Client
	 * @throws CouponSystemException
	 * @return ClientCouponFacade
	 */
	public CouponClientFacade login(String name,String password,ClientType type) throws CouponSystemException {
		
		//TODO ClientCouponFacade
		return null;
	}
	
	/**
	 * @throws CouponSystemException
	 */
	public void shutdown() throws CouponSystemException{
		ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl).closeAllConnections();
	}

}
