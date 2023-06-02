package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;


@Entity
public class NotInput {
    @Id
    private int notInputID;
    private String id;
    private String name;
    private String type;
    private int num;
    private Date productionDate;
    private int shelfLife;
    private String suid;
    private String reason;
    private int isRead;
    private int orderID;
    public int getNotInputID() {
        return notInputID;
    }
    public NotInput(String id, String name, String type, int num, Date productionDate, int shelfLife, String suid, String reason, int orderID) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.num = num;
        this.productionDate = productionDate;
        this.shelfLife = shelfLife;
        this.suid = suid;
        this.reason = reason;
        this.orderID = orderID;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public NotInput(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }

    public int getOrderID (){ return orderID; }


}
