package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.User;
import com.example.auto_warehouse.service.UserService;
import com.example.auto_warehouse.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //登录部分及相关函数，还需要根据用户type分别再写个函数/或者加个参数type判断（未完成）
    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public JsonResult<User> login(@RequestBody Map<String,String> map){
        /*
            实现登录功能模块
         */
        String uid = map.get("uid");
        String password = map.get("password");
        return userService.login(uid,password);
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/addUser")
    public JsonResult<User>addUser(@RequestBody Map<String,String>map){
        String userid = map.get("uid");
        String name = map.get("name");
        String password = map.get("password");
        String type = map.get("type");
        User user = new User();
        user.setUid(userid);
        user.setName(name);
        user.setPassword(password);
        user.setType(type);
        return userService.addUser(user);
    }
}
