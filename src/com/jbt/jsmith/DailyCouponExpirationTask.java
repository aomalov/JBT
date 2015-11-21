/**
 * 
 */
package com.jbt.jsmith;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import com.jbt.jsmith.DAO.CompanyCouponDBDAO;
import com.jbt.jsmith.DAO.CouponDBDAO;
import com.jbt.jsmith.DAO.CustomerCouponDBDAO;
import com.jbt.jsmith.DTO.Coupon;

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
	private final TimeUnit tuPeriod;
	private final  long periodValue;
	private Date lastRun=null;
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while(!quit){
			Date today=new Date(GregorianCalendar.getInstance().getTimeInMillis());
			if(lastRun==null || CouponDbHelper.computeDetailedDateDiff(today, lastRun).get(tuPeriod)>periodValue) {
				try {
					for(Coupon outdatedCoupon: couponDBDAO.realAllByEndDate(today)) {
						custcouponDBDAO.deleteAllCoupons(outdatedCoupon.getID()); //Delete from all customers
						compcouponDBDAO.deleteAllCoupons(outdatedCoupon.getID()); //Delete from company
						couponDBDAO.delete(outdatedCoupon);						  //Delete the entity record
						lastRun=today;
						Thread.sleep(1000);
					}
				} catch (InterruptedException | CouponSystemException e) {
					System.out.println("DailyExpirationRoutine interrupted by force, quit flag="+this.quit);
				}				
			}
		}
		
	}
	/**
	 * @throws CouponSystemException 
	 * 
	 */
	public DailyCouponExpirationTask(TimeUnit tuPeriod,long periodValue) throws CouponSystemException {
		super();
		quit=false;
		this.cPool=ConnectionPool.getInstance(ConnectionPool.defDriverName, ConnectionPool.defDbUrl);
		this.custcouponDBDAO=new CustomerCouponDBDAO(cPool);
		this.compcouponDBDAO=new CompanyCouponDBDAO(cPool);
		this.couponDBDAO=new CouponDBDAO(cPool);
		this.tuPeriod=tuPeriod;
		this.periodValue=periodValue;
	}

	
	/**
	 * Gracefully stops the task infinite thread
	 */
	public void stopTask(){
		quit=true;
	}
}
