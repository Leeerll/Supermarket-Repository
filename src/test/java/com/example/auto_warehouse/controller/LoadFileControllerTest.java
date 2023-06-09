package com.example.auto_warehouse.controller;

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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoadFileControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    public LoadFileController loadFileController;

    @Test
    public void testimportData_input() throws ParseException, IOException {
        File file = ResourceUtils.getFile("D:\\A\\repository\\in1.xlsx");
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", input);

        JsonResult response = loadFileController.importData_input(multipartFile);
        System.out.println(response.getCode());
        assertThat(response.getCode()).isEqualTo("1");
    }

    @Test
    public void testimportData_input_wrongfile() throws ParseException, IOException {
        File file = ResourceUtils.getFile("D:\\A\\repository\\nullfile.xlsx");
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", input);

        JsonResult response = loadFileController.importData_input(multipartFile);
        System.out.println("99999999999999999999999999");
        System.out.println(response);
        assertThat(response.getCode()).isEqualTo("1");
    }

    @Test
    public void testimportData_output() throws ParseException, IOException {
        File file = ResourceUtils.getFile("D:\\A\\repository\\out1.xlsx");
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", input);

        JsonResult response = loadFileController.importData_output(multipartFile);
        System.out.println(response);
        System.out.println("99999999999999999999999");
        System.out.println(response.getCode());
        assertThat(response.getCode()).isEqualTo("1");
    }

    @Test
    public void testimportFileActualInput() throws ParseException, IOException {
        File file = ResourceUtils.getFile("D:\\A\\repository\\in1.xlsx");
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", input);

        JsonResult response = loadFileController.importData_actualInput(multipartFile);
        System.out.println(response);
        System.out.println("99999999999999999999999");
        System.out.println(response.getCode());
        assertThat(response.getCode()).isEqualTo("1");
    }

    @Test
    public void testimportFileActualInput_wrongfile() throws ParseException, IOException {
        File file = ResourceUtils.getFile("D:\\A\\repository\\nullfile.xlsx");
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", input);

        JsonResult response = loadFileController.importData_actualInput(multipartFile);
        System.out.println(response);
        System.out.println("99999999999999999999999");
        System.out.println(response.getCode());
        assertThat(response.getCode()).isEqualTo("1");
    }
}