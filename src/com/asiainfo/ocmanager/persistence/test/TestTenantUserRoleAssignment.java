package com.asiainfo.ocmanager.persistence.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.TenantUserRoleAssignmentMapper;
import com.asiainfo.ocmanager.persistence.model.TenantUserRoleAssignment;

public class TestTenantUserRoleAssignment {

	public static void main(String[] args) {
		SqlSession session = TestDBConnectorFactory.getSession();
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
			
			List<TenantUserRoleAssignment> turas = mapper.selectAssignmentByTenant("ttt");
			for(TenantUserRoleAssignment tura1: turas){
				System.out.println(tura1.getTenantId());
				System.out.println(tura1.getUserId());
				System.out.println(tura1.getRoleId());
				System.out.println("====");
			}

			mapper.deleteTenantUserRoleAssignment("t2", "u1");
			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

	}

}
