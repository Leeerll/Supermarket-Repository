package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.*;
import com.example.auto_warehouse.mapper.*;
import com.example.auto_warehouse.service.InputService;
import com.example.auto_warehouse.util.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/state")
public class StateController {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private InputService inputService;

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

    // 管理员查看需要人工审核的全部订单的入库申请单详情
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
            map.put("others", order.getOthers()); // 如果未通过人工审核，原因会显示在这里
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

    // 超市查看未通过系统审核的申请单原因，其实就是notInput的信息
    @RequestMapping("/show_supermarket_order_notInput")
    @ResponseBody
    public List<Map<String,String>> show_supermarket_order_notInput(@RequestBody Map<String,String> map){
        int orderID = Integer.parseInt(map.get("orderID"));

        List<Map<String,String>> list = new ArrayList<>();
        List<NotInput> list_notInput = orderMapper.getNotinputByOrderID(orderID);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(NotInput not_Input:list_notInput){
            Map<String,String> map2 = new HashMap<>();
            map2.put("id",not_Input.getId());
            map2.put("name",not_Input.getName());
            map2.put("type",not_Input.getType());
            map2.put("reason",not_Input.getReason());
            map2.put("orderID",String.valueOf( not_Input.getOrderID()));
            map2.put("suid",String.valueOf( not_Input.getSuid()));
            map2.put("productionDate", sdf1.format(not_Input.getProduction_date()));
            map2.put("num", String.valueOf(not_Input.getNum()));
            map2.put("shelfLife", String.valueOf(not_Input.getShelf_life()));

            list.add(map2);
        }
        System.out.println("1234567889");
        System.out.println(list);
        return list;
    }

    // 人工审核通过
    @RequestMapping("/manual_review_passed")
    @ResponseBody
    public String manual_review_passed(@RequestBody Map<String,String> map1) throws ParseException {
        int orderID = Integer.parseInt(map1.get("orderID"));
        //List<Map<String,String>> list = new ArrayList<>();
        List<InputThings> list_inputThings = orderMapper.getInputThingsByOrderID(orderID);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        double cost=0;
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
            map.put("production_date", sdf1.format(inputThings.getProductionDate()));
            map.put("shelf_life",String.valueOf(inputThings.getShelfLife()));
            map.put("suid",inputThings.getSuid()); // suid是超市id
            map.put("size",inputThings.getSize());
            map.put("inputTime", sdf1.format(inputThings.getInputTime()));
            map.put("outputTime", sdf1.format(inputThings.getOutputTime()));
            map.put("orderID",String.valueOf(inputThings.getOrderID())); // order的id
            //list.add(map);
            // 预留货位
            inputService.callInput(map,orderID);
            int day = (int) ((inputThings.getOutputTime().getTime() - inputThings.getInputTime().getTime())
                                / (24 * 60 * 60 * 1000));
            System.out.println("day:"+day);
            cost+=day*2;
        }
        // 计费，写入对应order的cost
        orderMapper.modifyOrderCost(orderID,cost);
        // 修改状态为“待缴费状态”
        orderMapper.modifyOrderState(orderID,"待缴费状态",inputService.getNowTime());
        Message message1 = new Message(orderID, "待缴费状态", orderMapper.getSuid(orderID));
        orderMapper.insertMessage(message1);

        return "true";
    }

    // 人工审核不通过
    @RequestMapping("/manual_review_failed")
    @ResponseBody
    public String manual_review_failed(@RequestBody Map<String,String> map1) throws ParseException {
        int orderID = Integer.parseInt(map1.get("orderID"));
        String reason = map1.get("reason");
        orderMapper.modifyOrderState(orderID,"人工审核不通过",inputService.getNowTime());
        orderMapper.modifyOrderOthers(orderID,reason);
        Message message1 = new Message(orderID, "人工审核不通过", orderMapper.getSuid(orderID));
        orderMapper.insertMessage(message1);
        return "true";
    }

    // 超市查看需要缴费的订单
    @RequestMapping("/show_payment")
    @ResponseBody
    public List<Map<String,String>> show_payment(@RequestBody Map<String,String> map1){
        String suid = map1.get("suid");
        List<Order> list_order = orderMapper.getOrderByStatePay(suid);
        List<Map<String,String>> list = new ArrayList<>();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Order order:list_order){
            Map<String,String> map = new HashMap<>();
            map.put("orderID",String.valueOf(order.getOrderID()));
            map.put("cost",String.valueOf(order.getCost()));
            map.put("time", sdf1.format(order.getTime()));
            list.add(map);
        }
        return list;
    }

    // 超市缴费成功
    @RequestMapping("/finish_payment")
    @ResponseBody
    public String finish_payment(@RequestBody Map<String,String> map1) throws ParseException {
        int orderID = Integer.parseInt(map1.get("orderID"));
        return inputService.finish_payment(orderID);
    }

    // 到货接口(python程序调用)，传到货清单excel
    @RequestMapping("/arrival")
    @ResponseBody
    public String arrival(@RequestBody Map<String,String> map1) throws ParseException {
        // 处理excel

        // 改成“到货检查状态”

        // 调用核验函数

        return "true";
    }

    // 返回给超市核验单表，进行确认


    // 超市确认核验单
    @RequestMapping("/confirm_checkInput")
    @ResponseBody
    public String confirm_checkInput(@RequestBody Map<String,String> map1) throws ParseException {
        int orderID = Integer.parseInt(map1.get("orderID"));
        return inputService.confirm_checkInput(orderID);
    }

    // 实际入库（python发送，orderID需要去数据库中看）
    @RequestMapping("/actual_input")
    @ResponseBody
    public String actual_input(@RequestBody Map<String,String> map1) throws ParseException {
        int orderID = Integer.parseInt(map1.get("orderID"));
        return inputService.actual_input(orderID);
    }


    // 入库确认（python发送，orderID需要去数据库中看）
    @RequestMapping("/actual_input_confirm")
    @ResponseBody
    public String actual_input_confirm(@RequestBody Map<String,String> map1) throws ParseException {
        int orderID = Integer.parseInt(map1.get("orderID"));
        return inputService.actual_input_confirm(orderID);
    }


}
