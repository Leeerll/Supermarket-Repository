package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.NotInput;
import com.example.auto_warehouse.service.InputService;
import com.example.auto_warehouse.service.InputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cargo")
public class CargoController {
    @Autowired
    private InputService inputService;


//    @RequestMapping("/show_notInput")
//    @ResponseBody
//    public List<Map<String,String>> show_notInput(){
//        List<Map<String,String>> list = new ArrayList<>();
//        List<NotInput> list_notInput = inputService.allNotInput();
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//        for(NotInput notInput:list_notInput){
//            Map<String,String> map = new HashMap<>();
//            map.put("id",notInput.getId());
//            map.put("name",notInput.getName());
//            map.put("type",notInput.getType());
//            map.put("num",String.valueOf(notInput.getNum()));
//            map.put("production_date", sdf1.format(notInput.getProduction_date()));
//            System.out.println("shelf_life:"+notInput.getShelf_life());
//            map.put("shelf_life",String.valueOf(notInput.getShelf_life()));
//            map.put("suid",notInput.getSuid());
//            map.put("reason",notInput.getReason());
//            list.add(map);
//        }
//        return list;
//    }



}
