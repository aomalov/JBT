/**
 * 
 */
package jsmith.jbt.com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

/**
 * @author andrew
 *
 */
public final class CompanyDBDAO implements CompanyDAO {
	
	private final ConnectionPool cPool;

	/**
	 * @param cPool
	 */
	public CompanyDBDAO(ConnectionPool cPool) {
		super();
		this.cPool = cPool;
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.entityDAO#create(java.lang.Object)
	 */
	@Override
	public long create(Company entity) throws CouponSystemException {
		Connection con=cPool.getConnection();
		long res;
		try {
			PreparedStatement pstmt = con.prepareStatement("insert into COMPANY(COMP_NAME,PASSWORD,EMAIL) values(?,?,?)",Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, entity.getCOMP_NAME());
			pstmt.setString(2, entity.getPASSWORD());
			pstmt.setString(3, entity.getEMAIL());
			res=CouponDbHelper.getDBIdentityField(pstmt);
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't insert into Company DB table");		
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
	public Company read(long ID) throws CouponSystemException {
		Connection con=cPool.getConnection();
		Company res=new Company(ID,"","","");
		try {
			PreparedStatement pstmt = con.prepareStatement("select COMP_NAME,PASSWORD,EMAIL from COMPANY where ID=?");
			pstmt.setLong(1, ID);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())
			{
				res.setCOMP_NAME(rs.getString("COMP_NAME"));
				res.setPASSWORD(rs.getString("PASSWORD"));
				res.setEMAIL(rs.getString("EMAIL"));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't read a row from Company DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.entityDAO#update(java.lang.Object)
	 */
	@Override
	public void update(Company entity) throws CouponSystemException {
		Connection con=cPool.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement("update COMPANY set COMP_NAME=?,PASSWORD=?,EMAIL=? where ID=?");
			pstmt.setLong(4, entity.getID());
			pstmt.setString(1, entity.getCOMP_NAME());
			pstmt.setString(2, entity.getPASSWORD());
			pstmt.setString(3, entity.getEMAIL());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't update Company DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.CompanyDAO#getCoupons(jsmith.jbt.com.Company)
	 */
	@Override
	public Collection<Coupon> getCoupons(Company comp) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.entityDAO#delete(java.lang.Object)
	 */
	@Override
	public void delete(Company entity) throws CouponSystemException {
		Connection con=cPool.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement("delete from Company where ID=?");
			pstmt.setLong(1, entity.getID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't delete from Company DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.entityDAO#readAll()
	 */
	@Override
	public Collection<Company> readAll() throws CouponSystemException {
		// TODO Auto-generated method stub
		return null;
	}

}
