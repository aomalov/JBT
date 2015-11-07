package jsmith.jbt.com.TEST;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import jsmith.jbt.com.ConnectionPool;
import jsmith.jbt.com.CouponSystemException;
import jsmith.jbt.com.DAO.CustomerDBDAO;
import jsmith.jbt.com.DTO.Customer;
import jsmith.jbt.com.FACADE.AdminFacade;

/**
 * @author andrewm
 *
 */
@RunWith(Parameterized.class)
public class TestCustomerCreate {

	private String customerName;
	private CustomerDBDAO custDbDao;
	private AdminFacade adminFacade;
	private ConnectionPool cPool;
	private Customer aCust;
	
    @Parameters
    public static Collection<Object[]> data() {
        // @formatter:off
        Object[][] data = new Object[][] { 
            { "Customer 59" },
            { "Customer 60" }
        };
        // @formatter:on
        return asList(data);
    }

	public TestCustomerCreate(String customerName) throws CouponSystemException {
		this.customerName = customerName;
		this.cPool=ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl);

		this.custDbDao=new CustomerDBDAO(this.cPool);
		this.adminFacade=new AdminFacade();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception { //is run before each test on each iteration
		aCust=new Customer(0,customerName,"");
	}

	/**
	 * Test method for {@link jsmith.jbt.com.DAO.CompanyDBDAO#create(jsmith.jbt.com.DTO.Company)}.
	 * @throws CouponSystemException 
	 */
	@Test
	public void testCreate() throws CouponSystemException {
		long id=custDbDao.create(aCust);
		aCust.setID(id);
		Customer newCust=custDbDao.read(id);
		assertEquals(aCust, newCust);
	}

	/**
	 * Test method for {@link jsmith.jbt.com.DAO.CompanyDBDAO#read(long)}.
	 * @throws CouponSystemException 
	 */
	@Test(expected=CouponSystemException.class)
	public void testDuplicatedName() throws CouponSystemException {
		adminFacade.createCustomer(aCust);
	}


}
