/**
 * 
 */
package com.jbt.jsmith.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.dto.Coupon;
import com.jbt.jsmith.dto.Coupon.CouponType;

/**
 * @author andrew
 *
 */
public class CouponDBDAO implements CouponDAO {

	private final ConnectionPool cPool;
	
	
	/**
	 * @throws CouponSystemException 
	 * 
	 */
	public CouponDBDAO() throws CouponSystemException {
		super();
		this.cPool = ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl);
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.entityDAO#create(java.lang.Object)
	 */
	@Override
	public long create(Coupon entity) throws CouponSystemException {
		Connection con=cPool.getConnection();
		long res;
		try {
			PreparedStatement pstmt = con.prepareStatement("insert into COUPON(TITLE,MESSAGE,IMAGE,TYPE,AMOUNT,PRICE,START_DATE,END_DATE) values(?,?,?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, entity.getTITLE());
			pstmt.setString(2, entity.getMESSAGE());
			pstmt.setString(3, entity.getIMAGE());
			pstmt.setString(4, entity.getTYPE().name());
			pstmt.setInt(5, entity.getAMOUNT());
			pstmt.setDouble(6, entity.getPRICE());
			pstmt.setDate(7, entity.getSTART_DATE());
			pstmt.setDate(8, entity.getEND_DATE());
			res=CouponDbHelper.getDBIdentityField(pstmt);
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
		Connection con=cPool.getConnection();
		Coupon res=new Coupon(ID,CouponType.RESTAURANT,"","","",0,Double.MIN_VALUE,Date.valueOf("2015-01-01"),Date.valueOf("2015-01-01"));
		try {
			PreparedStatement pstmt = con.prepareStatement("select TITLE,MESSAGE,IMAGE,TYPE,AMOUNT,PRICE,START_DATE,END_DATE from COUPON where ID=?");
			pstmt.setLong(1, ID);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())
			{
				res.setTITLE(rs.getString("TITLE"));
				res.setTYPE(CouponType.valueOf(rs.getString("TYPE")));
				res.setIMAGE(rs.getString("IMAGE"));
				res.setMESSAGE(rs.getString("MESSAGE"));
				res.setAMOUNT(rs.getInt("AMOUNT"));
				res.setPRICE(rs.getDouble("PRICE"));
				res.setSqlSTART_DATE(rs.getDate("START_DATE"));
				res.setSqlEND_DATE(rs.getDate("END_DATE"));
			}
			else throw new CouponSystemException("No coupon exists with ID="+ID);
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't read a row from Coupon DB table");		
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
			pstmt.setDate(8, entity.getEND_DATE());
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
		Connection con=cPool.getConnection();
		Collection<Coupon> res=new ArrayList<>();
		try {
			PreparedStatement pstmt = con.prepareStatement("select ID,TITLE,MESSAGE,IMAGE,TYPE,AMOUNT,PRICE,START_DATE,END_DATE from COUPON");
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())
			{
				res.add(new Coupon(rs.getLong("ID"),CouponType.valueOf(rs.getString("TYPE")),rs.getString("TITLE"),rs.getString("IMAGE"),rs.getString("MESSAGE"),rs.getInt("AMOUNT"),
						rs.getDouble("PRICE"),rs.getDate("START_DATE"),rs.getDate("END_DATE")));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't read a row from Coupon DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see jsmith.jbt.com.CouponDAO#realAllByType(jsmith.jbt.com.Coupon.CouponType)
	 */
	@Override
	public Collection<Coupon> realAllByType(CouponType TYPE) throws CouponSystemException {
		Connection con=cPool.getConnection();
		Collection<Coupon> res=new ArrayList<>();
		try {
			PreparedStatement pstmt = con.prepareStatement("select ID,TITLE,MESSAGE,IMAGE,TYPE,AMOUNT,PRICE,START_DATE,END_DATE from COUPON where TYPE=?");
			pstmt.setString(1, TYPE.name());
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())
			{
				res.add(new Coupon(rs.getLong("ID"),CouponType.valueOf(rs.getString("TYPE")),rs.getString("TITLE"),rs.getString("IMAGE"),rs.getString("MESSAGE"),rs.getInt("AMOUNT"),
						rs.getDouble("PRICE"),rs.getDate("START_DATE"),rs.getDate("END_DATE")));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't read a row from Coupon DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
		return res;
	}

	@Override
	public Collection<Coupon> realAllByEndDate(Date couponDate) throws CouponSystemException {
		Connection con=cPool.getConnection();
		Collection<Coupon> res=new ArrayList<>();
		try {
			PreparedStatement pstmt = con.prepareStatement("select ID,TITLE,MESSAGE,IMAGE,TYPE,AMOUNT,PRICE,START_DATE,END_DATE from COUPON where END_DATE<?");
			pstmt.setDate(1, couponDate);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())
			{
				res.add(new Coupon(rs.getLong("ID"),CouponType.valueOf(rs.getString("TYPE")),rs.getString("TITLE"),rs.getString("IMAGE"),rs.getString("MESSAGE"),rs.getInt("AMOUNT"),
						rs.getDouble("PRICE"),rs.getDate("START_DATE"),rs.getDate("END_DATE")));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't read a row from Coupon DB table");		
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
			PreparedStatement pstmt = con.prepareStatement("select count(*) as cnt from COUPON where TITLE=?");
			pstmt.setString(1, name);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next() && rs.getLong("cnt")==0) res=false;
			else res=true;
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't read rows from Coupon DB table");		
		}
		finally {
			if(con!=null) cPool.returnConnection(con);
		}
		return res;
	}

}
