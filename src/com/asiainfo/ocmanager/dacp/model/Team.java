package com.asiainfo.ocmanager.dacp.model;

/**
 * Created by yujin on 2017/7/3.
 */
public class Team {
    private String xmlid;
    private int team_code;
    private String team_name;
    private int team_type;
    private String start_date;
    private String state;
    private String icon_path;
    private String remark;

    public Team(String xmlid,int team_code,String team_name,int team_type,String start_date,String state,String icon_path,String remark){
        this.xmlid = xmlid;
        this.team_name = team_name;
        this.team_code = team_code;
        this.team_type = team_type;
        this.start_date = start_date;
        this.state = state;
        this.icon_path = icon_path;
        this.remark = remark;
    }
    public void setxmlid(String xmlid){this.xmlid = xmlid;}
    public String getxmlid(){return this.xmlid;}

    public void setteam_code(int team_code){this.team_code = team_code;}
    public int getteam_code(){return this.team_code;}
    public void setteam_name(String team_name){this.team_name = team_name;}
    public String getteam_name(){return this.team_name;}

    public void setteam_type(int team_type){this.team_type = team_type;}
    public int getteam_type(){return this.team_type;}

    public void setstart_date(String start_date){this.start_date = start_date;}
    public String getstart_date(){return this.start_date;}

    public void setState(String state){this.state = state;}
    public String getState(){return this.state;}

    public void seticon_path(String icon_path){this.icon_path = icon_path;}
    public String geticon_path(){return this.icon_path;}

    public void setRemark(String remark){this.remark = remark;}
    public String getRemark(){return this.remark;}

}
