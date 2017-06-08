package com.asiainfo.ocmanager.persistence.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.UserMapper;
import com.asiainfo.ocmanager.persistence.model.User;

/**
 * 
 * @author zhaoyim
 *
 */
public class TestUser {
	public static void main(String[] args) {
		SqlSession session = DBConnectorFactory.getSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);

			mapper.insertUser(new User("id3", "username", "password", "email", "description"));

			List<User> users = mapper.selectAllUsers();

			System.out.println("=== All users ===");
			for (User u : users) {
				System.out.println(u.getId());
				System.out.println(u.getUsername());
				System.out.println(u.getEmail());
				System.out.println(u.getDescription());
			}
			System.out.println("=== User by id ===");
			User user = mapper.selectUserById("1");
			System.out.println(user.getId());
			System.out.println(user.getUsername());
			System.out.println(user.getEmail());
			System.out.println(user.getDescription());

			session.commit();

		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

	}
}
