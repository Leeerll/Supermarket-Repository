package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Cell {

    @Id
    private String crid;
    private String ceid;
    private String cetype;
    private double ch;
    private double cw;
    private double cd;
    private double costT;
    private double maxWeight;
    private int isUsed;

    public Cell(String crid, String ceid, String cetype, double ch, double cw, double cd, double costT, double maxWeight, int isUsed) {
        this.crid = crid;
        this.ceid = ceid;
        this.cetype = cetype;
        this.ch = ch;
        this.cw = cw;
        this.cd = cd;
        this.costT = costT;
        this.maxWeight = maxWeight;
        this.isUsed = isUsed;
    }

    public Cell(){

    }

    public String getRid() {
        return crid;
    }

    public void setRid(String crid) {
        this.crid = crid;
    }

    public String getCeid() {
        return ceid;
    }

    public void setCeid(String ceid) {
        this.ceid = ceid;
    }

    public String getCetype() {
        return cetype;
    }

    public void setCetype(String cetype) {
        this.cetype = cetype;
    }

    public double getCh() {
        return ch;
    }

    public void setCh(double ch) {
        this.ch = ch;
    }

    public double getCw() {
        return cw;
    }

    public void setCw(double cw) {
        this.cw = cw;
    }

    public double getCd() {
        return cd;
    }

    public void setCd(double cd) {
        this.cd = cd;
    }

    public double getCost_t() {
        return costT;
    }

    public void setCost_t(double costT) {
        this.costT = costT;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

}
