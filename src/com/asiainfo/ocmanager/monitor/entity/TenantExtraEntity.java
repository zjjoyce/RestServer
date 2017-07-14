package com.asiainfo.ocmanager.monitor.entity;

/**
 * Entity of the response from RestAPI <code>get_tenant_info_by_app_base_id.do</code>
 * @author EthanWang
 *
 */
public class TenantExtraEntity {
	private String code;
	private String msg;
	private AppExtraEntity data;
	
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	public AppExtraEntity getData() {
		return data;
	}
}
