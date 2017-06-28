package com.asiainfo.ocmanager.rest.resource.quotaUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by Allen on 2017/6/27.
 */
public class AmbariUtil {

    private static final String host = "10.247.11.9:8080";
    private static final String HDFS_PATH = "dfs.namenode.rpc-address";
    private static final String YARN_PATH = "yarn.resourcemanager.webapp.address";
    private static final String HBASE_PATH = "hbase.zookeeper.quorum";
    private static HttpURLConnection conn;
    private static BufferedReader reader;

    public static String getUrl(String servicename){

        String result = "";
        String url = "http://"+(host)+"/api/v1/clusters/zxjtcluster/configurations?type="+servicename+"-site&tag=version1";

        try {
            conn = (HttpURLConnection)new URL(url).openConnection();
            String user = "admin";
            String passwd = "admin";
            Base64 base64 = new Base64();
            String encoded = base64.encodeToString((user + ":" + passwd).getBytes("UTF-8"));
            conn.setRequestProperty("Authorization", "Basic " + encoded);
            int resultCode = conn.getResponseCode();
            if (resultCode == 200) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                String fsResult = "";
                while ((line = reader.readLine()) != null) {
                    //System.out.println(line);
                    fsResult += line;
                }
                JSONObject json = new JSONObject(fsResult);
                JSONArray items = json.getJSONArray("items");
                String itemStr = items.toString().replace("[","").replace("]","");
                JSONObject newItem = new JSONObject(itemStr);
                JSONObject properties = newItem.getJSONObject("properties");
                result = properties.getString(getparametername(servicename));
            }

        } catch (Exception e) {
            System.out.println("catch exception");
            System.out.println(e);
        } finally {
            conn.disconnect();

        }
        return result;

    }
    public static String getparametername(String servicename){

        String parametername = null;
        if(servicename=="hdfs"){
            parametername = HDFS_PATH;
        }else if(servicename=="yarn"){
            parametername = YARN_PATH;
        }else if(servicename=="hbase"){
            parametername = HBASE_PATH;
        }

        return parametername;
    }
}
