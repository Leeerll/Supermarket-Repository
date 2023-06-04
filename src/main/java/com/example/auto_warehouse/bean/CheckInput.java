package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class CheckInput {
    public CheckInput(int orderID, String sid, int num, String statement) {
        this.orderID = orderID;
        this.sid = sid;
        this.num = num;
        this.statement = statement;
    }

    @Id
    private int orderID;
    private String sid;
    private int num;
    private String statement;
}
