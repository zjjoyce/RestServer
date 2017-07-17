package com.asiainfo.ocmanager.dacp;

import com.asiainfo.ocmanager.dacp.model.DBDistribution;
import com.asiainfo.ocmanager.dacp.model.DBRegister;

import com.asiainfo.ocmanager.dacp.model.Team;
import com.asiainfo.ocmanager.dacp.service.TeamWrapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YANLSH
 * Created on 2017/7/3
 */
public class dacpForResourceUtil {

    public static Log logger = LogFactory.getLog(dacpForResourceUtil.class);

    private static Map<String, List> mapInfo;
    public static Map<String, List> getResult(String tenantId) {
        try {
            String resourceJson = DacpQuery.GetData(tenantId);
            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(resourceJson);
            JsonArray array = object.get("items").getAsJsonArray();

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
                    String backingservice_name = provisioningJsonObj.get("backingservice_name").getAsString();//dbname
                    String cnname = provisioningJsonObj.get("backingservice_name").getAsString();//cnname
                    String driveTypeStr = provisioningJsonObj.get("backingservice_name").getAsString().toLowerCase();
                    String driverclassname;
                    driverclassname = DriverTypeEnum.getDriverTypeEnum(driveTypeStr);

                    String databasename = "";
                    String username = "";
                    String password = "";
                    String url = "";
                    boolean flag = provisioningJsonObj.get("credentials").isJsonObject();
                    if (flag) {
                        JsonObject credentialsJsonObj = provisioningJsonObj.get("credentials").getAsJsonObject();
                        if (credentialsJsonObj.get("username") != null) {
                            username = credentialsJsonObj.get("username").getAsString();//username
                        }else{
                            logger.info("query for username is null");
                            continue;
                        }
                        if (credentialsJsonObj.get("password") != null) {
                            password = credentialsJsonObj.get("password").getAsString();//password
                        }
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


                    }
                    String remark = "";//remark

                    /*数据库分配*/
                    String state = "on";
                    String team_code;
                    Team team = TeamWrapper.getTeamFromTenant(tenantId);
                    team_code = String.valueOf(team.getteam_code());

                    DBRegister dbRegister = new DBRegister();
                    dbRegister.setXmlid(tenantId);
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

        } catch (JsonIOException e) {
            logger.info("DacpforResourceUtil JsonIOException " + e.getMessage());
        } catch (JsonSyntaxException e) {
            logger.info("DacpforResourceUtil JsonSyntaxException " + e.getMessage());
        }
        return mapInfo;
    }
}
