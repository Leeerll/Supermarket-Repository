package com.example.auto_warehouse.service;

import com.example.auto_warehouse.bean.NotInput;
import com.example.auto_warehouse.bean.Repository;
import com.example.auto_warehouse.mapper.CargoMapper;
import com.example.auto_warehouse.mapper.CargoStatusMapper;
import com.example.auto_warehouse.mapper.SpeciesMapper;
import com.example.auto_warehouse.mapper.SupermarketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InputService {

    @Autowired
    private SupermarketMapper supermarketMapper;
    @Autowired
    private SpeciesMapper speciesMapper;
    @Autowired
    private CargoMapper cargoMapper;
    @Autowired
    private CargoStatusMapper cargoStatusMapper;
//    @Autowired
//    private CellMapper cellMapper;
//    @Autowired
//    private RepositoryMapper repositoryMapper;
//    @Autowired
//    private SaveMapper saveMapper;
//    @Autowired
//    private LogMapper logMapper;

    public void check(List<Map<String,String>> data){
        // 不能入库的数据
        List<Map<String,String>> notInputData = new ArrayList<>();
        // 能入库的数据
        List<Map<String,String>> inputData = new ArrayList<>();
        // 遍历data
        for(Map map:data){
            // 检查体积
            if(Double.parseDouble((String) map.get("sh"))>80 || Double.parseDouble((String) map.get("sw"))>70 || Double.parseDouble((String) map.get("sd"))>60){
                map.put("reason","体积过大");
                notInputData.add(map);
            }
            // 检查重量
            else if(Double.parseDouble((String) map.get("weight"))>60){
                map.put("reason","超重");
                notInputData.add(map);
            }
            // 检查超市是否存在
            else if(supermarketMapper.findById("10")==null){
                map.put("reason","超市不存在");
                notInputData.add(map);
            }else {
                inputData.add(map);
            }
        }
        // 针对不能入库的货物
        if(notInputData.size()>0){
            notInput(notInputData);
        }
        // 针对能入库的货物
        if(inputData.size()>0){
            callInput(inputData);
        }
    }

    // 不能入库，写入停滞区
    public boolean notInput(List<Map<String,String>> list) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        int insert_num = 0;
        try {
            for(int i=0;i<list.size();i++){
                String id = list.get(i).get("sid");
                String name = list.get(i).get("sname");
                String type = list.get(i).get("stype");
                int num = Integer.parseInt(list.get(i).get("num"));
                double weight = Double.parseDouble(list.get(i).get("weight"));
                double sh = Double.parseDouble(list.get(i).get("sh"));
                double sw = Double.parseDouble(list.get(i).get("sw"));
                double sd = Double.parseDouble(list.get(i).get("sd"));
                Date production_date = sdf1.parse(list.get(i).get("production_date"));
                int shelf_life = Integer.parseInt(list.get(i).get("shelf_life"));
                String suid = list.get(i).get("suid");
                String reason = list.get(i).get("reason");
                NotInput notInput = new NotInput(id,name,type,num,weight,sh,sw,sd,production_date,shelf_life,suid,reason);
                insert_num += cargoStatusMapper.addNotInput(notInput);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return insert_num == list.size();
    }

    public List<NotInput> allNotInput(){
        return cargoStatusMapper.allNotInput();
    }

    public void callInput(List<Map<String,String>> data){
        // 调用好多个mapper
    }

}
