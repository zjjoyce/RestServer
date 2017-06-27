package com.asiainfo.ocmanager.rest.resource;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.asiainfo.ocmanager.persistence.model.Service;
import com.asiainfo.ocmanager.rest.constant.Constant;
import com.asiainfo.ocmanager.rest.resource.utils.ServicePersistenceWrapper;
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

		try {
			// TODO should call df service api and compare with adapter db
			// service data, insert the data which is not in the adapter db
			// every time when call the get all services api it will symnc the
			// adapter db with df services data
			// this is not a good solution should be enhance in future
			List<Service> servicesInDB = ServicePersistenceWrapper.getAllServices();

			String servicesFromDf = ServiceResource.callDFToGetAllServices();
			JsonObject servicesFromDfJson = new JsonParser().parse(servicesFromDf).getAsJsonObject();
			JsonArray items = servicesFromDfJson.getAsJsonArray("items");

			if (items != null) {
				for (int i = 0; i < items.size(); i++) {
					String name = items.get(i).getAsJsonObject().getAsJsonObject("spec").get("name").getAsString();
					String id = items.get(i).getAsJsonObject().getAsJsonObject("spec").get("id").getAsString();
					String description = items.get(i).getAsJsonObject().getAsJsonObject("spec").get("description")
							.getAsString();

					if (servicesInDB.size() == 0) {
						ServicePersistenceWrapper.addService(new Service(id, name, description));
					} else {
						for (Service s : servicesInDB) {
							if (!s.getId().equals(id)) {
								ServicePersistenceWrapper.addService(new Service(id, name, description));
							}
						}
					}

				}
			}
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}

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
		try {
			Service service = ServicePersistenceWrapper.getServiceById(serviceId);

			return Response.ok().entity(service == null ? new Service() : service).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}
	}

	/**
	 * Add service broker
	 *
	 * @return service
	 */
	@POST
	@Path("/broker")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addServiceBroker(String reqBodyStr) {

		try {
			String url = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_URL);
			String token = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_TOKEN);
			String dfRestUrl = url + "/oapi/v1/servicebrokers";

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
//					int statusCode = response2.getStatusLine().getStatusCode();
					String bodyStr = EntityUtils.toString(response2.getEntity());
					// if (statusCode == 201) {
					// // TODO should call df service api and compare with
					// adapter db service data, insert the data which is not in
					// the adapter db
					// List<Service> servicesInDB =
					// ServicePersistenceWrapper.getAllServices();
					//
					// String servicesFromDf =
					// ServiceResource.callDFToGetAllServices();
					// JsonObject servicesFromDfJson = new
					// JsonParser().parse(servicesFromDf).getAsJsonObject();
					// JsonArray items =
					// servicesFromDfJson.getAsJsonArray("items");
					//
					// if (items != null || items.size() != 0) {
					// for (int i = 0; i < items.size(); i++) {
					// String name =
					// items.get(i).getAsJsonObject().getAsJsonObject("spec")
					// .get("name").getAsString();
					// String id =
					// items.get(i).getAsJsonObject().getAsJsonObject("spec")
					// .get("id").getAsString();
					// String description =
					// items.get(i).getAsJsonObject().getAsJsonObject("spec")
					// .get("description").getAsString();
					//
					// for(Service s: servicesInDB){
					// if (!s.getId().equals(id)) {
					// ServicePersistenceWrapper.addService(new Service(id,
					// name, description));
					// }
					// }
					// }
					// }
					// }

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
		try {
			return Response.ok().entity(ServiceResource.callDFToGetAllServices()).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace().toString()).build();
		}
	}

	/**
	 * delete service broker
	 *
	 * @return service
	 */
	@DELETE
	@Path("/broker/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteServiceBroker(@PathParam("name") String serviceBrokerName) {
		try {
			String url = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_URL);
			String token = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_TOKEN);
			String dfRestUrl = url + "/oapi/v1/servicebrokers/" + serviceBrokerName;

			SSLConnectionSocketFactory sslsf = SSLSocketIgnoreCA.createSSLSocketFactory();

			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			try {
				HttpDelete httpDelete = new HttpDelete(dfRestUrl);
				httpDelete.addHeader("Content-type", "application/json");
				httpDelete.addHeader("Authorization", "bearer " + token);

				CloseableHttpResponse response1 = httpclient.execute(httpDelete);

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


	/**
	 * call data foundry rest api
	 *
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static String callDFToGetAllServices() throws KeyManagementException, NoSuchAlgorithmException,
			KeyStoreException, ClientProtocolException, IOException {

		String url = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_URL);
		String token = DFPropertiesFoundry.getDFProperties().get(Constant.DATAFOUNDRY_TOKEN);
		String dfRestUrl = url + "/oapi/v1/namespaces/openshift/backingservices";

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

}
