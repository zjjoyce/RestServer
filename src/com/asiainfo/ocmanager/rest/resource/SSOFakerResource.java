package com.asiainfo.ocmanager.rest.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.asiainfo.ocmanager.rest.bean.SSOFakerUserBean;

/**
 * 
 * @author zhaoyim
 *
 */

@Path("/sso")
public class SSOFakerResource {

	/**
	 * Get the user name for web which CAS proxy set into the header
	 * @param request
	 * @return
	 */
	@GET
	@Path("user")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserName(@Context HttpServletRequest request) {
		String ssoCurrentUser = request.getHeader("ssouser");

		if (ssoCurrentUser == null) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Can not get sso user information, please make sure the user info is set.").build();
		} else {
			return Response.ok().entity(new SSOFakerUserBean(ssoCurrentUser)).build();
		}
	}

}
