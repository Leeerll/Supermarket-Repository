package com.example.auto_warehouse.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class OrderCostLog {

    public OrderCostLog(String suid, int orderID, double money, String statement) throws ParseException {
        this.suid = suid;
        this.orderID = orderID;
        this.money = money;
        this.statement = statement;
        setTime();
    }

    @Id
    private String suid;
    private int orderID;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    private double money;
    private String statement;

    public void setTime() throws ParseException {
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.time = tFormat.parse(tFormat.format(now));
    }

}
