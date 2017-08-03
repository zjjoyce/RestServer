package com.asiainfo.ocmanager.dacp.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class KeyTabClient {

    public static final Log log = LogFactory.getLog(KeyTabClient.class);
    public static boolean CreatKeyTab(String s,String dest){
        /**
         * @param dest
         * 解密后字符串存储文件
         * @param s
         * 解密前字符串
         */
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(dest));
               if(s!=null){
                   String decryptString = decryptBASE64(s);
                   log.info("解密后：" + decryptString);
                   out.write(decryptString);
                   log.info("decryptString is true");
                   return true;
            }
             out.close();
        }catch (FileNotFoundException e) {
            log.info("error when read s" +e);
            return false;
        } catch (IOException e){
            log.info("error when read s,io is error" +e);
            return false;
        }
        return true;
    }
    public static String decryptBASE64(String key) {//解密
        byte[] bt ;
        try {
            bt = (new BASE64Decoder()).decodeBuffer(key);
            return new String(bt);//如果出现乱码加以修改
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static void main(String[] args) {
        // write your code here

        CreatKeyTab("BQIAAAAqAAEACUNJVElDLkNPTQAGd2FuZ2JnAAAAAVl13cAAAAMACNn3wlJUOMvTAAAAQgABAAlDSVRJQy5DT00ABndhbmdiZwAAAAFZdd3AAAASACAwEY/T1lm2fNC3joARxQeE9pDa+6DnsC1bU1NS/dmXAwAAADoAAQAJQ0lUSUMuQ09NAAZ3YW5nYmcAAAABWXXdwAAAEAAYW8IxcGiwnrUQuo8THCBufH8cKlF/vLwaAAAAMgABAAlDSVRJQy5DT00ABndhbmdiZwAAAAFZdd3AAAAXABAEd9ZpaQc4mN6Hr8DCLvjXAAAAMgABAAlDSVRJQy5DT00ABndhbmdiZwAAAAFZdd3AAAARABCpJVdbAJEdbxCeowfiz1VL","d:\\toutput.txt");
    }

}







