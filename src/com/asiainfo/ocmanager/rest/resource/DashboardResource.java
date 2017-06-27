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

import com.asiainfo.ocmanager.persistence.model.Dashboard;
import com.asiainfo.ocmanager.rest.bean.AdapterResponseBean;
import com.asiainfo.ocmanager.rest.resource.utils.DashboardPersistenceWrapper;

/**
 * 
 * @author zhaoyim
 *
 */

@Path("/dashboard")
public class DashboardResource {

	/**
	 * 
	 * @return
	 */
	@GET
	@Path("link")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllDashboardLinks() {
		try {
			List<Dashboard> dashboard = DashboardPersistenceWrapper.getAllLinks();

			return Response.ok().entity(dashboard).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	@GET
	@Path("link/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDashboardLink(@PathParam("name") String name) {
		try {
			Dashboard dashboard = DashboardPersistenceWrapper.getLinkByName(name);

			return Response.ok().entity(dashboard).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}
	}

	/**
	 * 
	 * @param dashboard
	 * @return
	 */
	@POST
	@Path("link")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addDashboardLink(Dashboard dashboard) {
		try {
			DashboardPersistenceWrapper.addLink(dashboard);

			return Response.ok().entity(new AdapterResponseBean("successful", "Add successfully", 200)).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}
	}

	/**
	 * 
	 * @param dashboard
	 * @return
	 */
	@PUT
	@Path("link/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateDashboardLink(Dashboard dashboard, @PathParam("id") int id) {
		try {
			dashboard.setId(id);
			DashboardPersistenceWrapper.updateLink(dashboard);

			return Response.ok().entity(new AdapterResponseBean("successful", "Update successfully", 200)).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("link/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteDashboardLink(@PathParam("id") int id) {
		try {
			DashboardPersistenceWrapper.deleteLink(id);

			return Response.ok().entity(new AdapterResponseBean("successful", "Delete successfully", 200)).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}
	}

}
