package com.asiainfo.ocmanager.monitor.entity;

import java.util.List;

/**
 * 中信云Rest接口返回响应中的租户Entity
 * @author EthanWang
 *
 */
public class TenantEntity {
	private String org_name; // 租户名称
	private String org_id; // 租户id
	private List<AppEntity> app_list; // 应用基线列表

	public String getOrg_name() {
		return org_name;
	}

	public String getOrg_id() {
		return org_id;
	}

	public List<AppEntity> getApp_list() {
		return app_list;
	}

	public String toString()
	{
		return "org_id:" + org_id + "	org_name:" + org_name;
	}
}
