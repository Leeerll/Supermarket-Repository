package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.Supermarket;
import com.example.auto_warehouse.bean.User;
import com.example.auto_warehouse.service.SupermarketService;
import com.example.auto_warehouse.service.UserService;
import com.example.auto_warehouse.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/supermarket")
public class SupermarketController {
    @Autowired
    private SupermarketService SupermarketService;

    @PostMapping("/login")
    public JsonResult<Supermarket> login(@RequestBody Map<String,String> map){
        String uid = map.get("uid");
        String password = map.get("password");
        return SupermarketService.login(uid,password);
    }

}
