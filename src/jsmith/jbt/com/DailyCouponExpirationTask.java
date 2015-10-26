/**
 * 
 */
package jsmith.jbt.com;

import java.sql.Date;
import java.util.GregorianCalendar;

import jsmith.jbt.com.DAO.CompanyCouponDBDAO;
import jsmith.jbt.com.DAO.CouponDBDAO;
import jsmith.jbt.com.DAO.CustomerCouponDBDAO;
import jsmith.jbt.com.DTO.Coupon;

/**
 * @author andrew
 * 
 * A daily routine thread to clean up outdated coupons
 *
 */
public class DailyCouponExpirationTask implements Runnable {

	private boolean quit;
	private final CustomerCouponDBDAO custcouponDBDAO;
	private final CompanyCouponDBDAO compcouponDBDAO;
	private final CouponDBDAO couponDBDAO;
	private final ConnectionPool cPool;
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while(!quit){
			try {
				Date today=new Date(GregorianCalendar.getInstance().getTimeInMillis());
				//TODO check the Time - is it time to run the scheduled cleaning
				for(Coupon outdatedCoupon: couponDBDAO.realAllByEndDate(today)) {
					custcouponDBDAO.deleteAllCoupons(outdatedCoupon.getID()); //Delete from all customers
					compcouponDBDAO.deleteAllCoupons(outdatedCoupon.getID()); //Delete from company
					couponDBDAO.delete(outdatedCoupon);						  //Delete the entity record
				}
				Thread.sleep(5000); //TODO remove the sleep
			} catch (InterruptedException | CouponSystemException e) {
				System.out.println("DailyExpirationRoutine interrupted by force, quit flag="+this.quit);
			}
		}
		
	}
	/**
	 * @throws CouponSystemException 
	 * 
	 */
	public DailyCouponExpirationTask() throws CouponSystemException {
		super();
		quit=false;
		this.cPool=ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl);
		this.custcouponDBDAO=new CustomerCouponDBDAO(cPool);
		this.compcouponDBDAO=new CompanyCouponDBDAO(cPool);
		this.couponDBDAO=new CouponDBDAO(cPool);
	}

	
	/**
	 * Gracefully stops the task infinite thread
	 */
	public void stopTask(){
		quit=true;
	}
}
