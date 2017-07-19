package com.asiainfo.ocmanager.rest.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.asiainfo.ocmanager.persistence.model.Tenant;
import com.asiainfo.ocmanager.persistence.model.User;
import com.asiainfo.ocmanager.persistence.model.UserRoleView;
import com.asiainfo.ocmanager.rest.bean.AdapterResponseBean;
import com.asiainfo.ocmanager.rest.constant.Constant;
import com.asiainfo.ocmanager.rest.resource.utils.TenantPersistenceWrapper;
import com.asiainfo.ocmanager.rest.resource.utils.UserPersistenceWrapper;
import com.asiainfo.ocmanager.rest.resource.utils.UserRoleViewPersistenceWrapper;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

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
	public Response createUser(User user, @Context HttpServletRequest request) {
		try {
			String createdUser = request.getHeader("username");
			if (createdUser == null) {
				return Response.status(Status.NOT_FOUND)
						.entity("Can not get the login user, please make sure the login user is pass to adapter.")
						.build();
			}

			List<UserRoleView> urvs = UserRoleViewPersistenceWrapper.getTenantAndRoleBasedOnUserName(createdUser);
			boolean canCreateUser = false;
			for (UserRoleView urv : urvs) {
				if (Constant.canCreateUserList.contains(urv.getRoleName())) {
					canCreateUser = true;
					break;
				}
			}

			if (canCreateUser) {
				user.setCreatedUser(createdUser);
				user = UserPersistenceWrapper.createUser(user);
				return Response.ok().entity(user).build();
			} else {
				return Response.status(Status.BAD_REQUEST)
						.entity(new AdapterResponseBean("create failed",
								"The user " + createdUser + " can not add user, because it is team member role.", 4003))
						.build();
			}

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
	public Response updateUser(User user, @Context HttpServletRequest request) {
		try {
			String loginUser = request.getHeader("username");
			User updateUser = UserPersistenceWrapper.getUserById(user.getId());

			if (updateUser == null) {
				return Response.status(Status.NOT_FOUND).entity("The user " + user + "can not find.").build();
			}

			if (updateUser.getCreatedUser().equals(loginUser)) {
				user = UserPersistenceWrapper.updateUser(user);
				return Response.ok().entity(user).build();
			} else {
				return Response.status(Status.BAD_REQUEST).entity(new AdapterResponseBean("update failed",
						"Can not update the user: " + updateUser.getUsername() + ", it is created by user: "
								+ updateUser.getCreatedUser() + ". please use the created user to update.",
						4004)).build();
			}

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
	public Response deleteUser(@PathParam("id") String userId, @Context HttpServletRequest request) {
		String userName = null;
		try {
			String loginUser = request.getHeader("username");
			User user = UserPersistenceWrapper.getUserById(userId);

			if (user == null) {
				return Response.status(Status.NOT_FOUND).entity("The user " + user + "can not find.").build();
			}

			userName = user.getUsername();

			if (user.getCreatedUser().equals(loginUser)) {
				UserPersistenceWrapper.deleteUser(userId);
			} else {
				return Response.status(Status.BAD_REQUEST)
						.entity(new AdapterResponseBean("delete failed",
								"Can not delete the user: " + user.getUsername() + ", it is created by user: "
										+ user.getCreatedUser() + ". please use the created user to delete.",
								4001))
						.build();
			}
			return Response.ok().entity(new AdapterResponseBean("delete success", userId, 200)).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("deleteUser -> " + e.getMessage());

			if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {

				List<UserRoleView> urvs = UserRoleViewPersistenceWrapper.getTenantAndRoleBasedOnUserName(userName);

				String tenants = "";
				for (UserRoleView t : urvs) {
					tenants = tenants + t.getTenantName() + ",";
				}
				tenants = tenants.substring(0, tenants.length() - 1);
				return Response
						.status(Status.BAD_REQUEST).entity(
								new AdapterResponseBean("delete failed",
										"The user is assign with the tenants: [" + tenants
												+ "], please unassign the user, then try to delete it again.",
										4002))
						.build();
			} else {
				return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
			}
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

	/**
	 *
	 * @param userId
	 * @return
	 */
	@GET
	@Path("id/{id}/all/tenants")
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	public Response getTenantsById(@PathParam("id") String userId) {
		try {
			List<UserRoleView> turs = UserRoleViewPersistenceWrapper.getTenantAndRoleBasedOnUserId(userId);

			List<Tenant> tenantList = UserResource.getTenantsList(turs);

			ArrayList<String> tenantIdList = new ArrayList<String>();
			ArrayList<Tenant> tenants = new ArrayList<Tenant>();
			for (Tenant ten : tenantList) {
				if (!tenantIdList.contains(ten.getId())) {
					tenantIdList.add(ten.getId());
					tenants.add(ten);
				}
			}

			return Response.ok().entity(tenants).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("getTenantsById -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
		}
	}

	/**
	 *
	 * @param userName
	 * @return
	 */
	@GET
	@Path("name/{name}/all/tenants")
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	public Response getTenantsByName(@PathParam("name") String userName) {
		try {
			List<UserRoleView> turs = UserRoleViewPersistenceWrapper.getTenantAndRoleBasedOnUserName(userName);

			List<Tenant> tenantList = UserResource.getTenantsList(turs);

			ArrayList<String> tenantIdList = new ArrayList<String>();
			ArrayList<Tenant> tenants = new ArrayList<Tenant>();
			for (Tenant ten : tenantList) {
				if (!tenantIdList.contains(ten.getId())) {
					tenantIdList.add(ten.getId());
					tenants.add(ten);
				}
			}

			return Response.ok().entity(tenants).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("getTenantsByName -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
		}
	}

	@GET
	@Path("id/{id}/tenant/{tenantId}/children/tenants")
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	public Response getChildrenTenantsByUserIdTenantId(@PathParam("id") String userId,
			@PathParam("tenantId") String tenantId) {
		try {
			List<UserRoleView> turs = UserRoleViewPersistenceWrapper.getTenantAndRoleBasedOnUserId(userId);

			List<Tenant> tenantList = UserResource.getTenantsList(turs);

			ArrayList<String> tenantIdList = new ArrayList<String>();
			ArrayList<Tenant> tenants = new ArrayList<Tenant>();
			for (Tenant ten : tenantList) {
				if (!tenantIdList.contains(ten.getId())) {
					tenantIdList.add(ten.getId());
					tenants.add(ten);
				}
			}

			ArrayList<Tenant> children = new ArrayList<Tenant>();
			for (Tenant t : tenants) {
				if (t.getParentId() != null) {
					if (t.getParentId().equals(tenantId)) {
						children.add(t);
					}
				}
			}

			return Response.ok().entity(children).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("getChildrenTenantsByUserIdTenantId -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
		}
	}

	@GET
	@Path("name/{name}/tenant/{tenantId}/children/tenants")
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	public Response getChildrenTenantsByUserNameTenantId(@PathParam("name") String userName,
			@PathParam("tenantId") String tenantId) {
		try {
			List<UserRoleView> turs = UserRoleViewPersistenceWrapper.getTenantAndRoleBasedOnUserName(userName);

			List<Tenant> tenantList = UserResource.getTenantsList(turs);

			ArrayList<String> tenantIdList = new ArrayList<String>();
			ArrayList<Tenant> tenants = new ArrayList<Tenant>();
			for (Tenant ten : tenantList) {
				if (!tenantIdList.contains(ten.getId())) {
					tenantIdList.add(ten.getId());
					tenants.add(ten);
				}
			}

			ArrayList<Tenant> children = new ArrayList<Tenant>();
			for (Tenant t : tenants) {
				if (t.getParentId() != null) {
					if (t.getParentId().equals(tenantId)) {
						children.add(t);
					}
				}
			}

			return Response.ok().entity(children).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("getChildrenTenantsByUserNameTenantId -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
		}
	}

	private static List<Tenant> getTenantsList(List<UserRoleView> turs) {
		List<Tenant> tenantList = new ArrayList<Tenant>();
		for (UserRoleView tur : turs) {
			Tenant tenant = TenantPersistenceWrapper.getTenantById(tur.getTenantId());
			if (tenant.getLevel() == 1) {
				// level 1 mean it can see all the tenants, because only one
				// root tenant
				tenantList.clear();
				tenantList = TenantPersistenceWrapper.getAllTenants();
				break;
			} else if (tenant.getLevel() == 2) {
				// level 2 add its children, itself and its parent
				List<Tenant> list = TenantPersistenceWrapper.getChildrenTenants(tenant.getId());
				tenantList.addAll(list);
				tenantList.add(tenant);
				Tenant level1 = TenantPersistenceWrapper.getTenantById(tenant.getParentId());
				if (level1 != null) {
					tenantList.add(level1);
				} else {
					logger.debug("getTenantsList level1 -> orphan tenant");
				}
			} else {
				// level 3 add itself, its parent and its parents parent
				tenantList.add(tenant);
				Tenant level2 = TenantPersistenceWrapper.getTenantById(tenant.getParentId());
				if (level2 != null) {
					tenantList.add(level2);
					Tenant level1 = TenantPersistenceWrapper.getTenantById(level2.getParentId());
					if (level1 != null) {
						tenantList.add(level1);
					} else {
						logger.debug("getTenantsList  level2 level1 -> orphan tenant");
					}
				} else {
					logger.debug("getTenantsList level2 -> orphan tenant");
				}
			}
		}
		return tenantList;
	}

}
