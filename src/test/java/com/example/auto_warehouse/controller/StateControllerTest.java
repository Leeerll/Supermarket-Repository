package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.OrderCostLog;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StateControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StateController stateController;

    @Test
    void manual_review() throws ParseException {
        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("state", "人工审核状态");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/state/manual_review",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

//        String sid="1";
//        System.out.println(jsonArray);
//        for (Object obj : jsonArray) {
//            JSONObject jsonObject = (JSONObject) obj;
//
//            String actualStype = jsonObject.getAsString("sid");
//            MatcherAssert.assertThat("sid should be " + sid, actualStype, equalTo(sid));
//        }
    }


    @Test
    void manual_review_detail() throws ParseException {
        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("orderID", "63");
        requestBody.put("reason", "人工审核不通过");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/state/manual_review_detail",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

//        String orderID="64";
        System.out.println(jsonArray);
//        for (Object obj : jsonArray) {
//            JSONObject jsonObject = (JSONObject) obj;
//
//            String actualStype = jsonObject.getAsString("orderID");
//            MatcherAssert.assertThat("orderID should be " + orderID, actualStype, equalTo(orderID));
//        }

    }

//    @Test
//    void show_supermarket_allOrder() throws ParseException {
//        // 构造请求体
//        Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("suid", "101");
//        //发送POST请求，获取响应结果下
//        ResponseEntity<String> response = restTemplate.postForEntity("/state/show_supermarket_allOrder",requestBody, String.class);
//        assertThat(response.getStatusCode().value()).isEqualTo(200);
//        assertThat(response.getBody()).isNotEmpty();
//        System.out.println("-------response----------");
//        System.out.println(response);
////
//        //采用parse处理
//        System.out.println(response.getBody());
//        JSONParser parser = new JSONParser();
//        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());
//
//        String orderID="63";
//        System.out.println(jsonArray);
//        for (Object obj : jsonArray) {
//            JSONObject jsonObject = (JSONObject) obj;
//
//            String actualStype = jsonObject.getAsString("orderID");
//            MatcherAssert.assertThat("orderID should be " + orderID, actualStype, equalTo(orderID));
//        }
//
//    }


    @Test
    void show_supermarket_order_allState()throws ParseException {
        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("orderID", "64");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/state/show_supermarket_order_allState",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);
//
        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        String orderID="63";
        System.out.println(jsonArray);
//        for (Object obj : jsonArray) {
//            JSONObject jsonObject = (JSONObject) obj;
//
//            String actualStype = jsonObject.getAsString("orderID");
//            MatcherAssert.assertThat("orderID should be " + orderID, actualStype, equalTo(orderID));
//        }

    }

    @Test
    void show_supermarket_order_notInput()  throws ParseException {
        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("orderID", "64");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/state/show_supermarket_order_notInput",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

//        String orderID="64";
//        System.out.println(jsonArray);
//        for (Object obj : jsonArray) {
//            JSONObject jsonObject = (JSONObject) obj;
//
//            String actualStype = jsonObject.getAsString("orderID");
//            MatcherAssert.assertThat("orderID should be " + orderID, actualStype, equalTo(orderID));
//        }

    }

    @Test
    void manual_review_passed()  throws ParseException {
        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("orderID", "64");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/state/manual_review_passed",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
//        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

//        String orderID="64";
//        System.out.println(jsonArray);
//        for (Object obj : jsonArray) {
//            JSONObject jsonObject = (JSONObject) obj;
//
//            String actualStype = jsonObject.getAsString("orderID");
//            MatcherAssert.assertThat("orderID should be " + orderID, actualStype, equalTo(orderID));
//        }

    }

    @Test
    void manual_review_failed()  throws ParseException {
        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("orderID", "63");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/state/manual_review_failed",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
//        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

//        String orderID="64";
//        System.out.println(jsonArray);
//        for (Object obj : jsonArray) {
//            JSONObject jsonObject = (JSONObject) obj;
//
//            String actualStype = jsonObject.getAsString("orderID");
//            MatcherAssert.assertThat("orderID should be " + orderID, actualStype, equalTo(orderID));
//        }

    }

    @Test
    void show_choose_payMethod() throws ParseException {
        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("suid", "101");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/state/show_choose_payMethod",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);
//
        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        String orderID="63";
        System.out.println(jsonArray);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            String actualStype = jsonObject.getAsString("orderID");
            MatcherAssert.assertThat("orderID should be " + orderID, actualStype, equalTo(orderID));
        }

    }

    @Test
    void choose_payMethod() throws java.text.ParseException {
        Map<String,String> map = new HashMap<>();
        map.put("orderID","63");
        map.put("payMethod","0.10");
        String res = stateController.choose_payMethod(map);
        assertEquals("true",res);
    }

    @Test
    void choose_payMethod_wrong() throws java.text.ParseException {
        Map<String,String> map = new HashMap<>();
        map.put("orderID","63");
        map.put("payMethod","9842393");
        String res = stateController.choose_payMethod(map);
        assertEquals("flase",res);
    }

    @Test
    void choose_payMethod_wrong2() throws java.text.ParseException {
        Map<String,String> map = new HashMap<>();
        map.put("orderID","6234782");
        map.put("payMethod","0.10");
        String res = stateController.choose_payMethod(map);
        assertEquals("flase",res);
    }

    @Test
    void actual_input() throws ParseException, java.text.ParseException {
        Map<String,String> map = new HashMap<>();
        map.put("orderID","63");
        String res = stateController.actual_input(map);
        assertEquals("true",res);
    }

    @Test
    void actual_input_wrong() throws ParseException, java.text.ParseException {
        Map<String,String> map = new HashMap<>();
        map.put("orderID","999");
        String res = stateController.actual_input(map);
        assertEquals("flase",res);
    }

    @Test
    void actual_input_confirm() throws ParseException, java.text.ParseException {
        Map<String,String> map = new HashMap<>();
        map.put("orderID","63");
        String res = stateController.actual_input_confirm(map);
        assertEquals("true",res);
    }

    @Test
    void actual_input_confirm_wrong() throws ParseException, java.text.ParseException {
        Map<String,String> map = new HashMap<>();
        map.put("orderID","6587123618");
        String res = stateController.actual_input_confirm(map);
        assertEquals("false",res);
    }

    @Test
    void actual_input_confirm_wrong2() throws ParseException, java.text.ParseException {
        Map<String,String> map = new HashMap<>();
        map.put("orderID","999");
        String res = stateController.actual_input_confirm(map);
        assertEquals("flase",res);
    }
    @Test
    void getPaymentOrderLog() {
        Map<String,String> map = new HashMap<>();
        map.put("suid","101");
        List<OrderCostLog> res = stateController.getPaymentOrderLog(map);
        //assertEquals("true",res);
        assertThat(res).isNotEmpty();
    }

    @Test
    void getPaymentOrderLog_wrong() {
        Map<String,String> map = new HashMap<>();
        map.put("suid","101766");
        List<OrderCostLog> res = stateController.getPaymentOrderLog(map);
        System.out.println(res);
        assertThat(res).isEmpty();
    }

    @Test
    void finish_payment() throws ParseException, java.text.ParseException {
        Map<String,String> map = new HashMap<>();
        map.put("orderID","64");
        String res = stateController.finish_payment(map);
        assertEquals("true",res);
    }

    @Test
    void finish_payment_wrong() throws ParseException, java.text.ParseException {
        Map<String,String> map = new HashMap<>();
        map.put("orderID","993");
        String res = stateController.finish_payment(map);
        assertEquals("flase",res);
    }
}