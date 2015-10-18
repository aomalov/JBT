/**
 * 
 */
package jsmith.jbt.com;

import java.util.Collection;

/**
 * @author andrew
 *
 *Generic interface for Customer, Company and Coupon
 *
 *use with specific entity type ET from DTO classes set
 */
public interface entityDAO<ET> {
	long create(ET entity) throws CouponSystemException;
	ET read(long ID) throws CouponSystemException;
	void update(ET entity) throws CouponSystemException;
	void delete(ET entity) throws CouponSystemException;
	Collection<ET> readAll() throws CouponSystemException; 
}
