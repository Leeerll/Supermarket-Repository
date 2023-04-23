package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Entity
public class Save {


    @Id
    private String sid;            // 货物品类id
    private int cid;               // 货物编号
    private String rid;            // 仓库id
    private String ceid;           // 仓库柜id
    private String suid;           // 超市id
    private Date inputTime;        // 入库时间
    private Date outputTime;       // 出库时间

    public Save(String sid, int cid, String rid, String ceid, String suid) throws ParseException {
        this.sid = sid;
        this.cid = cid;
        this.rid = rid;
        this.ceid = ceid;
        this.suid = suid;
        setInputTime();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getCeid() {
        return ceid;
    }

    public void setCeid(String ceid) {
        this.ceid = ceid;
    }

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime() throws ParseException {
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.inputTime = tFormat.parse(tFormat.format(now));
    }

    public Date getOutputTime() {
        return outputTime;
    }

    public void setOutputTime(Date outputTime) {
        this.outputTime = outputTime;
    }
    public Save(){

    }

}
