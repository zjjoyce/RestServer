package com.asiainfo.ocmanager.rest.resource;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.TenantMapper;
import com.asiainfo.ocmanager.persistence.model.ServiceInstance;
import com.asiainfo.ocmanager.persistence.model.Tenant;
import com.asiainfo.ocmanager.persistence.model.User;
import com.asiainfo.ocmanager.persistence.test.DBConnectorFactory;
import com.asiainfo.ocmanager.rest.resource.utils.TenantPersistenceWrapper;
import com.asiainfo.ocmanager.rest.utils.SSLSocketIgnoreCA;
import com.asiainfo.ocmanager.rest.utils.UUIDFactory;
import com.google.gson.Gson;
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
	public List<Tenant> getAllTenants() {
		List<Tenant> tenants = TenantPersistenceWrapper.getAllTenants();
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
	public Tenant getTenantById(@PathParam("id") String tenantId) {
		return TenantPersistenceWrapper.getTenantById(tenantId);
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
	public List<Tenant> getChildrenTeants(@PathParam("id") String parentTenantId) {
		return TenantPersistenceWrapper.getChildrenTenants(parentTenantId);
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
	public Response createTenant(Tenant tenant) {

		if (tenant.getName() == null) {
			return Response.status(Status.BAD_REQUEST).entity("input format is not correct").build();
		}
		// mapping DF tenant name with adapter tenant id
		tenant.setId(UUIDFactory.getUUID());

		try {
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
				HttpPost httpPost = new HttpPost("https://10.1.130.134:8443/oapi/v1/projectrequests");
				httpPost.addHeader("Content-type", "application/json");
				httpPost.addHeader("Authorization", "bearer "
						+ "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6Im9jbS10b2tlbi12NzZtNyIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50Lm5hbWUiOiJvY20iLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiI1ZTg1MGY0Yi00YzM3LTExZTctYWE0OS1mYTE2M2VmZGJlYTgiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6ZGVmYXVsdDpvY20ifQ.t1SrAN167RVL4slBo2botWDzjNtXG8J4tRlnlNJJL85HOHMYNEi-FvGJ5Nt37mKVVVPaZFjUoU5pGLBCzcE79pzrRJyBMXe_duCsCX23z9M-cEllX9Srn7Kex2N5D596M8S8mnSwtLSvXjYuX2ftW7eCWw1738hUtTg1UxXWO-HYW8yPYGTusZJFErtkdl7pV6wAcDl__ltSI62IjoeIjKT5ZGM5GLmInWDu9Dkk6i0pBy2kTWbLQqRD94QZKXMK9Zp4uAjCFaYaumT_DWhRh9DvHYK6dXvmxVXKvqXe9uVHYwT2AbNVZq-ix1Tev3xzaNw8ju9XZq4xHFLNi4LzFQ");

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
