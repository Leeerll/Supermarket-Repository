package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
public class Repository {
    public Repository(String rid, int totalNum, int restNum) {
        this.rid = rid;
        this.totalNum = totalNum;
        this.restNum = restNum;
    }

    @Id
    private String rid;
    private int totalNum;
    private int restNum;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getRestNum() {
        return restNum;
    }

    public void setRestNum(int restNum) {
        this.restNum = restNum;
    }


    public Repository(){

    }




}
