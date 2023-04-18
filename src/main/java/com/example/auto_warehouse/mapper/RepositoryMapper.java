package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.Cell;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RepositoryMapper {

    Cell getCellAttr(String crid, String cetype);
    int getCellNum(String crid, String cetype, int isUsed);
    List<Cell> getCellList(String crid, String cetype, int isUsed);
    int modifyCellIsUsed(String crid, String ceid);

}
