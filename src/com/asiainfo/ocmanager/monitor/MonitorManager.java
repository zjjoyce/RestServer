package com.asiainfo.ocmanager.monitor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.asiainfo.ocmanager.monitor.client.RestClient;
import com.asiainfo.ocmanager.monitor.entity.AppEntity;
import com.asiainfo.ocmanager.monitor.util.Configuration;
import com.asiainfo.ocmanager.rest.constant.Constant;

/**
 * Used to start and stop tenant sych-up payload.
 * @author EthanWang
 *
 */
public class MonitorManager {

	private static final Logger LOG = Logger.getLogger(MonitorManager.class);

	private ScheduledExecutorService scheduler;

	public void start() {
		try {
			if (!enabled()) {
				LOG.warn("MonitorManager disabled!");
				return;
			}
			scheduler = Executors.newScheduledThreadPool(1);
			String period = Configuration.getMonitorProperties().get(Constant.TENANT_MONITOR_PERIOD);
			scheduler.scheduleAtFixedRate(new TenantPayload(), 60, Long.valueOf(period), TimeUnit.SECONDS);
			LOG.info("MonitorManager start succeeded with period: " + Integer.parseInt(period));
		} catch (IOException e) {
			LOG.error("MonitorManager start error: ", e);
			throw new RuntimeException("MonitorManager start error: ", e);
		}
	}
	
	/**
	 * Is monitor enabled.
	 * @return
	 * @throws IOException
	 */
	private boolean enabled() throws IOException {
		String enabled = Configuration.getMonitorProperties().get(Constant.TENANT_MONITOR_ENABLE);
		return enabled.equalsIgnoreCase("true");
	}

	public void stop() {
		scheduler.shutdown();
		LOG.info("MonitorManager stopped!");
	}
	
	/**
	 * Used to sych up tenant between CITIC and RestServer.
	 * @author EthanWang
	 *
	 */
	private class TenantPayload implements Runnable
	{
		private final Logger LOG = Logger.getLogger(TenantPayload.class);
		
		private TenantCacheManager cache;
		
		public TenantPayload()
		{
			cache = TenantCacheManager.getInstance();
		}
		
		@Override
		public void run() {
			RestClient restCli = null;
			try {
				restCli = new RestClient();
				LOG.info("Tenant sych-up monitor starting... Tenants in cache: " + cache.getAllTenants());
				List<AppEntity> tenants = restCli.fetchAllTenantsApps();
				cache.pull();
				cache.updateCache(tenants);
				cache.commit();
				LOG.info("Tenant sych-up monitor finished, Tenants in cache: " + cache.getAllTenants());
			} catch (Exception e) {
				LOG.error("Error while sych-up Tenants: ", e);
			}
			finally{
				if (restCli != null) {
					restCli.close();
				}
			}
		}
	}
	
}
