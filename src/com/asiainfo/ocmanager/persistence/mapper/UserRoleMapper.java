package com.asiainfo.ocmanager.persistence.mapper;

import java.util.List;

import com.asiainfo.ocmanager.persistence.model.UserRoleView;

public interface UserRoleMapper {

	public List<UserRoleView> selectUsersRolesInTenant(String tenantId);
	
}
