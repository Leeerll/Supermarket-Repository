package com.example.auto_warehouse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 此类用于登录时设置全局用ID，供后端调用，查询数据库
public class Id {
    static String repositoryID;
    public static String getRepositoryID() {
        return repositoryID;
    }

    public static void setRepositoryID(String repositoryID) {
        Id.repositoryID = repositoryID;
    }






}
