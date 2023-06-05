package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.CheckInput;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckInputMapper {
    // 数量少
    List<CheckInput> getByOrderIDAndNum(int orderID);
    List<CheckInput> getByOrderIDAndNum2(int orderID);
    // 品类少
    List<CheckInput> getByOrderIDAndSpecies(int orderID);

    // 通过orderID获取到货单
    List<CheckInput> getInformalByOrderID(int orderID);

    List<CheckInput> getFormalByOrderIDAndSid(int orderID);

    // 通过orderID和sid获取checkInput
    List<CheckInput> getByOrderIDAndSid(int orderId, String sid);

    // 通过orderID和sid获取checkInput的状态
    void updateCheckInputByOrderIDAndSid(int orderId, String sid);


    // 插入到货项
    void insertCheckInput(CheckInput checkInput);
}
