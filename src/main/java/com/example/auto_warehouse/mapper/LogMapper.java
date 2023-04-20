package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.Log;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

    void addLog(Log log);
}
