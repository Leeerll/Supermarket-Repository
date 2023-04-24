package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.Order;
import com.example.auto_warehouse.bean.ShopStorage;
import com.example.auto_warehouse.bean.Supermarket;
import com.example.auto_warehouse.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SupermarketMapper {

//    @Select("select * from supermarket")
    Supermarket findById(String suid); //根据id查询超市，返回supermarket实体类
    List<ShopStorage> getStorage(String suid);
    List<Order> allOrder(String suid, String rid);
    int modifyIsReadOrder(int orderID);
}
