package com.asiainfo.ocmanager.rest.resource.quotaUtils;

import com.asiainfo.ocmanager.rest.utils.DFPropertiesFoundry;
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
import java.util.ArrayList;
import java.util.List;

import com.asiainfo.ocmanager.persistence.model.Quota;
import com.asiainfo.ocmanager.rest.resource.quotaUtils.AmbariUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * Created by yujin on 2017/6/23.
 */
public class HdfsUtils {
    private static final Log log = LogFactory.getLog(HdfsUtils.class);

  public static final String KEYTAB_FILE_KEY = "hdfs.keytab.file";
  public static final String USER_NAME_KEY = "hdfs.kerberos.principal";
  public static final String DEFAULT_FS = "fs.defaultFS";
  public static final String AUTHENTICATION = "hadoop.security.authentication";

  public static final Configuration conf = new Configuration();

  static{
      String currentClassPath = new HdfsUtils().getClass().getResource("/").getPath();
      String  keytabPath= currentClassPath.substring(0, currentClassPath.length() - 8) + "conf/shixiuru.keytab";
      String  krbPath = currentClassPath.substring(0,currentClassPath.length() - 8) + "conf/krb5.conf";
      log.info("keytab path is :" + keytabPath);
      log.info("krb path is :" +krbPath);
      // set http client connection timeout
      System.setProperty("sun.net.client.defaultConnectTimeout", "6000");
      System.setProperty("sun.net.client.defaultReadTimeout", "6000");
      // set kerbers conf
      System.setProperty("java.security.krb5.conf", krbPath);
      //get FS and yarn config
      String hdfsConfUrl = "http://10.247.11.9:8080/api/v1/clusters/zxjtcluster/configurations?type=hdfs-site&tag=version1";
      //String rsUrl = "http://10.247.11.9:8088/ws/v1/cluster/scheduler";
      String nnurl = AmbariUtils.getProperties(hdfsConfUrl,"dfs.namenode.rpc-address");

      //设置keytab文件的路径
      conf.set(KEYTAB_FILE_KEY, keytabPath);
      conf.set(DEFAULT_FS, nnurl);
      conf.set(AUTHENTICATION, "kerberos");
      try {
          UserGroupInformation.setConfiguration(conf);
          UserGroupInformation.loginUserFromKeytab("shixiuru@EXAMPLE.COM", keytabPath);
      } catch (IOException e) {
          System.out.println(e);
      }
  }
  public  List<Quota> getHdfsQuota(Path path) {
      List<Quota> hdfsQuota = getContenSummary(path);
      return hdfsQuota;
  }

  public static List<Quota> getContenSummary(Path filePath) {
      List quotaList = new ArrayList<Quota>();
    Quota spaceQuota = new Quota("hdfs space quota","","","","hdfs space quota");
    Quota fileQuota = new Quota("hdfs space quota","","","","hdfs space quota");

      try {
      FileSystem fs = FileSystem.get(conf);
      ContentSummary contentSum = fs.getContentSummary(filePath);
      Long spaceConsumed = contentSum.getSpaceConsumed();
      Long spaceQuota1 = contentSum.getSpaceQuota();
      spaceQuota.setUsed(String.valueOf(spaceConsumed));
      spaceQuota.setSize(String.valueOf(spaceQuota));
      spaceQuota.setAvailable(String.valueOf(spaceQuota1 - spaceConsumed));
      fileQuota.setUsed(String.valueOf(contentSum.getFileCount()));
      fileQuota.setSize(String.valueOf(contentSum.getQuota()));
      fileQuota.setAvailable(String.valueOf(contentSum.getQuota() - contentSum.getFileCount()));
      quotaList.add(fileQuota);
      quotaList.add(spaceQuota);
    } catch (IOException e) {
      System.out.println(e);
    }
    return quotaList;
  }


}
