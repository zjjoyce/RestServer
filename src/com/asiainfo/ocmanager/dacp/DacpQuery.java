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

    /*private static HostnameVerifier hv = new HostnameVerifier() {
        public boolean verify(String urlHostName, SSLSession session) {
            System.out.println("Warning: URL Host: " + urlHostName + " vs. "
                    + session.getPeerHost());
            return true;
        }
    };

    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
                .getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
                .getSocketFactory());
    }

    static class miTM implements javax.net.ssl.TrustManager,
            javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }*/
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
        conn.setDefaultHostnameVerifier(Authentication.hv);
        conn = (HttpsURLConnection) new URL(dfRestUrl).openConnection();
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
