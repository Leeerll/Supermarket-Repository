package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.CheckInput;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckInputMapper {
    // 数量少
    List<CheckInput> getByOrderIDAndNum(int orderID);
    // 品类少
    List<CheckInput> getByOrderIDAndSpecies(int orderID);

    // 通过orderID获取到货单
    List<CheckInput> getByOrderID(int orderID);

    // 插入到货项
    void insertCheckInput(CheckInput checkInput);
}
