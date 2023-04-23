package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.Cell;
import com.example.auto_warehouse.bean.Repository;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RepositoryMapper {

    Cell getCellByType(String type, String crid);
    Cell getCellByCeid(String ceid, String crid);
    int modifyCellTypeAndRestNumAndIsFull(Cell cell);
    int getRestNum(String rid);
    // 在ceid仓库柜中出一个货物
    void outCargo(String ceid);
    // 清柜：对cell的更改
    void clearCargo(String ceid);
    // 清柜：对repository的更改
    void updateRepositoryRestNum(String rid);
    int substractRepositoryRestNum(String rid);

    List<Repository> rate();

    //通过rid查询占有率
    List<Repository> selectRateByRid(String rid);
}
