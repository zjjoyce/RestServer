package com.asiainfo.ocmanager.rest.resource.quotaUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.asiainfo.ocmanager.persistence.model.Quota;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * Created by Allen on 2017/6/27.
 */
public class HdfsUtil {
    private static Log logger = LogFactory.getLog(HdfsUtil.class);
    public static final String KEYTAB_FILE_KEY = "hdfs.keytab.file";
    public static final String USER_NAME_KEY = "hdfs.kerberos.principal";
    public static final String DEFAULT_FS = "fs.defaultFS";
    public static final String AUTHENTICATION = "hadoop.security.authentication";

    public static final Configuration conf = new Configuration();

    public static List<Quota> getHDFSData(String path){
        String currentClassPath = new HdfsUtil().getClass().getResource("/").getPath();
        String  keytabPath= currentClassPath.substring(0, currentClassPath.length() - 8) + "conf/shixiuru.keytab";
        String  krbPath = currentClassPath.substring(0,currentClassPath.length() - 8) + "conf/krb5.conf";
        String dfsurl = AmbariUtil.getUrl("hdfs");

        conf.set(KEYTAB_FILE_KEY, keytabPath);
        conf.set(DEFAULT_FS,dfsurl);
        conf.set(AUTHENTICATION,"kerberos");
        System.setProperty("java.security.krb5.conf",krbPath);
        UserGroupInformation.setConfiguration(conf);
        Quota filesquota = new Quota("hdfsFileQuota","","","","hdfs file quota");
        Quota spacequota = new Quota("hdfsSpaceQuota","","","","hdfs space quota");
        try {
            UserGroupInformation.loginUserFromKeytab("shixiuru@EXAMPLE.COM",keytabPath);
            FileSystem fs = FileSystem.get(conf);
            ContentSummary contentSum = fs.getContentSummary(new Path(path));
            long Quota = contentSum.getQuota();
            long FileCount = contentSum.getFileCount();
            filesquota.setName("nameSpaceQuota");
            filesquota.setSize(String.valueOf(Quota));
            filesquota.setUsed(String.valueOf(FileCount));
            filesquota.setAvailable(String.valueOf(Quota-FileCount));
            filesquota.setDesc("hdfs quota");
            long spaceQuota = contentSum.getSpaceQuota();
            long spaceConsumed = contentSum.getSpaceConsumed();
            spacequota.setName("storageSpaceQuota");
            spacequota.setSize(String.valueOf(spaceQuota/1024/1024/1024));
            spacequota.setUsed(String.valueOf(spaceConsumed/1024/1024/1024));
            spacequota.setAvailable(String.valueOf((spaceQuota-spaceConsumed)/1024/1024/1024));
            spacequota.setDesc("hdfs space quota");
        } catch (IOException e) {
            logger.error("IOException :" +e);
            e.printStackTrace();
        }
        List<Quota> result = new ArrayList<Quota>();
        result.add(filesquota);
        result.add(spacequota);
        return result;
    }
}
