package com.asiainfo.ocmanager.rest.resource.quotaUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by Allen on 2017/6/27.
 */
public class AmbariUtil {

    private static final String HDFS_PATH = "dfs.namenode.rpc-address";
    private static final String YARN_PATH = "yarn.resourcemanager.webapp.address";
    private static final String HBASE_PATH = "hbase.zookeeper.quorum";
    private static Properties prop;
    private static HttpURLConnection conn;
    private static BufferedReader reader;

    private static Logger logger = Logger.getLogger(AmbariUtil.class);


    public static String getUrl(String servicename){

        getProp();
        String result = "";
        String clustername = getClustername();
        String version = getTag();
//        String url = "http://"+(prop.getProperty("ambari.host"))+"/api/v1/clusters/"+clustername+"/configurations?type="+servicename+"-site&tag="+version;
        String url = "http://"+(prop.getProperty("ambari.host"))+"/api/v1/clusters/"+clustername+"/configurations?type="+servicename+"-site&tag=version1";
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
            logger.error(e.getMessage());
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


    public static String getTag(){

        getProp();
        String result="";
        String clustername = getClustername();
        String rmHost = getrmHost();
        String url = "http://"+(prop.getProperty("ambari.host"))+"/api/v1/clusters/"+clustername+"/hosts/"+rmHost+"/host_components/RESOURCEMANAGER?fields=HostRoles/actual_configs/capacity-scheduler";
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
                    fsResult += line;
                }
                JSONObject json = new JSONObject(fsResult);
                JSONObject json1 = new JSONObject(json.getString("HostRoles"));
                JSONObject json2 = new JSONObject(json1.getString("actual_configs"));
                JSONObject json3 = new JSONObject(json2.getString("capacity-scheduler"));
                String version = json3.getString("default");
                result = version;
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            conn.disconnect();
        }
        return result;
    }

    public static String getrmHost(){

        getProp();
        String result="";
        String clustername = getClustername();
        String url = "http://"+(prop.getProperty("ambari.host"))+"/api/v1/clusters/"+clustername+"/services/YARN/components/RESOURCEMANAGER";

        try {
            conn = (HttpURLConnection)new URL(url).openConnection();
            String user = "admin";
            String passwd = "admin";
            Base64 base64 = new Base64();
            String encoded = base64.encodeToString((user + ":" + passwd).getBytes("UTF-8"));
            conn.setRequestProperty("Authorization", "Basic " + encoded);
            int resultCode = conn.getResponseCode();
            List<String> list = new ArrayList<>();
            if (resultCode == 200) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                String fsResult = "";
                while ((line = reader.readLine()) != null) {
                    fsResult += line;
                }
                JSONObject json = new JSONObject(fsResult);
                JSONArray items = json.getJSONArray("host_components");
                for(int i = 0;i<items.length();i++){
                    String str = items.getString(i);
                    JSONObject json1 = new JSONObject(str);
                    String hostroles = json1.getString("HostRoles");
                    JSONObject json2 = new JSONObject(hostroles);
                    String rmhost = json2.getString("host_name");
                    list.add(rmhost);
                }
            }
            result = list.get(1);

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            conn.disconnect();
        }
        return result;
    }

    public static String getClustername(){

        getProp();
        String result="";
        String url = "http://"+(prop.getProperty("ambari.host"))+"/api/v1/clusters";

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
                    fsResult += line;
                }
                JSONObject json = new JSONObject(fsResult);
                JSONArray items = json.getJSONArray("items");
                String item = items.getString(0);
                JSONObject json1 = new JSONObject(item);
                String clusters = json1.getString("Clusters");
                JSONObject json2 = new JSONObject(clusters);
                String clustername = json2.getString("cluster_name");
                result = clustername;
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            conn.disconnect();
        }
        return result;
    }


    public static void getProp(){
        String classPath = new AmbariUtil().getClass().getResource("/").getPath();
        String currentClassesPath = classPath.substring(0, classPath.length() - 8)+ "ocmanager/WEB-INF/conf/config.properties";
        try{
            InputStream inStream = new FileInputStream(new File(currentClassesPath ));
            prop = new Properties();
            prop.load(inStream);
        }catch(IOException e){
            logger.error(e.getMessage());
        }
    }
}
