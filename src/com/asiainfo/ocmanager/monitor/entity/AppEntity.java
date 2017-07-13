package com.asiainfo.ocmanager.monitor.entity;

/**
 * 中信云Rest返回响应中应用基线Entity
 * @author EthanWang
 *
 */
public class AppEntity {
	private String org_id; // 租户id
	private String id; //  应用基线id
	private String code; // 应用基线英文名称
	private String abbreviation; // 应用基线名称
	
	public AppEntity(){}
	
	public String getOrg_id() {
		return org_id;
	}

	public AppEntity(String id, String abbreviation)
	{
		this.id = id;
		this.abbreviation = abbreviation;
	}
	
	public String getCode() {
		return code;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getId() {
		return id;
	}

	public String toString()
	{
		return "id:" + id + "	abbreviation:" + abbreviation;
	}
}
