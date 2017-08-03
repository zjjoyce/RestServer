package com.asiainfo.ocmanager.rest.DownloadUtils;

import com.asiainfo.ocmanager.dacp.DFDataQuery;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


import java.io.*;
import java.util.*;

/**
 * Created by zhangfq on 2017/8/2.
 *
 * GetFile whether the user first Downloads
 */
public class GetFile {

    private static Properties prop = new Properties();
    private static Logger logger = Logger.getLogger(GetFile.class);

    static {
        String classPath = new GetFile().getClass().getResource("/").getPath();
        String currentClassesPath = classPath.substring(0, classPath.length() - 8)+ "conf/config.properties";
        try{
            InputStream inStream = new FileInputStream(new File(currentClassesPath ));
            prop.load(inStream);
        }catch(IOException e){
            logger.error(e.getMessage());
        }
    }

    public static Map getFile(String tanantid,String username){
        boolean isexists = GetFile.judgeFileExists(username);
        String filepaths = prop.getProperty("keytab.path") + username + prop.getProperty("keytab.type");
        Map result = new HashMap<>();
        result.put("filepaths",filepaths);
        if(isexists==true){
            File file = new File(filepaths);
            result.put("file",file);
            return result;
        }
        File file = null;
        try {
            file = createFile(tanantid,username);
            result.put("file",file);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public static File createFile(String tanantid,String username) throws IOException {
        String filepaths = prop.getProperty("keytab.path") + username + prop.getProperty("keytab.type");
        String keytab = getKeytabData(tanantid,username);
        Base64 base64 = new Base64();
        String decodekeytab = new String(base64.decode(keytab));
        File dir = new File(prop.getProperty("keytab.path"));
        File file = new File(filepaths);
        try {
            dir.mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter(filepaths);
            fw.write(decodekeytab);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fw != null) {
                fw.close();
            }
        }
        return file;
    }

    public static String getKeytabData(String tenantId,String username){
        String resource = DFDataQuery.GetTenantData(tenantId);
        logger.info("call DF tenant instance resource: \r\n"+resource);
        String result = "";
        try {
            JSONObject resourceJson = new JSONObject(resource);
            String items = resourceJson.getString("items");
            JSONArray itemsJson = new JSONArray(items);
            for(int i = 0;i<itemsJson.length();i++){
                JSONObject json = new JSONObject(itemsJson.getString(i));
                String status = json.getString("status");
                JSONObject statusJson = new JSONObject(status);
                String phase = statusJson.getString("phase");
                String spec = json.getString("spec");
                JSONObject specJson = new JSONObject(spec);
                String binding = specJson.getString("binding");
                if(phase.equals("Bound")){
                    JSONArray bindingJson = new JSONArray(binding);
                    for(int j = 0;j<bindingJson.length();j++){
                        JSONObject jsonn = new JSONObject(bindingJson.getString(j));
                        String credentials = jsonn.getString("credentials");
                        if(credentials==null||credentials.equals("")){
                            logger.info("Failed to obtain binding information for this user,The information is empty");
                            continue;
                        }
                        JSONObject credentialsJson = new JSONObject(credentials);
                        String principal = credentialsJson.getString("username");
                        String[] usernames = principal.split("@");
                        String splitusername = usernames[0];
                        if(username.equals(splitusername)){
                            String keytab = credentialsJson.getString("keytab");
                            result = keytab;
                            return result;
                        }
                    }
                }
            }

        } catch (JSONException e) {
            logger.error(e.getMessage());
        }
        return  result;
    }

    public static boolean judgeFileExists(String username) {

        File file = new File(prop.getProperty("keytab.path") + username + prop.getProperty("keytab.type"));
        if (file.exists()) {
            logger.info("The user is not download for the first time");
            return true;
        } else{
            logger.info("The user download for the first time");
            return false;
        }
    }
}
