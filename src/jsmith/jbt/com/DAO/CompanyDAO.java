package jsmith.jbt.com.DAO;

import java.util.Collection;

import jsmith.jbt.com.CouponSystemException;
import jsmith.jbt.com.DTO.Company;
import jsmith.jbt.com.DTO.Coupon;

public interface CompanyDAO extends entityDAO<Company> {
	Collection<Coupon> getCoupons(Company comp) throws CouponSystemException;
}
