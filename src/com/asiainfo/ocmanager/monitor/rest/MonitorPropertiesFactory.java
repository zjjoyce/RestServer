package com.asiainfo.ocmanager.monitor.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import com.asiainfo.ocmanager.rest.constant.Constant;

public class MonitorPropertiesFactory {

	private static HashMap<String, String> map = new HashMap<String, String>();

	public static HashMap<String, String> getMonitorProperties() throws IOException {
		if (map.size() == 0) {
			synchronized (MonitorPropertiesFactory.class) {
				if (map.size() == 0) {
					// we deployed in tomcat the path is: <tomcat
					// home>/webapps/ocmanager/
					// it will get <tomcat home>/webapps/ocmanager/classes/
					String classPath = new MonitorPropertiesFactory().getClass().getResource("/").getPath();
					// remove classes/
					// the path will be <tomcat home>/webapps/ocmanager/
					String currentClassesPath = classPath.substring(0, classPath.length() - 8);
					InputStream inStream = new FileInputStream(
							new File(currentClassesPath + "conf/tenantMonitor.properties"));
					Properties prop = new Properties();
					prop.load(inStream);
					map.put(Constant.TENANT_MONITOR_PERIOD, prop.getProperty(Constant.TENANT_MONITOR_PERIOD));
					map.put(Constant.TENANT_MONITOR_URL, prop.getProperty(Constant.TENANT_MONITOR_URL));
				}
			}
		}

		return map;
	}

}
