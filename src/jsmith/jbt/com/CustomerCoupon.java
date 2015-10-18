/**
 * 
 */
package jsmith.jbt.com;

import java.util.Collection;

/**
 * @author andrew
 *
 */
public interface CustomerCoupon {
	void create(long CUST_ID ,long COUPON_ID) throws CouponSystemException;
	Collection<Long> readAll(long CUST_ID) throws CouponSystemException;
	void delete(long CUST_ID, long COUPON_ID)  throws CouponSystemException;
	void deleteAll(long CUST_ID)  throws CouponSystemException;
	void deleteAllCoupons(long COUPON_ID)  throws CouponSystemException;
}
