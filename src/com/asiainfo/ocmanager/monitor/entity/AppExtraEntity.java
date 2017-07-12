package com.asiainfo.ocmanager.monitor.entity;

/**
 * Entity of inner object in {@link com.asiainfo.ocmanager.monitor.entity.TenantExtraEntity}
 * @author EthanWang
 *
 */
public class AppExtraEntity {
	private String org_name;
	private String org_id;
	private String id;
	private String code;
	private String abbreviation;
	
	public String getOrg_name() {
		return org_name;
	}
	public String getOrg_id() {
		return org_id;
	}
	public String getId() {
		return id;
	}
	public String getCode() {
		return code;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
}
