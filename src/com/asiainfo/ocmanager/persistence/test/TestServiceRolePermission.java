package com.asiainfo.ocmanager.persistence.test;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.ServiceRolePermissionMapper;
import com.asiainfo.ocmanager.persistence.model.ServiceRolePermission;

public class TestServiceRolePermission {
	public static void main(String[] args) {
		SqlSession session = TestDBConnectorFactory.getSession();
		try {
			ServiceRolePermissionMapper mapper = session.getMapper(ServiceRolePermissionMapper.class);
			ServiceRolePermission permission = mapper.selectPermissionByServiceNameRoleId("hdfs", "a12a84d0-524a-11e7-9dbb-fa163ed7d0ae");

			
				System.out.println(permission.getRoleId());
				System.out.println(permission.getServiceName());
				System.out.println(permission.getServicePermission());
			

			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

	}
}
