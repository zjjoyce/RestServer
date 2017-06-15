package com.asiainfo.ocmanager.rest.resource;

import java.util.List;

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

import com.asiainfo.ocmanager.persistence.model.User;
import com.asiainfo.ocmanager.rest.bean.AdapterResponseBean;
import com.asiainfo.ocmanager.rest.resource.utils.UserPersistenceWrapper;

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
		List<User> users = UserPersistenceWrapper.getUsers();
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
		User user = UserPersistenceWrapper.getUserById(userId);
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
		user = UserPersistenceWrapper.createUser(user);
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
		user = UserPersistenceWrapper.updateUser(user);
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
		UserPersistenceWrapper.deleteUser(userId);
		return Response.ok().entity(new AdapterResponseBean("delete success", userId, 200)).build();
	}

}
