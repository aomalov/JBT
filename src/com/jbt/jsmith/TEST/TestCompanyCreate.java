/**
 * 
 */
package com.jbt.jsmith.TEST;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.jbt.jsmith.ConnectionPool;
import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.DAO.CompanyDBDAO;
import com.jbt.jsmith.DTO.Company;
import com.jbt.jsmith.FACADE.AdminFacade;

/**
 * @author andrewm
 *
 */
@RunWith(Parameterized.class)
public class TestCompanyCreate {
	
	private String companyName;
	private CompanyDBDAO compDbDao;
	private AdminFacade adminFacade;
	private ConnectionPool cPool;
	private Company aComp;
	
    @Parameters
    public static Collection<Object[]> data() {
        // @formatter:off
        Object[][] data = new Object[][] { 
            { "Company 59" },
            { "Company 60" }
        };
        // @formatter:on
        return asList(data);
    }

	public TestCompanyCreate(String companyName) throws CouponSystemException {
		this.companyName = companyName;
		this.cPool=ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl);

		this.compDbDao=new CompanyDBDAO(this.cPool);
		this.adminFacade=new AdminFacade();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception { //is run before each test on each iteration
		aComp=new Company(0,companyName,"","");
	}

	/**
	 * Test method for {@link com.jbt.jsmith.DAO.CompanyDBDAO#create(com.jbt.jsmith.DTO.Company)}.
	 * @throws CouponSystemException 
	 */
	@Test
	public void testCreate() throws CouponSystemException {
		long id=compDbDao.create(aComp);
		aComp.setID(id);
		Company newComp=compDbDao.read(id);
		assertEquals(aComp, newComp);
	}

	/**
	 * Test method for {@link com.jbt.jsmith.DAO.CompanyDBDAO#read(long)}.
	 * @throws CouponSystemException 
	 */
	@Test(expected=CouponSystemException.class)
	public void testDuplicatedName() throws CouponSystemException {
		adminFacade.createCompany(aComp);
	}

}
