package com.asiainfo.ocmanager.persistence.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.UserRoleMapper;
import com.asiainfo.ocmanager.persistence.model.UserRoleView;

public class TestUserRole {

	public static void main(String[] args) {
		SqlSession session = DBConnectorFactory.getSession();
		try {
			UserRoleMapper mapper = session.getMapper(UserRoleMapper.class);
			List<UserRoleView> urs = mapper.selectUsersRolesInTenant("1");

			for (UserRoleView ur : urs) {
				System.out.println(ur.getUserId());
				System.out.println(ur.getUserName());
				System.out.println(ur.getUserDescription());
				System.out.println(ur.getRoleId());
				System.out.println(ur.getRoleName());
				System.out.println(ur.getTenantId());
			}
			session.commit();
			
			System.out.println("============");
			UserRoleView urs1 = mapper.selectRoleBasedOnUserAndTenant("zhaoyim", "2");
			
				System.out.println(urs1.getUserId());
				System.out.println(urs1.getUserName());
				System.out.println(urs1.getUserDescription());
				System.out.println(urs1.getRoleId());
				System.out.println(urs1.getRoleName());
				System.out.println(urs1.getTenantId());
			
			session.commit();
			
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

	}

}
