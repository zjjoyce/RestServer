package com.asiainfo.ocmanager.rest.resource;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

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

import com.asiainfo.ocmanager.dacp.model.DacpResult;
import com.asiainfo.ocmanager.dacp.model.Team;
import com.asiainfo.ocmanager.dacp.model.UserInfo;
import com.asiainfo.ocmanager.dacp.service.TeamWrapper;
import com.asiainfo.ocmanager.dacp.service.UserWrapper;
import com.asiainfo.ocmanager.persistence.model.*;
import com.asiainfo.ocmanager.rest.resource.quotaUtils.restClient;
import com.google.gson.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.asiainfo.ocmanager.rest.bean.AdapterResponseBean;
import com.asiainfo.ocmanager.rest.constant.Constant;
import com.asiainfo.ocmanager.rest.resource.utils.ServiceInstancePersistenceWrapper;
import com.asiainfo.ocmanager.rest.resource.utils.ServiceRolePermissionWrapper;
import com.asiainfo.ocmanager.rest.resource.utils.TURAssignmentPersistenceWrapper;
import com.asiainfo.ocmanager.rest.resource.utils.TenantPersistenceWrapper;
import com.asiainfo.ocmanager.rest.resource.utils.UserPersistenceWrapper;
import com.asiainfo.ocmanager.rest.resource.utils.UserRoleViewPersistenceWrapper;
import com.asiainfo.ocmanager.rest.utils.DFPropertiesFoundry;
import com.asiainfo.ocmanager.rest.utils.SSLSocketIgnoreCA;
import com.asiainfo.ocmanager.rest.resource.utils.TenantPersistenceWrapper;
import org.codehaus.jettison.json.JSONArray;

/**
 *
 * @author zhaoyim
 *
 */

@Path("/tenant")
public class TenantResource {

	private static Logger logger = Logger.getLogger(TenantResource.class);

	/**
	 * Get All OCManager tenants
	 *
	 * @return tenant list
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTenants() {
		try {
			List<Tenant> tenants = TenantPersistenceWrapper.getAllTenants();
			return Response.ok().entity(tenants).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}
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
		try {
			Tenant tenant = TenantPersistenceWrapper.getTenantById(tenantId);
			return Response.ok().entity(tenant).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}
	}

	/**
	 * Get the child tenants
	 *
	 * @param parentTenantId
	 *            tenant id
	 * @return tenant list
	 */
	@GET
	@Path("{id}/children")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChildrenTenants(@PathParam("id") String parentTenantId) {
		try {
			List<Tenant> tenants = TenantPersistenceWrapper.getChildrenTenants(parentTenantId);
			return Response.ok().entity(tenants).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}
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
	public Response getTenantUsers(@PathParam("id") String tenantId) {
		try {
			List<UserRoleView> usersRoles = UserRoleViewPersistenceWrapper.getUsersInTenant(tenantId);
			return Response.ok().entity(usersRoles).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}
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
		try {
			List<ServiceInstance> serviceInstances = ServiceInstancePersistenceWrapper
					.getServiceInstancesInTenant(tenantId);
			return Response.ok().entity(serviceInstances).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}
	}

	/**
	 * get the tenant service instance info which include the access info
	 *
	 * @param tenantId
	 * @param InstanceName
	 * @return
	 */
	@GET
	@Path("{tenantId}/service/instance/{InstanceName}/access/info")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTenantServiceInstanceAccessInfo(@PathParam("tenantId") String tenantId,
			@PathParam("InstanceName") String InstanceName) {
		try {
			return Response.ok().entity(TenantResource.getTenantServiceInstancesFromDf(tenantId, InstanceName)).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}

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

		if (tenant.getName() == null || tenant.getId() == null) {
			return Response.status(Status.BAD_REQUEST).entity("input format is not correct").build();
		}

		try {
			List<Tenant> allRootTenants = TenantPersistenceWrapper.getAllRootTenants();
			List<String> allRootTenantsId = new ArrayList<String>();
			for (Tenant t : allRootTenants) {
				allRootTenantsId.add(t.getId());
			}

			String url = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_URL);
			String token = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_TOKEN);
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
						// very ugly code here hard code 3 level for the zhong
						// xin
						if (tenant.getParentId() == null) {
							tenant.setLevel(1);
						} else if (allRootTenantsId.contains(tenant.getParentId())) {
							tenant.setLevel(2);
						} else {
							tenant.setLevel(3);
						}
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
			// system out the exception into the console log
			logger.info(e.getMessage());
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
			String url = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_URL);
			String token = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_TOKEN);
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
					ServiceInstance serviceInstance = new ServiceInstance();
					if (statusCode == 201) {
						JsonElement resBodyJson = new JsonParser().parse(bodyStr);
						JsonObject resBodyJsonObj = resBodyJson.getAsJsonObject();

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

						// insert the service instance into the adapter DB
						ServiceInstancePersistenceWrapper.createServiceInstance(serviceInstance);

						// get the just now created instance info
						String getInstanceResBody = TenantResource.getTenantServiceInstancesFromDf(tenantId,
								serviceInstance.getInstanceName());

						JsonElement serviceInstanceJson = new JsonParser().parse(getInstanceResBody);
						// get the service type
						String serviceName = serviceInstanceJson.getAsJsonObject().getAsJsonObject("spec")
								.getAsJsonObject("provisioning").get("backingservice_name").getAsString();
						// get status phase
						String phase = serviceInstanceJson.getAsJsonObject().getAsJsonObject("status").get("phase")
								.getAsString();
						// get the service instance name
						String instanceName = serviceInstanceJson.getAsJsonObject().getAsJsonObject("metadata")
								.get("name").getAsString();

						// only the OCDP services need to wait to assign the
						// permission
						if (Constant.list.contains(serviceName.toLowerCase())) {

							// loop to wait the instance status.phase change to
							// Unbound
							// if the status.phase is Provisioning the update
							// will failed, so need to wait
							while (phase.equals("Provisioning")) {
								Thread.sleep(10000);
								// get the instance info again
								getInstanceResBody = TenantResource.getTenantServiceInstancesFromDf(tenantId,
										serviceInstance.getInstanceName());
								serviceInstanceJson = new JsonParser().parse(getInstanceResBody);
								serviceName = serviceInstanceJson.getAsJsonObject().getAsJsonObject("spec")
										.getAsJsonObject("provisioning").get("backingservice_name").getAsString();
								phase = serviceInstanceJson.getAsJsonObject().getAsJsonObject("status").get("phase")
										.getAsString();
							}

							// get all the users under the tenant
							List<UserRoleView> users = UserRoleViewPersistenceWrapper.getUsersInTenant(tenantId);

							// loop the users to update the created instance
							for (UserRoleView u : users) {
								// get the instance response body, which will
								// pass to update
								JsonElement OCDPServiceInstanceJson = new JsonParser().parse(getInstanceResBody);
								// get the provisioning json
								JsonObject provisioning = OCDPServiceInstanceJson.getAsJsonObject()
										.getAsJsonObject("spec").getAsJsonObject("provisioning");
								// add the user name to the parameters for
								// update
								provisioning.getAsJsonObject("parameters").addProperty("user_name", u.getUserName());

								// get the service permission based on the
								// service name and role
								ServiceRolePermission permission = ServiceRolePermissionWrapper
										.getServicePermissionByRoleId(serviceName, u.getRoleId());

								if (permission == null) {
									permission = new ServiceRolePermission();
									permission.setServicePermission("");
								}

								// add the accesses fields into the request body
								provisioning.getAsJsonObject("parameters").addProperty("accesses",
										permission.getServicePermission());

								// add the patch Updating into the request body
								JsonObject status = OCDPServiceInstanceJson.getAsJsonObject().getAsJsonObject("status");
								status.addProperty("patch", "Updating");

								AdapterResponseBean updateRes = TenantResource.updateTenantServiceInstanceInDf(tenantId,
										instanceName, OCDPServiceInstanceJson.toString());

								if (updateRes.getResCodel() == 200) {
									logger.info("service updated");
								}

								// call the df binding to generate the OCDP
								// service credentials
								AdapterResponseBean bindingRes = TenantResource.generateOCDPServiceCredentials(tenantId,
										instanceName, u.getUserName());
								if (bindingRes.getResCodel() == 200) {
									logger.info("service binding");
								}

							}

						}

					}

					return Response.ok().entity(bodyStr).build();
				} finally {
					response2.close();
				}
			} finally {
				httpclient.close();
			}
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}

	}

	/**
	 * Update a service instance in specific tenant
	 *
	 * @param tenantId
	 * @param instanceName
	 * @param reqBodyStr
	 * @return
	 */
	@PUT
	@Path("{id}/service/instance/{instanceName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateServiceInstanceInTenant(@PathParam("id") String tenantId,
			@PathParam("instanceName") String instanceName, String reqBodyStr) {

		try {
			AdapterResponseBean responseBean = TenantResource.updateTenantServiceInstanceInDf(tenantId, instanceName,
					reqBodyStr);
			return Response.ok().entity(responseBean.getMessage()).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info(e.getMessage());
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
			String url = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_URL);
			String token = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_TOKEN);
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
			// system out the exception into the console log
			logger.info(e.getMessage());
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
	@Deprecated
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
			String url = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_URL);
			String token = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_TOKEN);
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
			// system out the exception into the console log
			logger.info(e.getMessage());
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
	@Path("{id}/user/role/assignment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response assignRoleToUserInTenant(@PathParam("id") String tenantId, TenantUserRoleAssignment assignment) {

		try {
			// assgin to the input tenant
			assignment.setTenantId(tenantId);

			// get all service instances from df
			String allServiceInstances = TenantResource.getTenantAllServiceInstancesFromDf(tenantId);
			JsonElement allServiceInstancesJson = new JsonParser().parse(allServiceInstances);

			JsonArray allServiceInstancesArray = allServiceInstancesJson.getAsJsonObject().getAsJsonArray("items");
			for (int i = 0; i < allServiceInstancesArray.size(); i++) {
				JsonObject instance = allServiceInstancesArray.get(i).getAsJsonObject();
				// get service name
				String serviceName = instance.getAsJsonObject("spec").getAsJsonObject("provisioning")
						.get("backingservice_name").getAsString();

				String phase = instance.getAsJsonObject("status").get("phase").getAsString();
				if (!phase.equals("Provisioning")) {
					// Because the Provisioning will make the update failed
					if (Constant.list.contains(serviceName.toLowerCase())) {
						// get service instance name
						String instanceName = instance.getAsJsonObject("metadata").get("name").getAsString();
						String OCDPServiceInstanceStr = TenantResource.getTenantServiceInstancesFromDf(tenantId,
								instanceName);

						// get the service permission based on the service name
						// and
						// role
						ServiceRolePermission permission = ServiceRolePermissionWrapper
								.getServicePermissionByRoleId(serviceName, assignment.getRoleId());

						if (permission == null) {
							permission = new ServiceRolePermission();
							permission.setServicePermission("");
						}

						// parse the update request body based on the get
						// service
						// instance
						// by id response body
						JsonElement OCDPServiceInstanceJson = new JsonParser().parse(OCDPServiceInstanceStr);
						// get the provisioning json
						JsonObject provisioning = OCDPServiceInstanceJson.getAsJsonObject().getAsJsonObject("spec")
								.getAsJsonObject("provisioning");
						// add the user name to the parameters for update
						String userName = UserPersistenceWrapper.getUserById(assignment.getUserId()).getUsername();
						provisioning.getAsJsonObject("parameters").addProperty("user_name", userName);

						// add the accesses fields into the request body
						provisioning.getAsJsonObject("parameters").addProperty("accesses",
								permission.getServicePermission());

						// add the patch Updating into the request body
						JsonObject status = OCDPServiceInstanceJson.getAsJsonObject().getAsJsonObject("status");
						status.addProperty("patch", "Updating");

						AdapterResponseBean updateRes = TenantResource.updateTenantServiceInstanceInDf(tenantId,
								instanceName, OCDPServiceInstanceJson.toString());

						if (updateRes.getResCodel() == 200) {
							logger.info("service updated");
						}

						// call the df binding to generate the OCDP service
						// credentials
						AdapterResponseBean bindingRes = TenantResource.generateOCDPServiceCredentials(tenantId,
								instanceName, userName);
						if (bindingRes.getResCodel() == 200) {
							logger.info("service binding");
						}
					}
				}
			}

			assignment = TURAssignmentPersistenceWrapper.assignRoleToUserInTenant(assignment);

            // post to dacp rest interface

            // get team param
            Team team = new Team("",0,1,"ON");
            team = TeamWrapper.getTeamFromTenant(tenantId);
            //get userinfo
            List<UserInfo> userInfos = UserWrapper.getUserInfoFromUserRoleView(tenantId);
            // add userinfo and team info to info jsonObject
            Gson gson = new Gson();
            String teamStr = gson.toJson(team);
            String userInfoStr = gson.toJson(userInfos);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("userInfo",userInfoStr);
            jsonObject.addProperty("team",teamStr);

            String infoStr = gson.toJson(jsonObject);
            Map info = new HashMap<String,String>();
            info.put("info",infoStr);
            // post to rest api
            String restResult = restClient.post("http://10.247.33.80:8080/dacp/dps/tenant/all",info);
            DacpResult dacpResult = gson.fromJson(restResult,DacpResult.class);
            String result = dacpResult.getResult();
            // log the result of sync process
            if(result.equals("true")){
                logger.info("dacp is ok");
            }else{
                logger.error("sync dacp is failed,error message is:" + dacpResult.getMessage());
            }
        // end post to dacp rest interface


			return Response.ok().entity(assignment).build();

		} catch (Exception e) {
			// system out the exception into the console log
			logger.info(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}

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

		try {
			// assgin to the input tenant
			assignment.setTenantId(tenantId);

			// get all service instances from df
			String allServiceInstances = TenantResource.getTenantAllServiceInstancesFromDf(tenantId);
			JsonElement allServiceInstancesJson = new JsonParser().parse(allServiceInstances);

			JsonArray allServiceInstancesArray = allServiceInstancesJson.getAsJsonObject().getAsJsonArray("items");
			for (int i = 0; i < allServiceInstancesArray.size(); i++) {
				JsonObject instance = allServiceInstancesArray.get(i).getAsJsonObject();
				// get service name
				String serviceName = instance.getAsJsonObject("spec").getAsJsonObject("provisioning")
						.get("backingservice_name").getAsString();

				if (Constant.list.contains(serviceName.toLowerCase())) {
					// get service instance name
					String instanceName = instance.getAsJsonObject("metadata").get("name").getAsString();
					String OCDPServiceInstanceStr = TenantResource.getTenantServiceInstancesFromDf(tenantId,
							instanceName);

					// get the service permission based on the service name and
					// role
					ServiceRolePermission permission = ServiceRolePermissionWrapper
							.getServicePermissionByRoleId(serviceName, assignment.getRoleId());

					if (permission == null) {
						permission = new ServiceRolePermission();
						permission.setServicePermission("");
					}

					// parse the update request body based on the get service
					// instance
					// by id response body
					JsonElement OCDPServiceInstanceJson = new JsonParser().parse(OCDPServiceInstanceStr);
					// get the provisioning json
					JsonObject provisioning = OCDPServiceInstanceJson.getAsJsonObject().getAsJsonObject("spec")
							.getAsJsonObject("provisioning");
					// add the user name to the parameters for update
					String userName = UserPersistenceWrapper.getUserById(assignment.getUserId()).getUsername();
					provisioning.getAsJsonObject("parameters").addProperty("user_name", userName);

					// add the accesses fields into the request body
					provisioning.getAsJsonObject("parameters").addProperty("accesses",
							permission.getServicePermission());

					// add the patch Updating into the request body
					JsonObject status = OCDPServiceInstanceJson.getAsJsonObject().getAsJsonObject("status");
					status.addProperty("patch", "Updating");

					AdapterResponseBean updateRes = TenantResource.updateTenantServiceInstanceInDf(tenantId,
							instanceName, OCDPServiceInstanceJson.toString());

					if (updateRes.getResCodel() == 200) {
						logger.info("service updated");
					}

					// call the df binding to generate the OCDP service
					// credentials
					AdapterResponseBean bindingRes = TenantResource.generateOCDPServiceCredentials(tenantId,
							instanceName, userName);
					if (bindingRes.getResCodel() == 200) {
						logger.info("service binding");
					}

				}
			}

			assignment = TURAssignmentPersistenceWrapper.updateRoleToUserInTenant(assignment);

			return Response.ok().entity(assignment).build();

		} catch (Exception e) {
			// system out the exception into the console log
			logger.info(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}

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

		try {
			// get all service instances from df
			String allServiceInstances = TenantResource.getTenantAllServiceInstancesFromDf(tenantId);
			JsonElement allServiceInstancesJson = new JsonParser().parse(allServiceInstances);

			JsonArray allServiceInstancesArray = allServiceInstancesJson.getAsJsonObject().getAsJsonArray("items");
			for (int i = 0; i < allServiceInstancesArray.size(); i++) {
				JsonObject instance = allServiceInstancesArray.get(i).getAsJsonObject();
				// get service name
				String serviceName = instance.getAsJsonObject("spec").getAsJsonObject("provisioning")
						.get("backingservice_name").getAsString();

				if (Constant.list.contains(serviceName.toLowerCase())) {
					// get service instance name
					String instanceName = instance.getAsJsonObject("metadata").get("name").getAsString();

					// the unassign df and service broker only use the unbinding
					// to do
					// so here not need to call update
					AdapterResponseBean bindingRes = TenantResource.removeOCDPServiceCredentials(tenantId, instanceName,
							UserPersistenceWrapper.getUserById(userId).getUsername());

					if (bindingRes.getResCodel() == 200) {
						logger.info("unassigned the permission");
					}
				}
			}

			TURAssignmentPersistenceWrapper.unassignRoleFromUserInTenant(tenantId, userId);

			return Response.ok().entity(new AdapterResponseBean("delete success", userId, 200)).build();

		} catch (Exception e) {
			// system out the exception into the console log
			logger.info(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}

	}

	private static AdapterResponseBean removeOCDPServiceCredentials(String tenantId, String instanceName,
			String userName) throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		String url = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_URL);
		String token = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_TOKEN);
		String dfRestUrl = url + "/oapi/v1/namespaces/" + tenantId + "/backingserviceinstances/" + instanceName
				+ ":instance_name/binding";

		JsonObject reqBody = new JsonObject();
		reqBody.addProperty("apiVersion", "v1");
		reqBody.addProperty("kind", "BindingRequestOptions");
		reqBody.addProperty("bindKind", "HadoopUser");
		reqBody.addProperty("resourceName", userName);

		JsonObject metadata = new JsonObject();
		metadata.addProperty("name", instanceName);
		reqBody.add("metadata", metadata);
		String reqBodyStr = reqBody.toString();

		SSLConnectionSocketFactory sslsf = SSLSocketIgnoreCA.createSSLSocketFactory();

		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			HttpPut httpPut = new HttpPut(dfRestUrl);
			httpPut.addHeader("Content-type", "application/json");
			httpPut.addHeader("Authorization", "bearer " + token);

			StringEntity se = new StringEntity(reqBodyStr);
			se.setContentType("application/json");
			httpPut.setEntity(se);

			CloseableHttpResponse response2 = httpclient.execute(httpPut);

			try {
				int statusCode = response2.getStatusLine().getStatusCode();

				String bodyStr = EntityUtils.toString(response2.getEntity());

				return new AdapterResponseBean("", bodyStr, statusCode);
			} finally {
				response2.close();
			}
		} finally {
			httpclient.close();
		}

	}

	private static AdapterResponseBean generateOCDPServiceCredentials(String tenantId, String instanceName,
			String userName) throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		String url = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_URL);
		String token = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_TOKEN);
		String dfRestUrl = url + "/oapi/v1/namespaces/" + tenantId + "/backingserviceinstances/" + instanceName
				+ ":instance_name/binding";

		JsonObject reqBody = new JsonObject();
		reqBody.addProperty("apiVersion", "v1");
		reqBody.addProperty("kind", "BindingRequestOptions");
		reqBody.addProperty("bindKind", "HadoopUser");
		reqBody.addProperty("resourceName", userName);

		JsonObject metadata = new JsonObject();
		metadata.addProperty("name", instanceName);
		reqBody.add("metadata", metadata);
		String reqBodyStr = reqBody.toString();

		SSLConnectionSocketFactory sslsf = SSLSocketIgnoreCA.createSSLSocketFactory();

		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			HttpPost httpPost = new HttpPost(dfRestUrl);
			httpPost.addHeader("Content-type", "application/json");
			httpPost.addHeader("Authorization", "bearer " + token);

			StringEntity se = new StringEntity(reqBodyStr);
			se.setContentType("application/json");
			httpPost.setEntity(se);

			CloseableHttpResponse response2 = httpclient.execute(httpPost);

			try {
				int statusCode = response2.getStatusLine().getStatusCode();

				String bodyStr = EntityUtils.toString(response2.getEntity());

				return new AdapterResponseBean("", bodyStr, statusCode);
			} finally {
				response2.close();
			}
		} finally {
			httpclient.close();
		}

	}

	private static String getTenantServiceInstancesFromDf(String tenantId, String instanceName)
			throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		String url = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_URL);
		String token = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_TOKEN);
		String dfRestUrl = url + "/oapi/v1/namespaces/" + tenantId + "/backingserviceinstances/" + instanceName;

		SSLConnectionSocketFactory sslsf = SSLSocketIgnoreCA.createSSLSocketFactory();

		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			HttpGet httpGet = new HttpGet(dfRestUrl);
			httpGet.addHeader("Content-type", "application/json");
			httpGet.addHeader("Authorization", "bearer " + token);

			CloseableHttpResponse response1 = httpclient.execute(httpGet);

			try {
				// int statusCode =
				// response1.getStatusLine().getStatusCode();

				String bodyStr = EntityUtils.toString(response1.getEntity());

				return bodyStr;
			} finally {
				response1.close();
			}
		} finally {
			httpclient.close();
		}
	}

	private static String getTenantAllServiceInstancesFromDf(String tenantId)
			throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		String url = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_URL);
		String token = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_TOKEN);
		String dfRestUrl = url + "/oapi/v1/namespaces/" + tenantId + "/backingserviceinstances";

		SSLConnectionSocketFactory sslsf = SSLSocketIgnoreCA.createSSLSocketFactory();

		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			HttpGet httpGet = new HttpGet(dfRestUrl);
			httpGet.addHeader("Content-type", "application/json");
			httpGet.addHeader("Authorization", "bearer " + token);

			CloseableHttpResponse response1 = httpclient.execute(httpGet);

			try {
				// int statusCode =
				// response1.getStatusLine().getStatusCode();

				String bodyStr = EntityUtils.toString(response1.getEntity());

				return bodyStr;
			} finally {
				response1.close();
			}
		} finally {
			httpclient.close();
		}

	}

	private static AdapterResponseBean updateTenantServiceInstanceInDf(String tenantId, String instanceName,
			String reqBodyStr) throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		String url = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_URL);
		String token = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_TOKEN);
		String dfRestUrl = url + "/oapi/v1/namespaces/" + tenantId + "/backingserviceinstances/" + instanceName;

		// parse the req body make sure it is json
		JsonElement reqBodyJson = new JsonParser().parse(reqBodyStr);
		SSLConnectionSocketFactory sslsf = SSLSocketIgnoreCA.createSSLSocketFactory();

		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			HttpPut httpPut = new HttpPut(dfRestUrl);
			httpPut.addHeader("Content-type", "application/json");
			httpPut.addHeader("Authorization", "bearer " + token);

			StringEntity se = new StringEntity(reqBodyJson.toString());
			se.setContentType("application/json");
			httpPut.setEntity(se);

			CloseableHttpResponse response2 = httpclient.execute(httpPut);

			try {
				int statusCode = response2.getStatusLine().getStatusCode();
				String bodyStr = EntityUtils.toString(response2.getEntity());

				return new AdapterResponseBean("", bodyStr, statusCode);
			} finally {
				response2.close();
			}
		} finally {
			httpclient.close();
		}
	}

}
