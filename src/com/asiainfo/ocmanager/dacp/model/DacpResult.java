package com.asiainfo.ocmanager.dacp.model;

/**
 * Created by yujin on 2017/7/4.
 */
public class DacpResult {
    private String result;
    private String message;
    public DacpResult(String result,String message){
        this.result = result;
        this.message = message;
    }
    public void setResult(String result){this.result = result;}
    public String getResult(){return this.result;}

    public void setMessage(String message){this.message = message;}
    public String getMessage(){return this.message;}
}
