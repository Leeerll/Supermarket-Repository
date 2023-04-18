package com.example.auto_warehouse.bean;

import jakarta.persistence.Id;

import java.util.Date;

public class Save {
    @Id
    private String sid;            // 货物品类id
    private int cid;               // 货物编号
    private String rid;            // 仓库id
    private String ceid;           // 仓库柜id
    private String suid;           // 超市id
    private Date inputTime;        // 入库时间
    private Date outputTime;       // 出库时间

    public Save(String sid, int cid, String rid, String ceid, String suid, Date inputTime) {
        this.sid = sid;
        this.cid = cid;
        this.rid = rid;
        this.ceid = ceid;
        this.suid = suid;
        this.inputTime = inputTime;
    }
}
