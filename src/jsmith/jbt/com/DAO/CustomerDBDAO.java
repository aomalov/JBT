/**
 * 
 */
package jsmith.jbt.com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

import jsmith.jbt.com.ConnectionPool;
import jsmith.jbt.com.CouponDbHelper;
import jsmith.jbt.com.CouponSystemException;
import jsmith.jbt.com.DTO.Coupon;
import jsmith.jbt.com.DTO.Customer;

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
			PreparedStatement pstmt = con.prepareStatement("insert into CUSTOMER(CUST_NAME,PASSWORD) values(?,?)",Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, entity.getCUST_NAME());
			pstmt.setString(2, entity.getPASSWORD());
			res=CouponDbHelper.getDBIdentityField(pstmt);
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't insert into Customer DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
		return res;

	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.CustomerDAO#getCoupons(jsmith.jbt.com.Customer)
	 */
	@Override
	public Collection<Coupon> getCoupons(Customer cust) throws CouponSystemException {
		Connection con=cPool.getConnection();
		Collection<Coupon> res=new ArrayList<>();
		try {
			PreparedStatement pstmt = con.prepareStatement("select ID,TITLE,MESSAGE,IMAGE,TYPE,AMOUNT,PRICE,START_DATE,END_DATE "
					+ "from COUPON cp, CUSTOMER_COUPON cc where cc.COUPON_ID=cp.ID and cc.CUST_ID=?");
			pstmt.setLong(1,cust.getID());
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())
			{
				res.add(new Coupon(rs.getLong("ID"),Coupon.CouponType.valueOf(rs.getString("TYPE")),rs.getString("MESSAGE"),rs.getString("IMAGE"),rs.getString("TITLE"),
						rs.getInt("AMOUNT"),rs.getDouble("PRICE"),rs.getDate("START_DATE"),rs.getDate("END_DATE")));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't read rows from Customer_Coupon DB table");		
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
		Connection con=cPool.getConnection();
		Customer res=new Customer(ID,"","");
		try {
			PreparedStatement pstmt = con.prepareStatement("select CUST_NAME,PASSWORD from CUSTOMER where ID=?");
			pstmt.setLong(1, ID);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())
			{
				res.setCUST_NAME(rs.getString("CUST_NAME"));
				res.setPASSWORD(rs.getString("PASSWORD"));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't read a row from Customer DB table");		
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
		Connection con=cPool.getConnection();
		Collection<Customer> res=new ArrayList<>();
		try {
			PreparedStatement pstmt = con.prepareStatement("select ID,CUST_NAME,PASSWORD from CUSTOMER");
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())
			{
				res.add(new Customer(rs.getLong("ID"),rs.getString("CUST_NAME"),rs.getString("PASSWORD")));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't rows from Customer DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
		return res;
	}

}