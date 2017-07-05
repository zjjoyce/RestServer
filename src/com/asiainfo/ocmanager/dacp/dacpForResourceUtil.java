package com.asiainfo.ocmanager.dacp;

import com.asiainfo.ocmanager.dacp.model.DBDistribution;
import com.asiainfo.ocmanager.dacp.model.DBRegister;
import com.asiainfo.ocmanager.dacp.model.HadoopResource;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YANLSH on 2017/7/3.
 */
public class dacpForResourceUtil {
    public static void main(String args[]){
        /*hiveuri hiveStr hiveEndStr*/
        String hiveStr = ";dacp.java.security.krb5.realm=EXAMPLE.COM;dacp.java.security.krb5.kdc=10.247.11.9;dacp.hadoop.security.authentication=Kerberos;";
        String hiveEndStr = "dacp.keytab.file=/home/dacp/dacp03dn;dacp.kerberos.principal=dacp/ZX-DN-03@EXAMPLE.COM";

        String postgresqlStr = "jdbc:postgresql://"; //postgresqlStr ip:port  /  dbname

        String oracleStr = "jdbc:oracle:thin:@";//oracleStr ip:port :  dbname

        String mysqlStr = "jdbc:mysql://";//mysqlStr ip:port /  dbname

        String greenplumStr = "jdbc:pivotal:greenplum://";
        String greenplumEndStr = ";DatabaseName=";//greenplumStr ip:port greenplumEndStr dbname

        String db2Str = "jdbc:db2://";//db2Str ip:port / dbname

        String sqlserverStr = "jdbc:sqlserver://";
        String sqlserverEndStr = ";Database=";//sqlserverStr ip:port sqlserverEndStr dbname

        try {

            String resourceJson = "{\n" +
                    "  \"kind\": \"BackingServiceInstanceList\",\n" +
                    "  \"apiVersion\": \"v1\",\n" +
                    "  \"metadata\": {\n" +
                    "    \"selfLink\": \"/oapi/v1/namespaces/00d85401-2361-41c4-8823-81331c9e7260/backingserviceinstances\",\n" +
                    "    \"resourceVersion\": \"963366\"\n" +
                    "  },\n" +
                    "  \"items\": [\n" +
                    "    {\n" +
                    "      \"metadata\": {\n" +
                    "        \"name\": \"Greenplum_08d0284f-ca4b-4def-a870-f5bd7b524046\",\n" +
                    "        \"namespace\": \"00d85401-2361-41c4-8823-81331c9e7260\",\n" +
                    "        \"selfLink\": \"/oapi/v1/namespaces/00d85401-2361-41c4-8823-81331c9e7260/backingserviceinstances/Greenplum_08d0284f-ca4b-4def-a870-f5bd7b524046\",\n" +
                    "        \"uid\": \"8af5ae58-5d63-11e7-bdc4-00163e0007e4\",\n" +
                    "        \"resourceVersion\": \"953676\",\n" +
                    "        \"creationTimestamp\": \"2017-06-30T07:12:57Z\"\n" +
                    "      },\n" +
                    "      \"spec\": {\n" +
                    "        \"provisioning\": {\n" +
                    "          \"dashboard_url\": \"http://phpgreenplumadmin.dataapp.c.citic?db=d0ea55bc3be335d5\\u0026user=ua82895dc723c4e5\\u0026pass=p163777ca894013b\",\n" +
                    "          \"backingservice_name\": \"Greenplum\",\n" +
                    "          \"backingservice_spec_id\": \"98E2AFE3-7279-40CA-B04E-74276B3FF4B2\",\n" +
                    "          \"backingservice_plan_guid\": \"B48A3972-536F-47A6-B04F-A5344F4DC5E0\",\n" +
                    "          \"backingservice_plan_name\": \"Experimental\",\n" +
                    "          \"parameters\": {\n" +
                    "            \"volumeSize\": \"3\"\n" +
                    "          },\n" +
                    "          \"credentials\": {\n" +
                    "            \"host\": \"10.247.32.84\",\n" +
                    "            \"name\": \"d0ea55bc3be335d5\",\n" +
                    "            \"password\": \"p163777ca894013b\",\n" +
                    "            \"port\": \"5432\",\n" +
                    "            \"uri\": \"postgres://ua82895dc723c4e5:p163777ca894013b@10.247.32.84:5432/d0ea55bc3be335d5\",\n" +
                    "            \"username\": \"ua82895dc723c4e5\"\n" +
                    "          }\n" +
                    "        },\n" +
                    "        \"userprovidedservice\": {\n" +
                    "          \"credentials\": null\n" +
                    "        },\n" +
                    "        \"binding\": null,\n" +
                    "        \"bound\": 0,\n" +
                    "        \"instance_id\": \"8eaa961f-5d63-11e7-bdc4-00163e0007e4\",\n" +
                    "        \"tags\": null\n" +
                    "      },\n" +
                    "      \"status\": {\n" +
                    "        \"phase\": \"Unbound\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"metadata\": {\n" +
                    "        \"name\": \"Greenplum_2e86e338-70db-4bba-af2a-7fa4f5a6366a\",\n" +
                    "        \"namespace\": \"00d85401-2361-41c4-8823-81331c9e7260\",\n" +
                    "        \"selfLink\": \"/oapi/v1/namespaces/00d85401-2361-41c4-8823-81331c9e7260/backingserviceinstances/Greenplum_2e86e338-70db-4bba-af2a-7fa4f5a6366a\",\n" +
                    "        \"uid\": \"bd801359-5cbe-11e7-81da-00163e0007e4\",\n" +
                    "        \"resourceVersion\": \"901055\",\n" +
                    "        \"creationTimestamp\": \"2017-06-29T11:33:15Z\"\n" +
                    "      },\n" +
                    "      \"spec\": {\n" +
                    "        \"provisioning\": {\n" +
                    "          \"dashboard_url\": \"http://phpgreenplumadmin.dataapp.c.citic?db=d71686fe38d14105\\u0026user=u4433649373c0bae\\u0026pass=p86e62b318f5e347\",\n" +
                    "          \"backingservice_name\": \"Greenplum\",\n" +
                    "          \"backingservice_spec_id\": \"98E2AFE3-7279-40CA-B04E-74276B3FF4B2\",\n" +
                    "          \"backingservice_plan_guid\": \"B48A3972-536F-47A6-B04F-A5344F4DC5E0\",\n" +
                    "          \"backingservice_plan_name\": \"Experimental\",\n" +
                    "          \"parameters\": {\n" +
                    "            \"instance_id\": \"bd809320-5cbe-11e7-81da-00163e0007e4\",\n" +
                    "            \"volumeSize\": \"2\"\n" +
                    "          },\n" +
                    "          \"credentials\": {\n" +
                    "            \"host\": \"10.247.32.84\",\n" +
                    "            \"name\": \"d71686fe38d14105\",\n" +
                    "            \"password\": \"p86e62b318f5e347\",\n" +
                    "            \"port\": \"5432\",\n" +
                    "            \"uri\": \"postgres://u4433649373c0bae:p86e62b318f5e347@10.247.32.84:5432/d71686fe38d14105\",\n" +
                    "            \"username\": \"u4433649373c0bae\"\n" +
                    "          }\n" +
                    "        },\n" +
                    "        \"userprovidedservice\": {\n" +
                    "          \"credentials\": null\n" +
                    "        },\n" +
                    "        \"binding\": null,\n" +
                    "        \"bound\": 0,\n" +
                    "        \"instance_id\": \"bd809320-5cbe-11e7-81da-00163e0007e4\",\n" +
                    "        \"tags\": null\n" +
                    "      },\n" +
                    "      \"status\": {\n" +
                    "        \"phase\": \"Unbound\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"metadata\": {\n" +
                    "        \"name\": \"Spark_13b6bec6-179c-461a-9520-3ccd53c572bd\",\n" +
                    "        \"namespace\": \"00d85401-2361-41c4-8823-81331c9e7260\",\n" +
                    "        \"selfLink\": \"/oapi/v1/namespaces/00d85401-2361-41c4-8823-81331c9e7260/backingserviceinstances/Spark_13b6bec6-179c-461a-9520-3ccd53c572bd\",\n" +
                    "        \"uid\": \"38bfa94b-5d6a-11e7-bdc4-00163e0007e4\",\n" +
                    "        \"resourceVersion\": \"959531\",\n" +
                    "        \"creationTimestamp\": \"2017-06-30T08:00:45Z\",\n" +
                    "        \"deletionTimestamp\": \"2017-06-30T09:12:21Z\"\n" +
                    "      },\n" +
                    "      \"spec\": {\n" +
                    "        \"provisioning\": {\n" +
                    "          \"dashboard_url\": \"\",\n" +
                    "          \"backingservice_name\": \"spark\",\n" +
                    "          \"backingservice_spec_id\": \"d3b9a485-f038-4605-9b9b-29792f5c61d1\",\n" +
                    "          \"backingservice_plan_guid\": \"5c3d471d-f94a-4bb8-b340-f783f3c15ba1\",\n" +
                    "          \"backingservice_plan_name\": \"shared\",\n" +
                    "          \"parameters\": {\n" +
                    "            \"yarnQueueQuota\": \"5\"\n" +
                    "          },\n" +
                    "          \"credentials\": {\n" +
                    "            \"host\": \"10.247.33.58\",\n" +
                    "            \"port\": \"8088\",\n" +
                    "            \"uri\": \"http://10.247.33.58:8088\"\n" +
                    "          }\n" +
                    "        },\n" +
                    "        \"userprovidedservice\": {\n" +
                    "          \"credentials\": null\n" +
                    "        },\n" +
                    "        \"binding\": [\n" +
                    "          {\n" +
                    "            \"bound_time\": \"2017-06-30T08:00:56Z\",\n" +
                    "            \"bind_uuid\": \"3ed4aa16-5d6a-11e7-bdc4-00163e0007e4\",\n" +
                    "            \"bind_hadoop_user\": \"atest\",\n" +
                    "            \"credentials\": {\n" +
                    "              \"Host\": \"\",\n" +
                    "              \"Name\": \"\",\n" +
                    "              \"Password\": \"\",\n" +
                    "              \"Port\": \"\",\n" +
                    "              \"Uri\": \"\",\n" +
                    "              \"Username\": \"\",\n" +
                    "              \"Vhost\": \"\"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"bound\": 1,\n" +
                    "        \"instance_id\": \"3e350344-5d6a-11e7-bdc4-00163e0007e4\",\n" +
                    "        \"tags\": null\n" +
                    "      },\n" +
                    "      \"status\": {\n" +
                    "        \"phase\": \"Bound\",\n" +
                    "        \"action\": \"_ToDelete\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            JsonParser parser=new JsonParser();  //创建JSON解析器
            JsonObject object=(JsonObject) parser.parse(resourceJson);  //创建JsonObject对象
            System.out.println("kind="+object.get("kind"));
            System.out.println("apiVersion="+object.get("apiVersion"));

            JsonArray array=object.get("items").getAsJsonArray();    //得到为json的数组

            Map<String,Object> mapInfo = new HashMap<>();
            List dbRegisterList = new ArrayList<>();
            List dbDistributionList = new ArrayList<>();
            List hadoopResourceList = new ArrayList<>();



            for(int i=0;i<array.size();i++){
                System.out.println("---------------");
                JsonObject subObject=array.get(i).getAsJsonObject();
                JsonObject specJsonObj = subObject.get("spec").getAsJsonObject();

                JsonObject provisioningJsonObj = specJsonObj.get("provisioning").getAsJsonObject();
                /*数据库注册*/
                String xmlid = "";//xmlid
                String backingservice_name = provisioningJsonObj.get("backingservice_name").getAsString();//dbname
                String cnname = provisioningJsonObj.get("backingservice_name").getAsString();//cnname
                String driveTypeStr = provisioningJsonObj.get("backingservice_name").getAsString().toLowerCase();
                String driverclassname = DriverTypeEnum.getDriverTypeEnum(driveTypeStr);//driverclassname

                JsonObject credentialsJsonObj = provisioningJsonObj.get("credentials").getAsJsonObject();
                String uri = credentialsJsonObj.get("uri").getAsString();//uri
                String host = credentialsJsonObj.get("host").getAsString();//host
                String port = credentialsJsonObj.get("port").getAsString();//port
                String databasename = "";
                String username = "";
                String password = "";
                if(credentialsJsonObj.get("name")!=null){
                    databasename = credentialsJsonObj.get("name").getAsString();
                }
                String url = DBUrlEnum.getDBUrlEnum(backingservice_name.toLowerCase(), uri, host, port, databasename);//url
                if(credentialsJsonObj.get("username")!=null){
                    username = credentialsJsonObj.get("username").getAsString();//username
                }
                if(credentialsJsonObj.get("password")!=null){
                    password = credentialsJsonObj.get("password").getAsString();//password
                }
                String remark = "";//remark
                String alias = backingservice_name;//alias

                /*数据库分配*/
                String state = "on";
                String team_code = "";
                String db_type = "";//与dbname值相同



                if(!(backingservice_name.equalsIgnoreCase("hdfs")||backingservice_name.equalsIgnoreCase("hive")||
                        backingservice_name.equalsIgnoreCase("spark")||backingservice_name.equalsIgnoreCase("mapreduce"))){
                    DBRegister dbRegister = new DBRegister();
                    dbRegister.setXmlid(xmlid);
                    dbRegister.setDbname(backingservice_name);
                    dbRegister.setCnname(cnname);
                    dbRegister.setDriverclassname(driverclassname);
                    dbRegister.setUrl(url);
                    dbRegister.setUsername(username);
                    dbRegister.setPassword(password);
                    dbRegister.setRemark(remark);
                    dbRegister.setAlias(alias);

                    DBDistribution dbDistribution = new DBDistribution();
                    dbDistribution.setDbname(backingservice_name);
                    dbDistribution.setCnname(cnname);
                    dbDistribution.setDriverclassname(driverclassname);
                    dbDistribution.setUrl(url);
                    dbDistribution.setUsername(username);
                    dbDistribution.setPassword(password);
                    dbDistribution.setState(state);
                    dbDistribution.setTeam_code(team_code);
                    dbDistribution.setDbtype(backingservice_name);

                    dbRegisterList.add(dbRegister);
                    dbDistributionList.add(dbDistribution);
                }else{
                    /*hadoop资源分配授权*/
                    String res_cfg = getResCfg(backingservice_name.toLowerCase());//针对yarn、hdfs、hive授权参数，参数已json格式传递
                    HadoopResource hadoopResource = new HadoopResource();
                    hadoopResource.setTeam_code(team_code);
                    hadoopResource.setType(backingservice_name);
                    hadoopResource.setRes_cfg(res_cfg);
                    hadoopResource.setStatus("1");
                    hadoopResourceList.add(hadoopResource);
                }
            }

            mapInfo.put("database",dbRegisterList);
            mapInfo.put("transdatabase",dbDistributionList);
            mapInfo.put("hadoopaudit",hadoopResourceList);
            JSONObject dacpJsonObject = JSONObject.fromObject(mapInfo);
            System.out.println("DACP info："+dacpJsonObject.toString());


        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    public static String getResCfg(String resourceType){
        Map<String,String> map = new HashMap<>();

        if("yarn".equals(resourceType)){
            String queue_name = "";
            String capacity = "";
            String maximum_capacity = "";
            String state = "RUNNING";
            map.put("queue_name",queue_name);
            map.put("capacity",capacity);
            map.put("maximum_capacity",maximum_capacity);
            map.put("state",state);

        }else if("hdfs".equals(resourceType)){
            String folderPath = "";
            String storageSpaceQuota = "";
            String nameSpaceQuota = "";
            map.put("folderPath",folderPath);
            map.put("storageSpaceQuota",storageSpaceQuota);
            map.put("nameSpaceQuota",nameSpaceQuota);

        }else if("hive".equals(resourceType)){
            String databaseName = "";
            map.put("databaseName",databaseName);
        }

        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject.toString();
    }
}
