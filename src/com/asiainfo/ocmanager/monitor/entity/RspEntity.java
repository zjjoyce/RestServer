package com.asiainfo.ocmanager.monitor.entity;

import java.util.List;

/**
 * 中信云Rest接口返回响应最外层响应实体
 * @author EthanWang
 *
 */
public class RspEntity {
	private String code;
	private String msg;
	private List<TenantEntity> data;
	
	public List<TenantEntity> getData() {
		return data;
	}
}
