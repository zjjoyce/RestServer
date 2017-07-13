package com.asiainfo.ocmanager.dacp;

import com.asiainfo.ocmanager.mail.ParamQuery;

import java.io.IOException;

/**
 * Created by YANLSH on 2017/7/3.
 */
public enum DBUrlEnum {

    /*hiveuri hiveStr hiveEndStr*/
    HIVE("dacp.java.security.krb5.realm=EXAMPLE.COM;dacp.java.security.krb5.kdc=10.247.11.9;dacp.hadoop.security.authentication=Kerberos;","hive"),
    POSTGRE("jdbc:postgresql://","postgresql"),         //postgresqlStr ip:port  /  dbname
    ORACLE("jdbc:oracle:thin:@","oracle"),              //oracleStr ip:port :  dbname
    MySQL("jdbc:mysql://","mysql"),                     //mysqlStr ip:port /  dbname
    GREENPLUM("jdbc:pivotal:greenplum://","greenplum"), //greenplumStr ip:port greenplumEndStr dbname
    DB2("jdbc:db2://","db2"),                           //db2Str ip:port / dbname
    SQLSERVER("jdbc:sqlserver://","sqlserver"),         //sqlserverStr ip:port sqlserverEndStr dbnam
    NEO4J("jdbc:neo4j://","neo4j"),
    MONGODB("","mongodb");



    static String hiveEndStr = "dacp.keytab.file=/home/dacp/dacp03dn;dacp.kerberos.principal=dacp/ZX-DN-03@EXAMPLE.COM";
    static String greenplumEndStr = ";DatabaseName=";//greenplumStr ip:port greenplumEndStr dbname
    static String sqlserverEndStr = ";Database=";//sqlserverStr ip:port sqlserverEndStr dbname
    static String dacpSecurityKrb5Realm;
    static String dacpSecutityKrb5Kdc;
    static String dacpHadoopSecurityAuth;
    static String dacpKeytabFile;
    static String dacpKerosPrincipal;
    static String hiveUrl;

    String driveUrl;

    String driveType;

    DBUrlEnum(String driveUrl, String driveType) {
        this.driveUrl = driveUrl;
        this.driveType = driveType;
    }


    public static String getDBUrlEnum(String driveTypeStr,String uri,String ip,String port,String dbname){
        for (DBUrlEnum c : DBUrlEnum.values()) {
            if (c.getDriveType().equals(driveTypeStr)) {
                if("hive".equals(driveTypeStr)){
                    try {
                        dacpSecurityKrb5Realm = ParamQuery.getCFProperties().get(ParamQuery.DACP_JAVA_SECURITY_KRB5_REALM);
                        dacpSecutityKrb5Kdc = ParamQuery.getCFProperties().get(ParamQuery.DACP_JAVA_SECURITY_KRB5_KDC);
                        dacpHadoopSecurityAuth = ParamQuery.getCFProperties().get(ParamQuery.DACP_HADOOP_SECURITY_AUTHENTICATION);
                        dacpKeytabFile = ParamQuery.getCFProperties().get(ParamQuery.DACP_KEYTAB_FILE);
                        dacpKerosPrincipal = ParamQuery.getCFProperties().get(ParamQuery.DACP_KERBEROS_PRINCIPAL);
                        hiveUrl = uri + ";"+ dacpSecurityKrb5Realm + dacpSecutityKrb5Kdc + dacpHadoopSecurityAuth + dacpKeytabFile + dacpKerosPrincipal;
                    } catch (IOException e) {
                        System.out.println("DBUrlEnum IOException "+e.getMessage());
                        hiveUrl = uri + ";"+ c.driveUrl + hiveEndStr;
                    }
                    return hiveUrl;
                }else if("postgresql".equals(driveTypeStr)){
                    return c.driveUrl + ip + ":" + port + "/" + dbname; //jdbc:postgresql://x.x.x.x:3433/odsdb
                }else if("oracle".equals(driveTypeStr)){
                    return c.driveUrl + ip + ":" + port + ":" + dbname; //jdbc:oracle:thin:@x.x.x.x:1521:odsdb
                }else if("mysql".equals(driveTypeStr)){
                    return c.driveUrl + ip + ":" + port + "/" + dbname; //jdbc:mysql://x.x.x.x:3306/d81ec211eec89cd5
                }else if("greenplum".equals(driveTypeStr)){
                    return c.driveUrl + ip + ":" + port + greenplumEndStr + dbname; //jdbc:pivotal:greenplum://x.x.x.x:5432;DatabaseName=d90b288fd395ee1b
                }else if("db2".equals(driveTypeStr)){
                    return c.driveUrl + ip + ":" + port + "/" + dbname; //jdbc:db2://x.x.x.x:50010/ngcqdw
                }else if("sqlserver".equals(driveTypeStr)){
                    return c.driveUrl + ip + ":" + port + sqlserverEndStr + dbname; //jdbc:mysql://x.x.x.x:3306/d81ec211eec89cd5
                }else if("mongodb".equals(driveTypeStr)){
                    return uri; //mongodb://username:password@ip:port/datavase
                }else if("neo4j".equals(driveTypeStr)){
                    return c.driveUrl + ip + ":" + port + "/"; //jdbc:neo4j://localhost:7474/
                }
            }
        }
        return "";
    }

    public String getDriveType() {
        return driveType;
    }
}
