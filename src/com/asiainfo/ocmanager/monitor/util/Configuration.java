package com.asiainfo.ocmanager.monitor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.asiainfo.ocmanager.rest.constant.Constant;
/**
 * Monitor configuration.
 * @author EthanWang
 *
 */
public class Configuration {
	private static final Logger LOG = Logger.getLogger(Configuration.class);
	
	private static HashMap<String, String> map = new HashMap<String, String>();

	public static HashMap<String, String> getMonitorProperties() throws IOException {
		if (map.size() == 0) {
			synchronized (Configuration.class) {
				if (map.size() == 0) {
					init();
				}
			}
		}
		return map;
	}

	private static void init() throws IOException {
		InputStream inStream = null;
		try {
			// we deployed in tomcat the path is: <tomcat
			// home>/webapps/ocmanager/
			// it will get <tomcat home>/webapps/ocmanager/classes/
			String classPath = new Configuration().getClass().getResource("/").getPath();
			// remove classes/
			// the path will be <tomcat home>/webapps/ocmanager/
			String currentClassesPath = classPath.substring(0, classPath.length() - 8);
			inStream = new FileInputStream(
					new File(currentClassesPath + "conf/tenantMonitor.properties"));
			Properties prop = new Properties();
			prop.load(inStream);
			map.put(Constant.TENANT_MONITOR_ENABLE, prop.getProperty(Constant.TENANT_MONITOR_ENABLE));
			map.put(Constant.TENANT_MONITOR_PERIOD, prop.getProperty(Constant.TENANT_MONITOR_PERIOD));
			map.put(Constant.TENANT_MONITOR_URL, prop.getProperty(Constant.TENANT_MONITOR_URL));
		} catch (Exception e) {
			LOG.error("init config failed: ", e);
			throw e;
		}
		finally {
			if (inStream != null) {
				inStream.close();
			}
		}
	}		

}
