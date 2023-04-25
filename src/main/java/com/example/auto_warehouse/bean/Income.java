package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Income {
    public Income(String repositoryId, int yearMonth, double income) {
        this.repositoryId = repositoryId;
        this.yearMonth = yearMonth;
        this.income = income;
    }
    public Income(){

    }

    @Id
    private String repositoryId;
    private int yearMonth;
    private double income;
    // 这个year用来传递当前年份
    private int year;

}
