package com.example.auto_warehouse.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
    String suid;
    String rid;
    Date time;
    double cost;

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
