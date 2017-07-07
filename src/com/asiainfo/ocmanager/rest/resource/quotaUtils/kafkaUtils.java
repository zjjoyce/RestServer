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

    public  static Quota  getKafkaQuota(String topicName){
        String currentClassPath = new kafkaUtils().getClass().getResource("/").getPath();
        String  jaasPath= currentClassPath.substring(0, currentClassPath.length() - 8) + "ocmanager/WEB-INF/conf/kafka-jaas.conf";
        String  krbPath = currentClassPath.substring(0,currentClassPath.length() - 8) + "ocmanager/WEB-INF/conf/krb5.conf";
        System.setProperty("java.security.auth.login.config",jaasPath);
        System.setProperty("java.security.krb5.conf", krbPath);
        //System.setProperty("sun.security.krb5.debug", "true");
        props.put("security.protocol", "SASL_PLAINTEXT");
        System.out.println("begin to register for kerberos");
        props.put("bootstrap.servers", "zx-dn-10:6667,zx-dn-11:6667,zx-dn-12:6667,zx-dn-13:6667,zx-dn-14:6667,zx-bdi-01:6667,zx-bdi-02:6667,zx-bdi-03:6667");
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

        Quota quota = new Quota();
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        String privateKeyPath = "\\C:\\users\\yujin\\yujing2_rsa_pub";
        String shellCommand = "du -sm /hdfs/data1/kafka-logs/__consumer_offsets-42\n";

        try{
            jsch.addIdentity(privateKeyPath);
            session = jsch.getSession("ai","zx-dn-10",22);
            session.setConfig("PreferredAuthentications","publickey,keyboard-interactive,password");
            Properties config = new Properties();
            config.put("StrictHostKeyChecking","no");
            session.setConfig(config);
            session.connect(30000);

            // 创建sftp通信通道
            channel = (Channel) session.openChannel("shell");
            channel.connect(1000);


            //获取输入流和输出流
            InputStream instream = channel.getInputStream();
            OutputStream outstream = channel.getOutputStream();


            // 发送需要执行的shell命令，需要用\n 结束，表示回车
            outstream.write(shellCommand.getBytes());
            outstream.flush();


            //获取执行结果
            if(instream.available() > 0){
                byte[] data = new byte[instream.available()];
                int nlen = instream.read(data);
                if(nlen < 0){
                    throw new Exception("network error.");
                }
                //转换输出结果并打印
                String temp = new String(data,0,nlen,"iso8859-1");
                System.out.println(temp);
                String[] cmdResult = temp.split("\n");
                String dirSize = cmdResult[1].split("\t")[0];
                quota.setSize(dirSize);

            }
//            outstream.close();
//            instream.close();

        }catch (JSchException e){
            logger.error("Error during SSH command execution.Command is :"+shellCommand);
        }catch (IOException e){
            logger.error(e);
        }catch (Exception ex){
            logger.error(ex);
            }finally {

            channel.disconnect();
            session.disconnect();
        }
        return quota;

    }
    public static void main(String[] args){
        kafkaUtils kafka= new kafkaUtils();
//        Quota quota = kafka.getKafkaQuota("__consumer_offsets");
        Quota quota = kafka.getKafkaSpaceQuota("test");
        logger.info("kafka quota is:"+quota.getSize());
    }
}
