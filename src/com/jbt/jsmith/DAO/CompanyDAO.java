package com.jbt.jsmith.DAO;

import java.util.Collection;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.DTO.Company;
import com.jbt.jsmith.DTO.Coupon;

public interface CompanyDAO extends entityDAO<Company> {
	Collection<Coupon> getCoupons(Company comp) throws CouponSystemException;
}
