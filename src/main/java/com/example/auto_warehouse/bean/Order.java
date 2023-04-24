package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Entity
public class Order {
    private String suid;
    private String rid;
    private Date time;
    private double cost;
    private int isRead;
    @Id
    private int orderID;

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }


    public Order() {
    }

    public Order(String suid, String rid, double cost) throws ParseException {
        this.suid = suid;
        this.rid = rid;
        setTime();
        this.cost = cost;
    }

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime() throws ParseException {
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.time = tFormat.parse(tFormat.format(now));
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
