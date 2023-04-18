package com.example.auto_warehouse.bean;

import jakarta.persistence.Id;

public class Save {
    @Id
    private String sid;            // 货物品类id
    private String sname;          // 货物品名
    private int cid;               // 货物编号

}
