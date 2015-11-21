/**
 * 
 */
package com.jbt.jsmith.DTO;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author andrew
 *
 * Company DTO
 */
@XmlRootElement
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
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((COMP_NAME == null) ? 0 : COMP_NAME.hashCode());
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
		if (!(obj instanceof Company)) {
			return false;
		}
		Company other = (Company) obj;
		if (COMP_NAME == null) {
			if (other.COMP_NAME != null) {
				return false;
			}
		} else if (!COMP_NAME.equals(other.COMP_NAME)) {
			return false;
		}
		if (ID != other.ID) {
			return false;
		}
		return true;
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
	
	
	/**
	 * Needed for JSON auto binding
	 */
	public Company() {
	}
}
