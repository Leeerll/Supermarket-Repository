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
public class Message {
    @Id
    private int orderID;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    private String action;
    private String suid;

    public Message(int orderID, String action, String suid) throws ParseException {
        this.orderID = orderID;
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = tFormat.parse(tFormat.format(now));
        this.time = time;
        this.action = action;
        this.suid = suid;
    }

}
