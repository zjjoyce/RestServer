package com.asiainfo.ocmanager.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.asiainfo.ocmanager.persistence.model.UserRoleView;

public interface UserRoleMapper {

	/**
	 * 
	 * @param tenantId
	 * @return
	 */
	public List<UserRoleView> selectUsersRolesInTenant(String tenantId);
	
	
	/**
	 * 
	 * @param userId
	 * @param tenantId
	 * @return
	 */
	public UserRoleView selectRoleBasedOnUserAndTenant(@Param("userName") String userName, @Param("tenantId") String tenantId);
	
}
