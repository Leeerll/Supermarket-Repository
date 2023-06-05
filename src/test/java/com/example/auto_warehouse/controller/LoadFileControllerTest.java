package com.example.auto_warehouse.controller;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoadFileControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testimportFileInput() throws ParseException {
        // 发送一个 GET 请求到 "/hello" API
        ResponseEntity<String> response = restTemplate.getForEntity("/file/importFileInput", String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        // 确认响应包含期望的文本内容
        assertThat(response.getBody()).isNotEmpty();
    }
}