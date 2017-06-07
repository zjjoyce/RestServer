package com.asiainfo.ocmanager.persistence.model;

import java.util.Map;

/**
 * 
 * @author zhaoyim
 *
 */
public class ServiceInstance {
	private int id;
	private int tenantId;
	private int ServiceType;
	private Map<String, String> quota;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public int getServiceType() {
		return ServiceType;
	}

	public void setServiceType(int serviceType) {
		ServiceType = serviceType;
	}

	public Map<String, String> getQuota() {
		return quota;
	}

	public void setQuota(Map<String, String> quota) {
		this.quota = quota;
	}

}
