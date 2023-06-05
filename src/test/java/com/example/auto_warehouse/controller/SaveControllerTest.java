package com.example.auto_warehouse.controller;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.hasItems;

import static org.hamcrest.Matchers.hasItem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;

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
        // 确认响应包含期望的文本内容
//        assertThat(response.getBody().contains("Hello, world!")).isEqualTo(true);
        assertThat(response.getBody()).isNotEmpty();
//        assertThat(response.getBody()).hasItem();
//    assertThat(response.getBody())
//            .isJson()
//            .extractingJsonPath("$.results")
//            .isArray()
//            .containsExactly(
//                tuple(
//                        hasEntry("id", 1),
//                        hasEntry("name", "John"),
//                        hasEntry("age", 30)
//                )
//            );
//        assertThat(response.getBody(), hasItems(
//                allOf(
//                        hasProperty("property1", equalTo("value1")),
//                        hasProperty("property2", equalTo("value2"))
//                )
//        ));

    }

    @Test
    void findByCid(){

        ResponseEntity<String> response = restTemplate.postForEntity("/save/findByCid",1520, String.class);
//        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
        System.out.println(response);


//            //方法2
//            // 构建你的请求头
//            HttpHeaders headers = new HttpHeaders();
////            headers.set("xxx","xxx");
//            headers.set("Content-Type", "application/json");
//            // 构建你的请求体参数
//            HashMap<String, String> map = new HashMap<>();
//            map.put("cid", "1520");
//            // 组合请求头与请求体参数
//            HttpEntity<String> requestEntity = new HttpEntity<>(JSONObject.toJSONString(map), headers);
//            // path -> 请求地址，requestEntity -> 请求参数相关对象，HashMap.class -> 返回结果映射类型
//            ResponseEntity<HashMap> response = restTemplate.postForEntity("/save/findByCid", requestEntity, String.class);
//            assertThat(response.getStatusCode().value()).isEqualTo(200);
//            assertThat(response.getBody()).isNotEmpty();
//            System.out.println(response);
//
//        String url = "/save/findByCid";
//        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
//        map.add("shopid","1");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
//        return restTemplate.postForEntity(url, request,String.class);
    }
}