package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.*;
import com.example.auto_warehouse.mapper.CargoStatusMapper;
import com.example.auto_warehouse.mapper.CheckInputMapper;
import com.example.auto_warehouse.mapper.OrderMapper;
import com.example.auto_warehouse.mapper.SupermarketMapper;
import com.example.auto_warehouse.service.SupermarketService;
import com.example.auto_warehouse.util.Id;
import com.example.auto_warehouse.util.JsonResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/supermarket")
public class SupermarketController {
    @Autowired
    private SupermarketService SupermarketService;
    @Autowired
    private SupermarketMapper supermarketMapper;
    @Autowired
    private CargoStatusMapper cargoStatusMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CheckInputMapper checkInputMapper;

    @PostMapping("/login")
    public JsonResult<Supermarket> login(@RequestBody Map<String,String> map){
        String uid = map.get("uid");
        String password = map.get("password");
        return SupermarketService.login(uid,password);
    }

    @RequestMapping("/get_storage")
    @ResponseBody
    public List<Map<String,String>> get_storage(){
        List<Map<String,String>> list = new ArrayList<>();
        List<ShopStorage> list_shopStorage = supermarketMapper.getStorage(Id.getShopID());
        //List<ShopStorage> list_shopStorage = supermarketMapper.getStorage("101");
        for(ShopStorage shopStorage:list_shopStorage){
            Map<String,String> map = new HashMap<>();
            map.put("sname",shopStorage.getSname());
            map.put("storageNum",String.valueOf(shopStorage.getStorageNum()));
            list.add(map);
        }
        return list;
    }

    @RequestMapping("/get_order")
    @ResponseBody
    public List<Map<String,String>> get_order(@RequestBody Map<String,String> map){
        String suid = map.get("suid");
        List<Map<String,String>> list = new ArrayList<>();
        List<Order> list_order = supermarketMapper.allOrder(suid,"1");
        DateFormat dateformat= new SimpleDateFormat("yyyy-MM-dd");
        for(Order order:list_order){
            Map<String,String> map1 = new HashMap<>();
            map1.put("time",dateformat.format(order.getTime()));
            map1.put("cost",String.valueOf(order.getCost()));
            list.add(map1);
            supermarketMapper.modifyIsReadOrder(order.getOrderID());
        }
        return list;
    }

    @RequestMapping("/show_notOutput")
    @ResponseBody
    public List<Map<String,String>> show_notOutput(@RequestBody Map<String,String> map){
        List<Map<String,String>> list = new ArrayList<>();
        List<NotOutput> list_not = cargoStatusMapper.supermarketNotOutput(map.get("suid"));
        for(NotOutput notOutput:list_not){
            Map<String,String> map1 = new HashMap<>();
            map1.put("sid",notOutput.getSid());
            map1.put("name",notOutput.getName());
            map1.put("num",String.valueOf(notOutput.getNum()));
            map1.put("reason",notOutput.getReason());
            list.add(map1);
            cargoStatusMapper.modifyIsReadOutput(notOutput.getNotOutputID());
        }
        return list;
    }


    @RequestMapping("/show_notConfirm")
    @ResponseBody
    public List<Map<String,String>> show_notConfirm(@RequestBody Map<String,String> map){
        String suid = map.get("suid");
        List<Order> orderList = orderMapper.getOrderBySuidAndstate(suid, "入库清单待确认状态");

        List<Map<String,String>> list = new ArrayList<>();
        for(Order order: orderList) {
            int orderId = order.getOrderID();
            List<CheckInput> checkInputList = checkInputMapper.getByOrderID(orderId);

            for (CheckInput checkInput : checkInputList) {
                Map<String, String> map1 = new HashMap<>();
                map1.put("orderID", String.valueOf(orderId));
                map1.put("sid", checkInput.getSid());
                map1.put("num", String.valueOf(checkInput.getNum()));
                map1.put("statement", checkInput.getStatement());
                map1.put("state", checkInput.getState());
                list.add(map1);
            }
        }
        return list;
    }

    @RequestMapping("/show_Confirm")
    @ResponseBody
    public void show_Confirm(@RequestBody List<Map<String,String>> mapList){
        for(Map map: mapList) {
            int orderID = (int) map.get("orderID");
            String sid = (String) map.get("sid");
            List<CheckInput> checkInputList = checkInputMapper.getByOrderIDAndSid(orderID, sid);
            for (CheckInput checkInput : checkInputList) {
                checkInputMapper.updateCheckInputByOrderIDAndSid(checkInput.getOrderID(), checkInput.getSid());
            }
        }
    }


}
