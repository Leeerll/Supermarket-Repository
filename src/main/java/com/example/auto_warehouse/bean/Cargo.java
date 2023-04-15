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
    private int cid;               // 货物编号
    private Date productionDate;   // 货物生产日期
    private int shelfLife;           // 货物保质期
    private Date inputTime;        // 货物入库时间
    private Date outputTime;       // 货物出库时间
    private String state;             // 状态
    private String uid;             // 关联超市

    public Cargo(String sid, String sname, String productionDate, int shelfLife, String suid){
        this.sid = sid;
        this.sname = sname;
        setProduction_date(productionDate);
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


    public Date getProduction_date() {
        return productionDate;
    }

    public void setProduction_date(String productionDate) {

        this.productionDate = productionDate;
    }

    public int getShelf_life() {
        return shelf_life;
    }

    public void setShelf_life(int shelf_life) {
        this.shelf_life = shelf_life;
    }

    public Date getInput_time() {
        return input_time;
    }

    public void setInput_time(Date input_time) {
        this.input_time = input_time;
    }

    public Date getOutput_time() {
        return output_time;
    }

    public void setOutput_time(Date output_time) {
        this.output_time = output_time;
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
