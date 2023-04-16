package com.example.auto_warehouse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 此类用于登录时设置全局用ID，供后端调用，查询数据库
public class Id {
    static String shopID;

    public static String getShopID() {
        return shopID;
    }

    public static void setShopID(String shopID) {
        Id.shopID = shopID;
    }


}
