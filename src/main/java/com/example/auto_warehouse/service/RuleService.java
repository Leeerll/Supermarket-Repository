package com.example.auto_warehouse.service;

import com.example.auto_warehouse.mapper.RuleMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Map;

import com.example.auto_warehouse.bean.Rule;
import org.springframework.stereotype.Service;

@Service
public class RuleService {
    final
    RuleMapper ruleMapper;

    public RuleService(RuleMapper ruleMapper) {
        this.ruleMapper = ruleMapper;
    }


    // set the rule
    public boolean setRule(Map<String,ArrayList<String>>map){
        ArrayList<String>export_value = map.get("export");
        ArrayList<String>dispatch_value = map.get("dispatch");
        boolean result = false;
        if(export_value.isEmpty() && dispatch_value.isEmpty()){
            return false;
        }

        // 设置值
        if(!export_value.isEmpty()){
            result = ruleMapper.setRuleExport(export_value.get(0));
        }else if(!dispatch_value.isEmpty()){
            result = ruleMapper.setRuleDispatch(dispatch_value.get(0));
        }
        return result;
    }

    // get the rule
    public Rule getRule(){
        return ruleMapper.getRule();
    }
}
