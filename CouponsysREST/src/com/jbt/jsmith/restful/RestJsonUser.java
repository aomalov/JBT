/**
 * 
 */
package com.jbt.jsmith.restful;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author andrew
 *
 */
@XmlRootElement
public class RestJsonUser {

    private String userName;
    private String password;
    private String clientType;
	/**
	 * 
	 */
	public RestJsonUser() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the clientType
	 */
	public String getClientType() {
		return clientType;
	}
	/**
	 * @param clientType the clientType to set
	 */
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	/**
	 * @param userName
	 * @param password
	 * @param clientType
	 */
	public RestJsonUser(String userName, String password, String clientType) {
		super();
		this.userName = userName;
		this.password = password;
		this.clientType = clientType;
	}

}
