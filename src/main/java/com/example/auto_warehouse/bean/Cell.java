package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
public class Cell {

    public Cell(String crid, String ceid, double costT, double maxWeight, int isFull, String type, int restNum) {
        this.crid = crid;
        this.ceid = ceid;
        this.costT = costT;
        this.maxWeight = maxWeight;
        this.isFull = isFull;
        this.type = type;
        this.restNum = restNum;
    }

    private String crid;
    @Id
    private String ceid;
    private double costT;
    private double maxWeight;
    private int isFull;
    private String type;
    private int restNum;

    public String getCrid() {
        return crid;
    }

    public void setCrid(String crid) {
        this.crid = crid;
    }

    public String getCeid() {
        return ceid;
    }

    public void setCeid(String ceid) {
        this.ceid = ceid;
    }

    public double getCostT() {
        return costT;
    }

    public void setCostT(double costT) {
        this.costT = costT;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int getIsFull() {
        return isFull;
    }

    public void setIsFull(int isFull) {
        this.isFull = isFull;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRestNum() {
        return restNum;
    }

    public void setRestNum(int restNum) {
        this.restNum = restNum;
    }


    public Cell(){

    }



}
