/**
 * 
 */
package jsmith.jbt.com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import jsmith.jbt.com.Coupon.CouponType;

/**
 * @author andrew
 *
 */
public class CouponDBDAO implements CouponDAO {

	private final ConnectionPool cPool;
	
	
	/**
	 * 
	 */
	public CouponDBDAO(ConnectionPool cPool) {
		super();
		this.cPool = cPool;
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.entityDAO#create(java.lang.Object)
	 */
	@Override
	public long create(Coupon entity) throws CouponSystemException {
		Connection con=cPool.getConnection();
		long res;
		try {
			PreparedStatement pstmt = con.prepareStatement("insert into COUPON values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, entity.getTITLE());
			pstmt.setString(2, entity.getMESSAGE());
			pstmt.setString(3, entity.getIMAGE());
			pstmt.setString(4, entity.getTYPE().name());
			pstmt.setInt(5, entity.getAMOUNT());
			pstmt.setDouble(6, entity.getPRICE());
			pstmt.setDate(7, entity.getSTART_DATE());
			pstmt.setDate(8, entity.getSTART_DATE());
			res=ConnectionPool.getDBIdentityField(pstmt);
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't insert into Coupon DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.entityDAO#read(long)
	 */
	@Override
	public Coupon read(long ID) throws CouponSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.entityDAO#update(java.lang.Object)
	 */
	@Override
	public void update(Coupon entity) throws CouponSystemException {
		Connection con=cPool.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement("update COUPON set TITLE=?,MESSAGE=?,IMAGE=?,TYPE=?,AMOUNT=?,PRICE=?,START_DATE=?,END_DATE=? where ID=?");
			pstmt.setLong(9, entity.getID());
			pstmt.setString(1, entity.getTITLE());
			pstmt.setString(2, entity.getMESSAGE());
			pstmt.setString(3, entity.getIMAGE());
			pstmt.setString(4, entity.getTYPE().name());
			pstmt.setInt(5, entity.getAMOUNT());
			pstmt.setDouble(6, entity.getPRICE());
			pstmt.setDate(7, entity.getSTART_DATE());
			pstmt.setDate(8, entity.getSTART_DATE());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't update Coupon DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}

	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.entityDAO#delete(java.lang.Object)
	 */
	@Override
	public void delete(Coupon entity) throws CouponSystemException {
		Connection con=cPool.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement("delete from COUPON where ID=?");
			pstmt.setLong(1, entity.getID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't delete from Coupon DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.entityDAO#readAll()
	 */
	@Override
	public Collection<Coupon> readAll() throws CouponSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.CouponDAO#realAllByType(jsmith.jbt.com.Coupon.CouponType)
	 */
	@Override
	public Collection<Coupon> realAllByType(CouponType TYPE) throws CouponSystemException {
		// TODO Auto-generated method stub
		return null;
	}

}
