package com.jbt.jsmith.dao;

import java.util.Collection;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.dto.Company;
import com.jbt.jsmith.dto.Coupon;

public interface CompanyDAO extends entityDAO<Company> {
	Collection<Coupon> getCoupons(Company comp) throws CouponSystemException;
}
