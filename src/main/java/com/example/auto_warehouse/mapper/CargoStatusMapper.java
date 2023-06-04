package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.NotInput;
import com.example.auto_warehouse.bean.NotOutput;
import com.example.auto_warehouse.bean.Save;
import com.example.auto_warehouse.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CargoStatusMapper {

    int addNotInput(NotInput notInput);

    List<NotInput> allNotInput();
    int modifyIsReadInput(int notInputID);
    int addNotOutput(NotOutput notOutput);
    List<NotOutput> allNotOutput();
    List<NotOutput> supermarketNotOutput(String suid);
    int modifyIsReadOutput(int notOutputID);
    // 判断进货超市在仓库中是否存有同类产品
    List<String> getSameSpecies(String sid, String suid, String rid);
    List<String> getSameSpeciesByOrderID(String sid, int orderID);

    List<String> getSameSpeciesAllCeid(String sid, String suid, String rid);
}
