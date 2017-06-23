package com.asiainfo.ocmanager.rest.resource.quotaUtils;

import com.asiainfo.ocmanager.persistence.model.Quota;
import com.asiainfo.ocmanager.rest.resource.quotaUtils.AmbariUtils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;

/**
 * Created by yujin on 2017/6/23.
 */
public class YarnUtils {
  public static final String KEYTAB_FILE_KEY = "hdfs.keytab.file";
  public static final String USER_NAME_KEY = "hdfs.kerberos.principal";
  public static final String DEFAULT_FS = "fs.defaultFS";
  public static final String AUTHENTICATION = "hadoop.security.authentication";

  public static final Configuration conf = new Configuration();

  public Quota getYarnQuota(String queueName) {
    // set http client connection timeout
    System.setProperty("sun.net.client.defaultConnectTimeout", "6000");
    System.setProperty("sun.net.client.defaultReadTimeout", "6000");
    //get FS and yarn config
    String schedulerUrl = "http://10.247.11.9:8080/api/v1/clusters/zxjtcluster/services/YARN/components/RESOURCEMANAGER";

    String resourceManagerUrl = AmbariUtils.getProperties(schedulerUrl,"yarn.resourcemanager.webapp.address");

    Quota queueQuota = getQueueQuota(queueName);

    return queueQuota;
  }
  public static Quota getQueueQuota(String queueName){
    JSONObject jsonObject = new JSONObject();
    Quota quota = new Quota();

    try {
      jsonObject = AmbariUtils.getRsUrl("http://10.247.11.9:8080/api/v1/clusters/zxjtcluster/services/YARN/components/RESOURCEMANAGER");
      JSONObject queue = jsonObject.getJSONObject("root").getJSONObject("Queue").getJSONObject(queueName);
      String usedMem = queue.getString("")

    }catch (JSONException e){
      System.out.println(e);
    }
    return quota;
  }
}
