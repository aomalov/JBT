/**
 * 
 */
package jsmith.jbt.com;

import java.util.Collection;

/**
 * @author andrew
 *
 */
public interface CompanyCouponDAO {
	
	void create(long COMP_ID ,long COUPON_ID) throws CouponSystemException;
	Collection<Long> readAll(long COMP_ID) throws CouponSystemException;
	void delete(long COMP_ID, long COUPON_ID)  throws CouponSystemException;
	void deleteAll(long COMP_ID)  throws CouponSystemException;
	void deleteAllCoupons(long COUPON_ID)  throws CouponSystemException;

}
