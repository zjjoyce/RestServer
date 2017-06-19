package com.asiainfo.ocmanager.rest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import com.asiainfo.ocmanager.rest.constant.Constant;

public class DFPropertiesFactory {

	private static HashMap<String, String> map = new HashMap<String, String>();

	public static HashMap<String, String> getDFProperties() throws IOException {

		if (map.size() == 0) {
			synchronized (DFPropertiesFactory.class) {
				if (map.size() == 0) {
					// we deployed in tomcat the path is: <tomcat home>/webapps/ocmanager/
					// it will get <tomcat home>/webapps/ocmanager/classes/
					String classPath = new DFPropertiesFactory().getClass().getResource("/").getPath();
					// remove classes/
					// the path will be <tomcat home>/webapps/ocmanager/
					String currentClassesPath = classPath.substring(0, classPath.length() - 8);
					InputStream inStream = new FileInputStream(
							new File(currentClassesPath + "conf/dataFactory.properties"));
					Properties prop = new Properties();
					prop.load(inStream);
					map.put(Constant.DATAFACTORY_URL, prop.getProperty(Constant.DATAFACTORY_URL));
					map.put(Constant.DATAFACTORY_TOKEN, prop.getProperty(Constant.DATAFACTORY_TOKEN));
					prop.load(inStream);
				}
			}
		}

		return map;

	}

}
