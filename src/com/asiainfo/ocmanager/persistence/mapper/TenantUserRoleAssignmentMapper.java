package com.asiainfo.ocmanager.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.asiainfo.ocmanager.persistence.model.TenantUserRoleAssignment;

public interface TenantUserRoleAssignmentMapper {

	/**
	 * 
	 * @param tenantId
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public TenantUserRoleAssignment selectAssignmentByTenantUserRole(@Param("tenantId") String tenantId,
			@Param("userId") String userId, @Param("roleId") String roleId);

	/**
	 * 
	 * @param assigment
	 */
	public void insertTenantUserRoleAssignment(TenantUserRoleAssignment assigment);

	/**
	 * 
	 * @param assigment
	 */
	public void updateTenantUserRoleAssignment(TenantUserRoleAssignment assigment);

	/**
	 * 
	 * @param tenantId
	 * @param userId
	 */
	public void deleteTenantUserRoleAssignment(@Param("tenantId") String tenantId, @Param("userId") String userId);

	/**
	 * 
	 * @param tenantId
	 * @return
	 */
	public List<TenantUserRoleAssignment> selectAssignmentByTenant(@Param("tenantId") String tenantId);

}
