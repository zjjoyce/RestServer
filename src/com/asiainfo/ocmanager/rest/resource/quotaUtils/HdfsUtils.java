package com.asiainfo.ocmanager.rest.resource.quotaUtils;

import org.apache.commons.codec.binary.Base64;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.security.UserGroupInformation;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.mortbay.util.ajax.JSON;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;import java.io.IOException;
import com.asiainfo.ocmanager.persistence.model.Quota;
import com.asiainfo.ocmanager.rest.resource.quotaUtils.AmbariUtils;
/**
 * Created by yujin on 2017/6/23.
 */
public class HdfsUtils {

  public static final String KEYTAB_FILE_KEY = "hdfs.keytab.file";
  public static final String USER_NAME_KEY = "hdfs.kerberos.principal";
  public static final String DEFAULT_FS = "fs.defaultFS";
  public static final String AUTHENTICATION = "hadoop.security.authentication";

  public static final Configuration conf = new Configuration();

  public  Quota getHdfsQuota(Path path) {
    // set http client connection timeout
    System.setProperty("sun.net.client.defaultConnectTimeout", "6000");
    System.setProperty("sun.net.client.defaultReadTimeout", "6000");
    // set kerbers conf
    System.setProperty("java.security.krb5.conf", "conf/krb5.conf");
    //get FS and yarn config
    String hdfsConfUrl = "http://10.247.11.9:8080/api/v1/clusters/zxjtcluster/configurations?type=hdfs-site&tag=version1";
    //String rsUrl = "http://10.247.11.9:8088/ws/v1/cluster/scheduler";
    String nnurl = AmbariUtils.getProperties(hdfsConfUrl,"dfs.namenode.rpc-address");

    //设置keytab文件的路径
    conf.set(KEYTAB_FILE_KEY, "conf/shixiuru.keytab");
    conf.set(DEFAULT_FS, nnurl);
    conf.set(AUTHENTICATION, "kerberos");

    try {
      UserGroupInformation.setConfiguration(conf);
      UserGroupInformation.loginUserFromKeytab("shixiuru@EXAMPLE.COM", "conf/shixiuru.keytab");
    } catch (IOException e) {
      System.out.println(e);
    }

    Quota hdfsQuota = getContenSummary(path);
    return hdfsQuota;
  }

  public static Quota getContenSummary(Path filePath) {
    Quota quota = new Quota();
    try {
      FileSystem fs = FileSystem.get(conf);
      ContentSummary contentSum = fs.getContentSummary(filePath);
      Long spaceConsumed = contentSum.getSpaceConsumed();
      Long spaceQuota = contentSum.getSpaceQuota();

      quota.setName("hdfsQuota");
      quota.setUsed(String.valueOf(spaceConsumed));
      quota.setSize(String.valueOf(spaceQuota));
      quota.setDesc("hive db hdfs quota");
      quota.setAvailable(String.valueOf(spaceQuota - spaceConsumed));
    } catch (IOException e) {
      System.out.println(e);
    }
    return quota;
  }


}
