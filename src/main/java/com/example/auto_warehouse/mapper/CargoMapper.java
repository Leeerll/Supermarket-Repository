package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.Cargo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface CargoMapper {
    // 根据入库时间查找
//    List<Cargo> findByInputT(String inputTime);

    // 根据状态查找
//    List<Cargo> findByState(String state);

    // 显示所有货物
    List<Cargo> showAll();

    // 获取最后一条记录的cid
    int getNewCid();

    // 添加货物
    boolean addCargo(Cargo cargo);

    // 未过期的货物存量
    int getNotExpireNum(String sid, int orderID);

    // 未出库、现在还未过期、但最近要过期的
    List<Cargo> outputBatch(String sid, int orderID);

    // 出库时更新出库时间
    void updateOutputTime(int cid, Date outputTime);

    // 更新数据库中过期货物
    void updateExpire();

    // 获取还未出库但已过期的货物
    List<Cargo> showExpire();

    // 通过（是否过期）状态查找货物
    List<Cargo> findByState(String state);

    // 根据货物品类id查找超市id
    String findSupByCid(int cid);

    void modifyCargoState(int cid);
    void updateInputTime(int orderID,Date inputTime);
    void modifyCargoStateCommon(int cid,String state);

}
