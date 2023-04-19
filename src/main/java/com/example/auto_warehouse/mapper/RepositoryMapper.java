package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.Cell;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RepositoryMapper {

    Cell getCellByType(String type, String crid);
    Cell getCellByCeid(String ceid, String crid);
    int modifyCellTypeAndRestNumAndIsFull(Cell cell);
    int getRestNum(String rid);

}
