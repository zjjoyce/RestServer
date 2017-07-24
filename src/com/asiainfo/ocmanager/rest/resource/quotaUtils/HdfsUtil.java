package com.asiainfo.ocmanager.rest.resource.quotaUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.asiainfo.ocmanager.persistence.model.Quota;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.log4j.Logger;

/**
 * Created by Allen on 2017/6/27.
 */
public class HdfsUtil {

    public static final String KEYTAB_FILE_KEY = "hdfs.keytab.file";
    public static final String USER_NAME_KEY = "hdfs.kerberos.principal";
    public static final String DEFAULT_FS = "fs.defaultFS";
    public static final String AUTHENTICATION = "hadoop.security.authentication";
    public static final Configuration conf = new Configuration();
    private static Properties prop = new Properties();
    private static Logger logger = Logger.getLogger(HdfsUtil.class);
//    private static Quota filesquota;
//    private static Quota spacequota;
    /*static {
        String currentClassPath = new HdfsUtil().getClass().getResource("/").getPath();
        String  keytabPath= currentClassPath.substring(0, currentClassPath.length() - 8) + "conf/shixiuru.keytab";
        String  krbPath = currentClassPath.substring(0,currentClassPath.length() - 8) + "conf/krb5.conf";
        String dfsurl = AmbariUtil.getUrl("hdfs");

        conf.set(KEYTAB_FILE_KEY, keytabPath);
        conf.set(DEFAULT_FS,dfsurl);
        conf.set(AUTHENTICATION,"kerberos");
        System.setProperty("java.security.krb5.conf",krbPath);
        UserGroupInformation.setConfiguration(conf);
        try {
            UserGroupInformation.loginUserFromKeytab("shixiuru@EXAMPLE.COM",keytabPath);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

    }*/

    public static List<Quota> getHDFSData(String path){

        String currentClassPath = new HdfsUtil().getClass().getResource("/").getPath();
        String  keytabPath= currentClassPath.substring(0, currentClassPath.length() - 8) + "conf/"+prop.getProperty("kerberos.keytab.name");
        String  krbPath = currentClassPath.substring(0,currentClassPath.length() - 8) + "conf/"+prop.getProperty("kerberos.krb.name");
        String dfsurl = AmbariUtil.getUrl("hdfs");

        conf.set(KEYTAB_FILE_KEY, keytabPath);
        conf.set(DEFAULT_FS,dfsurl);
        conf.set(AUTHENTICATION,"kerberos");
        System.setProperty("java.security.krb5.conf",krbPath);
        UserGroupInformation.setConfiguration(conf);

        Quota filesquota = new Quota("nameSpaceQuota","","","","hdfs file quota");
        Quota spacequota = new Quota("storageSpaceQuota","","","","hdfs space quota");
        try {
            UserGroupInformation.loginUserFromKeytab(prop.getProperty("kerberos.username"),keytabPath);
            FileSystem fs = FileSystem.get(conf);
            ContentSummary contentSum = fs.getContentSummary(new Path(path));
            long Quota = contentSum.getQuota();
            long FileCount = contentSum.getFileCount();
            logger.info("quota:"+Quota+"-------   filecount:"+FileCount);
            if(Quota==-1){
                filesquota.setSize("");
                filesquota.setUsed(String.valueOf(FileCount));
                filesquota.setAvailable("");
            }else {
                filesquota.setSize(String.valueOf(Quota));
                filesquota.setUsed(String.valueOf(FileCount));
                filesquota.setAvailable(String.valueOf(Quota-FileCount));
            }
            long spaceQuota = contentSum.getSpaceQuota();
            long spaceConsumed = contentSum.getSpaceConsumed();
            logger.info("spacequota:"+spaceQuota+"---------  spaceconsumed:"+spaceConsumed);
            if(spaceQuota==-1){
                spacequota.setSize("");
                spacequota.setUsed(String.valueOf(spaceConsumed/1024/1024/1024));
                spacequota.setAvailable("");
            }else {
                spacequota.setSize(String.valueOf(spaceQuota/1024/1024/1024));
                spacequota.setUsed(String.valueOf(spaceConsumed/1024/1024/1024));
                spacequota.setAvailable(String.valueOf((spaceQuota-spaceConsumed)/1024/1024/1024));
            }
        } catch (IOException e) {
            logger.error("IOException :" +e);
            e.printStackTrace();
            filesquota.setSize("");
            filesquota.setUsed(String.valueOf("-1"));
            filesquota.setAvailable("");
            spacequota.setSize("");
            spacequota.setUsed(String.valueOf("-1"));
            spacequota.setAvailable("");
            List<Quota> result = new ArrayList<Quota>();
            result.add(filesquota);
            result.add(spacequota);
            return result;
        }
        List<Quota> result = new ArrayList<Quota>();
        result.add(filesquota);
        result.add(spacequota);
        return result;
    }
}
