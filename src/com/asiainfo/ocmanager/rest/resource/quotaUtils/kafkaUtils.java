package com.asiainfo.ocmanager.rest.resource.quotaUtils;

import com.asiainfo.ocmanager.mail.ParamQuery;
import com.asiainfo.ocmanager.persistence.model.Quota;
import com.jcraft.jsch.*;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.log4j.Logger;


import java.io.*;
import java.util.*;

/**
 * Created by yujin on 2017/6/29.
 */
public class kafkaUtils {
    private static final Properties propsp = new Properties();
    private static Logger logger = Logger.getLogger(kafkaUtils.class);


    public  static Quota  getKafkaPartitionNumQuota(String topicName){

        Quota partitionQuota;
        String currentClassPath = new kafkaUtils().getClass().getResource("/").getPath();

        String  jaasPath= currentClassPath.substring(0, currentClassPath.length() - 8) + "conf/kafka-jaas.conf";
        String  krbPath = currentClassPath.substring(0,currentClassPath.length() - 8) + "conf/krb5.conf";

        logger.info("getKafkaPartitionNumQuota jaasPath: " + jaasPath);
        System.setProperty("java.security.auth.login.config", jaasPath);
        System.setProperty("java.security.krb5.conf", krbPath);

        try {
            String bootstrapServers = ParamQuery.getCFProperties().get(ParamQuery.BOOTSTRAP_SERVERS);
            propsp.put("security.protocol", "SASL_PLAINTEXT");
            //props.put("bootstrap.servers", "zx-dn-10:6667,zx-dn-11:6667,zx-dn-12:6667,zx-dn-13:6667,zx-dn-14:6667,zx-bdi-01:6667,zx-bdi-02:6667,zx-bdi-03:6667");
            //props.put("bootstrap.servers", "zx-dn-10:6667,zx-dn-11:6667,zx-dn-12:6667,zx-dn-13:6667,zx-dn-14:6667");
            propsp.put("bootstrap.servers", bootstrapServers);
            propsp.put("group.id", "group1");
            propsp.put("enable.auto.commit", "true");
            propsp.put("auto.commit.interval.ms", "1000");
            propsp.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            propsp.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            KafkaConsumer<byte[],byte[]> consumer = new KafkaConsumer<>(propsp);
            List<PartitionInfo> PartionInfoForTopic = consumer.partitionsFor(topicName);
            int partitionNum = PartionInfoForTopic.size();
            String partitionNumStr = String.valueOf(partitionNum);
            partitionQuota= new Quota("partitionQuota",partitionNumStr,"","","kafka topic partiton num");
            logger.info("partitionQuota is:" + partitionNumStr);
        }catch (Exception e){
            logger.info("KafkaUtils getKafkaPartitionNumQuota Exception "+e.getMessage());
            Quota partitionQuota1= new Quota("partitionQuota","-1","","","kafka topic partiton num");
            return partitionQuota1;
        }

        return partitionQuota;
    }
    public static Quota getKafkaSpaceQuota(String topicName){
        Quota partitionQuota;
        Process process = null;
        List<String> processList = new ArrayList<String>();
        BufferedReader input = null;
        try {
            process = Runtime.getRuntime().exec("sh /home/ai/getKafakQuota.sh  "+topicName+"-*"+"\n");
            input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        } catch (Exception e) {
            logger.error("KafkaUtils getKafkaSpaceQuota Exception"+e.getMessage());
            Quota partitionQuota1= new Quota("partitionQuota","-1","","","kafka topic partition used size");
            return partitionQuota1;
        } finally {
            try {
                String line = "";
                while ((line = input.readLine()) != null) {
                    processList.add(line);
                }
                for (String pro : processList) {
                    logger.info("kafka topic partiton num"+pro);
                }
                if(Integer.valueOf(processList.get(0))>Integer.MAX_VALUE){throw new IOException("num is too large!!");}
                partitionQuota= new Quota("partitionQuota",String.valueOf(processList.get(0)),"","","kafka topic partition used size");
                input.close();
            }catch (IOException e){
                logger.info("KafkaUtils getKafkaSpaceQuota IOException"+e.getMessage());
                Quota partitionQuota2= new Quota("partitionQuota","-1","","","kafka topic partition used size");
                return partitionQuota2;
            }

        }

        return partitionQuota;

    }
}
