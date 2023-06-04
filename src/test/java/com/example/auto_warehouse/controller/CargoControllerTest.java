package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.NotInput;
import lombok.Data;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CargoControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testshow_notInput() throws ParseException {
        // 发送一个 GET 请求到 "/hello" API
        ResponseEntity<String> response = restTemplate.getForEntity("/cargo/show_notInput", String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        // 确认响应包含期望的文本内容
        assertThat(response.getBody()).isNotEmpty();

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        String name="红牛";
        System.out.println(jsonArray);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            if (jsonObject.getAsString("name") == name) {
                assertThat(true);
            }
        }
        assertThat(false);

    }

    @Test
    public void testshow_notOutput() throws ParseException {
        // 发送一个 GET 请求到 "/hello" API
        ResponseEntity<String> response = restTemplate.getForEntity("/cargo/show_notOutput", String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        // 确认响应包含期望的文本内容
        assertThat(response.getBody()).isNotEmpty();
        //采用parse处理
        System.out.println("555555555555555555555555555555555");
        System.out.println(response.getBody());
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        String name="红牛";
        System.out.println(jsonArray);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            if (jsonObject.getAsString("name") == name) {
                assertThat(true);
            }
        }
        assertThat(false);
    }
}