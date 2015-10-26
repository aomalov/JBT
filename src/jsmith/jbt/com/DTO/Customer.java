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
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CUST_NAME == null) ? 0 : CUST_NAME.hashCode());
		result = prime * result + (int) (ID ^ (ID >>> 32));
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
		if (!(obj instanceof Customer)) {
			return false;
		}
		Customer other = (Customer) obj;
		if (CUST_NAME == null) {
			if (other.CUST_NAME != null) {
				return false;
			}
		} else if (!CUST_NAME.equals(other.CUST_NAME)) {
			return false;
		}
		if (ID != other.ID) {
			return false;
		}
		return true;
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
