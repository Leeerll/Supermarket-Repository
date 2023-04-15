package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.Cargo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CargoMapper {
    // 根据入库时间查找
//    List<Cargo> findByInputT(String inputTime);

    // 根据状态查找
//    List<Cargo> findByState(String state);

    // 添加货物
    boolean addCargo(Cargo cargo);
}
