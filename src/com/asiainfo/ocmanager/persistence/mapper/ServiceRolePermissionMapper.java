package com.asiainfo.ocmanager.persistence.mapper;

import java.util.List;

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
	public List<ServiceRolePermission> selectServicePermissionByRoleId(@Param("roleId") String roleId);
}
