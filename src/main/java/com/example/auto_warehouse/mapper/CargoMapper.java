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

    // 获取最后一条记录的cid
    int getNewCid();

    List<Cargo> findByStype(String stype);
    List<Cargo> finfByCid(int cid);

    // 添加货物
    boolean addCargo(Cargo cargo);

    // 未过期的货物存量
    int getNotExpireNum(String sid, String suid);

    // 未出库、现在还未过期、但最近要过期的
    List<Cargo> outputBatch();

    // 出库时更新出库时间
    void updateOutputTime(int cid, Date outputTime);

    // 更新数据库中过期货物
    void updateExpire();

    // 获取还未出库但已过期的货物
    List<Cargo> showExpire();

}
