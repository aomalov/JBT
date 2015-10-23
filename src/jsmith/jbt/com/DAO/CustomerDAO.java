/**
 * 
 */
package jsmith.jbt.com.DAO;

import java.util.Collection;

import jsmith.jbt.com.CouponSystemException;
import jsmith.jbt.com.DTO.Coupon;
import jsmith.jbt.com.DTO.Customer;

/**
 * @author andrew
 *
 */
public interface CustomerDAO extends entityDAO<Customer> {
	Collection<Coupon> getCoupons(Customer cust) throws CouponSystemException;
}
