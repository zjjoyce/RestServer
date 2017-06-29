package com.asiainfo.ocmanager.rest.resource.quotaUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.asiainfo.ocmanager.persistence.model.Quota;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by Allen on 2017/6/27.
 */
public class YarnUtil {

    private static HttpURLConnection conn;
    private static BufferedReader reader;

    public static List<Quota> getYarnData(String queuename){

        String yarnurl = AmbariUtil.getUrl("yarn");

        String restresult = "";
        String yarnresturl = "http://"+yarnurl+"/ws/v1/cluster/scheduler";

        try {
            conn = (HttpURLConnection)new URL(yarnresturl).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            int code = conn.getResponseCode();
            if(code == 200){
                reader = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                String line = "";
                String fsResult = "";
                while ((line = reader.readLine()) != null) {
                    fsResult += line;
                }
                restresult = fsResult;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Quota memoryquota = new Quota("","","","","");
        Quota vcoresquota = new Quota("","","","","");
        try {
            JSONObject json1 = new JSONObject(restresult);
            String scheduler = json1.getString("scheduler");
            JSONObject json2 = new JSONObject(scheduler);
            String schedulerInfo = json2.getString("schedulerInfo");
            JSONObject json3 = new JSONObject(schedulerInfo);
            String queues = json3.getString("queues");
            JSONObject json4 = new JSONObject(queues);
            String queue = json4.getString("queue");
            JSONArray json5 = new JSONArray(queue);
            for(int i = 0;i<json5.length();i++){
                String value = json5.getString(i);
                JSONObject json6 = new JSONObject(value);
                String queueName = json6.getString("queueName");
                if(queuename.equals(queueName)){
                    String resourcesUsed = json6.getString("resourcesUsed");
                    JSONObject json7 = new JSONObject(resourcesUsed);
                    String memory = json7.getString("memory");
                    memoryquota.setName("yarn memoryUsed");
                    memoryquota.setUsed(memory);
                    String vCores = json7.getString("vCores");
                    vcoresquota.setName("yarn vCoresUsed");
                    vcoresquota.setUsed(vCores);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<Quota> result = new ArrayList<Quota>();
        result.add(memoryquota);
        result.add(vcoresquota);
        return result;
    }
}
