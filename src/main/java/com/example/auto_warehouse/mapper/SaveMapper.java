package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.Save;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface SaveMapper {
    // 保存一条save记录
    void save(Save save);

    // 出库时更新出库时间
    void updateOutputTime(int cid, Date outputTime);

    // 根据货物cid获取存储的仓库柜ceid
    String getCellByCid(int cid);

}
