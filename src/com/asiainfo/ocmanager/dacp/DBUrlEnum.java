package com.asiainfo.ocmanager.dacp;

/**
 * Created by YANLSH on 2017/7/3.
 */
public enum DBUrlEnum {
    /*hiveuri hiveStr hiveEndStr*/
    HIVE(";dacp.java.security.krb5.realm=EXAMPLE.COM;dacp.java.security.krb5.kdc=10.247.11.9;dacp.hadoop.security.authentication=Kerberos;","hive"),
    POSTGRE("jdbc:postgresql://","postgresql"),         //postgresqlStr ip:port  /  dbname
    ORACLE("jdbc:oracle:thin:@","oracle"),              //oracleStr ip:port :  dbname
    MySQL("jdbc:mysql://","mysql"),                     //mysqlStr ip:port /  dbname
    GREENPLUM("jdbc:pivotal:greenplum://","greenplum"), //greenplumStr ip:port greenplumEndStr dbname
    DB2("jdbc:db2://","db2"),                           //db2Str ip:port / dbname
    SQLSERVER("jdbc:sqlserver://","sqlserver");         //sqlserverStr ip:port sqlserverEndStr dbname


    static String hiveEndStr = "dacp.keytab.file=/home/dacp/dacp03dn;dacp.kerberos.principal=dacp/ZX-DN-03@EXAMPLE.COM";
    static String greenplumEndStr = ";DatabaseName=";//greenplumStr ip:port greenplumEndStr dbname
    static String sqlserverEndStr = ";Database=";//sqlserverStr ip:port sqlserverEndStr dbname

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
                    return uri + c.driveUrl + hiveEndStr;
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
                }
            }
        }
        return "";
    }

    public String getDriveType() {
        return driveType;
    }

    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }

    public String getDriveClassname() {
        return driveUrl;
    }

    public void setDriveClassname(String driveClassname) {
        this.driveUrl = driveClassname;
    }



}
