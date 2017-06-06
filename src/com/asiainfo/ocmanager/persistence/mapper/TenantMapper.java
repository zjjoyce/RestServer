package com.asiainfo.ocmanager.persistence.mapper;

import com.asiainfo.ocmanager.persistence.model.Tenant;

/**
 * 
 * @author zhaoyim
 *
 */

public interface TenantMapper {

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Tenant selectTenantById(int id);
	
	
	
	
}
