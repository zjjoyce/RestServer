package com.asiainfo.ocmanager.persistence.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.RoleMapper;
import com.asiainfo.ocmanager.persistence.model.Role;

public class TestRole {

	public static void main(String[] args) {
		SqlSession session = DBConnectorFactory.getSession();
		try {
			RoleMapper mapper = session.getMapper(RoleMapper.class);
			List<Role> roles = mapper.selectAllRoles();

			for (Role r : roles) {
				System.out.println(r.getId());
				System.out.println(r.getRolename());
				System.out.println(r.getDescription());
			}

			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

	}

}
