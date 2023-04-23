package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.NotInput;
import com.example.auto_warehouse.bean.ShopStorage;
import com.example.auto_warehouse.bean.Supermarket;
import com.example.auto_warehouse.bean.User;
import com.example.auto_warehouse.mapper.SupermarketMapper;
import com.example.auto_warehouse.service.SupermarketService;
import com.example.auto_warehouse.service.UserService;
import com.example.auto_warehouse.util.Id;
import com.example.auto_warehouse.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        //List<ShopStorage> list_shopStorage = supermarketMapper.getStorage(Id.getShopID());
        List<ShopStorage> list_shopStorage = supermarketMapper.getStorage("101");
        for(ShopStorage shopStorage:list_shopStorage){
            Map<String,String> map = new HashMap<>();
            map.put("sname",shopStorage.getSname());
            map.put("storageNum",String.valueOf(shopStorage.getStorageNum()));
            list.add(map);
        }
        return list;
    }


}
