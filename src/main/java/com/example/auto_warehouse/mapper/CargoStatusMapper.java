package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.NotInput;
import com.example.auto_warehouse.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CargoStatusMapper {
    int addNotInput(NotInput notInput);
    List<NotInput> allNotInput();
}
