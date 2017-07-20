package com.asiainfo.ocmanager.dacp;

import com.asiainfo.ocmanager.dacp.model.*;
import com.asiainfo.ocmanager.dacp.service.TeamWrapper;
import com.asiainfo.ocmanager.dacp.service.UserWrapper;
import com.asiainfo.ocmanager.rest.resource.quotaUtils.restClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Allen on 2017/7/5.
 */
public class DacpAllResult {

    private static Map info;
    private static Team team;
    private static String result;
    private static Properties prop;

    private static Logger logger = Logger.getLogger(DacpAllResult.class);

    public static String getAllResult(String tenantId){
        logger.info("begin to call dacp interface");

        String classPath = new Property().getClass().getResource("/").getPath();
        String currentClassesPath = classPath.substring(0, classPath.length() - 8)+ "conf/config.properties";
        try{
            InputStream inStream = new FileInputStream(new File(currentClassesPath ));
            prop = new Properties();
            prop.load(inStream);
        }catch(IOException e){
            logger.error(e.getMessage());
        }


        info = new HashMap<>();
        try{
            //get team
            team = TeamWrapper.getTeamFromTenant(tenantId);
            List<Team> teams = new ArrayList<>() ;
            teams.add(team);
            //get userinfo
            List<UserInfo> userInfos = UserWrapper.getUserInfoFromUserRoleView(tenantId);
            //getDBRegister,DbDistribution,HadoopResource
            Map<String,List> map = dacpForResourceUtil.getResult(tenantId);
            List<DBRegister> DBRegisters = map.get("database");
            List<DBDistribution> DBDistributions = map.get("transdatabase");
            // add data to info jsonObject
            GsonBuilder gb =new GsonBuilder();
            gb.disableHtmlEscaping();
            String teamStr = gb.create().toJson(teams);
            String userInfoStr = gb.create().toJson(userInfos);
            String DBRegisterStr = gb.create().toJson(DBRegisters);
            String DBDistributionStr = gb.create().toJson(DBDistributions);
            Gson gson = new Gson();
            /*String teamStr = gson.toJson(teams);
            String userInfoStr = gson.toJson(userInfos);
            String DBRegisterStr = gson.toJson(DBRegisters);
            String DBDistributionStr = gson.toJson(DBDistributions);*/
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("userinfo",userInfoStr);
            jsonObject.addProperty("team",teamStr);
            jsonObject.addProperty("database",DBRegisterStr);
            jsonObject.addProperty("transdatabase",DBDistributionStr);
//            jsonObject.addProperty("hadoopaudit",HadoopRsourceStr);


            String infoStr = gb.create().toJson(jsonObject);
            //System.out.println(infoStr);

            String reinfoStr = infoStr.replace("\\","").replace("\"[","[").replace("]\"","]");
            logger.info(reinfoStr);
            info.put("info",reinfoStr);
            logger.info("dacp url :"+prop.getProperty("dacp.url"));
            String restResult = restClient.post(prop.getProperty("dacp.url"),info);
            DacpResult dacpResult = gson.fromJson(restResult,DacpResult.class);
            String result = dacpResult.getResult();
            // log the result of sync process
            if(result.equals("true")){
                logger.info("dacp is ok");
            }else{
                logger.error("sync dacp is failed,error message is:" + dacpResult.getMessage());
            }

        }catch (Exception e){
            logger.info(e.getMessage());
        }
        logger.info("end to call dacp interface");
        return result;

    }
}
