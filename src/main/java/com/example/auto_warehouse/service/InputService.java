package com.example.auto_warehouse.service;

import com.example.auto_warehouse.bean.*;
import com.example.auto_warehouse.mapper.*;
import com.example.auto_warehouse.util.Id;
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

    public void callInput(Map<String,String> map) throws ParseException {

        // 全局变量，传存的仓库柜，便于写save和log表

        // (1)对Species表的操作
        // 如果该种类的货物已存在，则只需更改num，否则需要插入操作
        if(speciesMapper.findById((String)(map.get("sid")))!=null){
            speciesMapper.updateNum((String) map.get("sid"), (Integer) map.get("num"));
        }else{
            Species species = new Species((String) map.get("sid"), (String) map.get("sname"), (String) map.get("stype"), (Integer) map.get("num"), (Double) map.get("weight"), (Double) map.get("sh"), (Double) map.get("sw"), (Double) map.get("sd"), (String) map.get("size"));
            speciesMapper.addSpecies(species);
        }

        // (2)对Cargo表的操作

        Cargo cargo = new Cargo((String) map.get("sid"), (String) map.get("sname"), (String) map.get("productionDate"), (Integer) map.get("shelfLife"), (String) map.get("suid"));
        cargoMapper.addCargo(cargo);
        // (3)对Cell表的操作
        Cell cells = repositoryMapper.getCellAttr(Id.getRepositoryID(),"s");
        Cell cellm = repositoryMapper.getCellAttr(Id.getRepositoryID(),"m");
        Cell celll = repositoryMapper.getCellAttr(Id.getRepositoryID(),"l");
        String type;
        if((Double)map.get("sh")<= cells.getCh() && (Double)map.get("sw")<= cells.getCw() && (Double)map.get("sd")<= cells.getCd() && (Double)map.get("weight")<= cells.getMaxWeight()){
            type = "s";
        }else if((Double)map.get("sh")<= cellm.getCh() && (Double)map.get("sw")<= cellm.getCw() && (Double)map.get("sd")<= cellm.getCd() && (Double)map.get("weight")<= cellm.getMaxWeight()){
            type = "m";
        }else{
            type = "l";
        }
        // 这块的算法合并优化很重要！！！！！！！！！(未完成)
        int num=(Integer) map.get("num");
        if(type.equals("s")){
            List<Cell> emptyCellList = repositoryMapper.getCellList(Id.getRepositoryID(),type,0);
            if(emptyCellList.size()!=0){
                int left = num-emptyCellList.size();
                for(int i=0;i<Math.max(num,emptyCellList.size());i++){
                    if(repositoryMapper.modifyCellIsUsed(Id.getRepositoryID(),emptyCellList.get(i).getCeid())==0){
                        System.out.println("callInput函数更新cell表失败！");
                    }
                }
                if(left>0){
                    // 当需要写入更大cell时，就应该合并空间放入了,需要写个合并函数 (未完成)----------------------------------------------
                    List<Cell> emptyCellListM = repositoryMapper.getCellList(Id.getRepositoryID(),"m",0);
                    if(emptyCellListM.size()!=0){
                        int left2 = left-emptyCellListM.size();
                        for(int i=0;i<Math.max(left,emptyCellListM.size());i++){
                            if(repositoryMapper.modifyCellIsUsed(Id.getRepositoryID(),emptyCellListM.get(i).getCeid())==0){
                                System.out.println("callInput函数更新cell表失败！");
                            }
                        }
                        if(left2>0){
                            List<Cell> emptyCellListL = repositoryMapper.getCellList(Id.getRepositoryID(),"l",0);
                            if(emptyCellListL.size()!=0){
                                int left3 = left2-emptyCellListL.size();
                                for(int i=0;i<Math.max(left2,emptyCellListL.size());i++){
                                    if(repositoryMapper.modifyCellIsUsed(Id.getRepositoryID(),emptyCellListL.get(i).getCeid())==0){
                                        System.out.println("callInput函数更新cell表失败！");
                                    }
                                }
                                if(left3>0){
                                    // 所有柜子都满了，只能写入NotInput表
                                }
                            }else{

                            }
                        }
                    }else{

                    }
                }
            }else{

            }

        }

    }

}
