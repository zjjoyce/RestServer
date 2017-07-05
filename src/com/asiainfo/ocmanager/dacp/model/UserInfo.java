package com.asiainfo.ocmanager.dacp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yujin on 2017/7/3.
 */
public class UserInfo {
    private String userName;
    private String usercnName;
    private String password;
    private String phone;
    private String qq;
    private String mail;
    private String msn;
    private List<String> role;

    public UserInfo(){}
    public UserInfo(String userName,String usercnName,String password,String phone,String qq,String mail,String msn,List<String> role){
        this.userName = userName;
        this.usercnName = usercnName;
        this.password = password;
        this.phone = phone;
        this.qq = qq;
        this.mail = mail;
        this.msn = msn;
        this.role = role;
    }
    public void setUserName(String userName){this.userName = userName;}
    public String getUserName(){return this.userName;}

    public void setUsercnName(String usercnName){this.usercnName = usercnName;}
    public String getUsercnName(){return this.usercnName;}

    public void setPassword(String password){this.password = password;}
    public String getPassword(){return this.password;}

    public void setPhone(String phone){this.phone = phone;}
    public String getPhone(){return this.phone;}
    public void setQq(String qq){this.qq = qq;}
    public String getQq(){return this.qq;}

    public void setMail(String mail){this.userName = mail;}
    public String getMail(){return this.mail;}

    public void setMsn(String userName){this.userName = userName;}
    public String getMsn(){return this.msn;}

    public void setRole(List<String> role){this.role = role;}
    public List<String> getRole(){return this.role;}

}
