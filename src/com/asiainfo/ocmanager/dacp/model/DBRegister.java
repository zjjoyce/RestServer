package com.asiainfo.ocmanager.dacp.model;

/**
 *
 * @author zhaoyim
 *
 */
public class DBRegister {
	private String xmlid;
	private String dbname;
	private String cnname;
	private String driverclassname;

	private String url;
	private String username;
	private String password;
	private String remark;

	private String alias;

	public String getXmlid() {
		return xmlid;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setXmlid(String xmlid) {
		this.xmlid = xmlid;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	public String getDriverclassname() {
		return driverclassname;
	}

	public void setDriverclassname(String driverclassname) {
		this.driverclassname = driverclassname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
