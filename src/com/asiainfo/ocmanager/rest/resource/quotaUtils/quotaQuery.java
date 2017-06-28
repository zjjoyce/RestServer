package com.asiainfo.ocmanager.rest.resource.quotaUtils;

import java.util.*;

import com.asiainfo.ocmanager.persistence.model.Quota;
import com.asiainfo.ocmanager.rest.resource.quotaUtils.HdfsUtils;
import com.sun.org.apache.xpath.internal.operations.Quo;
import org.apache.hadoop.fs.Path;

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
	        /*
	        todo :move Hdfs quota code to here
	         */
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
        /*
        todo :move mapreduce quota code to here
         */
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
   * @param serviceInstanceId
   * @return Spark Quota
   */
  public static Map getSparkQuota(String serviceInstanceId) {
    Map result = new HashMap();
        /*
        todo :move mapreduce quota code to here
         */

    Quota queueQuota= new Quota("queueQuota","100","10","190","spark service instance queue used memory");
    List<Quota> items = new ArrayList<Quota>();
    items.add(queueQuota);
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
	    /*
	    todo :move Hbase quota code to here
	     */
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
     * @param serviceInstanceId
     * @return hive Quota
     */
    public static Map getHiveQuota(String serviceInstanceId) {
        Map result = new HashMap();
        /*
        todo :move hive quota code to here
         */
        HdfsUtils hdfsUtils = new HdfsUtils();
        Quota hdfsQuota = hdfsUtils.getHdfsQuota(new Path("/user/ocdc"));
        Quota queueQuota = YarnUtils.getQueueQuota(serviceInstanceId);
        if(hdfsQuota==null){
          hdfsQuota = new Quota("hdfsQuota","100","20","180","hive service instance db used space");
        }
        if(queueQuota==null){
           queueQuota= new Quota("queueQuota","100","10","190","hive service instance queue used memory");

        }
        List<Quota> items = new ArrayList<Quota>();
        items.add(queueQuota);
        items.add(hdfsQuota);
        result.put("items",items);
        return result;
    }
    /**
     *
     * @param serviceInstanceId
     * @return kafka Quota
     */
    public static Map getKafkaQuota(String serviceInstanceId) {
      Map result = new HashMap();
        /*
        todo :move kafka  quota code to here
         */

      Quota partitionQuota= new Quota("partitionQuota","100","10","190","kafka topic partiton num");
      Quota topicQuota = new Quota("topicQuota","100","20","180","kafka topic used space");
      List<Quota> items = new ArrayList<Quota>();
      items.add(partitionQuota);
      items.add(topicQuota);
      result.put("items",items);
      return result;
    }

  /**
   *
   * @param serviceInstanceId
   * @return GP Quota
   */
  public static Map getGpQuota(String serviceInstanceId) {
    Map result = new HashMap();
    List<Quota> items = new ArrayList<Quota>();
    /*
        todo :move Gp  quota code to here
         */

    Quota volumeSize= new Quota("volumeSize","100","100","0","greenplum volume Size");
    items.add(volumeSize);
    result.put("items",items);
    return result;
  }

  /**
   *
   * @param serviceInstanceId
   * @return mongo Quota
   */
  public static Map getMongoQuota(String serviceInstanceId) {
    Map result = new HashMap();
        /*
        todo :move Gp  quota code to here
         */

    Quota volumSize= new Quota("volumSize","100","100","0","mongodb volume size");
    List<Quota> items = new ArrayList<Quota>();
    items.add(volumSize);
    result.put("items",items);
    return result;
  }
}
