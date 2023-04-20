package com.example.auto_warehouse.service;

import com.example.auto_warehouse.bean.*;
import com.example.auto_warehouse.controller.LoadFileController;
import com.example.auto_warehouse.mapper.*;
import com.example.auto_warehouse.util.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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
    @Autowired
    private RepositoryMapper repositoryMapper;
//    @Autowired
//    private SaveMapper saveMapper;
//    @Autowired
//    private LogMapper logMapper;
    private static final Logger logger = LoggerFactory.getLogger(LoadFileController.class);

    public void check(List<Map<String,String>> data) throws ParseException {
        // 不能入库的数据
        List<Map<String,String>> notInputData = new ArrayList<>();
        // 能入库的数据
        List<Map<String,String>> inputData = new ArrayList<>();
        // 遍历data
        for(Map map:data){
            // 检查体积
            if(Double.parseDouble((String) map.get("sh"))>1200 || Double.parseDouble((String) map.get("sw"))>800 || Double.parseDouble((String) map.get("sd"))>600){
                map.put("reason","体积过大");
                notInputData.add(map);
            }
            // 检查重量
            else if(Double.parseDouble((String) map.get("weight"))>160){
                map.put("reason","超重");
                notInputData.add(map);
            }
            // 检查超市是否存在
            else if(supermarketMapper.findById("10")==null){
                map.put("reason","超市不存在");
                notInputData.add(map);
            }
            // 检查仓库余位
            else if(repositoryMapper.getRestNum(Id.getRepositoryID())<1){
                map.put("reason", "仓库已满");
                notInputData.add(map);
            }
            else {
                inputData.add(map);
                callInput(map);
            }
        }
        // 针对不能入库的货物
        if(notInputData.size()>0){
            notInput(notInputData);
        }
//        // 针对能入库的货物
//        if(inputData.size()>0){
//            callInput(inputData);
//        }
    }
    public List<NotInput> allNotInput(){
        return cargoStatusMapper.allNotInput();
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
                Date production_date = sdf1.parse(list.get(i).get("production_date"));
                int shelf_life = Integer.parseInt(list.get(i).get("shelf_life"));
                String suid = list.get(i).get("suid");
                String reason = list.get(i).get("reason");
                NotInput notInput = new NotInput(id,name,type,num,production_date,shelf_life,suid,reason);
                insert_num += cargoStatusMapper.addNotInput(notInput);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return insert_num == list.size();
    }

    public void callInput(Map<String,String> map) throws ParseException {



        // (1)对Species表的操作
        // 如果该种类的货物已存在，则只需更改num，否则需要插入操作
//        if(speciesMapper.findById((String)(map.get("sid")))!=null){
//            speciesMapper.updateNum((String) map.get("sid"), (Integer) map.get("num"));
//        }else{
//            Species species = new Species((String) map.get("sid"), (String) map.get("sname"), (String) map.get("stype"), (Integer) map.get("num"), (Double) map.get("weight"), (Double) map.get("sh"), (Double) map.get("sw"), (Double) map.get("sd"), (String) map.get("size"));
//            speciesMapper.addSpecies(species);
//        }

        for(int i=0; i<Double.parseDouble(map.get("num")); i++) {
            // 这箱货物存放的仓库柜id
            String ceid;

            // (2)对Cargo表的操作

//            Cargo cargo = new Cargo((String) map.get("sid"), (String) map.get("sname"), (String) map.get("productionDate"), (Integer) Double.parseDouble(map.get("shelfLife")), (String) map.get("suid"));
//            cargoMapper.addCargo(cargo);

            // (3)对Cell表的操作
            if(cargoStatusMapper.getSameSpecies(map.get("sid"),map.get("suid"),Id.getRepositoryID()).equals("null")){
                // 该超市在仓库中无同类型产品
                Cell empty_cell = repositoryMapper.getCellByType("null",Id.getRepositoryID());
                ceid = empty_cell.getCeid();
                empty_cell.setType(map.get("type"));
                if(map.get("type").equals("s")){
                    empty_cell.setRestNum(16-1);
                }else if(map.get("type").equals("m")){
                    empty_cell.setRestNum(8-1);
                }else if(map.get("type").equals("l")){
                    empty_cell.setRestNum(4-1);
                }else{
                    logger.warn("excel表中的type属性不是s/m/l");
                }
                int result = repositoryMapper.modifyCellTypeAndRestNumAndIsFull(empty_cell);
                if(result==0){
                    logger.warn("修改空cell属性失败");
                }
            }else{
                // 该超市在仓库中有同类型产品
                List<Save> list = cargoStatusMapper.getSameSpeciesAllCeid(map.get("sid"),map.get("suid"),Id.getRepositoryID());
                Boolean have = false;
                for(Save save:list){
                    Cell cell = repositoryMapper.getCellByCeid(save.getCeid(), Id.getRepositoryID());
                    if(cell.getIsFull()==1){
                        continue;
                    }else{
                        have = true;
                        // 匹配到了未填满的cell
                        ceid = cell.getCeid();
                        if(cell.getRestNum()-1==0){
                            cell.setRestNum(0);
                            cell.setIsFull(1);
                        }else{
                            cell.setRestNum(cell.getRestNum()-1);
                        }
                        int result = repositoryMapper.modifyCellTypeAndRestNumAndIsFull(cell);
                        if(result==0){
                            logger.warn("修改未填满同类cell属性失败");
                        }
                        break;
                    }
                }
                if(!have){
                    // 同类产品的柜子都满了
                    Cell empty_cell = repositoryMapper.getCellByType("null",Id.getRepositoryID());
                    ceid = empty_cell.getCeid();
                    empty_cell.setType(map.get("type"));
                    if(map.get("type").equals("s")){
                        empty_cell.setRestNum(16-1);
                    }else if(map.get("type").equals("m")){
                        empty_cell.setRestNum(8-1);
                    }else if(map.get("type").equals("l")){
                        empty_cell.setRestNum(4-1);
                    }else{
                        logger.warn("excel表中的type属性不是s/m/l");
                    }
                    int result = repositoryMapper.modifyCellTypeAndRestNumAndIsFull(empty_cell);
                    if(result==0){
                        logger.warn("修改空cell属性失败");
                    }
                }
            }

        }

    }

}
