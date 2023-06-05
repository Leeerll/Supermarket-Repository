package com.example.auto_warehouse.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class OutputThings {
    public OutputThings(String sid, String suid, int num, String name, int orderID) {
        this.sid = sid;
        this.suid = suid;
        this.num = num;
        this.name = name;
        this.orderID = orderID;
    }

    @Id
    private String sid;
    private String suid;
    private int num;
    private String name;
    private int orderID;
    private String state;
}
