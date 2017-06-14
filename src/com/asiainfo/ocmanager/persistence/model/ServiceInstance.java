package com.asiainfo.ocmanager.persistence.model;

/**
 * 
 * @author zhaoyim
 *
 */
public class ServiceInstance {

	private String id;
	private String instanceName;
	private String tenantId;
	private String serviceTypeId;
	private String serviceTypeName;
	private String quota;

	public ServiceInstance() {

	}

	public ServiceInstance(String id, String instanceName, String tenantId, String serviceTypeId,
			String serviceTypeName, String quota) {
		this.id = id;
		this.instanceName = instanceName;
		this.tenantId = tenantId;
		this.serviceTypeId = serviceTypeId;
		this.serviceTypeName = serviceTypeName;
		this.quota = quota;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(String serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public String getServiceTypeName() {
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public String getQuota() {
		return quota;
	}

	public void setQuota(String quota) {
		this.quota = quota;
	}

}
