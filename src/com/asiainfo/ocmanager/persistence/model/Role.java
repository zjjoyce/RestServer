package com.asiainfo.ocmanager.persistence.model;

/**
 * 
 * @author zhaoyim
 *
 */
public class Role {
	private String id;
	private String rolename;
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
