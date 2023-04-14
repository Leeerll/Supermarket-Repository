package com.example.auto_warehouse.service;

import com.example.auto_warehouse.bean.NotInput;
import com.example.auto_warehouse.mapper.CargoStatusMapper;
import com.example.auto_warehouse.mapper.SupermarketMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputService {

    @Autowired
    private SupermarketMapper supermarketMapper;
    @Autowired
    private CargoStatusMapper cargoStatusMapper;

    public boolean check(List<Map<String,String>> data){
        // 检查体积
        if(Double.parseDouble(data.get(5).get("sh"))>80 || Double.parseDouble(data.get(6).get("sw"))>70 || Double.parseDouble(data.get(7).get("sd"))>60){
            Map<String, String> reason = new HashMap<>();
            reason.put("reason","体积过大");
            data.add(reason);
            notInput(data);
            return false;
        }
        //检查重量
        if(Double.parseDouble(data.get(4).get("weight"))>60){
            Map<String, String> reason = new HashMap<>();
            reason.put("reason","超重");
            data.add(reason);
            notInput(data);
            return false;
        }

        return true;
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
        //111
        return insert_num == list.size();
    }

}
