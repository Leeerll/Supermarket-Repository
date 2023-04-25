package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.Income;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IncomeMapper {
    // 每次生成订单后更新收入表
    void updateIncome(int yearMonth, double money, String repositoryId);
    void insertIncome(Income income);
    List<Income> findByYearMonth(String repositoryId, int yearMonth);
}
