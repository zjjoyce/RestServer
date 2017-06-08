package com.asiainfo.ocmanager.persistence.mapper;

import java.util.List;

import com.asiainfo.ocmanager.persistence.model.Role;

/**
 * 
 * @author zhaoyim
 *
 */

public interface RoleMapper {
	
	/**
	 * 
	 * @return
	 */
	public List<Role> selectAllRoles();

}
