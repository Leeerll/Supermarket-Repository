package com.example.auto_warehouse.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IncomeMapper {
    // 每次生成订单后更新收入表
    void updateIncome(int year, int month, double money);
}
