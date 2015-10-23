/**
 * 
 */
package jsmith.jbt.com.DTO;

/**
 * @author andrew
 *
 * Company DTO
 */
public class Company {
	private long ID ;
	private String COMP_NAME;
	private String PASSWORD ;
	private String EMAIL;
	/**
	 * @return the cOMP_NAME
	 */
	public String getCOMP_NAME() {
		return COMP_NAME;
	}
	/**
	 * @param cOMP_NAME the cOMP_NAME to set
	 */
	public void setCOMP_NAME(String cOMP_NAME) {
		COMP_NAME = cOMP_NAME;
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
	/**
	 * @return the eMAIL
	 */
	public String getEMAIL() {
		return EMAIL;
	}
	/**
	 * @param eMAIL the eMAIL to set
	 */
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	/**
	 * @return the iD
	 */
	public long getID() {
		return ID;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Company [ID=").append(ID).append(", COMP_NAME=").append(COMP_NAME).append("]");
		return builder.toString();
	}
	/**
	 * @param iD
	 * @param cOMP_NAME
	 * @param pASSWORD
	 * @param eMAIL
	 */
	public Company(long iD, String cOMP_NAME, String pASSWORD, String eMAIL) {
		super();
		ID = iD;
		COMP_NAME = cOMP_NAME;
		PASSWORD = pASSWORD;
		EMAIL = eMAIL;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(long iD) {
		ID = iD;
	} 
	
}
