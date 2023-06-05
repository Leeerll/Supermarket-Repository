package com.example.auto_warehouse.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Entity
public class Order {
    private String suid;
    private String rid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    private double cost;
    private int isRead;
    @Id
    private int orderID;
    private String state;
    private String others;
    private double paidMoney;
    private double payMethod;
    private double actualCost;

    public double getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(double paidMoney) {
        this.paidMoney = paidMoney;
    }

    public double getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(double payMethod) {
        this.payMethod = payMethod;
    }


    public double getActualCost() {
        return actualCost;
    }

    public void setActualCost(double actualCost) {
        this.actualCost = actualCost;
    }



    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }



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
    public Order(String suid, String rid, String state) throws ParseException {
        this.suid = suid;
        this.rid = rid;
        setTime();
        this.state = state;
        this.actualCost=0;
        this.paidMoney=0;
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
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.time = tFormat.parse(tFormat.format(now));
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
