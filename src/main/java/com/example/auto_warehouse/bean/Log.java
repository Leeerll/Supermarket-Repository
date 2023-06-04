package com.example.auto_warehouse.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
public class Log {
    @Id
    private String sid;            // 货物品类id
    private int cid;               // 货物编号
    private String rid;            // 仓库id
    private String ceid;           // 仓库柜id
    private String suid;           // 超市id
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recordTime;       // 记录时间
    private String logContent;      // 日志内容
    private int orderID;

    public Log(String sid, int cid, String rid, String ceid, String suid, String logContent,int orderID) throws ParseException {
        this.sid = sid;
        this.cid = cid;
        this.rid = rid;
        this.ceid = ceid;
        this.suid = suid;
        setRecordTime();
        this.logContent = logContent;
        this.orderID = orderID;
    }
    public Log(){

    }
    public void setRecordTime() throws ParseException {
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.recordTime = tFormat.parse(tFormat.format(now));
    }
}
