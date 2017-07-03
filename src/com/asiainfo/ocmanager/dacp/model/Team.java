package com.asiainfo.ocmanager.dacp.model;

/**
 * Created by yujin on 2017/7/3.
 */
public class Team {
    private String xmlId;
    private String teamCode;
    private String teamName;
    private String teamType;
    private String startDate;
    private String state;
    private String iconPath;
    private String remark;

    public Team(String xmlId,String teamCode,String teamType,String state){
        this.xmlId = xmlId;
        this.teamCode = teamCode;
        this.teamType = teamType;
        this.state = state;
    }
    public void setXmlId(String xmlId){this.xmlId = xmlId;}
    public String getXmlId(){return this.xmlId;}

    public void setTeamCode(String teamCode){this.teamCode = teamCode;}
    public String getTeamCode(){return this.teamCode;}
    public void setTeamName(String teamName){this.teamName = teamName;}
    public String getTeamName(){return this.teamName;}

    public void setTeamType(String teamType){this.teamType = teamType;}
    public String getTeamType(){return this.teamType;}

    public void setStartDate(String startDate){this.startDate = startDate;}
    public String getStartDate(){return this.startDate;}

    public void setState(String state){this.state = state;}
    public String getState(){return this.state;}

    public void setIconPath(String iconPath){this.iconPath = iconPath;}
    public String getIconPath(){return this.iconPath;}

    public void setRemark(String remark){this.remark = remark;}
    public String getRemark(){return this.remark;}



}
