/**
 * 
 */
package com.jbt.jsmith.dao;

import java.util.Collection;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.dto.Coupon;
import com.jbt.jsmith.dto.Customer;

/**
 * @author andrew
 *
 */
public interface CustomerDAO extends entityDAO<Customer> {
	Collection<Coupon> getCoupons(Customer cust) throws CouponSystemException;
}
