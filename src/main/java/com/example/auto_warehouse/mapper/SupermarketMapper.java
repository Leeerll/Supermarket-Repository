package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.Supermarket;
import com.example.auto_warehouse.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SupermarketMapper {
    Supermarket findById(String suid); //根据id查询某个用户，返回user实体类
}
