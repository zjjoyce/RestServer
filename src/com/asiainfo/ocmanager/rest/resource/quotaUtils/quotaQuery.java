package com.asiainfo.ocmanager.rest.resource.quotaUtils;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import com.asiainfo.ocmanager.mail.ParamQuery;
import com.asiainfo.ocmanager.persistence.model.Quota;
import com.asiainfo.ocmanager.rest.resource.quotaUtils.HdfsUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.Path;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;

/**
 *
 * @author yujing2
 *
 */
public class quotaQuery {


    public static Log logger = LogFactory.getLog(quotaQuery.class);


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
        Quota volumeSize= new Quota("volumeSize","","0","","greenplum database used Size");
        try {
            Class.forName("org.postgresql.Driver");
            String hostport = ParamQuery.getCFProperties().get(ParamQuery.GREENPLUM_HOST_PORT);
            //Connection db = DriverManager.getConnection("jdbc:postgresql://10.247.32.84:5432/d778303ea916d266", "u09f42e1eaa08a8b", "pa465990aee497b4");
            Connection db1 = DriverManager.getConnection("jdbc:postgresql://"+hostport+"/" + database, "gpadmin", "asiainfoldp");
            Statement st = db1.createStatement();
            ResultSet rs = st.executeQuery("select  sum((pg_relation_size(relid)))from pg_stat_user_tables");
            while (rs.next()) {
                useplace = Long.valueOf(rs.getString(1));
            }
            String used = String.valueOf(useplace);
            volumeSize= new Quota("volumeSize","",used,"","greenplum database used Size");
            rs.close();
            st.close();
        } catch (ClassNotFoundException e) {
            logger.info("quotaQuery getGpQuota ClassNotFoundException" + e.getMessage());
        } catch (SQLException e) {
            logger.info("quotaQuery getGpQuota SQLException" + e.getMessage());
        } catch (IOException e) {
            logger.info("quotaQuery getGpQuota IOException" + e.getMessage());
        }
        items.add(volumeSize);
        result.put("items", items);
        return result;
    }

    /**
     *
     * @param databasename
     * @return mongo Quota
     */
    public static Map getMongoQuota(String databasename) {
        Map result = new HashMap();
        List<Quota> items = new ArrayList<Quota>();
        Quota volumSize= new Quota("volumeSize","","0","","mongodb database used size");
        long useplace = 0;
        try {
            String host = ParamQuery.getCFProperties().get(ParamQuery.MONGO_HOST);
            String port = ParamQuery.getCFProperties().get(ParamQuery.MONGO_PORT);
            //MongoClient mongoClient = new MongoClient("10.247.32.97", 27021);
            MongoClient mongoClient = new MongoClient(host, Integer.valueOf(port));
            MongoDatabase database = mongoClient.getDatabase(databasename);
            for(Document colloctio:database.listCollections()){
                useplace+=colloctio.toJson().getBytes().length;
            }
            String used = String.valueOf(useplace);
            volumSize= new Quota("volumeSize","",used,"","mongodb database used size");
        }catch (Exception e){
            logger.info("quotaQuery getMongoQuota Exception "+e.getMessage());
        }
        items.add(volumSize);
        result.put("items",items);
        return result;
    }
}
