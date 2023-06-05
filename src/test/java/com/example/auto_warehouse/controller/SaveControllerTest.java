package com.example.auto_warehouse.controller;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SaveControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void findAll() {

        ResponseEntity<String> response = restTemplate.getForEntity("/save/findAll", String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        System.out.println(response);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void findByStype() throws ParseException {
        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("stype", "零食");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/save/findByStype",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        String stype="零食";

        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            String actualStype = jsonObject.getAsString("stype");
            assertThat("stype should be " + stype, actualStype, equalTo(stype));
        }
    }

    @Test
    void findByCid() throws ParseException {

        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("cid", "1520");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/save/findByCid",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        String name="农夫山泉";
        System.out.println(jsonArray);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            String actualStype = jsonObject.getAsString("sname");
            assertThat("cid should be " + name, actualStype, equalTo(name));
        }
    }

    @Test
    void findBySid() throws ParseException {

        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("sid", "1");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/save/findBySid",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        String sid="1";
        System.out.println(jsonArray);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            String actualStype = jsonObject.getAsString("sid");
            assertThat("sid should be " + sid, actualStype, equalTo(sid));
        }
    }

    @Test
    void findByCeid() throws ParseException {

        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("ceid", "001");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/save/findByCeid",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        String ceid="001";
        System.out.println(jsonArray);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            String actualStype = jsonObject.getAsString("ceid");
            assertThat("sid should be " + ceid, actualStype, equalTo(ceid));
        }
    }

    @Test
    void findByStypeAndInputTime() throws ParseException {

        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("stype", "饮品");
        requestBody.put("inputTime", "2023-06-04");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/save/findByStypeAndInputTime",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        String stype="饮品";
        String date="2023-06-04";
        System.out.println(jsonArray);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            String actualStype = jsonObject.getAsString("stype");
            assertThat("style should be " + stype, actualStype, equalTo(stype));

            String actualDate = jsonObject.getAsString("input_time");
            assertThat("inputTime should be " + date, actualDate, equalTo(date));
        }
    }

    @Test
    void findByStypeAndOutputTime() throws ParseException {

        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("stype", "饮品");
        requestBody.put("outputTime", "2023-06-05");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/save/findByStypeAndOutputTime",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        String stype="饮品";
        String date="2023-06-05";
        System.out.println(jsonArray);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            String actualStype = jsonObject.getAsString("stype");
            assertThat("stype should be " + stype, actualStype, equalTo(stype));

            String actualDate = jsonObject.getAsString("output_time");
            assertThat("output_time should be " + date, actualDate, equalTo(date));
        }
    }

    @Test
    void findBySidAndSuid() throws ParseException {

        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("sid", "2");
        requestBody.put("suid", "101");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/save/findBySidAndSuid",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        String sid="2";
        String suid="101";
        System.out.println(jsonArray);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            String actualSid = jsonObject.getAsString("sid");
            assertThat("sid should be " + sid, actualSid, equalTo(sid));

            String actualSuid = jsonObject.getAsString("suid");
            assertThat("SUID should be " + suid, actualSuid, equalTo(suid));
        }
    }

    @Test
    void findByStypeAndSuid() throws ParseException {

        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("stype", "零食");
        requestBody.put("suid", "101");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/save/findByStypeAndSuid",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        String stype="零食";
        String suid="101";
        System.out.println(jsonArray);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            String actualStype = jsonObject.getAsString("stype");
            assertThat("stype should be " + stype, actualStype, equalTo(stype));

            String actualSuid = jsonObject.getAsString("suid");
            assertThat("SUID should be " + suid, actualSuid, equalTo(suid));
        }
    }

    @Test
    void findBySuid() throws ParseException {

        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("suid", "101");
        //发送POST请求，获取响应结果下
        ResponseEntity<String> response = restTemplate.postForEntity("/save/findBySuid",requestBody, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println("-------response----------");
        System.out.println(response);

        //采用parse处理
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        String suid="101";
        System.out.println(jsonArray);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            String actualSuid = jsonObject.getAsString("suid");
            assertThat("suid should be " + suid, actualSuid, equalTo(suid));
        }
    }
}