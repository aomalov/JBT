/**
 * 
 */
package com.jbt.jsmith.dao;

import java.util.Collection;

import com.jbt.jsmith.CouponSystemException;

/**
 * @author andrew
 *
 */
public interface CustomerCouponDAO {
	void create(long CUST_ID ,long COUPON_ID) throws CouponSystemException;
	Collection<Long> readAll(long CUST_ID) throws CouponSystemException;
	void delete(long CUST_ID, long COUPON_ID)  throws CouponSystemException;
	void deleteAll(long CUST_ID)  throws CouponSystemException;
	void deleteAllCoupons(long COUPON_ID)  throws CouponSystemException;
	boolean lookupPair(long CUST_ID,long COUPON_ID) throws CouponSystemException;
}
