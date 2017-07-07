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
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.asiainfo.ocmanager.persistence.model.User;
import com.asiainfo.ocmanager.persistence.model.UserRoleView;
import com.asiainfo.ocmanager.rest.bean.AdapterResponseBean;
import com.asiainfo.ocmanager.rest.resource.utils.UserPersistenceWrapper;
import com.asiainfo.ocmanager.rest.resource.utils.UserRoleViewPersistenceWrapper;

/**
 *
 * @author zhaoyim
 *
 */

@Path("/user")
public class UserResource {

	private static Logger logger = Logger.getLogger(TenantResource.class);

	/**
	 * Get All OCManager users
	 *
	 * @return user list
	 */
	@GET
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	public Response getUsers() {
		try {
			List<User> users = UserPersistenceWrapper.getUsers();
			return Response.ok().entity(users).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("getUsers -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
		}
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
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	public Response getUserById(@PathParam("id") String userId) {
		try {
			User user = UserPersistenceWrapper.getUserById(userId);
			return Response.ok().entity(user == null ? new User() : user).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("getUserById -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
		}
	}

	/**
	 * Create a new user
	 *
	 * @param user
	 *            user obj json
	 * @return new user info
	 */
	@POST
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(User user) {
		try {
			user = UserPersistenceWrapper.createUser(user);
			return Response.ok().entity(user).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("createUser -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
		}
	}

	/**
	 * Update the existing user info
	 *
	 * @param user
	 *            user obj json
	 * @return updated user info
	 */
	@PUT
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(User user) {
		try {
			user = UserPersistenceWrapper.updateUser(user);
			return Response.ok().entity(user).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("updateUser -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
		}
	}

	/**
	 * Delete a user
	 *
	 * @param userId
	 *            user id
	 */
	@DELETE
	@Path("{id}")
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	public Response deleteUser(@PathParam("id") String userId) {
		try {
			UserPersistenceWrapper.deleteUser(userId);
			return Response.ok().entity(new AdapterResponseBean("delete success", userId, 200)).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("deleteUser -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
		}
	}

	/**
	 *
	 * @param userId
	 * @return
	 */
	@GET
	@Path("id/{id}/tenants")
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	public Response getTenantAndRoleById(@PathParam("id") String userId) {
		try {
			List<UserRoleView> turs = UserRoleViewPersistenceWrapper.getTenantAndRoleBasedOnUserId(userId);
			return Response.ok().entity(turs).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("getTenantAndRoleById -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
		}
	}

	@GET
	@Path("name/{name}/tenants")
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	public Response getTenantAndRoleByName(@PathParam("name") String userName) {
		try {
			List<UserRoleView> turs = UserRoleViewPersistenceWrapper.getTenantAndRoleBasedOnUserName(userName);
			return Response.ok().entity(turs).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("getTenantAndRoleByName -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
		}
	}
}
