/**
 * 
 */
package com.jbt.jsmith.DAO;

import java.util.Collection;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.DTO.Coupon;
import com.jbt.jsmith.DTO.Customer;

/**
 * @author andrew
 *
 */
public interface CustomerDAO extends entityDAO<Customer> {
	Collection<Coupon> getCoupons(Customer cust) throws CouponSystemException;
}
