package com.asiainfo.ocmanager.rest.resource;

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

import com.asiainfo.ocmanager.auth.PageAuth;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.asiainfo.ocmanager.persistence.model.ServiceInstance;
import com.asiainfo.ocmanager.persistence.model.Tenant;
import com.asiainfo.ocmanager.persistence.model.TenantUserRoleAssignment;
import com.asiainfo.ocmanager.persistence.model.UserRoleView;
import com.asiainfo.ocmanager.rest.bean.AdapterResponseBean;
import com.asiainfo.ocmanager.rest.constant.Constant;
import com.asiainfo.ocmanager.rest.resource.utils.ServiceInstancePersistenceWrapper;
import com.asiainfo.ocmanager.rest.resource.utils.TURAssignmentPersistenceWrapper;
import com.asiainfo.ocmanager.rest.resource.utils.TenantPersistenceWrapper;
import com.asiainfo.ocmanager.rest.resource.utils.UserRoleViewPersistenceWrapper;
import com.asiainfo.ocmanager.rest.utils.DFPropertiesFactory;
import com.asiainfo.ocmanager.rest.utils.SSLSocketIgnoreCA;
import com.asiainfo.ocmanager.rest.utils.UUIDFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
	public Response getAllTenants() {
		List<Tenant> tenants = TenantPersistenceWrapper.getAllTenants();
		return Response.ok().entity(tenants).build();
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
	public Response getTenantById(@PathParam("id") String tenantId) {
		Tenant tenant = TenantPersistenceWrapper.getTenantById(tenantId);
		return Response.ok().entity(tenant).build();
	}

	/**
	 * Get the child tenants
	 *
	 * @param tenantId
	 *            tenant id
	 * @return tenant list
	 */
	@GET
	@Path("{id}/children")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChildrenTenants(@PathParam("id") String parentTenantId) {
		List<Tenant> tenants = TenantPersistenceWrapper.getChildrenTenants(parentTenantId);
		return Response.ok().entity(tenants).build();
	}

	/**
	 * Get the users list in the specific tenant
	 *
	 * @param tenantId
	 *            tenant id
	 * @return user list
	 */
	@GET
	@Path("{id}/users")
	public Response getTenantUsers(@PathParam("id") String tenantId, @Context HttpServletRequest request) {
		// TODO how to get the request header info following comment code can do
		// this
		// Enumeration<String> aa = request.getHeaderNames();
		List<UserRoleView> usersRoles = UserRoleViewPersistenceWrapper.getUsersInTenant(tenantId);
		return Response.ok().entity(usersRoles).build();
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTenantServiceInstances(@PathParam("id") String tenantId) {

		List<ServiceInstance> serviceInstances = ServiceInstancePersistenceWrapper
				.getServiceInstancesInTenant(tenantId);
		return Response.ok().entity(serviceInstances).build();

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
	public Response createTenant(Tenant tenant, @Context HttpServletRequest request) {

		if (tenant.getName() == null) {
			return Response.status(Status.BAD_REQUEST).entity("input format is not correct").build();
		}
		// mapping DF tenant name with adapter tenant id
		tenant.setId(UUIDFactory.getUUID());

		try {
			String url = DFPropertiesFactory.getDFProperties().get(Constant.DATAFACTORY_URL);
			String token = DFPropertiesFactory.getDFProperties().get(Constant.DATAFACTORY_TOKEN);
			String dfRestUrl = url + "/oapi/v1/projectrequests";

			JsonObject jsonObj1 = new JsonObject();
			jsonObj1.addProperty("apiVersion", "v1");
			jsonObj1.addProperty("kind", "ProjectRequest");
			// mapping DF tenant display name with adapter tenant name
			jsonObj1.addProperty("displayName", tenant.getName());
			if (tenant.getDescription() != null) {
				jsonObj1.addProperty("description", tenant.getDescription());
			}

			JsonObject jsonObj2 = new JsonObject();
			jsonObj2.addProperty("name", tenant.getId());
			jsonObj1.add("metadata", jsonObj2);
			String reqBody = jsonObj1.toString();

			SSLConnectionSocketFactory sslsf = SSLSocketIgnoreCA.createSSLSocketFactory();

			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			try {
				HttpPost httpPost = new HttpPost(dfRestUrl);
				httpPost.addHeader("Content-type", "application/json");
				httpPost.addHeader("Authorization", "bearer " + token);

				StringEntity se = new StringEntity(reqBody);
				se.setContentType("application/json");
				httpPost.setEntity(se);

				CloseableHttpResponse response2 = httpclient.execute(httpPost);

				try {
					int statusCode = response2.getStatusLine().getStatusCode();

					if (statusCode == 201) {
						TenantPersistenceWrapper.createTenant(tenant);
					}
					String bodyStr = EntityUtils.toString(response2.getEntity());

					return Response.ok().entity(bodyStr).build();
				} finally {
					response2.close();
				}
			} finally {
				httpclient.close();
			}
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}
	}

	/**
	 * Create a service instance in specific tenant
	 *
	 * @param
	 * @return
	 */
	@POST
	@Path("{id}/service/instance")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createServiceInstanceInTenant(@PathParam("id") String tenantId, String reqBodyStr) {

		try {
			String url = DFPropertiesFactory.getDFProperties().get(Constant.DATAFACTORY_URL);
			String token = DFPropertiesFactory.getDFProperties().get(Constant.DATAFACTORY_TOKEN);
			String dfRestUrl = url + "/oapi/v1/namespaces/" + tenantId + "/backingserviceinstances";

			// parse the req body make sure it is json
			JsonElement reqBodyJson = new JsonParser().parse(reqBodyStr);
			SSLConnectionSocketFactory sslsf = SSLSocketIgnoreCA.createSSLSocketFactory();

			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			try {
				HttpPost httpPost = new HttpPost(dfRestUrl);
				httpPost.addHeader("Content-type", "application/json");
				httpPost.addHeader("Authorization", "bearer " + token);

				StringEntity se = new StringEntity(reqBodyJson.toString());
				se.setContentType("application/json");
				httpPost.setEntity(se);

				CloseableHttpResponse response2 = httpclient.execute(httpPost);

				try {
					int statusCode = response2.getStatusLine().getStatusCode();
					String bodyStr = EntityUtils.toString(response2.getEntity());
					if (statusCode == 201) {
						JsonElement resBodyJson = new JsonParser().parse(bodyStr);
						JsonObject resBodyJsonObj = resBodyJson.getAsJsonObject();
						ServiceInstance serviceInstance = new ServiceInstance();

						serviceInstance.setId(resBodyJsonObj.getAsJsonObject("metadata").get("uid").getAsString());
						serviceInstance
								.setInstanceName(resBodyJsonObj.getAsJsonObject("metadata").get("name").getAsString());
						serviceInstance.setTenantId(tenantId);
						serviceInstance.setServiceTypeId(resBodyJsonObj.getAsJsonObject("spec")
								.getAsJsonObject("provisioning").get("backingservice_spec_id").getAsString());
						serviceInstance.setServiceTypeName(resBodyJsonObj.getAsJsonObject("spec")
								.getAsJsonObject("provisioning").get("backingservice_name").getAsString());

						if ((resBodyJsonObj.getAsJsonObject("spec").getAsJsonObject("provisioning").get("parameters")
								.isJsonNull())) {
							// TODO should get df service quota
						} else {
							// parameters are a json format should use to string
							serviceInstance.setQuota(resBodyJsonObj.getAsJsonObject("spec")
									.getAsJsonObject("provisioning").get("parameters").toString());
						}

						ServiceInstancePersistenceWrapper.createServiceInstance(serviceInstance);
					}

					return Response.ok().entity(bodyStr).build();
				} finally {
					response2.close();
				}
			} finally {
				httpclient.close();
			}
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}

	}

	/**
	 * Delete a service instance in specific tenant
	 *
	 * @param tenantId
	 * @param instanceName
	 * @return
	 */
	@DELETE
	@Path("{id}/service/instance/{instanceName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteServiceInstanceInTenant(@PathParam("id") String tenantId,
			@PathParam("instanceName") String instanceName) {

		try {
			String url = DFPropertiesFactory.getDFProperties().get(Constant.DATAFACTORY_URL);
			String token = DFPropertiesFactory.getDFProperties().get(Constant.DATAFACTORY_TOKEN);
			String dfRestUrl = url + "/oapi/v1/namespaces/" + tenantId + "/backingserviceinstances/" + instanceName;

			SSLConnectionSocketFactory sslsf = SSLSocketIgnoreCA.createSSLSocketFactory();

			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			try {
				HttpDelete httpDelete = new HttpDelete(dfRestUrl);
				httpDelete.addHeader("Content-type", "application/json");
				httpDelete.addHeader("Authorization", "bearer " + token);

				CloseableHttpResponse response1 = httpclient.execute(httpDelete);

				try {
					int statusCode = response1.getStatusLine().getStatusCode();
					if (statusCode == 200) {
						ServiceInstancePersistenceWrapper.deleteServiceInstance(tenantId, instanceName);
					}
					String bodyStr = EntityUtils.toString(response1.getEntity());

					return Response.ok().entity(bodyStr).build();
				} finally {
					response1.close();
				}
			} finally {
				httpclient.close();
			}
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}

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
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteTenant(@PathParam("id") String tenantId) {
		try {
			String url = DFPropertiesFactory.getDFProperties().get(Constant.DATAFACTORY_URL);
			String token = DFPropertiesFactory.getDFProperties().get(Constant.DATAFACTORY_TOKEN);
			String dfRestUrl = url + "/oapi/v1/projects/" + tenantId;

			SSLConnectionSocketFactory sslsf = SSLSocketIgnoreCA.createSSLSocketFactory();

			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			try {
				HttpDelete httpDelete = new HttpDelete(dfRestUrl);
				httpDelete.addHeader("Content-type", "application/json");
				httpDelete.addHeader("Authorization", "bearer " + token);

				CloseableHttpResponse response1 = httpclient.execute(httpDelete);

				try {
					int statusCode = response1.getStatusLine().getStatusCode();
					if (statusCode == 200) {
						TenantPersistenceWrapper.deleteTenant(tenantId);
					}
					String bodyStr = EntityUtils.toString(response1.getEntity());

					return Response.ok().entity(bodyStr).build();
				} finally {
					response1.close();
				}
			} finally {
				httpclient.close();
			}
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}
	}

	/**
	 * Assign role to user in tenant
	 *
	 * @param tenantId
	 * @param assignment
	 * @return
	 */
	@POST
  @PageAuth(requiredPermission = "Grant")
	@Path("{id}/user/role/assignment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response assignRoleToUserInTenant(@PathParam("id") String tenantId, TenantUserRoleAssignment assignment) {
		// assgin to the input tenant
		assignment.setTenantId(tenantId);

		assignment = TURAssignmentPersistenceWrapper.assignRoleToUserInTenant(assignment);

		// assign to the child tenants
		// List<Tenant> children =
		// TenantPersistenceWrapper.getChildrenTenants(tenantId);
		// if (children.size() != 0) {
		// for(Tenant child: children){
		// TenantUserRoleAssignment childAssignment = new
		// TenantUserRoleAssignment();
		// childAssignment.setTenantId(child.getId());
		// childAssignment.setUserId(assignment.getUserId());
		// childAssignment.setRoleId(assignment.getRoleId());
		// TURAssignmentPersistenceWrapper.assignRoleToUserInTenant(childAssignment);
		// }
		// }


		return Response.ok().entity(assignment).build();
	}

	/**
	 * Update user role in tenant
	 *
	 * @param tenantId
	 * @param assignment
	 * @return
	 */
	@PUT
	@Path("{id}/user/role/assignment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateRoleToUserInTenant(@PathParam("id") String tenantId, TenantUserRoleAssignment assignment) {
		assignment.setTenantId(tenantId);
		assignment = TURAssignmentPersistenceWrapper.updateRoleToUserInTenant(assignment);
		return Response.ok().entity(assignment).build();
	}

	/**
	 * Unassign role to user in tenant
	 *
	 * @param tenantId
	 * @param userId
	 * @return
	 */
	@DELETE
	@Path("{id}/user/{userId}/role/assignment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response unassignRoleFromUserInTenant(@PathParam("id") String tenantId, @PathParam("userId") String userId) {
		TURAssignmentPersistenceWrapper.unassignRoleFromUserInTenant(tenantId, userId);
		return Response.ok().entity(new AdapterResponseBean("delete success", userId, 200)).build();
	}

}
