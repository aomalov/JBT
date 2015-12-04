/**
 * 
 */
package com.jbt.jsmith.facade;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.CouponSystem.ClientType;

/**
 * @author andrew
 * 
 * Root interface for different Facade classes
 *
 */
public interface CouponClientFacade {
	CouponClientFacade login(String name,String password,ClientType type) throws CouponSystemException;
}
