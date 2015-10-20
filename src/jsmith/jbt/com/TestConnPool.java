package jsmith.jbt.com;

import java.sql.Connection;

public class TestConnPool extends Thread {
	
	@Override
	public void run() {
		try {
			ConnectionPool cPool=ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl);
			Connection conn=cPool.getConnection();
			System.out.println(getName()+" got connection");
			Thread.sleep(1000);
			cPool.returnConnection(conn);
			System.out.println(getName()+" released connection");
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TestConnPool(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public static void TestMultyThreadingConnAquiring() {
		for(int k=0;k<15;k++){
			TestConnPool testThread=new TestConnPool("thread "+(k+1));
			testThread.start();
		}
	}

	public static void main(String[] args) throws CouponSystemException {
		// TODO Auto-generated method stub
	
		//CouponDbHelper.createTables(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl).getConnection());
		//CouponDbHelper.dropTables(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl).getConnection());
		CustomerDBDAO custDAO=new CustomerDBDAO(ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl));
		Customer cust=new Customer(0, "Test", "pASSWORD");
		
		long idCust=custDAO.create(cust);
		cust.setID(idCust);
		
		System.out.println("New customer ID="+idCust);
		
		cust.setCUST_NAME("New name");
		custDAO.update(cust);
		
		Customer cust2=custDAO.read(idCust);
		System.out.println(cust2);
		
		for (Customer c : custDAO.readAll()) {
			System.out.println(c);
		} 
		
		custDAO.delete(cust2);

	}

}
