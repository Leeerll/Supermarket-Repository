package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.InputThings;
import com.example.auto_warehouse.bean.Message;
import com.example.auto_warehouse.bean.Order;
import com.example.auto_warehouse.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/state")
public class StateController {
    @Autowired
    private OrderMapper orderMapper;

    // 管理员查看需要人工审核的全部订单
    @RequestMapping("/manual_review")
    @ResponseBody
    public List<Map<String,String>> manual_review(){
        List<Order> list_order = orderMapper.getOrderByStatePeople();
        List<Map<String,String>> list = new ArrayList<>();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Order order:list_order){
            Map<String,String> map = new HashMap<>();
            map.put("orderID",String.valueOf(order.getOrderID()));
            map.put("suid",order.getSuid());
            map.put("time", sdf1.format(order.getTime()));
            list.add(map);
        }
        return list;
    }

    // 管理员查看需要人工审核的全部订单
    @RequestMapping("/manual_review_detail")
    @ResponseBody
    public List<Map<String,String>> manual_review_detail(@RequestBody Map<String,String> map1){
        int orderID = Integer.parseInt(map1.get("orderID"));
        List<Map<String,String>> list = new ArrayList<>();
        List<InputThings> list_inputThings = orderMapper.getInputThingsByOrderID(orderID);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        for(InputThings inputThings:list_inputThings){
            Map<String,String> map = new HashMap<>();
            map.put("sid",inputThings.getSid());
            map.put("sname",inputThings.getSname());
            map.put("stype",inputThings.getStype());
            map.put("num",String.valueOf(inputThings.getNum()));
            map.put("weight",String.valueOf(inputThings.getWeight()));
            map.put("sh",String.valueOf(inputThings.getSh()));
            map.put("sw",String.valueOf(inputThings.getSw()));
            map.put("sd",String.valueOf(inputThings.getSd()));
            map.put("productionDate", sdf1.format(inputThings.getProductionDate()));
            map.put("shelfLife",String.valueOf(inputThings.getShelfLife()));
            map.put("suid",inputThings.getSuid()); // suid是超市id
            map.put("size",inputThings.getSize());
            map.put("inputTime", sdf1.format(inputThings.getInputTime()));
            map.put("outputTime", sdf1.format(inputThings.getOutputTime()));
            map.put("orderID",String.valueOf(inputThings.getOrderID())); // order的id
            list.add(map);
        }
        return list;
    }

    // 超市查看自己所有订单当前状态
    @RequestMapping("/show_supermarket_allOrder")
    @ResponseBody
    public List<Map<String,String>> show_supermarket_allOrder(@RequestBody Map<String,String> map1){
        String suid = map1.get("suid");
        List<Map<String,String>> list = new ArrayList<>();
        List<Order> list_order = orderMapper.getOrderBySuid(suid);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Order order:list_order){
            Map<String,String> map = new HashMap<>();
            map.put("orderID",String.valueOf(order.getOrderID()));
            map.put("state",order.getState());
            map.put("time", sdf1.format(order.getTime()));
            list.add(map);
        }
        return list;
    }

    // 超市查看订单所有状态流
    @RequestMapping("/show_supermarket_order_allState")
    @ResponseBody
    public List<Map<String,String>> show_supermarket_order_allState(@RequestBody Map<String,String> map1){
        int orderID = Integer.parseInt(map1.get("orderID"));
        List<Map<String,String>> list = new ArrayList<>();
        List<Message> list_message = orderMapper.getMessageByOrderID(orderID);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Message message:list_message){
            Map<String,String> map = new HashMap<>();
            map.put("orderID",String.valueOf(message.getOrderID()));
            map.put("action",message.getAction());
            map.put("time", sdf1.format(message.getTime()));
            list.add(map);
        }
        return list;
    }

    // 超市查看未通过系统审核的申请单原因，其实就是inputThings的信息
}
