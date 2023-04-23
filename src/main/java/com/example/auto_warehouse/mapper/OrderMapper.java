package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    // 更新每个超市的订单
    void insertOrder(Order order);
}
