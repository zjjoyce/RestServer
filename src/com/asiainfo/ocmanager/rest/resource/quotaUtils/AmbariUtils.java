package com.asiainfo.ocmanager.rest.resource.quotaUtils;

import java.io.IOException;
import org.apache.commons.codec.binary.Base64;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.security.UserGroupInformation;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yujin on 2017/6/23.
 */
public class AmbariUtils {
  /*
    @ get DEFAULT_FS uri
    @param ambariUrl
   */
  public static String getProperties(String ambariString,String property) {
    String result = "";
    HttpURLConnection conn = null;
    try {
      URL url = new URL(ambariString);
      conn = (HttpURLConnection) url.openConnection();
      String user = "admin";
      String passwd = "admin";
      Base64 base64 = new Base64();
      String encoded = base64.encodeToString((user + ":" + passwd).getBytes("UTF-8"));
      conn.setRequestProperty("Authorization", "Basic " + encoded);
      int resultCode = conn.getResponseCode();
      if (resultCode == 200) {
        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = "";
        String fsResult = "";
        while ((line = br.readLine()) != null) {
          fsResult += line;
        }
        System.out.println(fsResult);
        JSONObject json = new JSONObject(fsResult);
//                JSONObject items = json.getJSONObject("items");
        JSONArray items = json.getJSONArray("items");
        String itemStr = items.toString().replace("[", "").replace("]", "");
        JSONObject newItem = new JSONObject(itemStr);
        JSONObject properties = newItem.getJSONObject("properties");
        result = properties.getString(property);
      }
    } catch (Exception e) {
      System.out.println("catch exception");
      System.out.println(e);
    } finally {
      conn.disconnect();

    }
    return result;
  }

  public static JSONObject getRsUrl(String ambariString) {
    JSONObject jsonObject = new JSONObject();
    String result = "";
    HttpURLConnection conn = null;
    try {
      URL url = new URL(ambariString);
      conn = (HttpURLConnection) url.openConnection();
      String user = "admin";
      String passwd = "admin";
      Base64 base64 = new Base64();
      String encoded = base64.encodeToString((user + ":" + passwd).getBytes("UTF-8"));
      conn.setRequestProperty("Authorization", "Basic " + encoded);
      int resultCode = conn.getResponseCode();
      if (resultCode == 200) {
        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = "";
        String fsResult = "";
        while ((line = br.readLine()) != null) {
          fsResult += line;
        }
        System.out.println(fsResult);
        jsonObject = new JSONObject(fsResult);
      }
    } catch (Exception e) {
      System.out.println("get ambari json object error");
      System.out.println(e);
    } finally {
      conn.disconnect();

    }
    return jsonObject;
  }
}
