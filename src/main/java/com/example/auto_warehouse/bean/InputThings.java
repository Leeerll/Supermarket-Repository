package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class InputThings {

    public InputThings(String sid, String sname, String stype, int num, double weight, double sh, double sw, double sd, Date productionDate, int shelfLife, String suid, String size, Date inputTime, Date outputTime, int orderID, double price, String phone, String manufacturer) {
        this.sid = sid;
        this.sname = sname;
        this.stype = stype;
        this.num = num;
        this.weight = weight;
        this.sh = sh;
        this.sw = sw;
        this.sd = sd;
        this.productionDate = productionDate;
        this.shelfLife = shelfLife;
        this.suid = suid;
        this.size = size;
        this.inputTime = inputTime;
        this.outputTime = outputTime;
        this.orderID = orderID;
        this.price = price;
        this.phone = phone;
        this.manufacturer = manufacturer;
    }

    @Id
    private String sid;
    private String sname;
    private String stype;
    private int num;
    private double weight;
    private double sh;
    private double sw;
    private double sd;
    private Date productionDate;
    private int shelfLife;
    private String suid;
    private String size;
    private Date inputTime;
    private Date outputTime;
    private int orderID;
    private double price;
    private String phone;
    private String manufacturer;

}
