package com.asiainfo.ocmanager.monitor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class MonitorContextListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(MonitorContextListener.class);

	private MonitorManager monitor = new MonitorManager();

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		LOG.info("TenantNameMonitorContextListener -> contextInitialized");
		LOG.info("TenantNameMonitorContextListener -> syncer start");
		monitor.start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		LOG.info("TenantNameMonitorContextListener -> contextDestroyed");
		LOG.info("TenantNameMonitorContextListener -> syncer shutdown");
		monitor.stop();

	}

}
