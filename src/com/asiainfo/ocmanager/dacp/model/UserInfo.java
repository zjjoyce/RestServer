package com.asiainfo.ocmanager.dacp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yujin on 2017/7/3.
 */
public class UserInfo {
    private String username;
    private String usercnname;
    private String password;
    private String phone;
    private String qq;
    private String mail;
    private String msn;
    private List<String> role;

    public UserInfo(String username,String usercnname,String password,String phone,String qq,String mail,String msn,List<String> role){
        this.username = username;
        this.usercnname = usercnname;
        this.password = password;
        this.phone = phone;
        this.qq = qq;
        this.mail = mail;
        this.msn = msn;
        this.role = role;
    }
    public void setUserName(String userName){this.username = username;}
    public String getUserName(){return this.username;}

    public void setUsercnName(String usercnName){this.usercnname = usercnname;}
    public String getUsercnName(){return this.usercnname;}

    public void setPassword(String password){this.password = password;}
    public String getPassword(){return this.password;}

    public void setPhone(String phone){this.phone = phone;}
    public String getPhone(){return this.phone;}
    public void setQq(String qq){this.qq = qq;}
    public String getQq(){return this.qq;}

    public void setMail(String mail){this.username = mail;}
    public String getMail(){return this.mail;}

    public void setMsn(String userName){this.username = userName;}
    public String getMsn(){return this.msn;}

    public void setRole(List<String> role){this.role = role;}
    public List<String> getRole(){return this.role;}

}
