package com.asiainfo.ocmanager.dacp;

/**
 * Created by YANLSH on 2017/7/3.
 */
public enum DriverTypeEnum {
    ORACLE("oracle.jdbc.dirver.OracleDriver","oracle"), MySQL("com.mysql.jdbc.Driver","mysql"),
    DB2("com.ibm.db2.jdbc.net.DB2Driver","db2"), HIVE("org.apache.hadoop.hive.jdbc.HiveDriver","hive"),
    GREENPLUM("org.postgresql.Driver","greenplum");

    String driveClassname;

    String driveType;

    DriverTypeEnum(String driveClassname, String driveType) {
        this.driveClassname = driveClassname;
        this.driveType = driveType;
    }



    public static String getDriverTypeEnum(String driveTypeStr){
        for (DriverTypeEnum c : DriverTypeEnum.values()) {
            if (c.getDriveType().equals(driveTypeStr)) {
                return c.driveClassname;
            }
        }
        return null;
    }

    public String getDriveType() {
        return driveType;
    }

    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }

    public String getDriveClassname() {
        return driveClassname;
    }

    public void setDriveClassname(String driveClassname) {
        this.driveClassname = driveClassname;
    }



}
