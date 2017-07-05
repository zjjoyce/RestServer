package com.asiainfo.ocmanager.dacp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
/**
 * Created by Allen on 2017/7/3.
 * 获取Token认证参数
 */
public class Property {
    public final static String DATAFOUNDRY_URL_DACP = "dataFounday.url.dacp";
    public final static String DATAFOUNDRY_TOKEN_DACP = "dataFoundry.token.dacp";
    private static HashMap<String, String> map = new HashMap<String, String>();

    public static HashMap<String, String> getDFProperties() throws IOException {
        if (map.size() == 0) {
            synchronized (Property.class) {
                if (map.size() == 0) {
                    String classPath = new Property().getClass().getResource("/").getPath();
                    String currentClassesPath = classPath.substring(0, classPath.length() - 8)+ "conf/dataFoundry.properties";
                    System.out.println(currentClassesPath);
                    InputStream inStream = new FileInputStream(new File(currentClassesPath ));
                    Properties prop = new Properties();
                    prop.load(inStream);
                    map.put(DATAFOUNDRY_URL_DACP, prop.getProperty(DATAFOUNDRY_URL_DACP));
                    map.put(DATAFOUNDRY_TOKEN_DACP, prop.getProperty(DATAFOUNDRY_TOKEN_DACP));
                    prop.load(inStream);
                }
            }
        }
//        System.out.println(map);
        return map;
    }
}
