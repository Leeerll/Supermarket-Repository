package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.Cargo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface CargoMapper {

    // 根据状态查找
//    @Select("select * from cargo where state = #{state}")
    List<Cargo> findByState(String state);

    // 获取最后一条记录的cid
    int getNewCid();

    List<Cargo> findByStype(String stype);
    List<Cargo> finfByCid(int cid);

    // 添加货物
//    @Insert(" insert into auto_warehouseDB.cargo (sid, sname, productionDate, shelfLife, inputTime, state, suid)\n" +
//            "        values (#{cargo.sid}, #{cargo.sname}, #{cargo.productionDate}, #{cargo.shelfLife}, #{cargo.inputTime}, #{cargo.state}, #{cargo.suid})")
    boolean addCargo(Cargo cargo);

    // 未过期的货物存量
    int getNotExpireNum(String sid, String suid);

    // 未出库、现在还未过期、但最近要过期的
    List<Cargo> outputBatch();

    // 出库时更新出库时间
    void updateOutputTime(int cid, Date outputTime);


    //根据货物品类查找
//    @Select("select * from cargo where sid = #{sid}")
    List<Cargo> findBySid(String sid);

    //根据入库时间查找
//    @Select("select * from cargo where inputTime = #{inputTime}")
    List<Cargo> findByInputTime(Date inputTime);

    //根据出库时间查找
//    @Select("select * from cargo where outputTime = #{outTime}")
    List<Cargo> findByOutputTime(Date outputTime);

    //根据货物品类id查找超市id
//    @Select("select * from cargo where cid = #{cid}")
    List<Cargo> finfByCid(int cid);

}
