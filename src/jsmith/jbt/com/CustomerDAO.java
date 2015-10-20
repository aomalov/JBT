/**
 * 
 */
package jsmith.jbt.com;

import java.util.Collection;

/**
 * @author andrew
 *
 */
public interface CustomerDAO extends entityDAO<Customer> {
	Collection<Coupon> getCoupons(Customer cust) throws CouponSystemException;
}
