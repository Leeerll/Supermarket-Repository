package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.Rule;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RuleMapper {
    boolean setRuleExport(String export);
    boolean setRuleDispatch(String dispatch);
    Rule getRule();
}
