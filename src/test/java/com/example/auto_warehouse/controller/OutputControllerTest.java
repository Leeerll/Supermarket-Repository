package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.User;
import com.example.auto_warehouse.util.JsonResult;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OutputControllerTest {
    @Autowired
    public TestRestTemplate restTemplate;

    @Autowired
    public OutputController outputController;

    @Test
    public void testcheck() throws ParseException, java.text.ParseException {
        List<Map<String,String>> data=new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("sid", "4");
        map.put("num", "4");
        map.put("suid", "101");
        map.put("orderID", "63");
        map.put("name", "中桶矿泉水");
        map.put("output_time","2023-06-06 15:40:23");
        data.add(map);

        String res = outputController.check(data);
        System.out.println(res);
        assertEquals("true",res);

    }

    @Test
    public void testcheck_wrong() throws ParseException, java.text.ParseException {
        List<Map<String,String>> data=new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("sid", "9 ");
        map.put("num", "4");
        map.put("suid", "101");
        map.put("orderID", "63");
        map.put("name", "中桶矿泉水");
        map.put("output_time","2023-06-06 15:40:23");
        data.add(map);

        String res = outputController.check(data);
        System.out.println(res);
        assertEquals("true",res);

    }
}