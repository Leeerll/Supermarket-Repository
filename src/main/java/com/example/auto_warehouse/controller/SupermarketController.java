package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.*;
import com.example.auto_warehouse.mapper.CargoStatusMapper;
import com.example.auto_warehouse.mapper.CheckInputMapper;
import com.example.auto_warehouse.mapper.OrderMapper;
import com.example.auto_warehouse.mapper.SupermarketMapper;
import com.example.auto_warehouse.service.InputService;
import com.example.auto_warehouse.service.SupermarketService;
import com.example.auto_warehouse.util.Id;
import com.example.auto_warehouse.util.JsonResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private InputService inputService;
    private Date now_time;

    @PostMapping("/login")
    public JsonResult<Supermarket> login(@RequestBody Map<String,String> map){
        String uid = map.get("uid");
        String password = map.get("password");
        return SupermarketService.login(uid,password);
    }

    @RequestMapping("/get_storage")
    @ResponseBody
    public List<Map<String,String>> get_storage(@RequestBody Map<String,String> map1){
        List<Map<String,String>> list = new ArrayList<>();
        String suid = map1.get("suid");
        List<ShopStorage> list_shopStorage = supermarketMapper.getStorage(suid);
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
//        return list_order;
        System.out.println(list_order);
        DateFormat dateformat= new SimpleDateFormat("yyyy-MM-dd");
        for(Order order:list_order){
            Map<String,String> map1 = new HashMap<>();
            map1.put("time",dateformat.format(order.getTime()));
            map1.put("cost",String.valueOf(order.getCost()));
            list.add(map1);
            supermarketMapper.modifyIsReadOrder(order.getOrderID());
        }
//        System.out.println(list.toString());
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
            List<CheckInput> checkInputList = checkInputMapper.getInformalByOrderID(orderId);

            for (CheckInput checkInput : checkInputList) {
                Map<String, String> map1 = new HashMap<>();
                map1.put("orderID", String.valueOf(orderId));
                map1.put("sid", checkInput.getSid());
                map1.put("num", String.valueOf(Math.abs(checkInput.getNum())));
                map1.put("statement", checkInput.getStatement());
                map1.put("state", checkInput.getState());
                list.add(map1);
            }
        }
        return list;
    }

    @RequestMapping("/show_Confirm")
    @ResponseBody
    public String show_Confirm(@RequestBody Map<String,List<Map<String,String>>>maplistmap) throws ParseException {
        // 重计算
        double money;
        List<Map<String,String>> mapList = maplistmap.get("array");
        int orderID = Integer.parseInt(mapList.get(0).get("orderID"));
        checkInputMapper.updateCheckInputByOrderIDAndSid2(orderID);
        // 更改正常状态的货物入库
        List<CheckInput> formalCheckInputList = checkInputMapper.getFormalByOrderIDAndSid(orderID);
        for(CheckInput checkInput : formalCheckInputList){
            String sid = checkInput.getSid();
            checkInputMapper.updateCheckInputByOrderIDAndSid(checkInput.getOrderID(), checkInput.getSid());
            orderMapper.modifyOrderState(orderID, "核验单已确认状态", getNowTime());
        }
        // 核对用户新确认的货物
        for(Map map: mapList) {
//            int orderID = (int) map.get("orderID");
            String sid = (String) map.get("sid");
            List<CheckInput> checkInputList = checkInputMapper.getByOrderIDAndSid(orderID, sid);
            for (CheckInput checkInput : checkInputList) {
                checkInputMapper.updateCheckInputByOrderIDAndSid(checkInput.getOrderID(), checkInput.getSid());
                orderMapper.modifyOrderState(orderID, "核验单已确认状态", getNowTime());
            }
        }
        //调用释放货位
        return inputService.confirm_checkInput(orderID);
    }
    public Date getNowTime() throws ParseException {
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        now_time = tFormat.parse(tFormat.format(now));
        return now_time;
    }


}
