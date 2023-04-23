package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.Cargo;
import com.example.auto_warehouse.bean.Save;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface CargoMapper {
    // 获取最后一条记录的cid
    int getNewCid();

    List<Save> findByStype(String stype);
    List<Save> findByCid(int cid);

    // 添加货物
    boolean addCargo(Cargo cargo);

    // 未过期的货物存量
    int getNotExpireNum(String sid, String suid);

    // 未出库、现在还未过期、但最近要过期的
    List<Cargo> outputBatch();

    // 出库时更新出库时间
    void updateOutputTime(int cid, Date outputTime);


    //    新的
    // 根据状态查找
    //List<Cargo> findByState(String state);

    //根据货物品类查找
    List<Save> findBySid(String sid);

    //根据入库时间查找
    List<Save> findByInputTime(Date inputTime);

    //根据出库时间查找货物
    List<Save> findByOutputTime(Date outputTime);

//    //根据货物品类id查找超市id
//    List<Cargo> findSupByCid(int cid);

    //根据ceid查找货物
    List<Save> findByCeid(String ceid);

    //根据货物种类货物种类和入库时间
    List<Save> findByStypeAndInputTime(String stype,Date inputTime);

    //根据货物种类货物种类和出库时间
    List<Save> findByStypeAndOutputTime(String stype,Date outputTime);

    //根据货物品类id和入库时间查询
    List<Save> findBySidAndInputTime(String sid,Date inputTime);

    //根据货物品类id和入库时间查询
    List<Save> findBySidAndOutputTime(String sid,Date outputTime);

    //根据货物品类id和超市id查询
    List<Save> findBySidAndSuid(String sid,String suid);

    //根据货物种类和超市id查询
    List<Save> findByStypeAndSuid(String stype, String suid);

}
