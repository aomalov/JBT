/**
 * 
 */
package jsmith.jbt.com;

import java.util.Collection;

/**
 * @author andrew
 *
 */
public interface CouponDAO extends entityDAO<Coupon> {

	Collection<Coupon> realAllByType(Coupon.CouponType TYPE) throws CouponSystemException;
}
