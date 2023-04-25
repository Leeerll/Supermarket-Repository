package com.example.auto_warehouse.mapper;
import com.example.auto_warehouse.bean.Species;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


@Mapper
public interface SpeciesMapper {

    // 根据货物品类id找出全部该货物
//    @Select("select * from cargo where sid = #{sid}")
    List<Species> findById(String sid);

    // 根据货物品名sname找出全部该货物
//    @Select("select * from species where sname = #{sname}")
    Species findByName(String sname);

    // 根据货物类型stype找出全部该类货物
//    @Select("select * from cargo where stype = #{stype}")
    List<Species> findByType(String stype);

    // 全部货物品类
//    @Select("select * from cargo")
    List<Species> allSpecies();



    // 入库：更改货物存货
    boolean addNum(String sid, int newNum);

    // 添加货物品类
    boolean addSpecies(Species species);

    // 出库：减少货物存货
    boolean reduceNum(String sid, int newNum);

    // 通过sid返回stype
    String findStypeBySid(String sid);
}
