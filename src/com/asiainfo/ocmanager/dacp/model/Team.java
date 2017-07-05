package com.asiainfo.ocmanager.dacp.model;

/**
 * Created by yujin on 2017/7/3.
 */
public class Team {
    private String xmlId;
    private int teamCode;
    private String teamName;
    private int teamType;
    private String startDate;
    private String state;
    private String iconPath;
    private String remark;

    public Team(String xmlId,int teamCode,String teamName,int teamType,String startDate,String state,String iconPath){
        this.xmlId = xmlId;
        this.teamName = teamName;
        this.teamCode = teamCode;
        this.teamType = teamType;
        this.startDate = startDate;
        this.state = state;
        this.iconPath = iconPath;
    }
    public void setXmlId(String xmlId){this.xmlId = xmlId;}
    public String getXmlId(){return this.xmlId;}

    public void setTeamCode(int teamCode){this.teamCode = teamCode;}
    public int getTeamCode(){return this.teamCode;}
    public void setTeamName(String teamName){this.teamName = teamName;}
    public String getTeamName(){return this.teamName;}

    public void setTeamType(int teamType){this.teamType = teamType;}
    public int getTeamType(){return this.teamType;}

    public void setStartDate(String startDate){this.startDate = startDate;}
    public String getStartDate(){return this.startDate;}

    public void setState(String state){this.state = state;}
    public String getState(){return this.state;}

    public void setIconPath(String iconPath){this.iconPath = iconPath;}
    public String getIconPath(){return this.iconPath;}

    public void setRemark(String remark){this.remark = remark;}
    public String getRemark(){return this.remark;}



}
