package com.asiainfo.ocmanager.rest.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.asiainfo.ocmanager.persistence.model.Service;
import com.asiainfo.ocmanager.rest.resource.utils.ServicePersistenceWrapper;
import com.asiainfo.ocmanager.rest.utils.SSLSocketIgnoreCA;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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

	/**
	 * Add service broker
	 * 
	 * @return service
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response addServiceBroker(String reqBodyStr) {

		String dfRestUrl = "https://10.1.130.134:8443/oapi/v1/servicebrokers";
		try {
			// parse the req body make sure it is json
			JsonElement reqBodyJson = new JsonParser().parse(reqBodyStr);
			SSLConnectionSocketFactory sslsf = SSLSocketIgnoreCA.createSSLSocketFactory();

			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			try {
				HttpPost httpPost = new HttpPost(dfRestUrl);
				httpPost.addHeader("Content-type", "application/json");
				httpPost.addHeader("Authorization", "bearer "
						+ "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6Im9jbS10b2tlbi12NzZtNyIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50Lm5hbWUiOiJvY20iLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiI1ZTg1MGY0Yi00YzM3LTExZTctYWE0OS1mYTE2M2VmZGJlYTgiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6ZGVmYXVsdDpvY20ifQ.t1SrAN167RVL4slBo2botWDzjNtXG8J4tRlnlNJJL85HOHMYNEi-FvGJ5Nt37mKVVVPaZFjUoU5pGLBCzcE79pzrRJyBMXe_duCsCX23z9M-cEllX9Srn7Kex2N5D596M8S8mnSwtLSvXjYuX2ftW7eCWw1738hUtTg1UxXWO-HYW8yPYGTusZJFErtkdl7pV6wAcDl__ltSI62IjoeIjKT5ZGM5GLmInWDu9Dkk6i0pBy2kTWbLQqRD94QZKXMK9Zp4uAjCFaYaumT_DWhRh9DvHYK6dXvmxVXKvqXe9uVHYwT2AbNVZq-ix1Tev3xzaNw8ju9XZq4xHFLNi4LzFQ");

				StringEntity se = new StringEntity(reqBodyJson.toString());
				se.setContentType("application/json");
				httpPost.setEntity(se);

				CloseableHttpResponse response2 = httpclient.execute(httpPost);

				try {
					int statusCode = response2.getStatusLine().getStatusCode();
					String bodyStr = EntityUtils.toString(response2.getEntity());
					if (statusCode == 201) {
						// TODO should call df service api and compare with adapter db service data, insert the data which is not in the adapter db
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
	 * Get all service from df
	 * 
	 * @return services
	 */
	@GET
	@Path("/df")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getServiceFromDf() {

		String dfRestUrl = "https://10.1.130.134:8443/oapi/v1/namespaces/openshift/backingservices";
		try {
			SSLConnectionSocketFactory sslsf = SSLSocketIgnoreCA.createSSLSocketFactory();

			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			try {
				HttpGet httpGet = new HttpGet(dfRestUrl);
				httpGet.addHeader("Content-type", "application/json");
				httpGet.addHeader("Authorization", "bearer "
						+ "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6Im9jbS10b2tlbi12NzZtNyIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50Lm5hbWUiOiJvY20iLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiI1ZTg1MGY0Yi00YzM3LTExZTctYWE0OS1mYTE2M2VmZGJlYTgiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6ZGVmYXVsdDpvY20ifQ.t1SrAN167RVL4slBo2botWDzjNtXG8J4tRlnlNJJL85HOHMYNEi-FvGJ5Nt37mKVVVPaZFjUoU5pGLBCzcE79pzrRJyBMXe_duCsCX23z9M-cEllX9Srn7Kex2N5D596M8S8mnSwtLSvXjYuX2ftW7eCWw1738hUtTg1UxXWO-HYW8yPYGTusZJFErtkdl7pV6wAcDl__ltSI62IjoeIjKT5ZGM5GLmInWDu9Dkk6i0pBy2kTWbLQqRD94QZKXMK9Zp4uAjCFaYaumT_DWhRh9DvHYK6dXvmxVXKvqXe9uVHYwT2AbNVZq-ix1Tev3xzaNw8ju9XZq4xHFLNi4LzFQ");

				CloseableHttpResponse response1 = httpclient.execute(httpGet);

				try {
					// int statusCode =
					// response1.getStatusLine().getStatusCode();

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

}
