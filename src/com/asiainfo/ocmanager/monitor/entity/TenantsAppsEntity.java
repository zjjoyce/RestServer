package com.asiainfo.ocmanager.monitor.entity;

import java.util.List;

/**
 * Entity of response from CITIC Rest request: <code>get_tenant_and_appBase_info.do</code>
 * @author EthanWang
 *
 */
public class TenantsAppsEntity {
	private String code;
	private String msg;
	private List<TenantEntity> data;
	
	public List<TenantEntity> getData() {
		return data;
	}
}
