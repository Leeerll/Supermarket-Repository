package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.Rule;
import com.example.auto_warehouse.service.RuleService;
import com.example.auto_warehouse.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/rule")
public class RuleController {

    final
    RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    // 设置规则

    @PostMapping("/setRule")
    public JsonResult setRule(@RequestBody Map<String,ArrayList<String>>map){

        if (map.isEmpty()){
            return new JsonResult<>("0","修改失败");
        }else {
            // 获取规则数组对象
            if(ruleService.setRule(map)){
                return new JsonResult<>("1","修改成功");
            }else {
                return new JsonResult<>("0","修改失败");
            }
        }
    }

    @GetMapping ("/sendRule")
    public JsonResult <Rule> getRule(){
        Rule rule = ruleService.getRule();
        if(rule != null){
            return new JsonResult<>(rule,"查询成功！");
        }else {
            return new JsonResult<>("0","查询失败,空值");
        }
    }
}
