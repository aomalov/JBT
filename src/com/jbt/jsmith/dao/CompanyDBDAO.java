/**
 * 
 */
package com.jbt.jsmith.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.CouponSystem.ClientType;
import com.jbt.jsmith.dao.security.Owasp;
import com.jbt.jsmith.dto.Company;
import com.jbt.jsmith.dto.Coupon;

/**
 * @author andrew
 *
 */
public final class CompanyDBDAO implements CompanyDAO {
	
	private final ConnectionPool cPool;

	/**
	 * @param cPool
	 * @throws CouponSystemException 
	 */
	public CompanyDBDAO() throws CouponSystemException {
		super();
		this.cPool = ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl);
	}
	


	/**
	 * @return the cPool
	 */
	public ConnectionPool getConnectionPool() {
		return cPool;
	}



	/* (non-Javadoc)
	 * @see jsmith.jbt.com.entityDAO#create(java.lang.Object)
	 */
	@Override
	public long create(Company entity) throws CouponSystemException {
		Connection con=cPool.getConnection();
		long res;
		try {
			PreparedStatement pstmt = con.prepareStatement("insert into COMPANY(COMP_NAME,EMAIL) values(?,?)",Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, entity.getCOMP_NAME());
			pstmt.setString(2, entity.getEMAIL());
			res=CouponDbHelper.getDBIdentityField(pstmt);
			Owasp.createUser(con, entity.getCOMP_NAME(), entity.getPASSWORD(),ClientType.Company.toString());
		} 
		catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
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
			PreparedStatement pstmt = con.prepareStatement("select COMP_NAME,EMAIL from COMPANY where ID=?");
			pstmt.setLong(1, ID);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())
			{
				res.setCOMP_NAME(rs.getString("COMP_NAME"));
				res.setEMAIL(rs.getString("EMAIL"));
			}
			else throw new CouponSystemException("No company exists with ID="+ID);
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
			PreparedStatement pstmt = con.prepareStatement("update COMPANY set COMP_NAME=?,EMAIL=? where ID=?");
			pstmt.setLong(3, entity.getID());
			pstmt.setString(1, entity.getCOMP_NAME());
			pstmt.setString(2, entity.getEMAIL());
			pstmt.executeUpdate();
			Owasp.dropUser(con, entity.getCOMP_NAME(),ClientType.Company.toString());
			Owasp.createUser(con, entity.getCOMP_NAME(),entity.getPASSWORD(),ClientType.Company.toString());
		} 
		catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
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
	public Collection<Coupon> getCoupons(Company comp) throws CouponSystemException {
		Connection con=cPool.getConnection();
		Collection<Coupon> res=new ArrayList<>();
		try {
			PreparedStatement pstmt = con.prepareStatement("select ID,TITLE,MESSAGE,IMAGE,TYPE,AMOUNT,PRICE,START_DATE,END_DATE "
					+ "from COUPON cp, COMPANY_COUPON cc where cc.COUPON_ID=cp.ID and cc.COMP_ID=?");
			pstmt.setLong(1,comp.getID());
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())
			{
				res.add(new Coupon(rs.getLong("ID"),Coupon.CouponType.valueOf(rs.getString("TYPE")),rs.getString("MESSAGE"),rs.getString("IMAGE"),rs.getString("TITLE"),
						rs.getInt("AMOUNT"),rs.getDouble("PRICE"),rs.getDate("START_DATE"),rs.getDate("END_DATE")));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't read rows from Company_Coupon DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
		return res;
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
			Owasp.dropUser(con, entity.getCOMP_NAME(),ClientType.Company.toString());
		} catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
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
		Connection con=cPool.getConnection();
		Collection<Company> res=new ArrayList<>() ;
		try {
			PreparedStatement pstmt = con.prepareStatement("select ID,COMP_NAME,EMAIL from COMPANY");
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())
			{
				res.add(new Company(rs.getLong("ID"),rs.getString("COMP_NAME"),"empty_password",rs.getString("EMAIL")));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't rows from Company DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
		return res;
	}

	@Override
	public boolean lookupByName(String name) throws CouponSystemException {
		boolean res;
		Connection con=cPool.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement("select count(*) as cnt from COMPANY where COMP_NAME=?");
			pstmt.setString(1, name);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next() && rs.getLong("cnt")==0) res=false;
			else res=true;
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't read rows from Company DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
		return res;
	}

}
