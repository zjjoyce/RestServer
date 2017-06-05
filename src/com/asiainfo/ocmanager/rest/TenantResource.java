package com.asiainfo.ocmanager.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tenant")
public class TenantResource {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getTenants() {
		return "Hello World!";
	}
}
