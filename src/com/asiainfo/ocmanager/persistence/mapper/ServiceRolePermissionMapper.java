package com.asiainfo.ocmanager.persistence.mapper;

import org.apache.ibatis.annotations.Param;

import com.asiainfo.ocmanager.persistence.model.ServiceRolePermission;

/**
 * 
 * @author zhaoyim
 *
 */
public interface ServiceRolePermissionMapper {

	/**
	 * 
	 * @param roleId
	 * @return
	 */
	public ServiceRolePermission selectPermissionByServiceNameRoleId(@Param("serviceName") String serviceName, @Param("roleId") String roleId);
}
