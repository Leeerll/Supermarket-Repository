package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.Save;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface SaveMapper {
    // 保存一条save记录
    void save(Save save);

    // 出库时更新出库时间
    void updateOutputTime(int cid, Date outputTime);

    // 根据货物cid获取存储的仓库柜ceid
    String getCellByCid(int cid);

    // 根据状态查找
//    List<Cargo> findByState(String state);
    List<Save> findAll();
    List<Save> findByStype(String stype);
    List<Save> findByCid(int cid);

    //根据货物品类查找
    List<Save> findBySid(String sid);

    //根据入库时间查找
    List<Save> findByInputTime(Date inputTime);

    //根据出库时间查找货物
//    List<Save> findByOutputTime(Date outputTime);


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

    //根据和超市id查询
    List<Save> findBySuid(String suid);

    List<String> findAllCeidByOrderIDAndSid(String sid,int orderID);
    int findCountByOrderIDAndSidAndCeid(String sid,int orderID,String ceid);
    List<Integer> getNumCid(String sid,int orderID,String ceid,int num);
    void modifySaveState(int cid);
    void modifySaveStateCommon(int cid,String state);
    void updateInputTimeAndState(int orderID,Date inputTime);
    void updateConfirmState(int orderID);
    // 查询这个order中有没有还未出库的货物
    List<Save> findInputState(int orderID);

}
