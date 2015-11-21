/**
 * 
 */
package com.jbt.jsmith.DAO;

import java.sql.Date;
import java.util.Collection;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.DTO.Coupon;

/**
 * @author andrew
 *
 */
public interface CouponDAO extends entityDAO<Coupon> {

	Collection<Coupon> realAllByType(Coupon.CouponType TYPE) throws CouponSystemException;
	Collection<Coupon> realAllByEndDate(Date couponDate) throws CouponSystemException;
}
