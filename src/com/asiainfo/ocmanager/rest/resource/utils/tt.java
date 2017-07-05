package com.asiainfo.ocmanager.rest.resource.utils;


import com.asiainfo.ocmanager.persistence.model.Quota;
import com.asiainfo.ocmanager.rest.resource.quotaUtils.HdfsUtils;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;

import java.sql.*;
import java.util.*;

/**
 * Created by YANLSH on 2017/6/29.
 */
public class tt {
    public static void main(String []args){
        /*Properties props = new Properties();
        String currentClassPath = new tt().getClass().getResource("/").getPath();
        String  keytabPath= currentClassPath.substring(0, currentClassPath.length() - 8) + "conf/shixiuru.keytab";
        System.out.println(keytabPath);

        String  jaasPath= currentClassPath.substring(0, currentClassPath.length() - 8) + "conf/kafka-jaas.conf";
        String  krbPath = currentClassPath.substring(0,currentClassPath.length() - 8) + "conf/krb5.conf";
        System.setProperty("java.security.auth.login.config", "G:\\work_place_duozhufu_test\\RestServer\\target\\ocmanager\\WEB-INF\\conf\\kafka-jaas.conf");
        System.setProperty("java.security.krb5.conf", "G:\\work_place_duozhufu_test\\RestServer\\target\\ocmanager\\WEB-INF\\conf\\krb5.conf");
        *//*System.setProperty("java.security.auth.login.config", jaasPath);
        System.setProperty("java.security.krb5.conf", krbPath);*//*
        //System.setProperty("sun.security.krb5.debug", "true");
        props.put("security.protocol", "SASL_PLAINTEXT");
        System.out.println("begin to register for kerberos");
        props.put("bootstrap.servers", "zx-dn-10:6667,zx-dn-11:6667,zx-dn-12:6667,zx-dn-13:6667,zx-dn-14:6667,zx-bdi-01:6667,zx-bdi-02:6667,zx-bdi-03:6667");
        props.put("group.id", "group1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        System.out.println("keytab is :" + props.getProperty("keyTab"));

        KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<byte[], byte[]>(props);
        //consumer.subscribe(Arrays.asList(topicName));
        //Map<String, List<PartitionInfo>> listPartionInfoForTopic = consumer.listTopics();
        List<PartitionInfo> PartionInfoForTopic = consumer.partitionsFor("__consumer_offsets");
        int partitionNum = PartionInfoForTopic.size();
        System.out.println(partitionNum);*/
        Map result = new HashMap();
        List<Quota> items = new ArrayList<Quota>();
        long useplace = 0;
        try {
            Class.forName("org.postgresql.Driver");
            //Connection db = DriverManager.getConnection("jdbc:postgresql://10.247.32.84:5432/d778303ea916d266", "u09f42e1eaa08a8b", "pa465990aee497b4");
            Connection db1 = DriverManager.getConnection("jdbc:postgresql://10.247.32.84:5432/" + "dd054eb911658027", "gpadmin", "asiainfoldp");
            Statement st = db1.createStatement();
            ResultSet rs = st.executeQuery("select  sum((pg_relation_size(relid)))from pg_stat_user_tables");
            while (rs.next()) {
                useplace = Long.valueOf(rs.getString(1))/1024;
                System.out.println("xxxxx库已使用空间大小："+useplace+"M");
            }
            rs.close();
            st.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String used = String.valueOf(useplace);
    }
}
