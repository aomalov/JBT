/**
 * 
 */
package jsmith.jbt.com.DTO;

/**
 * @author andrew
 *
 *Customer DTO class
 */
public class Customer {

	private long ID;
	private String CUST_NAME; 
	private String PASSWORD;
	/**
	 * @param iD
	 * @param cUST_NAME
	 * @param pASSWORD
	 */
	public Customer(long iD, String cUST_NAME, String pASSWORD) {
		super();
		ID = iD;
		CUST_NAME = cUST_NAME;
		PASSWORD = pASSWORD;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Customer [ID=").append(ID).append(", CUST_NAME=").append(CUST_NAME).append("]");
		return builder.toString();
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
	 * @return the cUST_NAME
	 */
	public String getCUST_NAME() {
		return CUST_NAME;
	}
	/**
	 * @param cUST_NAME the cUST_NAME to set
	 */
	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
	}
	/**
	 * @return the pASSWORD
	 */
	public String getPASSWORD() {
		return PASSWORD;
	}
	/**
	 * @param pASSWORD the pASSWORD to set
	 */
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	
	
}
