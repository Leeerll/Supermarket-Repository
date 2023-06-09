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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RepositoryControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    public RepositoryController repositoryController;

    @Test
    public void rate() throws ParseException {
        ResponseEntity<String> response = restTemplate.getForEntity("/repository/rate", String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        // 确认响应包含期望的文本内容
        assertThat(response.getBody()).isNotEmpty();

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

        System.out.println("444444444444444444444444444444");
        System.out.println(jsonArray);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            if (!jsonObject.getAsString("machine_health_rate").isEmpty()) {
                assertThat(true);
            }else {
                assertTrue(false);
            }
        }
    }

    @Test
    void selectRateByRid() {
        Map<String,String> map = new HashMap<>();
        map.put("rid", "1");

        Map<String,String> res = repositoryController.selectRateByRid(map);
        System.out.println(res.get("machine_health_rate"));
        String machine_health_rate = res.get("machine_health_rate");
        if(!machine_health_rate.isEmpty()){
            assertThat(true);
        }else {
            assertTrue(false);
        }
    }

    @Test
    void selectRateByRid_wrong() {
        Map<String,String> map = new HashMap<>();
        map.put("rid", "1367846237846238");

        Map<String,String> res = repositoryController.selectRateByRid(map);
        System.out.println(res);
        assertEquals("0",res.get("machine_health_rate"));
        assertEquals("0",res.get("takerate"));
    }

    @Test
    void getMonthIncome() {
        Map<String,String> map = new HashMap<>();
        map.put("rid", "1");

        List<Map<String, String>> res = repositoryController.getMonthIncome(map);
        String income = res.get(0).get("income");
        System.out.println(res);
        if(!income.isEmpty()){
            assertThat(true);
        }else {
            assertTrue(false);
        }
    }

    @Test
    void getMonthIncome_wrong() {
        Map<String,String> map = new HashMap<>();
        map.put("rid", "17482974238947239");

        List<Map<String, String>> res = repositoryController.getMonthIncome(map);
        if (res.isEmpty()){
            assertThat(true);
            return;
        }
        String income = res.get(0).get("income");
        System.out.println(res);
        if(!income.isEmpty()){
            assertThat(true);
        }else {
            assertTrue(false);
        }
    }
}