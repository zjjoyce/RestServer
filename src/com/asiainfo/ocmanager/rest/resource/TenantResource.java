package com.asiainfo.ocmanager.rest.resource;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
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

import com.asiainfo.ocmanager.persistence.model.ServiceInstance;
import com.asiainfo.ocmanager.persistence.model.ServiceRolePermission;
import com.asiainfo.ocmanager.persistence.model.Tenant;
import com.asiainfo.ocmanager.persistence.model.TenantUserRoleAssignment;
import com.asiainfo.ocmanager.persistence.model.UserRoleView;
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
import com.google.gson.JsonArray;
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

	private static Logger logger = Logger.getLogger(TenantResource.class);

	/**
	 * Get All OCManager tenants
	 *
	 * @return tenant list
	 */
	@GET
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	public Response getAllTenants() {
		try {
			List<Tenant> tenants = TenantPersistenceWrapper.getAllTenants();
			return Response.ok().entity(tenants).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("getAllTenants -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
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
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	public Response getTenantById(@PathParam("id") String tenantId) {
		try {
			Tenant tenant = TenantPersistenceWrapper.getTenantById(tenantId);
			return Response.ok().entity(tenant).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("getTenantById -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
		}
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
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	public Response getChildrenTenants(@PathParam("id") String parentTenantId) {
		try {
			List<Tenant> tenants = TenantPersistenceWrapper.getChildrenTenants(parentTenantId);
			return Response.ok().entity(tenants).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("getChildrenTenants -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
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
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	public Response getTenantUsers(@PathParam("id") String tenantId) {
		try {
			List<UserRoleView> usersRoles = UserRoleViewPersistenceWrapper.getUsersInTenant(tenantId);
			return Response.ok().entity(usersRoles).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("getTenantUsers -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
		}
	}

	private static UserRoleView getRole(String tenantId, String userName) {

		UserRoleView role = UserRoleViewPersistenceWrapper.getRoleBasedOnUserAndTenant(userName, tenantId);
		if (role == null) {
			logger.debug("getRole -> start get tenant");
			Tenant tenant = TenantPersistenceWrapper.getTenantById(tenantId);
			logger.debug("getRole -> finish get tenant");
			if (tenant == null) {
				return null;
			}
			if (tenant.getParentId() == null) {
				return null;
			} else {
				role = TenantResource.getRole(tenant.getParentId(), userName);
			}
		}
		return role;
	}

	/**
	 * Get the role based on the tenant and user
	 * 
	 * @param tenantId
	 * @param userName
	 * @return
	 */
	@GET
	@Path("{id}/user/{userName}/role")
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	public Response getRoleByTenantUserName(@PathParam("id") String tenantId, @PathParam("userName") String userName) {
		try {
			UserRoleView role = TenantResource.getRole(tenantId, userName);
			if (role != null) {
				// set the tenant id to the passed tenant id
				role.setTenantId(tenantId);
			}
			return Response.ok().entity(role).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("getRoleByTenantUserName -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
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
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	public Response getTenantServiceInstances(@PathParam("id") String tenantId) {
		try {
			List<ServiceInstance> serviceInstances = ServiceInstancePersistenceWrapper
					.getServiceInstancesInTenant(tenantId);
			return Response.ok().entity(serviceInstances).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("getTenantServiceInstances -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
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
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	public Response getTenantServiceInstanceAccessInfo(@PathParam("tenantId") String tenantId,
			@PathParam("InstanceName") String InstanceName) {
		try {
			return Response.ok().entity(TenantResource.getTenantServiceInstancesFromDf(tenantId, InstanceName)).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("getTenantServiceInstanceAccessInfo -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
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
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
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

				logger.info("createTenant -> start create");
				CloseableHttpResponse response2 = httpclient.execute(httpPost);

				try {
					int statusCode = response2.getStatusLine().getStatusCode();

					if (statusCode == 201) {
						logger.info("createTenant -> start successfully");
						// very ugly code here hard code 3 level
						// only for citic should enhance
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
			logger.info("createTenant -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
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
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
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

				logger.info("createServiceInstanceInTenant -> begin to create service instance");
				CloseableHttpResponse response2 = httpclient.execute(httpPost);

				try {
					int statusCode = response2.getStatusLine().getStatusCode();
					String bodyStr = EntityUtils.toString(response2.getEntity());
					ServiceInstance serviceInstance = new ServiceInstance();
					if (statusCode == 201) {
						logger.info("createServiceInstanceInTenant -> create service instances successfully");

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

						// loop to wait the instance status.phase change to
						// Unbound if the status.phase is Provisioning the
						// update will failed, so need to wait
						logger.info("createServiceInstanceInTenant -> waiting Provisioning to Unbound");
						while (phase.equals(Constant.PROVISIONING)) {
							// wait for 3 secs
							Thread.sleep(3000);
							// get the instance info again
							getInstanceResBody = TenantResource.getTenantServiceInstancesFromDf(tenantId,
									serviceInstance.getInstanceName());
							serviceInstanceJson = new JsonParser().parse(getInstanceResBody);
							serviceName = serviceInstanceJson.getAsJsonObject().getAsJsonObject("spec")
									.getAsJsonObject("provisioning").get("backingservice_name").getAsString();
							phase = serviceInstanceJson.getAsJsonObject().getAsJsonObject("status").get("phase")
									.getAsString();
						}
						logger.info("createServiceInstanceInTenant -> waiting Provisioning to Unbound successfully");

						// sync here after the create successfully
						// get the latest status and insert into DB
						if ((resBodyJsonObj.getAsJsonObject("spec").getAsJsonObject("provisioning").get("parameters")
								.isJsonNull())) {
							// TODO should get df service quota
						} else {
							// parameters are a json format should use to string
							serviceInstance.setQuota(serviceInstanceJson.getAsJsonObject().getAsJsonObject("spec")
									.getAsJsonObject("provisioning").get("parameters").toString());
						}
						serviceInstance.setStatus(phase);

						// insert the service instance into the adapter DB
						ServiceInstancePersistenceWrapper.createServiceInstance(serviceInstance);

						// if the phase is failed, it means the create failed
						if (phase.equals(Constant.FAILURE)) {
							logger.info("createServiceInstanceInTenant -> phase is failure, throw directly");
							return Response.ok().entity(getInstanceResBody).build();
						}

						// only the OCDP services need to wait to assign the
						// permission
						if (Constant.list.contains(serviceName.toLowerCase())) {

							List<UserRoleView> users = UserRoleViewPersistenceWrapper.getUsersInTenant(tenantId);

							List<String> userNameList = new ArrayList<String>();
							for (UserRoleView u : users) {
								ServiceRolePermission permission = ServiceRolePermissionWrapper
										.getServicePermissionByRoleId(serviceName, u.getRoleId());
								if (!(permission == null)) {
									userNameList.add(u.getUserName());
								}
							}
							if (!(userNameList.size() == 0)) {

								getInstanceResBody = TenantResource.getTenantServiceInstancesFromDf(tenantId,
										serviceInstance.getInstanceName());

								JsonElement OCDPServiceInstanceJson = new JsonParser().parse(getInstanceResBody);

								JsonObject provisioning = OCDPServiceInstanceJson.getAsJsonObject()
										.getAsJsonObject("spec").getAsJsonObject("provisioning");

								String userNameListStr = "";
								for (String u : userNameList) {
									userNameListStr = userNameListStr + "," + u;
								}
								// remove first comma
								userNameListStr = userNameListStr.substring(1, userNameListStr.length());

								provisioning.getAsJsonObject("parameters").addProperty("user_name", userNameListStr);

								// hard code the role id here
								// because now only pm and team member
								// can access the service instance
								// and they have the same permission
								ServiceRolePermission permission = ServiceRolePermissionWrapper
										.getServicePermissionByRoleId(serviceName, Constant.PROJECTMANAGERROLE);

								provisioning.getAsJsonObject("parameters").addProperty("accesses",
										permission.getServicePermission());

								JsonObject status = OCDPServiceInstanceJson.getAsJsonObject().getAsJsonObject("status");
								status.addProperty("patch", Constant.UPDATE);

								logger.info("createServiceInstanceInTenant -> begin update service instance");
								AdapterResponseBean updateRes = TenantResource.updateTenantServiceInstanceInDf(tenantId,
										instanceName, OCDPServiceInstanceJson.toString());

								if (updateRes.getResCodel() == 200) {

									logger.info("createServiceInstanceInTenant -> wait update complete");
									TenantResource.watiInstanceUpdateComplete(updateRes, tenantId, instanceName);
									logger.info("createServiceInstanceInTenant -> update complete");

									logger.info("createServiceInstanceInTenant -> begin to binding");
									for (int i = 0; i < userNameList.size(); i++) {
										AdapterResponseBean bindingRes = TenantResource.generateOCDPServiceCredentials(
												tenantId, instanceName, userNameList.get(i));

										if (bindingRes.getResCodel() == 201) {
											logger.info("createServiceInstanceInTenant -> wait binding complete");
											TenantResource.watiInstanceBindingComplete(bindingRes, tenantId,
													instanceName);
											logger.info("createServiceInstanceInTenant -> binding complete");
										}
									}
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
			logger.info("createServiceInstanceInTenant -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
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
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateServiceInstanceInTenant(@PathParam("id") String tenantId,
			@PathParam("instanceName") String instanceName, String parametersStr) {

		try {

			// get the just now created instance info
			String getInstanceResBody = TenantResource.getTenantServiceInstancesFromDf(tenantId, instanceName);

			JsonElement serviceInstanceJson = new JsonParser().parse(getInstanceResBody);
			// get status phase
			String phase = serviceInstanceJson.getAsJsonObject().getAsJsonObject("status").get("phase").getAsString();

			if (phase.equals(Constant.PROVISIONING)) {
				logger.info(
						"updateServiceInstanceInTenant -> The instance can not be updated when it is Provisioning!");
				return Response.status(Status.BAD_REQUEST)
						.entity("The instance can not be updated when it is Provisioning!").build();
			}

			if (phase.equals(Constant.FAILURE)) {
				logger.info("updateServiceInstanceInTenant -> The instance can not be updated when it is Failure!");
				return Response.status(Status.BAD_REQUEST).entity("The instance can not be updated when it is Failure!")
						.build();
			}

			// get the provisioning json
			JsonObject provisioning = serviceInstanceJson.getAsJsonObject().getAsJsonObject("spec")
					.getAsJsonObject("provisioning");

			// parse the input parameters json
			JsonElement parameterJon = new JsonParser().parse(parametersStr);
			// add into the update json
			provisioning.add("parameters", parameterJon.getAsJsonObject().getAsJsonObject("parameters"));

			// add the patch Updating into the request body
			JsonObject status = serviceInstanceJson.getAsJsonObject().getAsJsonObject("status");
			status.addProperty("patch", Constant.UPDATE);

			logger.info("updateServiceInstanceInTenant -> update start");
			AdapterResponseBean responseBean = TenantResource.updateTenantServiceInstanceInDf(tenantId, instanceName,
					serviceInstanceJson.toString());

			String quota = null;
			if (responseBean.getResCodel() == 200) {
				logger.info("updateServiceInstanceInTenant -> update successfully");
				JsonElement resBodyJson = new JsonParser().parse(responseBean.getMessage());
				JsonObject resBodyJsonObj = resBodyJson.getAsJsonObject();
				if ((resBodyJsonObj.getAsJsonObject("spec").getAsJsonObject("provisioning").get("parameters")
						.isJsonNull())) {
					// TODO should get df service quota
				} else {
					quota = serviceInstanceJson.getAsJsonObject().getAsJsonObject("spec")
							.getAsJsonObject("provisioning").get("parameters").toString();
				}

				ServiceInstancePersistenceWrapper.updateServiceInstanceQuota(tenantId, instanceName, quota);
			}

			return Response.ok().entity(responseBean.getMessage()).build();
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("updateServiceInstanceInTenant -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
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
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteServiceInstanceInTenant(@PathParam("id") String tenantId,
			@PathParam("instanceName") String instanceName) {

		try {

			String getInstanceResBody = TenantResource.getTenantServiceInstancesFromDf(tenantId, instanceName);
			JsonElement resBodyJson = new JsonParser().parse(getInstanceResBody);
			JsonObject instance = resBodyJson.getAsJsonObject();
			String serviceName = instance.getAsJsonObject("spec").getAsJsonObject("provisioning")
					.get("backingservice_name").getAsString();
			// get status phase
			String phase = instance.getAsJsonObject("status").get("phase").getAsString();

			// if the instance is Failure do not need to unbound
			if (!phase.equals(Constant.FAILURE)) {
				// get all the users under the tenant
				List<UserRoleView> users = UserRoleViewPersistenceWrapper.getUsersInTenant(tenantId);
				for (UserRoleView u : users) {

					// ignore the users who not have the service permissions
					// align with create service instance
					ServiceRolePermission permission = ServiceRolePermissionWrapper
							.getServicePermissionByRoleId(serviceName, u.getRoleId());
					if (permission == null) {
						continue;
					}

					if (Constant.list.contains(serviceName.toLowerCase())) {

						// only the Unbound service instances can be delete
						// so unbinding the service instance first
						logger.info("deleteServiceInstanceInTenant -> begin to unbinding");
						AdapterResponseBean unBindingRes = TenantResource.removeOCDPServiceCredentials(tenantId,
								instanceName, UserPersistenceWrapper.getUserById(u.getUserId()).getUsername());

						if (unBindingRes.getResCodel() == 201) {
							logger.info("deleteServiceInstanceInTenant -> wait unbinding complete");
							TenantResource.watiInstanceUnBindingComplete(unBindingRes, tenantId, instanceName);
							logger.info("deleteServiceInstanceInTenant -> unbinding complete");
						}
					}
				}
			}

			// begin to delete the instance
			String url = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_URL);
			String token = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_TOKEN);
			String dfRestUrl = url + "/oapi/v1/namespaces/" + tenantId + "/backingserviceinstances/" + instanceName;

			SSLConnectionSocketFactory sslsf = SSLSocketIgnoreCA.createSSLSocketFactory();

			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			try {
				HttpDelete httpDelete = new HttpDelete(dfRestUrl);
				httpDelete.addHeader("Content-type", "application/json");
				httpDelete.addHeader("Authorization", "bearer " + token);

				logger.info("deleteServiceInstanceInTenant -> start delete");
				CloseableHttpResponse response1 = httpclient.execute(httpDelete);

				try {
					int statusCode = response1.getStatusLine().getStatusCode();
					if (statusCode == 200) {
						ServiceInstancePersistenceWrapper.deleteServiceInstance(tenantId, instanceName);
						logger.info("deleteServiceInstanceInTenant -> delete successfully");
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
			logger.info("deleteServiceInstanceInTenant -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
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
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
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
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
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

				logger.info("deleteTenant -> delete start");
				CloseableHttpResponse response1 = httpclient.execute(httpDelete);

				try {
					int statusCode = response1.getStatusLine().getStatusCode();
					if (statusCode == 200) {
						TenantPersistenceWrapper.deleteTenant(tenantId);
						logger.info("deleteTenant -> delete successfully");
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
			logger.info("deleteTenant -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
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
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
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
				// TODO should consider the resource version changed need to
				// call get instance by id
				JsonObject instance = allServiceInstancesArray.get(i).getAsJsonObject();
				// get service name
				String serviceName = instance.getAsJsonObject("spec").getAsJsonObject("provisioning")
						.get("backingservice_name").getAsString();

				String phase = instance.getAsJsonObject("status").get("phase").getAsString();
				if (!phase.equals(Constant.PROVISIONING) && !phase.equals(Constant.FAILURE)) {
					// Because the Provisioning will make the update failed
					if (Constant.list.contains(serviceName.toLowerCase())) {
						// get service instance name
						String instanceName = instance.getAsJsonObject("metadata").get("name").getAsString();
						String OCDPServiceInstanceStr = TenantResource.getTenantServiceInstancesFromDf(tenantId,
								instanceName);

						// get the service permission based on the service name
						// and role
						ServiceRolePermission permission = ServiceRolePermissionWrapper
								.getServicePermissionByRoleId(serviceName, assignment.getRoleId());

						if (permission == null) {
							permission = new ServiceRolePermission();
							permission.setServicePermission("");
						}

						// parse the update request body
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
						status.addProperty("patch", Constant.UPDATE);

						logger.info("assignRoleToUserInTenant -> begin to update");
						AdapterResponseBean updateRes = TenantResource.updateTenantServiceInstanceInDf(tenantId,
								instanceName, OCDPServiceInstanceJson.toString());

						if (updateRes.getResCodel() == 200) {
							logger.info("assignRoleToUserInTenant -> wait update complete");
							TenantResource.watiInstanceUpdateComplete(updateRes, tenantId, instanceName);
							logger.info("assignRoleToUserInTenant -> update complete");

							logger.info("assignRoleToUserInTenant -> begin to binding");
							AdapterResponseBean bindingRes = TenantResource.generateOCDPServiceCredentials(tenantId,
									instanceName, userName);
							if (bindingRes.getResCodel() == 201) {
								logger.info("assignRoleToUserInTenant -> binding successfully");
							}
						}
					}
				}
			}

			assignment = TURAssignmentPersistenceWrapper.assignRoleToUserInTenant(assignment);

			return Response.ok().entity(assignment).build();

		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("assignRoleToUserInTenant -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
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
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
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
				String phase = instance.getAsJsonObject("status").get("phase").getAsString();

				if (!phase.equals(Constant.PROVISIONING) && !phase.equals(Constant.FAILURE)) {
					if (Constant.list.contains(serviceName.toLowerCase())) {

						// get service instance name
						String instanceName = instance.getAsJsonObject("metadata").get("name").getAsString();
						String userName = UserPersistenceWrapper.getUserById(assignment.getUserId()).getUsername();

						logger.info("updateRoleToUserInTenant -> begin to unbinding");
						AdapterResponseBean unBindingRes = TenantResource.removeOCDPServiceCredentials(tenantId,
								instanceName, userName);

						if (unBindingRes.getResCodel() == 201) {
							logger.info("updateRoleToUserInTenant -> wait unbinding compelte");
							TenantResource.watiInstanceUnBindingComplete(unBindingRes, tenantId, instanceName);
							logger.info("updateRoleToUserInTenant -> unbinding compelte");

							String OCDPServiceInstanceStr = TenantResource.getTenantServiceInstancesFromDf(tenantId,
									instanceName);

							// get the service permission
							ServiceRolePermission permission = ServiceRolePermissionWrapper
									.getServicePermissionByRoleId(serviceName, assignment.getRoleId());

							if (permission == null) {
								permission = new ServiceRolePermission();
								permission.setServicePermission("");
							}

							// parse the update request body
							JsonElement OCDPServiceInstanceJson = new JsonParser().parse(OCDPServiceInstanceStr);
							// get the provisioning json
							JsonObject provisioning = OCDPServiceInstanceJson.getAsJsonObject().getAsJsonObject("spec")
									.getAsJsonObject("provisioning");

							provisioning.getAsJsonObject("parameters").addProperty("user_name", userName);

							// add the accesses fields into the request body
							provisioning.getAsJsonObject("parameters").addProperty("accesses",
									permission.getServicePermission());

							// add the patch Updating into the request body
							JsonObject status = OCDPServiceInstanceJson.getAsJsonObject().getAsJsonObject("status");
							status.addProperty("patch", Constant.UPDATE);

							logger.info("updateRoleToUserInTenant -> begin to update");
							AdapterResponseBean updateRes = TenantResource.updateTenantServiceInstanceInDf(tenantId,
									instanceName, OCDPServiceInstanceJson.toString());

							if (updateRes.getResCodel() == 200) {

								logger.info("updateRoleToUserInTenant -> wait update compete");
								TenantResource.watiInstanceUpdateComplete(updateRes, tenantId, instanceName);
								logger.info("updateRoleToUserInTenant -> update compete");

								logger.info("updateRoleToUserInTenant -> begin to binding");
								AdapterResponseBean bindingRes = TenantResource.generateOCDPServiceCredentials(tenantId,
										instanceName, userName);
								if (bindingRes.getResCodel() == 201) {
									logger.info("updateRoleToUserInTenant -> binding successfully");
								}
							}
						}
					}
				}
			}

			assignment = TURAssignmentPersistenceWrapper.updateRoleToUserInTenant(assignment);

			return Response.ok().entity(assignment).build();

		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("updateRoleToUserInTenant -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
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
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))
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
				String phase = instance.getAsJsonObject("status").get("phase").getAsString();

				if (!phase.equals(Constant.PROVISIONING) && !phase.equals(Constant.FAILURE)) {
					if (Constant.list.contains(serviceName.toLowerCase())) {
						// get service instance name
						String instanceName = instance.getAsJsonObject("metadata").get("name").getAsString();

						// the unassign df and service broker only use the
						// unbinding
						// to do
						// so here not need to call update
						logger.info("unassignRoleFromUserInTenant -> begin to unbinding");
						AdapterResponseBean bindingRes = TenantResource.removeOCDPServiceCredentials(tenantId,
								instanceName, UserPersistenceWrapper.getUserById(userId).getUsername());

						if (bindingRes.getResCodel() == 201) {
							logger.info("unassignRoleFromUserInTenant -> unbinding successfully");
						}
					}
				}
			}

			TURAssignmentPersistenceWrapper.unassignRoleFromUserInTenant(tenantId, userId);

			return Response.ok().entity(new AdapterResponseBean("delete success", userId, 200)).build();

		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("unassignRoleFromUserInTenant -> " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
		}

	}

	private static void watiInstanceUnBindingComplete(AdapterResponseBean unBindingRes, String tenantId,
			String instanceName) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException,
			IOException, InterruptedException {

		String unBindingResStr = unBindingRes.getMessage();
		JsonElement unBindingResJson = new JsonParser().parse(unBindingResStr);
		int bound = unBindingResJson.getAsJsonObject().getAsJsonObject("spec").get("bound").getAsInt();

		String instStr = TenantResource.getTenantServiceInstancesFromDf(tenantId, instanceName);
		JsonElement instJson = new JsonParser().parse(instStr);

		int currentBound = instJson.getAsJsonObject().getAsJsonObject("spec").get("bound").getAsInt();

		while (currentBound == bound) {
			logger.debug("watiInstanceUnBindingComplete -> waiting");
			Thread.sleep(500);
			instStr = TenantResource.getTenantServiceInstancesFromDf(tenantId, instanceName);
			instJson = new JsonParser().parse(instStr);
			currentBound = instJson.getAsJsonObject().getAsJsonObject("spec").get("bound").getAsInt();
		}

	}

	private static void watiInstanceBindingComplete(AdapterResponseBean bindingRes, String tenantId,
			String instanceName) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException,
			IOException, InterruptedException {

		String bindingResStr = bindingRes.getMessage();
		JsonElement bindingResJson = new JsonParser().parse(bindingResStr);
		int bound = bindingResJson.getAsJsonObject().getAsJsonObject("spec").get("bound").getAsInt();

		String instStr = TenantResource.getTenantServiceInstancesFromDf(tenantId, instanceName);
		JsonElement instJson = new JsonParser().parse(instStr);

		int currentBound = instJson.getAsJsonObject().getAsJsonObject("spec").get("bound").getAsInt();

		while (currentBound == bound) {
			logger.debug("watiInstanceBindingComplete -> waiting");
			Thread.sleep(500);
			instStr = TenantResource.getTenantServiceInstancesFromDf(tenantId, instanceName);
			instJson = new JsonParser().parse(instStr);
			currentBound = instJson.getAsJsonObject().getAsJsonObject("spec").get("bound").getAsInt();
		}

	}

	private static void watiInstanceUpdateComplete(AdapterResponseBean updateRes, String tenantId, String instanceName)
			throws InterruptedException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException,
			IOException {

		String updateInstStr = updateRes.getMessage();
		JsonElement updateInstJson = new JsonParser().parse(updateInstStr);

		JsonElement patch = updateInstJson.getAsJsonObject().getAsJsonObject("status").get("patch");

		while (patch != null) {
			logger.debug("watiInstanceUpdateComplete -> waiting");
			Thread.sleep(500);
			updateInstStr = TenantResource.getTenantServiceInstancesFromDf(tenantId, instanceName);
			updateInstJson = new JsonParser().parse(updateInstStr);

			patch = updateInstJson.getAsJsonObject().getAsJsonObject("status").get("patch");
		}

	}

	private static AdapterResponseBean removeOCDPServiceCredentials(String tenantId, String instanceName,
			String userName) throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		String url = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_URL);
		String token = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_TOKEN);
		String dfRestUrl = url + "/oapi/v1/namespaces/" + tenantId + "/backingserviceinstances/" + instanceName
				+ "/binding";

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
				+ "/binding";

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
				int statusCode = response1.getStatusLine().getStatusCode();

				String bodyStr = EntityUtils.toString(response1.getEntity());

				// filter the _ToDelete instances
				if (statusCode == 200) {
					JsonElement jsonE = new JsonParser().parse(bodyStr);
					JsonObject jsonO = jsonE.getAsJsonObject();

					JsonArray items = jsonO.getAsJsonArray(("items"));

					Iterator<JsonElement> it = items.iterator();
					while (it.hasNext()) {
						JsonElement je = it.next();
						JsonObject status = je.getAsJsonObject().getAsJsonObject("status");
						JsonElement action = status.get("action");

						if (action != null) {
							if (action.getAsString().equals(Constant._TODELETE)) {
								it.remove();
							}
						}
					}
					bodyStr = jsonO.toString();
				}
				logger.info("getTenantAllServiceInstancesFromDf -> " +  bodyStr);
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

	/**
	 * specific method for citic
	 * 
	 * @param parentId
	 * @param tenantId
	 */
	private static void checkApp(String appId) {
		Tenant tenant = TenantPersistenceWrapper.getTenantById(appId);
		if (tenant == null) {
			List<Tenant> list = TenantResource.getTenantAndAPPByAppId(appId);

			TenantResource.checkTenant(list.get(0));

			Tenant newTenant = new Tenant(list.get(1).getId(), list.get(1).getName(), list.get(1).getDescription(),
					list.get(0).getId(), 3);
			TenantResource.createTenantInternal(newTenant);
		}

	}

	private static List<Tenant> getTenantAndAPPByAppId(String appId) {
		return new ArrayList<Tenant>();
	}

	private static void checkTenant(Tenant tenant) {
		Tenant DBtenant = TenantPersistenceWrapper.getTenantById(tenant.getId());
		if (DBtenant == null) {
			Tenant newTenant = new Tenant(tenant.getId(), tenant.getName(), tenant.getDescription(),
					"ae783b6d-655a-11e7-aa10-fa163ed7d0ae", 2);
			TenantResource.createTenantInternal(newTenant);
		}
	}

	private static void createTenantInternal(Tenant tenant) {

		try {
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

				logger.info("createTenantInternal -> start create");
				CloseableHttpResponse response2 = httpclient.execute(httpPost);

				try {
					int statusCode = response2.getStatusLine().getStatusCode();

					if (statusCode == 201) {
						logger.info("createTenantInternal -> start successfully");
						TenantPersistenceWrapper.createTenant(tenant);
					}
					String bodyStr = EntityUtils.toString(response2.getEntity());
				} finally {
					response2.close();
				}
			} finally {
				httpclient.close();
			}
		} catch (Exception e) {
			// system out the exception into the console log
			logger.info("createTenantInternal -> " + e.getMessage());
		}
	}

}
