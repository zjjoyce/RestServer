package com.asiainfo.ocmanager.rest.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asiainfo.ocmanager.persistence.model.Role;
import com.asiainfo.ocmanager.rest.resource.utils.RolePersistenceWrapper;

/**
 * 
 * @author zhaoyim
 *
 */

@Path("/role")
public class RoleResource {

	/**
	 * Get All OCManager roles
	 * 
	 * @return role list
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoles() {
		List<Role> roles = RolePersistenceWrapper.getRoles();

		return Response.ok().entity(roles).build();
	}

}
