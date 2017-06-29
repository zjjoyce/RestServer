package com.asiainfo.ocmanager.rest.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.asiainfo.ocmanager.persistence.model.Role;
import com.asiainfo.ocmanager.rest.resource.utils.RolePersistenceWrapper;

/**
 * 
 * @author zhaoyim
 *
 */

@Path("/role")
public class RoleResource {

	private static Logger logger = Logger.getLogger(TenantResource.class);
	
	/**
	 * Get All OCManager roles
	 * 
	 * @return role list
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoles() {
		try {
			List<Role> roles = RolePersistenceWrapper.getRoles();

			return Response.ok().entity(roles).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}
	}

}
