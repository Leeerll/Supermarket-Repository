package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.InputThings;
import com.example.auto_warehouse.bean.Message;
import com.example.auto_warehouse.bean.Order;
import com.example.auto_warehouse.bean.NotInput;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {

    // 更新每个超市的订单
    void insertOrder(Order order);
    void insertOrder2(Order order);
    // 获得刚插入数据的orderID
    int getOrderID();
    void insertInputThings(InputThings inputThings);
    void modifyOrderState(int orderID, String state, Date time);
    void modifyOrderCost(int orderID,double cost);
    void modifyOrderOthers(int orderID,String others);
    void insertMessage(Message message);
    String getSuid(int orderID);
    List<Order> getOrderByStatePeople();
    List<InputThings> getInputThingsByOrderID(int orderID);
    List<Order> getOrderBySuid(String suid);
    List<Message> getMessageByOrderID(int orderID);

    List<NotInput> getNotinputByOrderID(int orderID);
}
