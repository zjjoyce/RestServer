package com.asiainfo.ocmanager.rest.bean;

/**
 * 
 * @author zhaoyim
 *
 */
public class SSOFakerUserBean {

	private String currentUser;

	public SSOFakerUserBean() {

	}

	public SSOFakerUserBean(String currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * 
	 * @return
	 */
	public String getCurrentUser() {
		return currentUser;
	}

	/**
	 * 
	 * @param currentUser
	 */
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

}
