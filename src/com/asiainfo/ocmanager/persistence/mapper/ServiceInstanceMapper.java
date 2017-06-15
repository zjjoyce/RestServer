package com.asiainfo.ocmanager.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.asiainfo.ocmanager.persistence.model.ServiceInstance;

/**
 * 
 * @author zhaoyim
 *
 */
public interface ServiceInstanceMapper {

	/**
	 *
	 * @param tenantId
	 * @return
	 */
	public List<ServiceInstance> selectServiceInstancesByTenant(@Param("tenantId") String tenantId);

	/**
	 *
	 * @param serviceInstance
	 */
	public void insertServiceInstance(ServiceInstance serviceInstance);

	/**
	 *
	 * @param tenantId
	 * @param instanceName
	 */
	public void deleteServiceInstance(@Param("tenantId") String tenantId, @Param("instanceName") String instanceName);

}
