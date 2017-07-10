package com.asiainfo.ocmanager.rest.resource.quotaUtils;

import java.sql.*;
import java.util.*;

import com.asiainfo.ocmanager.persistence.model.Quota;
import com.asiainfo.ocmanager.rest.resource.quotaUtils.HdfsUtils;
import org.apache.hadoop.fs.Path;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.bson.Document;


import org.apache.kafka.common.PartitionInfo;
import java.util.Properties;

/**
 *
 * @author yujing2
 *
 */
public class quotaQuery {


    /**
     *
     * @param path
     * @return Hdfs Quota
     */
    public static Map<String,List<Quota>> getHdfsQuota(String path) {
        Map<String,List<Quota>> result = new HashMap<String,List<Quota>>();
        List<Quota> dfsquota = HdfsUtil.getHDFSData(path);
        result.put("items", dfsquota);
	    /*Quota hdfsQuota = new Quota("hdfsQuota","100","20","180","hive service instance db used space");
	    List<Quota> items = new ArrayList<Quota>();
	    items.add(hdfsQuota);
	    result.put("items",items);*/
        return result;
    }

    /**
     *
     * @param queuename
     * @return Yarn Quota
     */
    public static Map<String,List<Quota>> getMrQuota(String queuename) {
        Map<String,List<Quota>> result = new HashMap<String,List<Quota>>();
        List<Quota> mrquota = YarnUtil.getYarnData(queuename);
        result.put("items", mrquota);
      /*Quota queueQuota= new Quota("queueQuota","100","10","190","mr service instance queue used memory");
      List<Quota> items = new ArrayList<Quota>();
      items.add(queueQuota);
      result.put("items",items);*/
        return result;
    }

    /**
     *
     * @param queuename
     * @return Spark Quota
     */
    public static Map getSparkQuota(String queuename) {
        Map result = new HashMap();
        List<Quota> sparkQuota = YarnUtil.getYarnData(queuename);
        List<Quota> items = new ArrayList<Quota>();
        Iterator<Quota> iterator = sparkQuota.iterator();
        while(iterator.hasNext()){
            items.add(iterator.next());
        }
        result.put("items",items);
        return result;
    }


    /**
     *
     * @param namespace
     * @return Hbase Quota
     */
    public static Map<String,List<Quota>> getHbaseQuota(String namespace) {
        Map<String,List<Quota>> result = new HashMap<String,List<Quota>>();
        List<Quota> hbasequota = HbaseUtil.getHbaseData(namespace);
        result.put("items", hbasequota);
	    /*Quota regionQuota= new Quota("regionQuota","100","10","190","hbase region num quota");
	    Quota tableQuota = new Quota("tableQuota","100","20","180","hbase table num quota");
	    List<Quota> items = new ArrayList<Quota>();
	    items.add(regionQuota);
	    items.add(tableQuota);
	    result.put("items",items);*/
        return result;
    }


    /**
     *
     * @param dbname
     * @param queuename
     * @return hive Quota
     */
    public static Map getHiveQuota(String dbname,String queuename) {
        Map result = new HashMap();
        /*
        todo :move hive quota code to here
         */
        HdfsUtils hdfsUtils = new HdfsUtils();
        List<Quota> hdfsQuota = hdfsUtils.getHdfsQuota(new Path("/apps/hive/warehouse/"+dbname+".db"));
        List<Quota> queueQuota = YarnUtil.getYarnData(queuename);
        //yarn quota
        Iterator<Quota> iterator = queueQuota.iterator();
        List<Quota> items = new ArrayList<Quota>();
        while(iterator.hasNext()){
            items.add(iterator.next());
        }
        // hdfs quota
        Iterator<Quota> hdfsIter = hdfsQuota.iterator();
        while(hdfsIter.hasNext()){
            items.add(hdfsIter.next());
        }
        result.put("items",items);
        return result;
    }
    /**
     *
     * @param topicName
     * @return kafka Quota
     */
    public static Map getKafkaQuota(String topicName) {
        Map result = new HashMap();
        List<Quota> items = new ArrayList<>();

        Quota topicPartitionQuota = kafkaUtils.getKafkaPartitionNumQuota(topicName);
        Quota kafkaSpaceQuota = kafkaUtils.getKafkaSpaceQuota(topicName);
        items.add(topicPartitionQuota);
        items.add(kafkaSpaceQuota);
        result.put("items",items);
        return result;
    }

    /**
     *
     * @param database
     * @return GP Quota
     */
    public static Map getGpQuota(String database) {
        Map result = new HashMap();
        List<Quota> items = new ArrayList<Quota>();
        long useplace = 0;
        try {
            Class.forName("org.postgresql.Driver");
            //Connection db = DriverManager.getConnection("jdbc:postgresql://10.247.32.84:5432/d778303ea916d266", "u09f42e1eaa08a8b", "pa465990aee497b4");
            Connection db1 = DriverManager.getConnection("jdbc:postgresql://10.247.32.84:5432/" + database, "gpadmin", "asiainfoldp");
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
        Quota volumeSize= new Quota("volumeSize","",used,"","greenplum database used Size");
        items.add(volumeSize);
        result.put("items",items);
        return result;
    }

    /**
     *
     * @param databasename
     * @return mongo Quota
     */
    public static Map getMongoQuota(String databasename) {
        Map result = new HashMap();
        // 获取链接
        MongoClient mongoClient = new MongoClient("10.247.32.97", 27021);
        // 获取数据库
        //MongoDatabase database = mongoClient.getDatabase("e7cd929a-3aa5-11e7-a678-00163e00009d");
        MongoDatabase database = mongoClient.getDatabase(databasename);

        long useplace = 0;
        for(Document colloctio:database.listCollections()){
            //System.out.println(colloctio.toJson().getBytes().length);
            useplace+=colloctio.toJson().getBytes().length;
        }
        useplace = useplace/1024;
        String used = String.valueOf(useplace);
        //System.out.println("xxxxx库已使用空间大小："+userplace+"M");
        Quota volumSize= new Quota("volumeSize","",used,"","mongodb database used size");
        List<Quota> items = new ArrayList<Quota>();
        items.add(volumSize);
        result.put("items",items);
        return result;
    }
}
