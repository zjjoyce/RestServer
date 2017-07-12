package com.asiainfo.ocmanager.monitor.client;

import java.io.BufferedReader;
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
import com.asiainfo.ocmanager.monitor.entity.RspEntity;
import com.asiainfo.ocmanager.monitor.entity.TenantEntity;
import com.asiainfo.ocmanager.monitor.util.Configuration;
import com.asiainfo.ocmanager.rest.constant.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RestClient {
	
	private static final Logger LOG = Logger.getLogger(RestClient.class);
	
	private static String url;
	
	private CloseableHttpClient client;
	
	private Gson gson = new GsonBuilder().create();
	
	static
	{
		try {
			url = Configuration.getMonitorProperties().get(Constant.TENANT_MONITOR_URL);
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
	 * Fetch both tenants and apps from Citic rest api
	 * @return
	 */
	public List<AppEntity> fetchTenants()
	{
		CloseableHttpResponse rsp = null;
		try {
			rsp = client.execute(new HttpGet(URI.create(url)));
			return listTenantsAndApps(rsp);
		} catch (Exception e) {
			LOG.error("Fetch Tenants by url failed: " + url, e);
			throw new RuntimeException("Fetch Tenants by url failed: " + url, e);
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
		List<TenantEntity> tenants = toEntity(rsp).getData();
		List<AppEntity> list = new ArrayList<>();
		for(TenantEntity tenant : tenants)
		{
			list.addAll(tenant.getApp_list());
			list.add(new AppEntity(tenant.getOrg_id(), tenant.getOrg_name()));// construct a new appEntity for Tenant itself.
		}
		LOG.debug("Tenants and Apps retrieved from citic response: " + list);
		return list;
	}

	private RspEntity toEntity(CloseableHttpResponse rsp) throws ParseException, IOException {
		String entityStr = EntityUtils.toString(rsp.getEntity());
		LOG.debug("Entity string of citic response: " + entityStr);
		return gson.fromJson(entityStr, RspEntity.class);
	}

	public static void main(String[] args) {
		String testStr = new RestClient().testString();
		System.out.println(">>> demo input: " + testStr);
	}
	
	private String testString() {
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
