package com.asiainfo.ocmanager.monitor.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.asiainfo.ocmanager.persistence.model.Tenant;
import com.asiainfo.ocmanager.rest.constant.Constant;
import com.asiainfo.ocmanager.rest.resource.utils.TenantPersistenceWrapper;

public class TenantNameMonitor {

	private static Logger logger = Logger.getLogger(TenantNameMonitor.class);

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public void taskBegin() {

		final Runnable tenantNameSyncer = new Runnable() {
			public void run() {
				try {
					logger.info("start task");
				} catch (Exception e) {
					logger.info("TenantNameMonitor -> " + e.getMessage());
				}
			}
		};
		try {
			String period = MonitorPropertiesFactory.getMonitorProperties().get(Constant.TENANT_MONITOR_PERIOD);
			logger.info("TenantNameMonitor -> get period - " + Integer.parseInt(period));
			scheduler.scheduleAtFixedRate(tenantNameSyncer, 300, Integer.parseInt(period), TimeUnit.SECONDS);
		} catch (IOException e) {
			logger.info("TenantNameMonitor -> " + e.getMessage());
			e.printStackTrace();
		}
		
	}

	public void taskEnd() {
		scheduler.shutdown();
	}
	
	
	private void syncTenantName(){
		HashMap<String, Tenant> tenantsMappings = new HashMap<String, Tenant>();
		List<Tenant> tenants = TenantPersistenceWrapper.getAllRootTenants();
		for(Tenant t: tenants){
			tenantsMappings.put(t.getId(), t);
		}
		
		
		
	}
	
	
//	private List<String> getTenantInfo(){
//		
//	}
	
	
}
