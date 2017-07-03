package com.asiainfo.ocmanager.rest.resource.quotaUtils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yujin on 2017/6/16.
 */
public class restClient {
    private static final Log log = LogFactory.getLog(restClient.class);

    public static String operate(String service, Map params) throws Exception {
        String url = "";
        if(url==null||"".equals(url)) throw new Exception("url is not defined!");

        return null;

    }
    public static String post(String url, Map params) throws Exception {
        String result = null;
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        if (params != null) {
            Iterator keys = params.keySet().iterator();
            while (keys.hasNext()) {
                String key = String.valueOf(keys.next());
                method.addParameter(key, String.valueOf(params.get(key)));
            }
            method.getParams().setContentCharset("UTF-8");
            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
                throw new Exception("request failed:" + HttpStatus.getStatusText(statusCode));
            } else {
                result = method.getResponseBodyAsString();
            }
        }
        return result;

    }
    public static String get(String url) throws Exception {
        String result = null;

        HttpClient client = new HttpClient();

        GetMethod method = new GetMethod(url);
        int statusCode = client.executeMethod(method);

        if (statusCode != HttpStatus.SC_OK) {
            throw new Exception("request failed:" + HttpStatus.getStatusText(statusCode));
        } else {
            result = method.getResponseBodyAsString();
        }
        return result;
    }
    public static String delete(String url)throws Exception{
        String result = null;
        HttpClient client = new HttpClient();
        DeleteMethod method = new DeleteMethod(url);

        method.getParams().setContentCharset("UTF-8");
        int statusCode = client.executeMethod(method);

        if (statusCode != HttpStatus.SC_OK) {
            throw new Exception("请求失败:"+HttpStatus.getStatusText(statusCode));
        }else {
            result = method.getResponseBodyAsString();
        }

        return result;
    }
    public static void main(String[] args)throws Exception {
        restClient rc = new restClient();
        String hiveDir  = "/hive/test.db";
        String hiveQueue = "test";
        String dirResult = null;
        String queueResult = null;
        String url = "http://10.247.33.80:8080/dacp/dps/tenant/all";
        Map params = new HashMap();
//        String json = "{   \"userinfo\":[     {\"username\":\"aaa\",     \"usercnname\":\"bbb\",     \"password\":\"sss\",     \"phone\":\"ddd\",     \"qq\":\"www\",     \"mail\":\"fff\",     \"msn\":\"ddd\"     },     {\"username\":\"aaa\",     \"usercnname\":\"bbb\",     \"password\":\"sss\",     \"phone\":\"ddd\",     \"qq\":\"www\",     \"mail\":\"fff\",     \"msn\":\"ddd\" }   ], \"team\":[     { \"xmlid\":\"1a65a0f98a614fb28f6bbb5aebac3043\",       \"team_code\":\"T004\",     \"team_name\":\"测试团队\",     \"team_type\":\"1\",     \"start_date\":\"2017-01-14 11:57:42\",     \"state\":\"ON\",     \"icon_path\":\"/dacp-res/dps/img/team1.png\",     \"remark\":\"xxx\"     },           { \"xmlid\":\"2\",       \"team_code\":\"aaa\",     \"team_name\":\"bbb\",     \"team_type\":\"1\",     \"start_date\":\"aaa\",     \"state\":\"bbb\",     \"icon_path\":\"1\",     \"remark\":\"xxx\" }   ], \"database\":[     { \"xmlid\":\"35893df0edbf05f54d0481150d5f54a8\",       \"dbname\":\"oracle\",     \"cnname\":\"oracle\",     \"driverclassname\":\"oracle.jdbc.driver.OracleDriver\",     \"url\":\"jdbc:oracle:thin:@10.5.1.21:1521:orcl\",     \"username\":\"dps\",     \"password\":\"dps\",     \"remark\":\"xxx\"     },           { \"xmlid\":\"1\",       \"dbname\":\"aaa\",     \"cnname\":\"bbb\",     \"driverclassname\":\"1\",     \"url\":\"aaa\",     \"username\":\"bbb\",     \"password\":\"1\",     \"remark\":\"xxx\" }        ], \"transdatabase\":[     { \"dbname\":\"hive\",       \"cnname\":\"hive\",     \"dbtype\":\"hive\",     \"username\":\"hive\",     \"password\":\"hive\",     \"url\":\"jdbc:hive2://223.105.3.187:10000\",     \"team_code\":\"T003\",     \"state\":\"on\",     \"driverclassname\":\"org.apache.hive.jdbc.HiveDriver\"     },          {\"dbname\":\"1\",     \"cnname\":\"aaa\",     \"dbtype\":\"bbb\",     \"username\":\"1\",     \"password\":\"aaa\",     \"url\":\"bbb\",     \"team_code\":\"1\",     \"state\":\"1\",     \"driverclassname\":\"1\" }], \"hadoopaudit\":[     { \"team_code\":\"T003\",       \"type\":\"YARN\",       \"res_cfg\":{         \"queue_name\":\"asiainfo\",         \"capacity\":\"0.6\",         \"maximum_capacity\":\"0.8\",         \"state\":\"RUNNING\"       },       \"status\":\"1\"     },          { \"team_code\":\"T003\",       \"type\":\"HDFS\",       \"res_cfg\":{         \"folderPath\":\"/tmp/\",         \"storageSpaceQuota\":\"325900\",         \"nameSpaceQuota\":\"1000\"       },       \"status\":\"1\"     },          { \"team_code\":\"T003\",       \"type\":\"HIVE\",       \"res_cfg\":{         \"databaseName\":\"hive\"       },       \"status\":\"1\" }       ] } ";
      String json = "{   \"userinfo\":[     {\"username\":\"aaa\",     \"usercnname\":\"bbb\",     \"password\":\"sss\",     \"phone\":\"ddd\",     \"qq\":\"www\",     \"mail\":\"fff\",     \"msn\":\"ddd\"     },     {\"username\":\"aaa\",     \"usercnname\":\"bbb\",     \"password\":\"sss\",     \"phone\":\"ddd\",     \"qq\":\"www\",     \"mail\":\"fff\",     \"msn\":\"ddd\" }   ], \"team\":[     { \"xmlid\":\"1a65a0f98a614fb28f6bbb5aebac3043\",       \"team_code\":\"T004\",     \"team_name\":\"测试团队\",     \"team_type\":\"1\",     \"start_date\":\"2017-01-14 11:57:42\",     \"state\":\"ON\",     \"icon_path\":\"/dacp-res/dps/img/team1.png\",     \"remark\":\"xxx\"     },           { \"xmlid\":\"2\",       \"team_code\":\"aaa\",     \"team_name\":\"bbb\",     \"team_type\":\"1\",     \"start_date\":\"aaa\",     \"state\":\"bbb\",     \"icon_path\":\"1\",     \"remark\":\"xxx\" }   ], \"database\":[     { \"xmlid\":\"35893df0edbf05f54d0481150d5f54a8\",       \"dbname\":\"oracle\",     \"cnname\":\"oracle\",     \"driverclassname\":\"oracle.jdbc.driver.OracleDriver\",     \"url\":\"jdbc:oracle:thin:@10.5.1.21:1521:orcl\",     \"username\":\"dps\",     \"password\":\"dps\",     \"remark\":\"xxx\"     },           { \"xmlid\":\"1\",       \"dbname\":\"aaa\",     \"cnname\":\"bbb\",     \"driverclassname\":\"1\",     \"url\":\"aaa\",     \"username\":\"bbb\",     \"password\":\"1\",     \"remark\":\"xxx\" }        ], \"transdatabase\":[     { \"dbname\":\"hive\",       \"cnname\":\"hive\",     \"dbtype\":\"hive\",     \"username\":\"hive\",     \"password\":\"hive\",     \"url\":\"jdbc:hive2://223.105.3.187:10000\",     \"team_code\":\"T003\",     \"state\":\"on\",     \"driverclassname\":\"org.apache.hive.jdbc.HiveDriver\"     },          {\"dbname\":\"1\",     \"cnname\":\"aaa\",     \"dbtype\":\"bbb\",     \"username\":\"1\",     \"password\":\"aaa\",     \"url\":\"bbb\",     \"team_code\":\"1\",     \"state\":\"1\",     \"driverclassname\":\"1\" }], \"hadoopaudit\":[     { \"team_code\":\"T003\",       \"type\":\"YARN\",       \"res_cfg\":{         \"queue_name\":\"asiainfo\",         \"capacity\":\"0.6\",         \"maximum_capacity\":\"0.8\",         \"state\":\"RUNNING\"       },       \"status\":\"1\"     },          { \"team_code\":\"T003\",       \"type\":\"HDFS\",       \"res_cfg\":{         \"folderPath\":\"/tmp/\",         \"storageSpaceQuota\":\"325900\",         \"nameSpaceQuota\":\"1000\"       },       \"status\":\"1\"     },          { \"team_code\":\"T003\",       \"type\":\"HIVE\",       \"res_cfg\":{         \"databaseName\":\"hive\"       },       \"status\":\"1\" }       ] } ";

      params.put("info",json);
        try{
            System.out.println(rc.post(url,params));
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
