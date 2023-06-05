package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.Rule;
import com.example.auto_warehouse.util.JsonResult;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RuleControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    public RuleController ruleController;

    @Test
    void setRule() {
        Map<String, ArrayList<String>> map=new HashMap<>();

        ArrayList<String> dis=new ArrayList<>();
        dis.add("first dispatch");
        map.put("dispatch",dis);

        ArrayList<String> exp=new ArrayList<>();
        dis.add("FIFO");
        map.put("export",exp);

        JsonResult response = ruleController.setRule(map);
        System.out.println(response);
        System.out.println("99999999999999999999999");
        System.out.println(response.getCode());
        assertThat(response.getCode()).isEqualTo("1");
    }

    @Test
    void getRule() throws ParseException {
        ResponseEntity<String> response = restTemplate.getForEntity("/rule/sendRule", String.class);
        String body= response.getBody();
        assertThat(response.getBody()).contains("查询成功");
    }
}