package com.asiainfo.ocmanager.persistence.model;

/**
 * 
 * @author zhaoyim
 *
 */
public class ServiceRolePermission {

	private String serviceName;
	private String roleId;
	private String ServicePermission;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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
