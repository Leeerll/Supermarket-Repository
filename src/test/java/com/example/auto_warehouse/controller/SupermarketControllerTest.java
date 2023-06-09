package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.Supermarket;
import com.example.auto_warehouse.util.JsonResult;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SupermarketControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SupermarketController supermarketController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void login() {
        Map<String,String> map = new HashMap<>();
        map.put("uid","101");
        map.put("password","123456");
        JsonResult<Supermarket> res = supermarketController.login(map);
        assertEquals("登录成功!",res.getMsg());
    }


//    @Test
//    void get_storage() throws ParseException {
//
//        // 构造请求体
//        Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("suid", "101");
//        //发送POST请求，获取响应结果下
//        ResponseEntity<String> response = restTemplate.postForEntity("/supermarket/get_storage",requestBody, String.class);
//        assertThat(response.getStatusCode().value()).isEqualTo(200);
//        assertThat(response.getBody()).isNotEmpty();
//        System.out.println("-------response----------");
//        System.out.println(response);
//
//        //采用parse处理
//        System.out.println(response.getBody());
//        JSONParser parser = new JSONParser();
//        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());
//
//        String suid="101";
//        System.out.println(jsonArray);
//        for (Object obj : jsonArray) {
//            JSONObject jsonObject = (JSONObject) obj;
//
//            String actualSuid = jsonObject.getAsString("suid");
//            MatcherAssert.assertThat("suid should be " + suid, actualSuid, equalTo(suid));
//        }
//    }

    @Test
    void get_order() throws ParseException {

        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("suid", "101");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/supermarket/get_order",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

//        String suid="101";
        System.out.println(jsonArray);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            System.out.println(jsonObject);
//            String actualCost = jsonObject.getAsString("cost");
//            MatcherAssert.assertThat("suid should be " + suid, actualSuid, equalTo(suid));
        }
    }

    @Test
    void show_notOutput() throws ParseException {

        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("suid", "101");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/supermarket/show_notOutput",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        String cost="80";
        System.out.println(jsonArray);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            System.out.println(jsonObject);
//            String actualSuid = jsonObject.getAsString("cost");

        }
    }

    @Test
    void show_notConfirm() throws ParseException {

        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("suid", "101");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/supermarket/show_notConfirm",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        String cost="80";
        System.out.println(jsonArray);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            System.out.println(jsonObject);
//            String actualSuid = jsonObject.getAsString("cost");

        }
    }

    @Test
    void show_Confirm()  {

    }


}