package com.asiainfo.ocmanager.rest.resource.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.DBConnectorFactory;
import com.asiainfo.ocmanager.persistence.mapper.RoleMapper;
import com.asiainfo.ocmanager.persistence.mapper.ServiceRolePermissionMapper;
import com.asiainfo.ocmanager.persistence.model.Role;
import com.asiainfo.ocmanager.persistence.model.ServiceRolePermission;

/**
 * 
 * @author zhaoyim
 *
 */
public class ServiceRolePermissionWrapper {

	/**
	 * 
	 * @return
	 */
	public static List<ServiceRolePermission> getServicePermissionByRoleId(String roleId) {
		SqlSession session = DBConnectorFactory.getSession();
		List<ServiceRolePermission> permissionList = new ArrayList<ServiceRolePermission>();
		try {
			ServiceRolePermissionMapper mapper = session.getMapper(ServiceRolePermissionMapper.class);
			permissionList = mapper.selectServicePermissionByRoleId(roleId);

			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

		return permissionList;
	}

}
