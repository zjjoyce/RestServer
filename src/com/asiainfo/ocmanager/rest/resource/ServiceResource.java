package com.asiainfo.ocmanager.rest.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.ServiceMapper;
import com.asiainfo.ocmanager.persistence.model.Service;
import com.asiainfo.ocmanager.persistence.test.DBConnectorFactory;
import com.asiainfo.ocmanager.rest.resource.utils.ServicePersistenceWrapper;

/**
 * 
 * @author zhaoyim
 *
 */
@Path("/service")
public class ServiceResource {

	/**
	 * Get All OCManager services
	 * 
	 * @return service list
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getServices() {

		List<Service> services = ServicePersistenceWrapper.getAllServices();

		return Response.ok().entity(services).build();
	}

	/**
	 * Get service by id
	 * 
	 * @return service
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getServiceById(@PathParam("id") String serviceId) {

		Service service = ServicePersistenceWrapper.getServiceById(serviceId);

		return Response.ok().entity(service == null ? new Service() : service).build();
	}

}
