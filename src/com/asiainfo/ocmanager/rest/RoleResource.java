package com.asiainfo.ocmanager.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.asiainfo.ocmanager.persistence.model.Role;

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
	public List<Role> getRoles() {
		List<Role> roles = new ArrayList();
		return roles;
	}
}
