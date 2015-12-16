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
public class RestJsonMessage {

	private String messageType;
	private String messageText;
	/**
	 * @param messageType
	 * @param messageText
	 */
	public RestJsonMessage(String messageType, String messageText) {
		super();
		this.messageType = messageType;
		this.messageText = messageText;
	}
	/**
	 * @return the messageType
	 */
	public String getMessageType() {
		return messageType;
	}
	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	/**
	 * @return the messageText
	 */
	public String getMessageText() {
		return messageText;
	}
	/**
	 * @param messageText the messageText to set
	 */
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	/**
	 * 
	 */
	public RestJsonMessage() {
		// TODO Auto-generated constructor stub
	}

}
