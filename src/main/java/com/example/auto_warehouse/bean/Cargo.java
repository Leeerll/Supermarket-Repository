package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
public class Cargo {


    private String sid;               // 货物品类id
    private String sname;             // 货物品名
    @Id
    private int cid;               // 货物编号
    private Date productionDate;   // 货物生产日期
    private int shelfLife;           // 货物保质期
    private Date inputTime;        // 货物入库时间

    public Date getOutputTime() {
        return outputTime;
    }

    public void setOutputTime(Date outputTime) {
        this.outputTime = outputTime;
    }

    private Date outputTime;       // 货物出库时间
    private String state;          // 定时更新状态（过期或未过期）
    private String suid;             // 关联超市
    private int orderID;

    public Cargo(String sid, String sname, String productionDate, int shelfLife, String suid,int orderID) throws ParseException {
        this.sid = sid;
        this.sname = sname;
        setProductionDate(productionDate);
        this.shelfLife = shelfLife;
        //setInputTime();
        this.state = "not expire";
        this.suid = suid;
        this.orderID = orderID;
    }

    public Cargo() {

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

    public int getCid() {
        return cid;
    }


    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.productionDate = format.parse(productionDate);
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife = shelfLife;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime() throws ParseException {
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.inputTime = tFormat.parse(tFormat.format(now));
    }



    public String getState() {
        return state;
    }

    public void setState() {
//        if(this.inputTime+this.shelfLife>=new Date()){
//            this.state="expire";
//        }else {
//            this.state = "not expire";
//        }
        this.state = "not expire";
    }

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }
}
