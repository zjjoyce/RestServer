package com.asiainfo.ocmanager.dacp;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
/**
 * Created by Allen on 2017/7/3.
 * 调用DataFoundry API获取数据
 */
public class DacpQuery {

    private static HttpsURLConnection conn;
    private static BufferedReader reader;

    public static String GetData(String name)throws Exception {

        String url = Property.getDFProperties().get(Property.DATAFOUNDRY_URL_DACP);
        String token = Property.getDFProperties().get(Property.DATAFOUNDRY_TOKEN_DACP);
        String dfRestUrl = url + "/oapi/v1/namespaces/"+name+"/backingserviceinstances";
        /*System.out.println(url);
        System.out.println(token);
        System.out.println(dfRestUrl);*/
        /*HttpPost httpPost = new HttpPost(dfRestUrl);
        httpPost.addHeader("Content-type", "application/json");
        httpPost.addHeader("Authorization", "bearer " + token);*/

        String restresult = "";
        Authentication.trustAllHttpsCertificates();
        conn = (HttpsURLConnection) new URL(dfRestUrl).openConnection();
        conn.setDefaultHostnameVerifier(Authentication.hv);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization","bearer " + token);
        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            //System.out.println(line);
            restresult += line;
        }

        return restresult;
    }
}
