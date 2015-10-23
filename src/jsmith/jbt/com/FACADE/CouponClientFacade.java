/**
 * 
 */
package jsmith.jbt.com.FACADE;

import jsmith.jbt.com.CouponSystemException;
import jsmith.jbt.com.CouponSystem.ClientType;

/**
 * @author andrew
 * 
 * Root interface for different Facade classes
 *
 */
public interface CouponClientFacade {
	CouponClientFacade login(String name,String password,ClientType type) throws CouponSystemException;
}
