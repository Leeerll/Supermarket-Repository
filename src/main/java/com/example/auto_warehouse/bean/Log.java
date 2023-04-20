package com.example.auto_warehouse.bean;

import jakarta.persistence.Id;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    @Id
    private String sid;            // 货物品类id
    private int cid;               // 货物编号
    private String rid;            // 仓库id
    private String ceid;           // 仓库柜id
    private String suid;           // 超市id
    private Date recordTime;       // 记录时间
    private String logContent;      // 日志内容

    public Log(String sid, int cid, String rid, String ceid, String suid, String logContent) throws ParseException {
        this.sid = sid;
        this.cid = cid;
        this.rid = rid;
        this.ceid = ceid;
        this.suid = suid;
        setRecordTime();
        this.logContent = logContent;
    }
    public void setRecordTime() throws ParseException {
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.recordTime = tFormat.parse(tFormat.format(now));
    }
}
