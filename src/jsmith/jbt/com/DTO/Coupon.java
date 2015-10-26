/**
 * 
 */
package jsmith.jbt.com.DTO;

import java.sql.Date;

/**
 * @author andrew
 * 
 * Coupon DTO class
 *
 */
public class Coupon {
	
	public enum CouponType {
		RESTAURANT,TRAVEL,HEALTHCARE
	}
	
	private CouponType TYPE;
	private long ID ;
	private String TITLE ;
	private String MESSAGE;
	private String IMAGE ;
	private int AMOUNT ;
	private Double PRICE ;
	private Date START_DATE ;
	private Date END_DATE ;
	/**
	 * @param iD
	 * @param tYPE
	 * @param tITLE
	 * @param mESSAGE
	 * @param iMAGE
	 * @param aMOUNT
	 * @param pRICE
	 * @param sTART_DATE
	 * @param eND_DATE
	 */
	public Coupon(long iD, CouponType tYPE, String tITLE, String mESSAGE, String iMAGE, int aMOUNT, Double pRICE,
			Date sTART_DATE, Date eND_DATE) {
		super();
		ID = iD;
		TYPE = tYPE;
		TITLE = tITLE;
		MESSAGE = mESSAGE;
		IMAGE = iMAGE;
		AMOUNT = aMOUNT;
		PRICE = pRICE;
		START_DATE = sTART_DATE;
		END_DATE = eND_DATE;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Coupon [ID=").append(ID).append(", TYPE=").append(TYPE).append(", TITLE=").append(TITLE)
				.append(", MESSAGE=").append(MESSAGE).append(", IMAGE=").append(IMAGE).append(", AMOUNT=")
				.append(AMOUNT).append(", PRICE=").append(PRICE).append(", START_DATE=").append(START_DATE)
				.append(", END_DATE=").append(END_DATE).append("]");
		return builder.toString();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ID ^ (ID >>> 32));
		result = prime * result + ((TITLE == null) ? 0 : TITLE.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Coupon)) {
			return false;
		}
		Coupon other = (Coupon) obj;
		if (ID != other.ID) {
			return false;
		}
		if (TITLE == null) {
			if (other.TITLE != null) {
				return false;
			}
		} else if (!TITLE.equals(other.TITLE)) {
			return false;
		}
		return true;
	}
	/**
	 * @return the tYPE
	 */
	public CouponType getTYPE() {
		return TYPE;
	}
	/**
	 * @param tYPE the tYPE to set
	 */
	public void setTYPE(CouponType tYPE) {
		TYPE = tYPE;
	}
	/**
	 * @return the iD
	 */
	public long getID() {
		return ID;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(long iD) {
		ID = iD;
	}
	/**
	 * @return the tITLE
	 */
	public String getTITLE() {
		return TITLE;
	}
	/**
	 * @param tITLE the tITLE to set
	 */
	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}
	/**
	 * @return the mESSAGE
	 */
	public String getMESSAGE() {
		return MESSAGE;
	}
	/**
	 * @param mESSAGE the mESSAGE to set
	 */
	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}
	/**
	 * @return the iMAGE
	 */
	public String getIMAGE() {
		return IMAGE;
	}
	/**
	 * @param iMAGE the iMAGE to set
	 */
	public void setIMAGE(String iMAGE) {
		IMAGE = iMAGE;
	}
	/**
	 * @return the aMOUNT
	 */
	public int getAMOUNT() {
		return AMOUNT;
	}
	/**
	 * @param aMOUNT the aMOUNT to set
	 */
	public void setAMOUNT(int aMOUNT) {
		AMOUNT = aMOUNT;
	}
	/**
	 * @return the pRICE
	 */
	public Double getPRICE() {
		return PRICE;
	}
	/**
	 * @param pRICE the pRICE to set
	 */
	public void setPRICE(Double pRICE) {
		PRICE = pRICE;
	}
	/**
	 * @return the sTART_DATE
	 */
	public Date getSTART_DATE() {
		return START_DATE;
	}
	/**
	 * @param sTART_DATE the sTART_DATE to set
	 */
	public void setSTART_DATE(Date sTART_DATE) {
		START_DATE = sTART_DATE;
	}
	/**
	 * @return the eND_DATE
	 */
	public Date getEND_DATE() {
		return END_DATE;
	}
	/**
	 * @param eND_DATE the eND_DATE to set
	 */
	public void setEND_DATE(Date eND_DATE) {
		END_DATE = eND_DATE;
	}

	
}
