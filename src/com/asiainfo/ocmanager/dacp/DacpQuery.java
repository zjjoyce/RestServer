package com.asiainfo.ocmanager.dacp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
/**
 * Created by Allen on 2017/7/3.
 * 调用DataFoundry API获取数据
 */
public class DacpQuery {
    private static Log logger = LogFactory.getLog(DacpQuery.class);
    private static HttpsURLConnection conn;
    private static BufferedReader reader;

    public static String GetData(String name){

        String restresult = "";
        try{
            String url = Property.getDFProperties().get(Property.DATAFOUNDRY_URL_DACP);
            String token = Property.getDFProperties().get(Property.DATAFOUNDRY_TOKEN_DACP);
            String dfRestUrl = url + "/oapi/v1/namespaces/"+name+"/backingserviceinstances";
            Authentication.trustAllHttpsCertificates();
            conn = (HttpsURLConnection) new URL(dfRestUrl).openConnection();
            conn.setDefaultHostnameVerifier(Authentication.hv);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization","bearer " + token);
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                restresult += line;
            }
        }catch (Exception e){
            logger.error("IOException :" +e);
            e.printStackTrace();
        }

        return restresult;
    }
}
