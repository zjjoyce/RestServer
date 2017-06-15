package com.asiainfo.ocmanager.rest.bean;

/**
 * 
 * @author zhaoyim
 *
 */
public class AdapterResponseBean {
	private String status;
	private String message;
	private int resCode;

	public AdapterResponseBean() {

	}

	public AdapterResponseBean(String status, String message, int resCode) {
		this.status = status;
		this.message = message;
		this.resCode = resCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getResCodel() {
		return resCode;
	}

	public void setResCodel(int resCode) {
		this.resCode = resCode;
	}

}
