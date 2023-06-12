package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.User;
import com.example.auto_warehouse.util.JsonResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    void testLogin() {
        Map<String,String> map = new HashMap<>();
        map.put("uid","1");
        map.put("password","123456");
        JsonResult<User> res = userController.login(map);
        assertEquals("登录成功!",res.getMsg());
    }

    @Test
    void testLogin_wrong() {
        Map<String,String> map = new HashMap<>();
        map.put("uid","1");
        map.put("password","000000");
        JsonResult<User> res = userController.login(map);
        assertEquals("登录成功!",res.getMsg());
    }

    @Test
    void addSupermarket() {
        Map<String,String> map = new HashMap<>();
        int randomSuid = (int)(Math.random() * 1000000);
        map.put("suid",String.valueOf(randomSuid));
        map.put("password","123456");
        JsonResult<User> res = userController.addSupermarket(map);
        System.out.println(res.getMsg());
        //assertEquals("密码错误！",res.getMsg());
    }

    @Test
    void modifyPassword() {
        Map<String,String> map = new HashMap<>();
        map.put("uid","1");
        map.put("password","123456");
        map.put("old_password","123456");
        JsonResult<User> res = userController.modifyPassword(map);
        assertEquals("修改成功!",res.getMsg());
    }
}