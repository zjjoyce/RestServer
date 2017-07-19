package com.asiainfo.ocmanager.dacp.model;

/**
 * Created by yujin on 2017/7/4.
 */
public class DacpResult {
    private String result;
    private String msg;
    public DacpResult(String result,String msg){
        this.result = result;
        this.msg = msg;
    }
    public void setResult(String result){this.result = result;}
    public String getResult(){return this.result;}

    public void setMessage(String msg){this.msg = msg;}
    public String getMessage(){return this.msg;}
}
