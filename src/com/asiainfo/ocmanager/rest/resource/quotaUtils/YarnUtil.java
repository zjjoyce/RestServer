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
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by Allen on 2017/6/27.
 */
public class YarnUtil {

    private static HttpURLConnection conn;
    private static BufferedReader reader;

    private static Logger logger = Logger.getLogger(YarnUtil.class);
    public static List<Quota> getYarnData(String queuename){

        String yarnurl = AmbariUtil.getUrl("yarn");

        String restresult = "";
        String yarnresturl = "http://"+yarnurl+"/ws/v1/cluster/scheduler";

        Quota memoryquota = new Quota("yarnQueueQuota","","","","queue memory quota(GB)");
        Quota vcoresquota = new Quota("queueVcoreQuota","","","","queue vcore qutoa(GB)");

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
            logger.error(e.getMessage());
            memoryquota.setName("queueMemoryQuota");
            memoryquota.setUsed("-1");
            vcoresquota.setName("queueVcoreQuota");
            vcoresquota.setUsed("-1");
            List<Quota> result = new ArrayList<Quota>();
            result.add(memoryquota);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            memoryquota.setName("queueMemoryQuota");
            memoryquota.setUsed("-1");
            vcoresquota.setName("queueVcoreQuota");
            vcoresquota.setUsed("-1");
            List<Quota> result = new ArrayList<Quota>();
            result.add(memoryquota);
            return result;
        }

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
                    int memoryGb = Integer.valueOf(memory) / 1024;
                    memoryquota.setName("queueMemoryQuota");
                    memoryquota.setUsed(String.valueOf(memoryGb));
                    String vCores = json7.getString("vCores");
                    vcoresquota.setName("queueVcoreQuota");
                    vcoresquota.setUsed(vCores);
                }
            }
        } catch (JSONException e) {
            logger.error(e.getMessage());
            memoryquota.setName("queueMemoryQuota");
            memoryquota.setUsed("-1");
            vcoresquota.setName("queueVcoreQuota");
            vcoresquota.setUsed("-1");
            List<Quota> result = new ArrayList<Quota>();
            result.add(memoryquota);
            return result;
        }
        List<Quota> result = new ArrayList<Quota>();
        result.add(memoryquota);
        return result;
    }
}
