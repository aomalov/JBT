/**
 * 
 */
package com.jbt.jsmith.dao;

import java.sql.Date;
import java.util.Collection;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.dto.Coupon;

/**
 * @author andrew
 *
 */
public interface CouponDAO extends entityDAO<Coupon> {

	Collection<Coupon> realAllByType(Coupon.CouponType TYPE) throws CouponSystemException;
	Collection<Coupon> realAllOutdated(Date couponDate) throws CouponSystemException;
	Collection<Coupon> realAllValidAvailable(Date couponDate) throws CouponSystemException;
}
