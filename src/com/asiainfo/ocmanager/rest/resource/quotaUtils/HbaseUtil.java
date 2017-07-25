package com.asiainfo.ocmanager.rest.resource.quotaUtils;

/**
 * Created by Allen on 2017/6/27.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.asiainfo.ocmanager.persistence.model.Quota;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HRegionInfo;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.log4j.Logger;

public class HbaseUtil {

    public static final Configuration conf = new Configuration();
    private static Properties prop = new Properties();
//    private static Connection hconn;
//    private static Admin admin;
//    private static int tabnum;
//    private static int regnum;

    private static Logger logger = Logger.getLogger(HbaseUtil.class);

 /*   static {
        String currentClassPath = new HbaseUtil().getClass().getResource("/").getPath();
        String  keytabPath= currentClassPath.substring(0, currentClassPath.length() - 8) + "conf/shixiuru.keytab";
        String  krbPath = currentClassPath.substring(0,currentClassPath.length() - 8) + "conf/krb5.conf";
        String hbaseurl = AmbariUtil.getUrl("hbase");

        conf.set("hbase.zookeeper.quorum", hbaseurl);
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("zookeeper.znode.parent", "/hbase-secure");

        conf.set("hadoop.security.authentication", "kerberos");
        conf.set("hbase.security.authentication", "kerberos");
        conf.set("hbase.master.kerberos.principal", "hbase/_HOST@EXAMPLE.COM");
        conf.set("hbase.regionserver.kerberos.principal", "hbase/_HOST@EXAMPLE.COM");
        System.setProperty("java.security.krb5.conf",krbPath);
        UserGroupInformation.setConfiguration(conf);

        try {
            UserGroupInformation.loginUserFromKeytab("shixiuru@EXAMPLE.COM", keytabPath);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }*/
     static {
        String classPath = new AmbariUtil().getClass().getResource("/").getPath();
        String currentClassesPath = classPath.substring(0, classPath.length() - 8)+ "conf/config.properties";
        try{
            InputStream inStream = new FileInputStream(new File(currentClassesPath ));
            //            prop = new Properties();
            prop.load(inStream);
        }catch(IOException e){
            logger.error(e.getMessage());
        }
    }

    public static List<Quota> getHbaseData(String namespace){

        Connection hconn;
        Admin admin;
        int tabnum = 0;
        int regnum = 0;
        List<Quota> result = new ArrayList<Quota>();
        String currentClassPath = new HbaseUtil().getClass().getResource("/").getPath();
        String  keytabPath= currentClassPath.substring(0, currentClassPath.length() - 8) +"conf/"+prop.getProperty("hbase.kerberos.keytab.name");
        String  krbPath = currentClassPath.substring(0,currentClassPath.length() - 8) + "conf/"+prop.getProperty("kerberos.krb.name");
        String hbaseurl = AmbariUtil.getUrl("hbase");
        conf.set("hbase.zookeeper.quorum", hbaseurl);
        conf.set("hbase.zookeeper.property.clientPort", prop.getProperty("hbase.clientport"));
        conf.set("zookeeper.znode.parent", prop.getProperty("zookeeper.znode.parent"));

        conf.set("hadoop.security.authentication", "kerberos");
        conf.set("hbase.security.authentication", "kerberos");
        conf.set("hbase.master.kerberos.principal", prop.getProperty("hbase.master.kerberos.principal"));
        conf.set("hbase.regionserver.kerberos.principal", prop.getProperty("hbase.regionserver.kerberos.principal"));
        System.setProperty("java.security.krb5.conf",krbPath);
        UserGroupInformation.setConfiguration(conf);

        try {
            UserGroupInformation.loginUserFromKeytab(prop.getProperty("kerberhbase.master.kerberos.principal"), keytabPath);
            hconn = ConnectionFactory.createConnection(conf);
            admin = hconn.getAdmin();
            TableName[] tables = admin.listTableNamesByNamespace(namespace);
            tabnum = tables.length;
            for(TableName tab:tables){
                if(tab!=null){
                    List<HRegionInfo> tableRegions = admin.getTableRegions(tab);
                    int regs = tableRegions.size();
                    regnum = regnum+regs;
                }
            }
            logger.info("tabnum:"+tabnum+"------  regnum:"+regnum);
            Quota tabquota = new Quota();
            Quota regquota = new Quota();
            tabquota.setName("maximumTablesQuota");
            tabquota.setUsed(String.valueOf(tabnum));
            regquota.setName("maximumRegionsQuota");
            regquota.setUsed(String.valueOf(regnum));
            result.add(tabquota);
            result.add(regquota);
        } catch (IOException e) {
            logger.error(e.getMessage());
            Quota tabquota = new Quota();
            Quota regquota = new Quota();
            tabquota.setName("maximumTablesQuota");
            tabquota.setUsed(String.valueOf(-1));
            regquota.setName("maximumRegionsQuota");
            regquota.setUsed(String.valueOf(-1));
            result.add(tabquota);
            result.add(regquota);
        }
        return result;
    }
}
