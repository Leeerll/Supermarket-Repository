package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogMapper {

    void addLog(Log log);
    Log getLogByCid(int cid);
    // 得到根据申请单分配的所有cid货物编号
    List<Integer> getAllCidByOrderID(int orderID);
    // 得到释放的货物编号
    List<Integer> getReleaseCidByOrderID(int orderID);
    // 得到入库待确认的货物编号
    List<Integer> getConfirmCidByOrderID(int orderID);

}
