package com.asiainfo.ocmanager.persistence.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.ServiceRolePermissionMapper;
import com.asiainfo.ocmanager.persistence.model.ServiceRolePermission;

public class TestServiceRolePermission {
	public static void main(String[] args) {
		SqlSession session = TestDBConnectorFactory.getSession();
		try {
			ServiceRolePermissionMapper mapper = session.getMapper(ServiceRolePermissionMapper.class);
			List<ServiceRolePermission> permissionList = mapper.selectServicePermissionByRoleId("a12a84d0-524a-11e7-9dbb-fa163ed7d0ae");

			for (ServiceRolePermission pl : permissionList) {
				System.out.println(pl.getRoleId());
				System.out.println(pl.getServiceId());
				System.out.println(pl.getServicePermission());
			}

			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

	}
}
