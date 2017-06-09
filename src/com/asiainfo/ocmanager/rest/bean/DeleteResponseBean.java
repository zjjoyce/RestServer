package com.asiainfo.ocmanager.rest.bean;

/**
 * 
 * @author zhaoyim
 *
 */
public class DeleteResponseBean {
	private String status;
	private int resCode;

	public DeleteResponseBean() {

	}

	public DeleteResponseBean(String status, int resCode) {
		this.status = status;
		this.resCode = resCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getResCodel() {
		return resCode;
	}

	public void setResCodel(int resCode) {
		this.resCode = resCode;
	}

}
