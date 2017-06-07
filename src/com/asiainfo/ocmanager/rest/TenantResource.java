package com.asiainfo.ocmanager.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.TenantMapper;
import com.asiainfo.ocmanager.persistence.model.ServiceInstance;
import com.asiainfo.ocmanager.persistence.model.Tenant;
import com.asiainfo.ocmanager.persistence.model.User;
import com.asiainfo.ocmanager.persistence.test.DBConnectorFactory;

/**
 * 
 * @author zhaoyim
 *
 */

@Path("/tenant")
public class TenantResource {

	/**
	 * Get All OCManager tenants
	 * 
	 * @return tenant list
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Tenant> getTenants() {
		List<Tenant> tenants = new ArrayList();
		return tenants;
	}

	/**
	 * Get the specific tenant by id
	 * 
	 * @param tenantId
	 *            tenant id
	 * @return tenant
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Tenant getTenantById(@PathParam("id") int tenantId) {
		Tenant tenant = null;
		SqlSession session = DBConnectorFactory.getSession();
		try {
			TenantMapper mapper = session.getMapper(TenantMapper.class);
			tenant = mapper.selectTenantById(tenantId);
			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

		return tenant;
	}

	/**
	 * Create a new tenant
	 * 
	 * @param tenant
	 *            tenant obj json
	 * @return new tenant info
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Tenant createTenant(Tenant tenant) {
		return tenant;
	}

	/**
	 * Update the existing tenant info
	 * 
	 * @param tenant
	 *            tenant obj json
	 * @return updated tenant info
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Tenant updateTenant(Tenant tenant) {
		return tenant;
	}

	/**
	 * Delete a tenant
	 * 
	 * @param tenantId
	 *            tenant id
	 */
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteTenant(@PathParam("id") int tenantId) {

	}

	/**
	 * Get the users list in the specific tenant
	 * 
	 * @param tenantId
	 *            tenant id
	 * @return user list
	 */
	@GET
	@Path("{id}/user")
	public List<User> getTenantUsers(@PathParam("id") int tenantId) {
		List<User> tenantUsers = new ArrayList();
		return tenantUsers;
	}

	/**
	 * Get the service instance list in the specific tenant
	 * 
	 * @param tenantId
	 *            tenant id
	 * @return service instance list
	 */
	@GET
	@Path("{id}/service/instances")
	public List<ServiceInstance> getTenantServiceInstances(@PathParam("id") int tenantId) {
		List<ServiceInstance> serviceInstances = new ArrayList();
		return serviceInstances;
	}

}
