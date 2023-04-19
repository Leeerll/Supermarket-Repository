package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.NotInput;
import com.example.auto_warehouse.bean.Save;
import com.example.auto_warehouse.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CargoStatusMapper {
    int addNotInput(NotInput notInput);
    List<NotInput> allNotInput();
    // 判断进货超市在仓库中是否存有同类产品
    String getSameSpecies(String sid, String suid, String rid);
    List<Save> getSameSpeciesAllCeid(String sid, String suid, String rid);
}
