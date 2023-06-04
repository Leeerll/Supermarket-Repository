package com.example.auto_warehouse.bean;


import java.util.Date;

public class OrderCostLog {

  private String suid;
  private int orderID;
  private Date time;
  private double money;
  private String statement;


  public String getSuid() {
    return suid;
  }

  public void setSuid(String suid) {
    this.suid = suid;
  }


  public int getOrderId() {
    return orderID;
  }

  public void setOrderId(int orderID) {
    this.orderID = orderID;
  }


  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }


  public double getMoney() {
    return money;
  }

  public void setMoney(double money) {
    this.money = money;
  }


  public String getStatement() {
    return statement;
  }

  public void setStatement(String statement) {
    this.statement = statement;
  }

}
