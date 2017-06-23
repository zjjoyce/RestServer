package com.asiainfo.ocmanager.rest.resource.quotaUtils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
    public static void main(String[] args)throws Exception {
        restClient rc = new restClient();
        String hiveDir  = "/hive/test.db";
        String hiveQueue = "test";
        String dirResult = null;
        String queueResult = null;
        String hdfsUrl = "http://10.247.11.9:8088/ws/v1/cluster/scheduler";
        try{
            System.out.println(rc.get(hdfsUrl));
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
