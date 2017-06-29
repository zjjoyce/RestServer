package com.asiainfo.ocmanager.rest.bean;

/**
 * 
 * @author zhaoyim
 *
 */
public class SSOFakerUserBean {

	private String http_x_proxy_cas_loginname;
	private String http_x_proxy_cas_username;
	private String http_x_proxy_cas_email;
	private String http_x_proxy_cas_userid;
	private String http_x_proxy_cas_mobile;
	private boolean isAdmin;

	public SSOFakerUserBean() {

	}

	public SSOFakerUserBean(String http_x_proxy_cas_loginname, String http_x_proxy_cas_username,
			String http_x_proxy_cas_email, String http_x_proxy_cas_userid, String http_x_proxy_cas_mobile,
			boolean isAdmin) {
		this.http_x_proxy_cas_loginname = http_x_proxy_cas_loginname;
		this.http_x_proxy_cas_username = http_x_proxy_cas_username;
		this.http_x_proxy_cas_email = http_x_proxy_cas_email;
		this.http_x_proxy_cas_userid = http_x_proxy_cas_userid;
		this.http_x_proxy_cas_mobile = http_x_proxy_cas_mobile;
		this.isAdmin = isAdmin;
	}

	public String getHttp_x_proxy_cas_loginname() {
		return http_x_proxy_cas_loginname;
	}

	public void setHttp_x_proxy_cas_loginname(String http_x_proxy_cas_loginname) {
		this.http_x_proxy_cas_loginname = http_x_proxy_cas_loginname;
	}

	public String getHttp_x_proxy_cas_username() {
		return http_x_proxy_cas_username;
	}

	public void setHttp_x_proxy_cas_username(String http_x_proxy_cas_username) {
		this.http_x_proxy_cas_username = http_x_proxy_cas_username;
	}

	public String getHttp_x_proxy_cas_email() {
		return http_x_proxy_cas_email;
	}

	public void setHttp_x_proxy_cas_email(String http_x_proxy_cas_email) {
		this.http_x_proxy_cas_email = http_x_proxy_cas_email;
	}

	public String getHttp_x_proxy_cas_userid() {
		return http_x_proxy_cas_userid;
	}

	public void setHttp_x_proxy_cas_userid(String http_x_proxy_cas_userid) {
		this.http_x_proxy_cas_userid = http_x_proxy_cas_userid;
	}

	public String getHttp_x_proxy_cas_mobile() {
		return http_x_proxy_cas_mobile;
	}

	public void setHttp_x_proxy_cas_mobile(String http_x_proxy_cas_mobile) {
		this.http_x_proxy_cas_mobile = http_x_proxy_cas_mobile;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
