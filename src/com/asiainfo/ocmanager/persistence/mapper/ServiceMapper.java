package com.asiainfo.ocmanager.persistence.mapper;

import java.util.List;

import com.asiainfo.ocmanager.persistence.model.Service;

/**
 * 
 * @author zhaoyim
 *
 */

public interface ServiceMapper {

	/**
	 * 
	 * @return
	 */
	public List<Service> selectAllServices();
	
	
	/**
	 * 
	 * @param serviceId
	 * @return
	 */
	public Service selectServiceById(String serviceId);
	
}
