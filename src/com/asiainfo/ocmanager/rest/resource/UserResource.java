package com.asiainfo.ocmanager.rest.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.UserMapper;
import com.asiainfo.ocmanager.persistence.model.User;
import com.asiainfo.ocmanager.persistence.test.DBConnectorFactory;
import com.asiainfo.ocmanager.rest.bean.DeleteResponseBean;

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
	public Response getUsers() {
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

		return Response.ok().entity(users).build();
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
	public Response getUserById(@PathParam("id") String userId) {
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

		
		return Response.ok().entity(user == null ? new User() : user).build();
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
	public Response createUser(User user) {
		SqlSession session = DBConnectorFactory.getSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			String uuid = UUID.randomUUID().toString();
			user.setId(uuid);
			mapper.insertUser(user);

			user = mapper.selectUserById(uuid);
			session.commit();

		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}
		
		return Response.ok().entity(user).build();
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
	public Response updateUser(User user) {
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
		
		return Response.ok().entity(user).build();
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
	public Response deleteUser(@PathParam("id") String userId) {
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
		
		return Response.ok().entity(new DeleteResponseBean("success", 200)).build();
	}

}
