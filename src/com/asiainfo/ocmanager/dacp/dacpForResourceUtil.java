package com.asiainfo.ocmanager.dacp;

import com.asiainfo.ocmanager.dacp.model.DBDistribution;
import com.asiainfo.ocmanager.dacp.model.DBRegister;
import com.asiainfo.ocmanager.dacp.model.HadoopResource;

import com.asiainfo.ocmanager.dacp.model.Team;
import com.asiainfo.ocmanager.dacp.service.TeamWrapper;
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

    private static Map<String, List> mapInfo;

    public static Map<String, List> getResult(String tenantId) {
        try {

            String resourceJson = DacpQuery.GetData(tenantId);

            JsonParser parser = new JsonParser();  //创建JSON解析器
            JsonObject object = (JsonObject) parser.parse(resourceJson);  //创建JsonObject对象
//            System.out.println("kind="+object.get("kind"));
//            System.out.println("apiVersion="+object.get("apiVersion"));

            JsonArray array = object.get("items").getAsJsonArray();    //得到为json的数组

            mapInfo = new HashMap<>();
            List dbRegisterList = new ArrayList<>();
            List dbDistributionList = new ArrayList<>();

            for (int i = 0; i < array.size(); i++) {
                JsonObject subObject = array.get(i).getAsJsonObject();
                JsonObject specJsonObj = subObject.get("spec").getAsJsonObject();
                JsonObject statusJsonObj = subObject.get("status").getAsJsonObject();
                String phase = statusJsonObj.get("phase").getAsString();
                if (!"Failure".equals(phase)) {
                    JsonObject provisioningJsonObj = specJsonObj.get("provisioning").getAsJsonObject();
                    /*数据库注册*/
                    String xmlid = tenantId;//xmlid
                    String backingservice_name = provisioningJsonObj.get("backingservice_name").getAsString();//dbname
                    String cnname = provisioningJsonObj.get("backingservice_name").getAsString();//cnname
                    String driveTypeStr = provisioningJsonObj.get("backingservice_name").getAsString().toLowerCase();
                    String driverclassname;//driverclassname
                    driverclassname = DriverTypeEnum.getDriverTypeEnum(driveTypeStr);

                    String databasename = "";
                    String username = "";
                    String password = "";
                    String url = "";
                    boolean flag = provisioningJsonObj.get("credentials").isJsonObject();
                    if (flag) {
                        JsonObject credentialsJsonObj = provisioningJsonObj.get("credentials").getAsJsonObject();
                        String uri = "";
                        if (credentialsJsonObj.has("uri")) {
                            uri = credentialsJsonObj.get("uri").getAsString();//uri
                        }
                        String host = credentialsJsonObj.get("host").getAsString();//host
                        String port = credentialsJsonObj.get("port").getAsString();//port
                        if (credentialsJsonObj.get("name") != null) {
                            databasename = credentialsJsonObj.get("name").getAsString();
                        }

                        url = DBUrlEnum.getDBUrlEnum(backingservice_name.toLowerCase(), uri, host, port, databasename);//url
                        if (credentialsJsonObj.get("username") != null) {
                            username = credentialsJsonObj.get("username").getAsString();//username
                        }else{
                            System.out.println("query for username is null");
                            continue;
                        }
                        if (credentialsJsonObj.get("password") != null) {
                            password = credentialsJsonObj.get("password").getAsString();//password
                        }
                    }
                    String remark = "";//remark

                    /*数据库分配*/
                    String state = "on";
                    String team_code;
                    Team team = TeamWrapper.getTeamFromTenant(xmlid);
                    team_code = String.valueOf(team.getteam_code());

                    DBRegister dbRegister = new DBRegister();
                    dbRegister.setXmlid(xmlid);
                    dbRegister.setDbname(backingservice_name);
                    dbRegister.setCnname(cnname);
                    dbRegister.setDriverclassname(driverclassname);
                    dbRegister.setUrl(url);
                    dbRegister.setUsername(username);
                    dbRegister.setPassword(password);
                    dbRegister.setRemark(remark);
                    dbRegister.setAlias(backingservice_name.toLowerCase());

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
                }
            }

            mapInfo.put("database", dbRegisterList);
            mapInfo.put("transdatabase", dbDistributionList);
            JSONObject dacpJsonObject = JSONObject.fromObject(mapInfo);


        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return mapInfo;
    }
}
