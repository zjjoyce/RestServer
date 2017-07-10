package com.asiainfo.ocmanager.monitor.rest;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class TenantNameMonitorContextListener implements ServletContextListener {

	private static Logger logger = Logger.getLogger(TenantNameMonitorContextListener.class);

	private TenantNameMonitor TNM = new TenantNameMonitor();

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("TenantNameMonitorContextListener -> contextInitialized");
		logger.info("TenantNameMonitorContextListener -> syncer start");
		TNM.taskBegin();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("TenantNameMonitorContextListener -> contextDestroyed");
		logger.info("TenantNameMonitorContextListener -> syncer shutdown");
		TNM.taskEnd();

	}

}
