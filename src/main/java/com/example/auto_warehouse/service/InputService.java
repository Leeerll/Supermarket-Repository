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
    @Autowired
    private SaveMapper saveMapper;
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private OrderMapper orderMapper;
    private static final Logger logger = LoggerFactory.getLogger(LoadFileController.class);
    private Date now_time;

    private Date getNowTime() throws ParseException {
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        now_time = tFormat.parse(tFormat.format(now));
        return now_time;
    }

    public void check(List<Map<String,String>> data) throws ParseException {
        // 写入order表，状态设为“系统审核状态”
        String suid = data.get(0).get("suid");
        Order order = new Order(suid,Id.getRepositoryID(),"系统审核状态");
        orderMapper.insertOrder2(order);
        Message message = new Message(orderMapper.getOrderID(), "系统审核状态",suid);
        orderMapper.insertMessage(message);

        // 不能入库的数据
        List<Map<String,String>> notInputData = new ArrayList<>();
        // 遍历data
        for(Map map:data){
            // 内容完整核实
            if(!(map.containsKey("sid")&&map.containsKey("sname")&&map.containsKey("stype")&&map.containsKey("num")&&map.containsKey("weight")&&map.containsKey("sh")&&map.containsKey("sw")&&map.containsKey("sd")&&map.containsKey("production_date")&&map.containsKey("shelf_life")&&map.containsKey("suid")&&map.containsKey("size")&&map.containsKey("input_time")&&map.containsKey("output_time"))){
                map.put("reason","货品内容不完整");
                notInputData.add(map);
            }
            // 检查体积,长宽高不能超过2m
            else if(Double.parseDouble((String) map.get("sh"))>200 || Double.parseDouble((String) map.get("sw"))>200 || Double.parseDouble((String) map.get("sd"))>200){
                map.put("reason","体积过大");
                notInputData.add(map);
            }
            // 检查重量
            else if(Double.parseDouble((String) map.get("weight"))>160){
                map.put("reason","超重");
                notInputData.add(map);
            }
            // 检查超市是否存在（身份核实）
            else if(supermarketMapper.findById((String) map.get("suid"))==null){
                map.put("reason","超市不存在");
                notInputData.add(map);
            }
            // 检查仓库余位
            else if(repositoryMapper.getRestNum(Id.getRepositoryID())<1){
                map.put("reason", "仓库已满");
                notInputData.add(map);
            }
            else if(map.get("stype").equals("易炸品")||map.get("stype").equals("腐蚀品")||map.get("stype").equals("危险品")){
                map.put("reason", "货物类型无法进入仓库");
                notInputData.add(map);
            }
            else {


            }
        }
        // 针对不能入库的货物
        if(notInputData.size()>0){
            orderMapper.modifyOrderState(orderMapper.getOrderID(),"未通过系统审核状态",getNowTime());
            Message message2 = new Message(orderMapper.getOrderID(), "未通过系统审核状态",suid);
            orderMapper.insertMessage(message2);
            notInput(notInputData);
        }else{
            orderMapper.modifyOrderState(orderMapper.getOrderID(),"通过系统审核状态",getNowTime());
            Message message1 = new Message(orderMapper.getOrderID(), "通过系统审核状态",suid);
            orderMapper.insertMessage(message1);
            // 进入人工审核状态
            orderMapper.modifyOrderState(orderMapper.getOrderID(),"人工审核状态",getNowTime());
            Message message3 = new Message(orderMapper.getOrderID(), "人工审核状态",suid);
            orderMapper.insertMessage(message3);
            // 写入入库单表
            writeInputThings(data);
        }
    }
    public List<NotInput> allNotInput(){
        return cargoStatusMapper.allNotInput();
    }


    // 不能入库，写入停滞区
    public boolean notInput(List<Map<String,String>> list) {
        int orderID = orderMapper.getOrderID();
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
                NotInput notInput = new NotInput(id,name,type,num,production_date,shelf_life,suid,reason,orderID);
                insert_num += cargoStatusMapper.addNotInput(notInput);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return insert_num == list.size();
    }

    // 写入inputThings表
    public void writeInputThings(List<Map<String,String>> list) {
        int orderID = orderMapper.getOrderID();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            for(int i=0;i<list.size();i++){
                String sid = list.get(i).get("sid");
                String sname = list.get(i).get("sname");
                String stype = list.get(i).get("stype");
                int num = Integer.parseInt(list.get(i).get("num"));
                double weight = Double.parseDouble(list.get(i).get("weight"));
                double sh = Double.parseDouble(list.get(i).get("sh"));
                double sw = Double.parseDouble(list.get(i).get("sw"));
                double sd = Double.parseDouble(list.get(i).get("sd"));
                Date production_date = sdf1.parse(list.get(i).get("production_date"));
                int shelf_life = Integer.parseInt(list.get(i).get("shelf_life"));
                String suid = list.get(i).get("suid");
                String size = list.get(i).get("size");
                Date inputTime = sdf1.parse(list.get(i).get("input_time"));
                Date outputTime = sdf1.parse(list.get(i).get("output_time"));
                InputThings inputThings = new InputThings(sid,sname,stype,num,weight,sh,sw,sd,production_date,shelf_life,suid,size,inputTime,outputTime,orderID);
                orderMapper.insertInputThings(inputThings);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 预留库位
    public void callInput(Map<String,String> map) throws ParseException {

        // (1)对Species表的操作
        // 如果该种类的货物已存在，则只需更改num，否则需要插入操作
        String sid = map.get("sid");
        List<Species> list_species = speciesMapper.findById((String)(map.get("sid")));
        if(list_species.size()!=0){
            speciesMapper.addNum((String) map.get("sid"), Integer.parseInt(map.get("num")));
        }else{
            Species species = new Species((String) map.get("sid"), (String) map.get("sname"), (String) map.get("stype"), Integer.parseInt(map.get("num")), Double.parseDouble(map.get("weight")), Double.parseDouble(map.get("sh")), Double.parseDouble(map.get("sw")), Double.parseDouble(map.get("sd")), (String) map.get("size"));
            speciesMapper.addSpecies(species);
        }

        for(int i=0; i<Double.parseDouble(map.get("num")); i++) {
            // 这箱货物存放的仓库柜id
            String ceid = "";

            // (2)对Cargo表的操作
            Cargo cargo = new Cargo((String) map.get("sid"), (String) map.get("sname"), (String) map.get("production_date"), Integer.parseInt(map.get("shelf_life")), (String) map.get("suid"));
            cargoMapper.addCargo(cargo);
            int cid = cargoMapper.getNewCid();
            // (3)对Cell表的操作
            if(cargoStatusMapper.getSameSpecies(map.get("sid"),map.get("suid"),Id.getRepositoryID())==null){
                // 该超市在仓库中无同类型产品
                Cell empty_cell = repositoryMapper.getCellByType("null",Id.getRepositoryID());
                ceid = empty_cell.getCeid();
                empty_cell.setType(map.get("size"));
                if(map.get("size").equals("s")){
                    empty_cell.setRestNum(16-1);
                }else if(map.get("size").equals("m")){
                    empty_cell.setRestNum(8-1);
                }else if(map.get("size").equals("l")){
                    empty_cell.setRestNum(4-1);
                }else{
                    logger.warn("excel表中的size属性不是s/m/l");
                }
                System.out.println(empty_cell);
                //-------------------------------------------------
                int result = repositoryMapper.modifyCellTypeAndRestNumAndIsFull(empty_cell);
                if(result==0){
                    logger.warn("修改空cell属性失败");
                }
                repositoryMapper.modifyMachineHealth(Id.getRepositoryID());
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
                            int result = repositoryMapper.substractRepositoryRestNum(Id.getRepositoryID());
                            if(result==0){
                                logger.warn("修改repository's restNum失败");
                            }
                        }else{
                            cell.setRestNum(cell.getRestNum()-1);
                        }
                        //--------------------------------------------------------------
                        int result = repositoryMapper.modifyCellTypeAndRestNumAndIsFull(cell);
                        if(result==0){
                            logger.warn("修改未填满同类cell属性失败");
                        }
                        repositoryMapper.modifyMachineHealth(Id.getRepositoryID());
                        break;
                    }
                }
                if(!have){
                    // 同类产品的柜子都满了
                    Cell empty_cell = repositoryMapper.getCellByType("null",Id.getRepositoryID());
                    ceid = empty_cell.getCeid();
                    empty_cell.setType(map.get("size"));
                    if(map.get("size").equals("s")){
                        empty_cell.setRestNum(16-1);
                    }else if(map.get("size").equals("m")){
                        empty_cell.setRestNum(8-1);
                    }else if(map.get("size").equals("l")){
                        empty_cell.setRestNum(4-1);
                    }else{
                        logger.warn("excel表中的size属性不是s/m/l");
                    }
                    //--------------------------------------------------------------------------------------
                    int result = repositoryMapper.modifyCellTypeAndRestNumAndIsFull(empty_cell);
                    if(result==0){
                        logger.warn("修改空cell属性失败");
                    }
                    repositoryMapper.modifyMachineHealth(Id.getRepositoryID());
                }
            }

            // (4)对save表的操作
            Save save = new Save(sid, cid, Id.getRepositoryID(), ceid, map.get("suid"),"待入库");
            saveMapper.save(save);

            // (5)对log表的操作
            Log log = new Log(sid, cid, Id.getRepositoryID(), ceid, map.get("suid"), "input");
            logMapper.addLog(log);
        }

    }

}
