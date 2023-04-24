package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.service.OutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ouptput")
public class OutputController {
    @Autowired
    private OutputService outputService;

    @PostMapping("/check")
    @ResponseBody
    public void check(List<Map<String,String>> data) throws ParseException {
        outputService.check(data);
    }

}
