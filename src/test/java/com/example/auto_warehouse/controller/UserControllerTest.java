package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.User;
import com.example.auto_warehouse.util.JsonResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    void addUser() {
        Map<String,String> map = new HashMap<>();
        map.put("uid","123456");
        map.put("name","abc");
        map.put("password","123");
        map.put("type","admin");

        int num = Integer.parseInt(userController.addUser(map).getCode());
        assertEquals(1,num);
    }

    @Test
    void login() {
        Map<String,String> map = new HashMap<>();
        map.put("uid","123456");
        map.put("password","123");
        JsonResult<User> res = userController.login(map);
        assertEquals("登录成功!",res.getMsg());
    }
}