package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Cell {

    @Id
    private String rid;
    private String ceid;
    private String cetype;
    private double ch;
    private double cw;
    private double cd;
    private double cost_t;
    private double maxWeight;
    private int isUsed;

    public Cell(String rid, String ceid, String cetype, double ch, double cw, double cd, double cost_t, double maxWeight, int isUsed) {
        this.rid = rid;
        this.ceid = ceid;
        this.cetype = cetype;
        this.ch = ch;
        this.cw = cw;
        this.cd = cd;
        this.cost_t = cost_t;
        this.maxWeight = maxWeight;
        this.isUsed = isUsed;
    }

    public Cell(){

    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
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
        return cost_t;
    }

    public void setCost_t(double cost_t) {
        this.cost_t = cost_t;
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
