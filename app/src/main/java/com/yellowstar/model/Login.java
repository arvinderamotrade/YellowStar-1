package com.yellowstar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login
{
    private String type;
    private String uname;
    private String pass;


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("SID")
    @Expose
    private String sid;
    @SerializedName("branch")
    @Expose
    private String branch;
    @SerializedName("PID")
    @Expose
    private String pid;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
