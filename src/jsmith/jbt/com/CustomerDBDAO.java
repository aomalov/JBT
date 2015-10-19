/**
 * 
 */
package jsmith.jbt.com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

/**
 * @author andrew
 *
 */
public class CustomerDBDAO implements CustomerDAO {

	private final ConnectionPool cPool;
	/* (non-Javadoc)
	 * @see jsmith.jbt.com.entityDAO#create(java.lang.Object)
	 */
	@Override
	public long create(Customer entity) throws CouponSystemException {
		Connection con=cPool.getConnection();
		long res;
		try {
			PreparedStatement pstmt = con.prepareStatement("insert into CUSTOMER values(?,?)",Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, entity.getCUST_NAME());
			pstmt.setString(2, entity.getPASSWORD());
			res=ConnectionPool.getDBIdentityField(pstmt);
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't insert into Customer DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
		return res;

	}

	/**
	 * @param cPool
	 */
	public CustomerDBDAO(ConnectionPool cPool) {
		super();
		this.cPool = cPool;
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.entityDAO#read(long)
	 */
	@Override
	public Customer read(long ID) throws CouponSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.entityDAO#update(java.lang.Object)
	 */
	@Override
	public void update(Customer entity) throws CouponSystemException {
		Connection con=cPool.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement("update CUSTOMER set CUST_NAME=?,PASSWORD=? where ID=?");
			pstmt.setLong(3, entity.getID());
			pstmt.setString(1, entity.getCUST_NAME());
			pstmt.setString(2, entity.getPASSWORD());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't update Customer DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}

	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.entityDAO#delete(java.lang.Object)
	 */
	@Override
	public void delete(Customer entity) throws CouponSystemException {
		Connection con=cPool.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement("delete from CUSTOMER where ID=?");
			pstmt.setLong(1, entity.getID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't delete from Customer DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}


	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.entityDAO#readAll()
	 */
	@Override
	public Collection<Customer> readAll() throws CouponSystemException {
		// TODO Auto-generated method stub
		return null;
	}

}
