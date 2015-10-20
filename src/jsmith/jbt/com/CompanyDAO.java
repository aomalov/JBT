package jsmith.jbt.com;

import java.util.Collection;

public interface CompanyDAO extends entityDAO<Company> {
	Collection<Coupon> getCoupons(Company comp) throws CouponSystemException;
}
