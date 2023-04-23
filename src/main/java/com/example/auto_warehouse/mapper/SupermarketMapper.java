package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.Supermarket;
import com.example.auto_warehouse.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SupermarketMapper {

//    @Select("select * from supermarket")
    Supermarket findById(String suid); //根据id查询超市，返回supermarket实体类
}
