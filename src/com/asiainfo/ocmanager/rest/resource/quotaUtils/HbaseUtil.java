package com.asiainfo.ocmanager.rest.resource.quotaUtils;

/**
 * Created by Allen on 2017/6/27.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.asiainfo.ocmanager.persistence.model.Quota;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HRegionInfo;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.security.UserGroupInformation;

public class HbaseUtil {

    public static final Configuration conf = new Configuration();
    private static Connection hconn;
    private static Admin admin;
    private static int tabnum;
    private static int regnum;

    public static List<Quota> getHbaseData(String namespace){

        String currentClassPath = new HdfsUtils().getClass().getResource("/").getPath();
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
        //System.setProperty("sun.security.krb5.debug", "true");
        System.setProperty("java.security.krb5.conf",krbPath);
        UserGroupInformation.setConfiguration(conf);
        try {
            UserGroupInformation.loginUserFromKeytab("shixiuru@EXAMPLE.COM", keytabPath);
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        Quota tabquota = new Quota();
        Quota regquota = new Quota();

        tabquota.setName("hbase tableUsed");
        tabquota.setUsed(String.valueOf(tabnum));
        regquota.setName("hbase regionUsed");
        regquota.setUsed(String.valueOf(regnum));

        List<Quota> result = new ArrayList<Quota>();
        result.add(tabquota);
        result.add(regquota);

        return result;
    }
}
