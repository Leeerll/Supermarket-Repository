package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
public class Species {
    @Id
    private String sid;    // 货物品类id
    private String sname;  // 货物品名
    private String stype;  // 货物种类
    private int num;       // 货物存量
    private double weight; // 货物重量
    private double sh;     // 货物长度
    private double sw;     // 货物宽度
    private double sd;     // 货物深度
    private String size;   // 货物尺寸

    public Species(String sid, String sname, String stype, int num, double weight, double sh, double sw, double sd, String size) {
        this.sid = sid;
        this.sname = sname;
        this.stype = stype;
        this.num = num;
        this.weight = weight;
        this.sh = sh;
        this.sw = sw;
        this.sd = sd;
        this.size = size;
    }

    public Species(){

    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getSh() {
        return sh;
    }

    public void setSh(double sh) {
        this.sh = sh;
    }

    public double getSw() {
        return sw;
    }

    public void setSw(double sw) {
        this.sw = sw;
    }

    public double getSd() {
        return sd;
    }

    public void setSd(double sd) {
        this.sd = sd;
    }
}
