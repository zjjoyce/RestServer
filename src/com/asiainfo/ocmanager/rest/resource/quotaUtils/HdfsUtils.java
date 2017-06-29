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
  public  Quota getHdfsQuota(Path path) {
      Quota hdfsQuota = getContenSummary(path);
      return hdfsQuota;
  }

  public static Quota getContenSummary(Path filePath) {
    Quota quota = new Quota("","","","","");
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
  public static void main(String[] args){
     Quota quota =  HdfsUtils.getContenSummary(new Path("/user/ocdc"));
     System.out.println(quota.getName()+quota.getAvailable()+quota.getDesc()+" "+quota.getSize());
  }


}
