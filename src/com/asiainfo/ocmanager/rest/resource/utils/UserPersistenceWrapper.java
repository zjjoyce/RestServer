package com.asiainfo.ocmanager.rest.resource.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.PathParam;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.UserMapper;
import com.asiainfo.ocmanager.persistence.model.User;
import com.asiainfo.ocmanager.persistence.test.DBConnectorFactory;
import com.asiainfo.ocmanager.rest.utils.UUIDFactory;

/**
 * 
 * @author zhaoyim
 *
 */
public class UserPersistenceWrapper {

	/**
	 * 
	 * @return
	 */
	public static List<User> getUsers() {
		SqlSession session = DBConnectorFactory.getSession();
		List<User> users = new ArrayList<User>();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			users = mapper.selectAllUsers();

			session.commit();

		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

		return users;
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public static User getUserById(@PathParam("id") String userId) {
		SqlSession session = DBConnectorFactory.getSession();
		User user = null;
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			user = mapper.selectUserById(userId);

			session.commit();

		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

		return user;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public static User createUser(User user) {
		SqlSession session = DBConnectorFactory.getSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			String uuid = UUIDFactory.getUUID();
			user.setId(uuid);
			mapper.insertUser(user);

			user = mapper.selectUserById(uuid);
			session.commit();

		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

		return user;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public static User updateUser(User user) {
		SqlSession session = DBConnectorFactory.getSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.updateUser(user);

			user = mapper.selectUserById(user.getId());
			session.commit();

		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

		return user;
	}

	/**
	 * 
	 * @param userId
	 */
	public static void deleteUser(@PathParam("id") String userId) {
		SqlSession session = DBConnectorFactory.getSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.deleteUser(userId);

			session.commit();

		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

	}

}
