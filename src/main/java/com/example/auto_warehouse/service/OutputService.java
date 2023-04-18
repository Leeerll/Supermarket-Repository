package com.example.auto_warehouse.service;

import com.example.auto_warehouse.bean.Cell;
import com.example.auto_warehouse.bean.Repository;
import com.example.auto_warehouse.mapper.CargoMapper;
import com.example.auto_warehouse.mapper.SpeciesMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class OutputService {
    @Autowired
    private SpeciesMapper speciesMapper;  // 更改存量
    @Autowired
    private CargoMapper cargoMapper;      // 更新出库时间
//    @Autowired
//    private CellMapper cellMapper;      // 更改使用状态
//    @Autowired
//    private RepositoryMapper repositoryMapper; // 更改柜子剩余数量
//    @Autowired
//    private SaveMapper saveMapper;
//    @Autowired
//    private LogMapper logMapper;

}
