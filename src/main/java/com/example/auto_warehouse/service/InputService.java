package com.example.auto_warehouse.service;

import java.util.HashMap;
import java.util.List;

import com.example.auto_warehouse.mapper.SupermarketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

public class InputService {

    @Autowired
    private SupermarketMapper supermarketMapper;

    public boolean check(List<Map<String,String>> data){
        // 检查体积
        if(Double.parseDouble(data.get(5).get("sh"))>80 || Double.parseDouble(data.get(6).get("sw"))>70 || Double.parseDouble(data.get(7).get("sd"))>60){
            Map<String, String> reason = new HashMap<>();
            reason.put("reason","体积过大");
            data.add(reason);
            notInput(data);
            return false;
        }
        //检查重量
        if(Double.parseDouble(data.get(4).get("weight"))>60){
            Map<String, String> reason = new HashMap<>();
            reason.put("reason","超重");
            data.add(reason);
            notInput(data);
            return false;
        }

        return true;
    }

    // 不能入库，写入停滞区
    public boolean notInput(List<Map<String,String>> list){
        return false;


    }

}
