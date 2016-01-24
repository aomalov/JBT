/**
 * 
 */
package com.jbt.jsmith.test;

import java.sql.Connection;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.CouponSystem.ClientType;
import com.jbt.jsmith.dao.ConnectionPool;
import com.jbt.jsmith.dao.security.Owasp;

/**
 * @author andrewm
 *
 */
public class TestSecureLogin {

	/**
	 * @param args
	 * @throws CouponSystemException 
	 */
	public static void main(String[] args) throws CouponSystemException {
		// TODO Auto-generated method stub
		
		ConnectionPool cPool=ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl);
		Connection conn=cPool.getConnection();
		
		try {
			Owasp.createTable(conn);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		try {
			Owasp.createUser(conn, "company1", "pass_Company1", ClientType.Company.toString());
			System.out.println("Authenticated:"+Owasp.authenticate("company1", "pass_Company1", ClientType.Company.toString()));
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}

}
