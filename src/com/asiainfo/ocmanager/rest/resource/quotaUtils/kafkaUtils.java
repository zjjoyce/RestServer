package com.asiainfo.ocmanager.rest.resource.quotaUtils;

import com.asiainfo.ocmanager.persistence.model.Quota;
import com.jcraft.jsch.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;


import java.io.*;
import java.util.*;

/**
 * Created by yujin on 2017/6/29.
 */
public class kafkaUtils {
    private static final Properties props = new Properties();
    public static Log logger = LogFactory.getLog(kafkaUtils.class);

    public  static Quota  getKafkaPartitionNumQuota(String topicName){
        String currentClassPath = new kafkaUtils().getClass().getResource("/").getPath();
        /*String  jaasPath= currentClassPath.substring(0, currentClassPath.length() - 8) + "ocmanager/WEB-INF/conf/kafka-jaas.conf";
        String  krbPath = currentClassPath.substring(0,currentClassPath.length() - 8) + "ocmanager/WEB-INF/conf/krb5.conf";*/

        String  jaasPath= currentClassPath.substring(0, currentClassPath.length() - 8) + "conf/kafka-jaas.conf";
        String  krbPath = currentClassPath.substring(0,currentClassPath.length() - 8) + "conf/krb5.conf";
        System.setProperty("java.security.auth.login.config",jaasPath);
        System.setProperty("java.security.krb5.conf", krbPath);
        //System.setProperty("sun.security.krb5.debug", "true");
        props.put("security.protocol", "SASL_PLAINTEXT");
        System.out.println("begin to register for kerberos");
        //props.put("bootstrap.servers", "zx-dn-10:6667,zx-dn-11:6667,zx-dn-12:6667,zx-dn-13:6667,zx-dn-14:6667,zx-bdi-01:6667,zx-bdi-02:6667,zx-bdi-03:6667");
        props.put("bootstrap.servers", "zx-dn-10:6667,zx-dn-11:6667,zx-dn-12:6667,zx-dn-13:6667,zx-dn-14:6667");
        props.put("group.id", "group1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<byte[],byte[]> consumer = new KafkaConsumer<>(props);

        List<PartitionInfo> PartionInfoForTopic = consumer.partitionsFor(topicName);
        int partitionNum = PartionInfoForTopic.size();
        String partitionNumStr = String.valueOf(partitionNum);
        Quota partitionQuota= new Quota("partitionQuota",partitionNumStr,"","","kafka topic partiton num");


        logger.info("partitionQuota is:" + partitionNumStr);

        return partitionQuota;
    }
    public static Quota getKafkaSpaceQuota(String topicName){


        Quota partitionQuota = new Quota();

        //String shellCommand = "du -k /hdfs/data*/kafka-logs/"+topicName+"-*/\n";


        //String shellCommand = "sh /home/ai/getKafakQuota.sh  "+topicName+"-*"+"\n";
        //String shellCommand = "sh /home/ai/getKafakQuota1.sh\n";
        Process process = null;
        List<String> processList = new ArrayList<String>();
        try {
            process = Runtime.getRuntime().exec("sh /home/ai/getKafakQuota.sh  "+topicName+"-*"+"\n");
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String line : processList) {
            System.out.println("kafka topic partiton num"+line);
        }


        partitionQuota= new Quota("partitionQuota",String.valueOf(processList.get(0)),"","","kafka topic partition used size");

        return partitionQuota;

    }
    public static void main(String[] args){
        kafkaUtils kafka= new kafkaUtils();
        //Quota quota = getKafkaSpaceQuota("__consumer_offsets");
        Quota quota1 = getKafkaPartitionNumQuota("__consumer_offsets");
        //System.out.println(quota.getSize());
        System.out.println(quota1.getSize());

    }
}
