/**
 * 
 */
package com.jbt.jsmith.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.jbt.jsmith.CouponSystemException;

/**
 * @author andrew
 *
 */
public class CustomerCouponDBDAO implements CustomerCouponDAO {
	
	private final ConnectionPool cPool;

	/**
	 * @param cPool
	 * @throws CouponSystemException 
	 */
	public CustomerCouponDBDAO() throws CouponSystemException {
		super();
		this.cPool = ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl);;
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.CompanyCouponDAO#create(long, long)
	 */
	@Override
	public void create(long CUST_ID, long COUPON_ID) throws CouponSystemException {
		Connection con=cPool.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement("insert into CUSTOMER_COUPON(CUST_ID,COUPON_ID) values(?,?)");
			pstmt.setLong(1, CUST_ID);
			pstmt.setLong(2, COUPON_ID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't insert a row into  CustomerCoupon DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.CompanyCouponDAO#readAll(long)
	 */
	@Override
	public Collection<Long> readAll(long CUST_ID) throws CouponSystemException {
		Connection con=cPool.getConnection();
		Collection<Long> res=new ArrayList<>();
		try {
			PreparedStatement pstmt = con.prepareStatement("select COUPON_ID from CUSTOMER_COUPON where CUST_ID=?");
			pstmt.setLong(1, CUST_ID);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())
			{
				res.add(rs.getLong("COUPON_ID"));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't read a row from CustomerCoupon DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.CompanyCouponDAO#delete(long, long)
	 */
	@Override
	public void delete(long CUST_ID, long COUPON_ID) throws CouponSystemException {
		Connection con=cPool.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement("delete from CUSTOMER_COUPON where CUST_ID=? and COUPON_ID=?");
			pstmt.setLong(1, CUST_ID);
			pstmt.setLong(2, COUPON_ID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't delete a row from CustomerCoupon DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}

	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.CompanyCouponDAO#deleteAll(long)
	 */
	@Override
	public void deleteAll(long CUST_ID) throws CouponSystemException {
		Connection con=cPool.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement("delete from CUSTOMER_COUPON where CUST_ID=?");
			pstmt.setLong(1, CUST_ID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't delete all rows from CustomerCoupon DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}


	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.CompanyCouponDAO#deleteAllCoupons(long)
	 */
	@Override
	public void deleteAllCoupons(long COUPON_ID) throws CouponSystemException {
		Connection con=cPool.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement("delete from CUSTOMER_COUPON where COUPON_ID=?");
			pstmt.setLong(1, COUPON_ID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't delete a row from CustomerCoupon DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}


	}

	@Override
	public boolean lookupPair(long CUST_ID, long COUPON_ID) throws CouponSystemException {
		boolean res;
		Connection con=cPool.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement("select count(COUPON_ID) as cnt from CUSTOMER_COUPON where CUST_ID=? and COUPON_ID=?");
			pstmt.setLong(1, CUST_ID);
			pstmt.setLong(2, COUPON_ID);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next() && rs.getLong("cnt")==0) res=false;
			else res=true;
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't read a row from CustomerCoupon DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
		return res;
	}
}
