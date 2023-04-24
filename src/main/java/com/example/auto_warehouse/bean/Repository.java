package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Repository {

    @Id
    private String rid;
    private int totalNum;
    private int restNum;
    private double machineHealth;
    private double income;


    public Repository(){

    }


}
