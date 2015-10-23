/**
 * 
 */
package jsmith.jbt.com.DAO;

import java.util.Collection;

import jsmith.jbt.com.CouponSystemException;
import jsmith.jbt.com.DTO.Coupon;

/**
 * @author andrew
 *
 */
public interface CouponDAO extends entityDAO<Coupon> {

	Collection<Coupon> realAllByType(Coupon.CouponType TYPE) throws CouponSystemException;
}
