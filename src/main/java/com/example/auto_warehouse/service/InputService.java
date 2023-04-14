package com.example.auto_warehouse.service;

import com.example.auto_warehouse.mapper.CargoStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class InputService {

    @Autowired
    private CargoStatusMapper CargoStatusMapper;

    // 不能入库，写入数据库停滞区
    public boolean notInput(List<Map<String,String>> list){
        return false;


    }

}
