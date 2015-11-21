/**
 * 
 */
package com.jbt.jsmith.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.jbt.jsmith.ConnectionPool;
import com.jbt.jsmith.CouponSystemException;

/**
 * @author andrew
 *
 */
public class CompanyCouponDBDAO implements CompanyCouponDAO {

	private final ConnectionPool cPool;
	
	/**
	 * @param cPool
	 */
	public CompanyCouponDBDAO(ConnectionPool cPool) {
		super();
		this.cPool = cPool;
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.CompanyCouponDAO#create(long, long)
	 */
	@Override
	public void create(long COMP_ID, long COUPON_ID) throws CouponSystemException {
		Connection con=cPool.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement("insert into COMPANY_COUPON(COMP_ID,COUPON_ID) values(?,?)");
			pstmt.setLong(1, COMP_ID);
			pstmt.setLong(2, COUPON_ID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't insert a row into  CompanyCoupon DB table - "+e.getMessage());		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.CompanyCouponDAO#readAll(long)
	 */
	@Override
	public Collection<Long> readAll(long COMP_ID) throws CouponSystemException {
		Connection con=cPool.getConnection();
		Collection<Long> res=new ArrayList<>();
		try {
			PreparedStatement pstmt = con.prepareStatement("select COUPON_ID from COMPANY_COUPON where COMP_ID=?");
			pstmt.setLong(1, COMP_ID);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())
			{
				res.add(rs.getLong("COUPON_ID"));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't read a row from CompanyCoupon DB table");		
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
	public void delete(long COMP_ID, long COUPON_ID) throws CouponSystemException {
		Connection con=cPool.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement("delete from COMPANY_COUPON where COMP_ID=? and COUPON_ID=?");
			pstmt.setLong(1, COMP_ID);
			pstmt.setLong(2, COUPON_ID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't delete a row from CompanyCoupon DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}

	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.CompanyCouponDAO#deleteAll(long)
	 */
	@Override
	public void deleteAll(long COMP_ID) throws CouponSystemException {
		Connection con=cPool.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement("delete from COMPANY_COUPON where COMP_ID=?");
			pstmt.setLong(1, COMP_ID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't delete all rows from CompanyCoupon DB table");		
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
			PreparedStatement pstmt = con.prepareStatement("delete from COMPANY_COUPON where COUPON_ID=?");
			pstmt.setLong(1, COUPON_ID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't delete a row from CompanyCoupon DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}


	}

}
