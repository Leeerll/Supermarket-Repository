package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
public class NotOutput {
    @Id
    private int notOutputID;
    private String sid;
    private String suid;
    private int num;
    private String reason;
    private int isRead;

    public int getNotOutputID() {
        return notOutputID;
    }


    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }


    public NotOutput(String sid, String suid, int num, String reason) {
        this.sid = sid;
        this.suid = suid;
        this.num = num;
        this.reason = reason;
    }
    public NotOutput(){

    }
    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


}
