package com.example.auto_warehouse.controller;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpeciesControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void findByType() throws ParseException {
        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("type", "饮品");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/species/findByType",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        String type="饮品";

        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            String actualStype = jsonObject.getAsString("type");
            MatcherAssert.assertThat("type should be " + type, actualStype, equalTo(type));
        }
    }

    @Test
    void findById() throws ParseException {
        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("sid", "1");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/species/findById",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        String sid="1";

        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            String actualSid = jsonObject.getAsString("sid");
            MatcherAssert.assertThat("sid should be " + sid, actualSid, equalTo(sid));
        }
    }

}