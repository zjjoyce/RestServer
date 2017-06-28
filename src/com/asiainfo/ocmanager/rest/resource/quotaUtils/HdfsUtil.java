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

/**
 * Created by Allen on 2017/6/27.
 */
public class HdfsUtil {

    public static final String KEYTAB_FILE_KEY = "hdfs.keytab.file";
    public static final String USER_NAME_KEY = "hdfs.kerberos.principal";
    public static final String DEFAULT_FS = "fs.defaultFS";
    public static final String AUTHENTICATION = "hadoop.security.authentication";

    public static final Configuration conf = new Configuration();

    public static List<Quota> getHDFSData(String path){

        String dfsurl = AmbariUtil.getUrl("hdfs");

        conf.set(KEYTAB_FILE_KEY, "conf/shixiuru.keytab");
        conf.set(DEFAULT_FS,dfsurl);
        conf.set(AUTHENTICATION,"kerberos");
        System.setProperty("java.security.krb5.conf","conf/krb5.conf");
        UserGroupInformation.setConfiguration(conf);
        Quota filesquota = new Quota();
        Quota spacequota = new Quota();
        try {
            UserGroupInformation.loginUserFromKeytab("shixiuru@EXAMPLE.COM","conf/shixiuru.keytab");
            FileSystem fs = FileSystem.get(conf);
            ContentSummary contentSum = fs.getContentSummary(new Path(path));
            long Quota = contentSum.getQuota();
            long FileCount = contentSum.getFileCount();
            filesquota.setName("hdfsQuota");
            filesquota.setSize(String.valueOf(Quota));
            filesquota.setUsed(String.valueOf(FileCount));
            filesquota.setAvailable(String.valueOf(Quota-FileCount));
            filesquota.setDesc("hdfs quota");
            long spaceQuota = contentSum.getSpaceQuota();
            long spaceConsumed = contentSum.getSpaceConsumed();
            spacequota.setName("hdfsSpaceQuota");
            spacequota.setSize(String.valueOf(spaceQuota));
            spacequota.setUsed(String.valueOf(spaceConsumed));
            spacequota.setAvailable(String.valueOf(spaceQuota-spaceConsumed));
            spacequota.setDesc("hdfs space quota");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Quota> result = new ArrayList<Quota>();
        result.add(filesquota);
        result.add(spacequota);
        return result;
    }
}
