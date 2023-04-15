package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Cargo {

    @Id
    private String sid;               // 货物品类id
    private String sname;             // 货物品名
    private String cid;               // 货物编号
    private Date productionDate;   // 货物生产日期
    private int shelfLife;           // 货物保质期
    private Date inputTime;        // 货物入库时间
    private Date outputTime;       // 货物出库时间
    private String state;             // 状态
    private String utype;             // 用户角色

    public Cargo(){

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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Date getProduction_date() {
        return productionDate;
    }

    public void setProduction_date(Date productionDate) {
        this.productionDate = productionDate;
    }

    public int getShelf_life() {
        return shelfLife;
    }

    public void setShelf_life(int shelfLife) {
        this.shelfLife = shelfLife;
    }

    public Date getInput_time() {
        return inputTime;
    }

    public void setInput_time(Date inputTime) {
        this.inputTime = inputTime;
    }

    public Date getOutput_time() {
        return outputTime;
    }

    public void setOutput_time(Date outputTime) {
        this.outputTime = outputTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }
}
