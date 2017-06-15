package com.asiainfo.ocmanager.persistence.test;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.TenantUserRoleAssignmentMapper;
import com.asiainfo.ocmanager.persistence.model.TenantUserRoleAssignment;

public class TestTenantUserRoleAssignment {

	public static void main(String[] args) {
		SqlSession session = DBConnectorFactory.getSession();
		try {
			TenantUserRoleAssignmentMapper mapper = session.getMapper(TenantUserRoleAssignmentMapper.class);

			mapper.insertTenantUserRoleAssignment(new TenantUserRoleAssignment("t2", "u1", "r1"));
			session.commit();
			mapper.updateTenantUserRoleAssignment(new TenantUserRoleAssignment("t2", "u1", "r2"));
			session.commit();

			TenantUserRoleAssignment tura = mapper.selectAssignmentByTenantUserRole("t2", "u1", "r2");
			System.out.println(tura.getTenantId());
			System.out.println(tura.getUserId());
			System.out.println(tura.getRoleId());
			System.out.println("====");

			mapper.deleteTenantUserRoleAssignment("t2", "u1");
			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

	}

}
