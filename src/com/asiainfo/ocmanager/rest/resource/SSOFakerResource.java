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
	 * 
	 * @param request
	 * @return
	 */
	@GET
	@Path("user")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserName(@Context HttpServletRequest request) {
		String http_x_proxy_cas_loginname = request.getHeader("http-x-proxy-cas-loginname");
		String http_x_proxy_cas_username = request.getHeader("http-x-proxy-cas-username");
		String http_x_proxy_cas_email = request.getHeader("http-x-proxy-cas-email");
		http_x_proxy_cas_email = http_x_proxy_cas_email == null ? "" : http_x_proxy_cas_email;
		String http_x_proxy_cas_userid = request.getHeader("http-x-proxy-cas-userid");
		http_x_proxy_cas_userid = http_x_proxy_cas_userid == null ? "" : http_x_proxy_cas_userid;
		String http_x_proxy_cas_mobile = request.getHeader("http-x-proxy-cas-mobile");
		http_x_proxy_cas_mobile = http_x_proxy_cas_mobile == null ? "" : http_x_proxy_cas_mobile;

		// make sure the user name is set
		if (http_x_proxy_cas_loginname == null || http_x_proxy_cas_username == null) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Can not get sso user information, please make sure the user info is set.").build();
		} else {
			return Response.ok().entity(new SSOFakerUserBean(http_x_proxy_cas_loginname, http_x_proxy_cas_username,
					http_x_proxy_cas_email, http_x_proxy_cas_userid, http_x_proxy_cas_mobile)).build();
		}
	}

}
