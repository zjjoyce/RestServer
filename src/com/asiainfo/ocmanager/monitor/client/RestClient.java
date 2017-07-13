package com.asiainfo.ocmanager.monitor.client;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.asiainfo.ocmanager.monitor.entity.AppEntity;
import com.asiainfo.ocmanager.monitor.entity.AppExtraEntity;
import com.asiainfo.ocmanager.monitor.entity.TenantEntity;
import com.asiainfo.ocmanager.monitor.entity.TenantExtraEntity;
import com.asiainfo.ocmanager.monitor.entity.TenantsAppsEntity;
import com.asiainfo.ocmanager.monitor.util.Configuration;
import com.asiainfo.ocmanager.rest.constant.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Rest client to connect CITIC RestServer
 * @author EthanWang
 *
 */
public class RestClient implements Closeable{
	
	private static final Logger LOG = Logger.getLogger(RestClient.class);
	
	private static final String SUFFIX = "/internal/usermanagement/v1";
	
	private static String base_url;
	
	private CloseableHttpClient client;
	
	private Gson gson = new GsonBuilder().create();
	
	static
	{
		try {
			base_url = Configuration.getMonitorProperties().get(Constant.TENANT_MONITOR_URL) + SUFFIX;
		} catch (Throwable e) {
			LOG.error("Failed to init RestClient class: ", e);
			throw new RuntimeException("Failed to init RestClient class: ", e);
		}
	}
	
	public RestClient()
	{
		 client = HttpClientBuilder.create().build();
	}
	
	/**
	 * Fetch tenant by appId.
	 * @param appId
	 * @return
	 */
	public AppExtraEntity fetchTenantAndAppByAppId(String appId)
	{
		CloseableHttpResponse rsp = null;
		String postfix = new StringBuilder().append("/").append(appId).append("/get_tenant_info_by_app_base_id.do").toString();
		try {
			rsp = client.execute(new HttpGet(URI.create(base_url + postfix)));
			return getAppExtraEntity(rsp);
		} catch (Exception e) {
			LOG.error("Fetch Tenant by AppId failed: " + base_url + postfix, e);
			throw new RuntimeException("Fetch Tenants by url failed: " + base_url + postfix, e);
		}
		finally{
			if (rsp != null) {
				try {
					rsp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private AppExtraEntity getAppExtraEntity(CloseableHttpResponse rsp) throws ParseException, IOException {
		AppExtraEntity tenant = toEntity(rsp, TenantExtraEntity.class).getData();
		return tenant;
	}
	
	public static void main(String[] args) throws ParseException, IOException {
		String body = testString();
		Gson parser = new GsonBuilder().create();
		AppExtraEntity result = parser.fromJson(body, TenantExtraEntity.class).getData();
		System.out.println(">>> result: " + result);
	}
	
	/**
	 * Fetch all tenants and apps from Citic RestServer.
	 * @return
	 */
	public List<AppEntity> fetchAllTenantsApps()
	{
		CloseableHttpResponse rsp = null;
		String postfix = "/get_tenant_and_appBase_info.do";
		try {
			rsp = client.execute(new HttpGet(URI.create(base_url + postfix)));
			return listTenantsAndApps(rsp);
		} catch (Exception e) {
			LOG.error("Fetch Tenants by url failed: " + base_url + postfix, e);
			throw new RuntimeException("Fetch Tenants by url failed: " + base_url + postfix, e);
		}
		finally {
			if (rsp != null) {
				try {
					rsp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Retrieve both tenants and apps from Citic response.
	 * @param rsp
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	private List<AppEntity> listTenantsAndApps(CloseableHttpResponse rsp) throws ParseException, IOException {
		List<TenantEntity> tenants = toEntity(rsp, TenantsAppsEntity.class).getData();
		List<AppEntity> list = new ArrayList<>();
		for(TenantEntity tenant : tenants)
		{
			list.addAll(tenant.getApp_list());
			list.add(new AppEntity(tenant.getOrg_id(), tenant.getOrg_name()));// construct a new appEntity for Tenant itself.
		}
		LOG.debug("Tenants and Apps retrieved from citic response: " + list);
		return list;
	}

	private <T> T toEntity(CloseableHttpResponse rsp, Class<T> clz) throws ParseException, IOException {
		String entityStr = EntityUtils.toString(rsp.getEntity());
		LOG.debug("Entity string of citic response: " + entityStr);
		return gson.fromJson(entityStr, clz);
	}

	private static String testString() {
		try {
			StringBuilder sb = new StringBuilder();
			FileReader reader = new FileReader(new File("D:\\git\\RestServer\\src\\com\\asiainfo\\ocmanager\\monitor\\client\\citic_rsp.demo"));
			BufferedReader br = new BufferedReader(reader);
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void close()
	{
		try {
			client.close();
		} catch (IOException e) {
			LOG.error("Closing restClient error: ", e);
		}
	}
}
