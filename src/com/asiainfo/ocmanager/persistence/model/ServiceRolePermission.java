package com.asiainfo.ocmanager.persistence.model;

/**
 * 
 * @author zhaoyim
 *
 */
public class ServiceRolePermission {

	private String serviceId;
	private String roleId;
	private String ServicePermission;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getServicePermission() {
		return ServicePermission;
	}

	public void setServicePermission(String servicePermission) {
		ServicePermission = servicePermission;
	}

}
