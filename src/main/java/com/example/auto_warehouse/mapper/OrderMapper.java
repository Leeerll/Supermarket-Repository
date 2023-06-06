package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.*;
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
    List<Order> getOrderByStatePay(String suid);
    List<Order> getOrderByStatePay2(String suid);
    List<InputThings> getInputThingsByOrderID(int orderID);
    InputThings getInputThingsByOrderIDAndSid(int orderID,String sid);
    List<Order> getOrderBySuid(String suid);
    List<Message> getMessageByOrderID(int orderID);

    List<NotInput> getNotinputByOrderID(int orderID);
    Order getOrderByOrderID(int orderID);
    Save getOrderPayment(int orderID,String sid);
    void updatePayment(int orderID,double amount);
    void updatePaidMoney(int orderID,double paidMoney);
    List<Order>getActualOrderPayment(String suid);
    List<OrderCostLog>getOrderPaymentLog(String suid);
    void insertOrderCostLog(OrderCostLog orderCostLog);
    List<Order> getOrderBySuidAndstate(String suid, String state);

    void insertOutputThings(OutputThings outputThings);
    List<OutputThings> getOutputThingsByOrderID(int orderID);
    void modifyOutputThingsState(int orderID,String sid);
    void setPayMethod(int orderID, double payMethod);
    List<Order> showNonePayMethod(String suid);

}
