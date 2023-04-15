package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class NotInput {
    @Id
    private String id;
    private String name;
    private String type;
    private int num;
    private double weight;
    private double sh;
    private double sw;
    private double sd;
    private Date productionDate;
    private int shelfLife;
    private String suid;
    private String reason;

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


    public NotInput(String id, String name, String type, int num, double weight, double sh, double sw, double sd, Date productionDate, int shelfLife, String suid, String reason) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.num = num;
        this.weight = weight;
        this.sh = sh;
        this.sw = sw;
        this.sd = sd;
        this.productionDate = productionDate;
        this.shelfLife = shelfLife;
        this.suid = suid;
        this.reason = reason;
    }




}
