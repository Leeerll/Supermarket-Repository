package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.Supermarket;
import com.example.auto_warehouse.bean.User;
import com.example.auto_warehouse.service.UserService;
import com.example.auto_warehouse.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //登录部分及相关函数，还需要根据用户type分别再写个函数/或者加个参数type判断（未完成）

    @PostMapping("/login")
    public JsonResult<User> login(@RequestBody Map<String,String> map){
        /*
            实现登录功能模块
         */
        String uid = map.get("uid");
        String password = map.get("password");
        return userService.login(uid,password);
    }

    @PostMapping("/addSupermarket")
    public JsonResult<User>addSupermarket(@RequestBody Map<String,String>map){
        String suid = map.get("suid");
        String password = map.get("password");
        Supermarket supermarket = new Supermarket();
        supermarket.setSuid(suid);
        supermarket.setPassword(password);
        supermarket.setSuregion("A");
        supermarket.setRid("1");
        return userService.addSupermarket(supermarket);
    }
    @PostMapping("/modifyPassword")
    public JsonResult<User>modifyPassword(@RequestBody Map<String,String>map){
        String uid = map.get("uid");
        String password = map.get("password");
        String old_password = map.get("old_password");
        if(userService.checkPassword(uid,old_password)){
            User user = new User();
            user.setUid(uid);
            user.setPassword(password);
            return userService.modify(user);
        }else{
            return new JsonResult<>("0","原始密码错误!");
        }
    }
}
