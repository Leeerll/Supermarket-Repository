package com.example.auto_warehouse.service;

import com.example.auto_warehouse.bean.Save;
import com.example.auto_warehouse.mapper.OrderMapper;
import org.hibernate.type.descriptor.java.ObjectJavaType;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OutputServiceTest {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OutputService outputService;
//    @Test
//    void getOrderPayment() throws ParseException {
//        List<Map<String,String>>list = new ArrayList<>();
//        Map<String,String>map = new HashMap<>();
//        map.put("suid","101");
//        map.put("orderID","59");
//        map.put("state","待缴费状态");
//        list.add(map);
//        outputService.getOrderPayment(list);
//
////        System.out.println(orderMapper.getOrderPayment(suid,orderId,state));
//    }
    @Test
    void getActualOrderPayment() throws ParseException {
        String suid = "101";
        //System.out.println(outputService.getActualOrderPayment(suid).toString());
    }

}