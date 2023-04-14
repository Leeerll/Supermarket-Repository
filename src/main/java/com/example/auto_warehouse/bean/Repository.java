package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Repository {
    @Id
    private String rid;
    private int aTotalNum;
    private int bTotalNum;
    private int cTotalNum;
    private int aRestNum;
    private int bRestNum;
    private int cRestNum;

    public Repository(){

    }
    public Repository(String rid, int aTotalNum, int bTotalNum, int cTotalNum, int aRestNum, int bRestNum, int cRestNum) {
        this.rid = rid;
        this.aTotalNum = aTotalNum;
        this.bTotalNum = bTotalNum;
        this.cTotalNum = cTotalNum;
        this.aRestNum = aRestNum;
        this.bRestNum = bRestNum;
        this.cRestNum = cRestNum;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public int getaTotalNum() {
        return aTotalNum;
    }

    public void setaTotalNum(int aTotalNum) {
        this.aTotalNum = aTotalNum;
    }

    public int getbTotalNum() {
        return bTotalNum;
    }

    public void setbTotalNum(int bTotalNum) {
        this.bTotalNum = bTotalNum;
    }

    public int getcTotalNum() {
        return cTotalNum;
    }

    public void setcTotalNum(int cTotalNum) {
        this.cTotalNum = cTotalNum;
    }

    public int getaRestNum() {
        return aRestNum;
    }

    public void setaRestNum(int aRestNum) {
        this.aRestNum = aRestNum;
    }

    public int getbRestNum() {
        return bRestNum;
    }

    public void setbRestNum(int bRestNum) {
        this.bRestNum = bRestNum;
    }

    public int getcRestNum() {
        return cRestNum;
    }

    public void setcRestNum(int cRestNum) {
        this.cRestNum = cRestNum;
    }



}
