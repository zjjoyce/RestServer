package com.asiainfo.ocmanager.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.UserMapper;
import com.asiainfo.ocmanager.persistence.model.User;
import com.asiainfo.ocmanager.persistence.test.DBConnectorFactory;

/**
 * 
 * @author zhaoyim
 *
 */

@Path("/user")
public class UserResource {

	/**
	 * Get All OCManager users
	 * 
	 * @return user list
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
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
	 * Get the specific user by id
	 * 
	 * @param userId
	 *            user id
	 * @return user
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserById(@PathParam("id") String userId) {
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

		return user == null ? new User() : user;
	}

	/**
	 * Create a new user
	 * 
	 * @param user
	 *            user obj json
	 * @return new user info
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User createUser(User user) {
		return user;
	}

	/**
	 * Update the existing user info
	 * 
	 * @param user
	 *            user obj json
	 * @return updated user info
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User updateUser(User user) {
		return user;
	}

	/**
	 * Delete a user
	 * 
	 * @param userId
	 *            user id
	 */
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteUser(@PathParam("id") int userId) {

	}

}
