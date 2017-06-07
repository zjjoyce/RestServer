package com.asiainfo.ocmanager.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.asiainfo.ocmanager.persistence.model.User;

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
		List<User> users = new ArrayList();
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
	public User getUserById(@PathParam("id") int userId) {
		User user = new User();
		return user;
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
