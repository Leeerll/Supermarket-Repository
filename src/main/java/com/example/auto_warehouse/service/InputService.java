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
    private CheckInputMapper checkInputMapper;
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private IncomeMapper incomeMapper;
    @Autowired
    private SaveMapper saveMapper;
    @Autowired
    private OrderMapper orderMapper;
    private static final Logger logger = LoggerFactory.getLogger(LoadFileController.class);
    public Date now_time;

    public Date getNowTime() throws ParseException {
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        now_time = tFormat.parse(tFormat.format(now));
        return now_time;
    }


    // 入库申请
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
            if(!(map.containsKey("sid")&&map.containsKey("sname")&&map.containsKey("stype")&&map.containsKey("num")&&map.containsKey("weight")&&map.containsKey("sh")&&map.containsKey("sw")&&map.containsKey("sd")&&map.containsKey("production_date")&&map.containsKey("shelf_life")&&map.containsKey("suid")&&map.containsKey("size")&&map.containsKey("input_time")&&map.containsKey("output_time")&&map.containsKey("price")&&map.containsKey("phone")&&map.containsKey("manufacturer"))){
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
            // 进入人工审核状态
            orderMapper.modifyOrderState(orderMapper.getOrderID(),"人工审核状态",getNowTime());
            Message message3 = new Message(orderMapper.getOrderID(), "人工审核状态",suid);
            orderMapper.insertMessage(message3);
            // 写入入库单表
            writeInputThings(data);
        }
    }

    // 正式到货申请
    public void checkIn(List<Map<String,String>> data) throws ParseException {

        // (1) 检查费用是否已缴

        // (2) 检查货物信息和之前的入库申请单是否相同
        // 先根据orderId找到上次的入库申请单信息
        int orderId = Integer.parseInt(data.get(0).get("orderID"));
        List<InputThings> inputThingsList = orderMapper.getInputThingsByOrderID(orderId);
        int[] checkThing = new int[inputThingsList.size()];
        // 遍历data
        for(Map map: data){
            int find = 0;
            int index = 0;
            // 数量检查
            for(InputThings inputThings: inputThingsList){
                if(inputThings.getSid().equals(map.get("sid"))){
                    find = 1;
                    checkThing[index] = 1;
                    if(inputThings.getNum()==Integer.parseInt((String) map.get("num"))){
                        CheckInput checkInput  = new CheckInput(orderId, inputThings.getSid(), 0, "正常", "待确认入库");
                        checkInputMapper.insertCheckInput(checkInput);
                    }else if(inputThings.getNum()<Integer.parseInt((String) map.get("num"))){
                        CheckInput checkInput  = new CheckInput(orderId, inputThings.getSid(), inputThings.getNum() - Integer.parseInt((String) map.get("num")), "实际到货数量少于入库申请数量", "待确认入库");
                        checkInputMapper.insertCheckInput(checkInput);
                    }else{
                        CheckInput checkInput  = new CheckInput(orderId, inputThings.getSid(), inputThings.getNum() - Integer.parseInt((String) map.get("num")), "实际到货数量多于入库申请数量", "待确认入库");
                        checkInputMapper.insertCheckInput(checkInput);
                    }
                    break;
                }
                index ++;
            }
            // 种类检查
            if(find==0){
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                String sid = (String) map.get("sid");
                String sname = (String) map.get("sname");
                String stype = (String) map.get("stype");
                int num = Integer.parseInt((String) map.get("num"));
                double weight = Double.parseDouble((String) map.get("weight"));
                double sh = Double.parseDouble((String) map.get("sh"));
                double sw = Double.parseDouble((String) map.get("sw"));
                double sd = Double.parseDouble((String) map.get("sd"));
                Date production_date = sdf1.parse((String) map.get("production_date"));
                int shelf_life = Integer.parseInt((String) map.get("shelf_life"));
                String suid = (String) map.get("suid");
                String size = (String) map.get("size");
                Date inputTime = sdf1.parse((String) map.get("input_time"));
                Date outputTime = sdf1.parse((String) map.get("output_time"));
                double price = Double.parseDouble((String) map.get("price"));
                String phone = (String) map.get("phone");
                String manufacturer = (String) map.get("manufacturer");
                CheckInput checkInput  = new CheckInput(orderId, (String) map.get("sid"), Integer.parseInt((String) map.get("num")) , "入库申请中无该物品", "待确认入库");
                checkInputMapper.insertCheckInput(checkInput);
                InputThings inputThings = new InputThings(checkInput.getSid(), (String) map.get("sname"), (String) map.get("stype"), Integer.parseInt((String) map.get("num")), Double.parseDouble((String) map.get("weight")), Double.parseDouble((String) map.get("sh")), Double.parseDouble((String) map.get("sw")), Double.parseDouble((String) map.get("sd")), production_date, shelf_life, (String) map.get("suid"), (String) map.get("size"), inputTime, outputTime, orderId, price, (String) map.get("phone"), (String) map.get("manufacturer"));
                orderMapper.insertInputThings(inputThings);
            }
        }
        // 种类检查
        for(int i=0; i<inputThingsList.size(); i++) {
            if (checkThing[i] == 0) {
                CheckInput checkInput = new CheckInput(orderId, inputThingsList.get(i).getSid(), 0, "实际到货缺少该物品", "待确认入库");
                checkInputMapper.insertCheckInput(checkInput);
            }
        }
        orderMapper.modifyOrderState(orderId,"入库清单待确认状态", getNowTime());
        Message message1 = new Message(orderId, "入库清单待确认状态", orderMapper.getSuid(orderId));
        orderMapper.insertMessage(message1);
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
                double price = Double.parseDouble(list.get(i).get("price"));
                String phone = list.get(i).get("phone");
                String manufacturer = list.get(i).get("manufacturer");
                InputThings inputThings = new InputThings(sid,sname,stype,num,weight,sh,sw,sd,production_date,shelf_life,suid,size,inputTime,outputTime,orderID,price,phone,manufacturer);
                orderMapper.insertInputThings(inputThings);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 预留库位
    public void callInput(Map<String,String> map,int orderID) throws ParseException {

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
            Cargo cargo = new Cargo((String) map.get("sid"), (String) map.get("sname"), (String) map.get("production_date"), Integer.parseInt(map.get("shelf_life")), (String) map.get("suid"),orderID);
            cargoMapper.addCargo(cargo);
            int cid = cargoMapper.getNewCid();
            // (3)对Cell表的操作
            if(cargoStatusMapper.getSameSpecies(map.get("sid"),map.get("suid"),Id.getRepositoryID()).size()==0){
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
                List<String> list = cargoStatusMapper.getSameSpeciesAllCeid(map.get("sid"),map.get("suid"),Id.getRepositoryID());
                Boolean have = false;
                for(String ceid1:list){
                    Cell cell = repositoryMapper.getCellByCeid(ceid1, Id.getRepositoryID());
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
            Save save = new Save(sid, cid, Id.getRepositoryID(), ceid, map.get("suid"),"待入库",orderID);
            saveMapper.save(save);

            // (5)对log表的操作
            Log log = new Log(sid, cid, Id.getRepositoryID(), ceid, map.get("suid"), "待入库",orderID);
            logMapper.addLog(log);
        }

    }


    // 超市缴费成功
    public String finish_payment(int orderID) throws ParseException {
        // 改成“已缴费状态”
        orderMapper.modifyOrderState(orderID,"已预留库位状态",getNowTime());
        Message message1 = new Message(orderID, "已预留库位状态", orderMapper.getSuid(orderID));
        if  (message1.getSuid()==null){
            return "flase";
        }
        orderMapper.insertMessage(message1);


        return "true";
    }

    public String confirm_checkInput(int orderID) throws ParseException {
        // 根据核验单checkInput对货位进行释放/增加
        double refund = 0;
        // 1.针对入库货物数量少了的情况
        List<CheckInput> list_num = checkInputMapper.getByOrderIDAndNum(orderID);
        for(CheckInput checkInput:list_num){
            String sid = checkInput.getSid();
            List<String> list_ceid = saveMapper.findAllCeidByOrderIDAndSid(sid,orderID);
            Map<String,Integer> modified_ceid = new LinkedHashMap<>();
            int num = Math.abs(checkInput.getNum());
            // 减少species表中的num
            speciesMapper.reduceNum(sid,num);
            // 加到退款的费用里
            InputThings inputThings = orderMapper.getInputThingsByOrderIDAndSid(orderID,sid);
            int day = (int) ((inputThings.getOutputTime().getTime() - inputThings.getInputTime().getTime())
                    / (24 * 60 * 60 * 1000));
            refund+=num*day*2;
            for(String ceid:list_ceid){
                if(saveMapper.findCountByOrderIDAndSidAndCeid(sid,orderID,ceid)>=num){
                    modified_ceid.put(ceid,num);
                    break;
                }
            }
            if(modified_ceid.size()>0){
                Cell cell = repositoryMapper.getCellByCeid(modified_ceid.entrySet().iterator().next().getKey(),Id.getRepositoryID());
                cell.setRestNum(cell.getRestNum()+num);
                if(((cell.getType().equals("s"))&&(cell.getRestNum()==16))||((cell.getType().equals("m"))&&(cell.getRestNum()==8))||((cell.getType().equals("l"))&&(cell.getRestNum()==4))){
                    cell.setType("null");
                    cell.setIsFull(0);
                    repositoryMapper.updateRepositoryRestNum(Id.getRepositoryID());
                }
                repositoryMapper.modifyCellTypeAndRestNumAndIsFull(cell);
            }else{
                // 如果这个num比任何一个cell中存的这个sid的数量都多
                for(String ceid:list_ceid){
                    if(saveMapper.findCountByOrderIDAndSidAndCeid(sid,orderID,ceid)>=num){
                        modified_ceid.put(ceid,num);
                        break;
                    }else{
                        int num1 = num;
                        modified_ceid.put(ceid,num1);
                        num=num-saveMapper.findCountByOrderIDAndSidAndCeid(sid,orderID,ceid);
                    }
                }
                for(String ceid2:modified_ceid.keySet()){
                    Cell cell = repositoryMapper.getCellByCeid(ceid2,Id.getRepositoryID());
                    cell.setRestNum(cell.getRestNum()+modified_ceid.get(ceid2));
                    if(((cell.getType().equals("s"))&&(cell.getRestNum()==16))||((cell.getType().equals("m"))&&(cell.getRestNum()==8))||((cell.getType().equals("l"))&&(cell.getRestNum()==4))){
                        cell.setType("null");
                        cell.setIsFull(0);
                        repositoryMapper.updateRepositoryRestNum(Id.getRepositoryID());
                    }
                    repositoryMapper.modifyCellTypeAndRestNumAndIsFull(cell);
                }
            }
            // 更改cargo、save、log的state信息
            for(String ceid:modified_ceid.keySet()){
                List<Integer> list_cid = saveMapper.getNumCid(sid,orderID,ceid,modified_ceid.get(ceid));
                for(int cid:list_cid){
                    saveMapper.modifySaveState(cid);
                    cargoMapper.modifyCargoState(cid);
                    Log log = new Log(sid, cid, Id.getRepositoryID(), ceid, logMapper.getLogByCid(cid).getSuid(), "变更未送达",orderID);
                    logMapper.addLog(log);
                }
            }
        }

        // 2.针对入库货物品类少了的情况
        List<CheckInput> list_species = checkInputMapper.getByOrderIDAndSpecies(orderID);
        List<CheckInput> list_species2 = checkInputMapper.getByOrderIDAndNum2(orderID);
        list_species.addAll(list_species2);
        for(CheckInput checkInput:list_species){
            InputThings inputThings = orderMapper.getInputThingsByOrderIDAndSid(orderID,checkInput.getSid());
            // 减少species表中的num
            speciesMapper.reduceNum(checkInput.getSid(),inputThings.getNum());
            // 加到退款的费用里
            int day = (int) ((inputThings.getOutputTime().getTime() - inputThings.getInputTime().getTime())
                    / (24 * 60 * 60 * 1000));
            refund+=inputThings.getNum()*day*2;
            // 减少所占cell的restNum
            List<String> list_ceid = saveMapper.findAllCeidByOrderIDAndSid(checkInput.getSid(),orderID);
            for(String ceid:list_ceid){
                Cell cell = repositoryMapper.getCellByCeid(ceid,Id.getRepositoryID());
                cell.setRestNum(cell.getRestNum()+saveMapper.findCountByOrderIDAndSidAndCeid(checkInput.getSid(),orderID,ceid));
                if(((cell.getType().equals("s"))&&(cell.getRestNum()==16))||((cell.getType().equals("m"))&&(cell.getRestNum()==8))||((cell.getType().equals("l"))&&(cell.getRestNum()==4))){
                    cell.setType("null");
                    cell.setIsFull(0);
                    repositoryMapper.updateRepositoryRestNum(Id.getRepositoryID());
                }
                repositoryMapper.modifyCellTypeAndRestNumAndIsFull(cell);
                // 更改cargo、save、log的state信息
                List<Integer> list_cid = saveMapper.getNumCid(checkInput.getSid(),orderID,ceid,saveMapper.findCountByOrderIDAndSidAndCeid(checkInput.getSid(),orderID,ceid));
                for(int cid:list_cid){
                    saveMapper.modifySaveState(cid);
                    cargoMapper.modifyCargoState(cid);
                    Log old_log = logMapper.getLogByCid(cid);
                    Log log = new Log(old_log.getSid(), cid, Id.getRepositoryID(), ceid, old_log.getSuid(), "变更未送达",orderID);
                    logMapper.addLog(log);
                }
            }
        }

        // 3.针对入库货物品类多了/数量多了的情况
        double pay_add = 0; // 需要补缴费的金额
        List<CheckInput> list_num_add = checkInputMapper.getByOrderIDAndNumAdd(orderID);
        List<CheckInput> list_species_add = checkInputMapper.getByOrderIDAndSpeciesAdd(orderID);
        list_num_add.addAll(list_species_add);
        List<Map<String,String>> list_pack = pack(list_num_add);
        for(Map<String,String> map:list_pack){
            callInput(map,orderID);
        }
        for(CheckInput checkInput:list_num_add){
            InputThings inputThings = orderMapper.getInputThingsByOrderIDAndSid(orderID,checkInput.getSid());
            // 加到退款的费用里
            int day = (int) ((inputThings.getOutputTime().getTime() - inputThings.getInputTime().getTime())
                    / (24 * 60 * 60 * 1000));
            int num = 0;
            if(checkInput.getStatement().equals("实际到货数量多于入库申请数量")){
                num = checkInput.getNum();
            }
            if(checkInput.getStatement().equals("入库申请中无该物品")){
                num = inputThings.getNum();
            }
            pay_add+=num*day*2;
            refund-=num*day*2;
        }
        if(refund>0){
            // 需要退款
            // 修改order表的cost
            Order order = orderMapper.getOrderByOrderID(orderID);
            orderMapper.modifyOrderCost(orderID,order.getCost()-refund);
            // 记录cost更改日志到orderCostLog
//            OrderCostLog orderCostLog = new OrderCostLog(order.getSuid(),orderID,refund,"入库重计费退款");
//            orderMapper.insertOrderCostLog(orderCostLog);

        }else if(refund==0){
            // 什么都不需要
        }else{
            // 需要补缴费
            Order order = orderMapper.getOrderByOrderID(orderID);
            orderMapper.modifyOrderCost(orderID,order.getCost()-refund);
        }
        orderMapper.modifyOrderState(orderID,"入库缴费状态",getNowTime());
        Message message1 = new Message(orderID, "入库缴费状态", orderMapper.getSuid(orderID));
        orderMapper.insertMessage(message1);
        return "true";
    }

    public List<Map<String,String>> pack(List<CheckInput> list_para){
        List<Map<String,String>> list = new ArrayList<>();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        for(CheckInput checkInput:list_para){
            Map<String,String> map = new HashMap<>();
            map.put("sid",checkInput.getSid());
            InputThings inputThings = orderMapper.getInputThingsByOrderIDAndSid(checkInput.getOrderID(), checkInput.getSid());
            map.put("sname",inputThings.getSname());
            map.put("stype",inputThings.getStype());
            map.put("weight",String.valueOf(inputThings.getWeight()));
            map.put("sh",String.valueOf(inputThings.getSh()));
            map.put("sw",String.valueOf(inputThings.getSw()));
            map.put("sd",String.valueOf(inputThings.getSd()));
            map.put("size",inputThings.getSize());
            map.put("production_date", sdf1.format(inputThings.getProductionDate()));
            map.put("shelf_life",String.valueOf(inputThings.getShelfLife()));
            map.put("suid",inputThings.getSuid()); // suid是超市id
            if(checkInput.getStatement().equals("实际到货数量多于入库申请数量")){
                map.put("num",String.valueOf(checkInput.getNum()));
            }
            if(checkInput.getStatement().equals("入库申请中无该物品")){
                map.put("num",String.valueOf(inputThings.getNum()));
            }
            list.add(map);
        }
        return list;
    }

    public String actual_input(int orderID) throws ParseException {
        // 实际入库：写入cargo、save的入库时间，修改state状态；插入新log
        // 修改成“入库待确认状态”
        orderMapper.modifyOrderState(orderID,"入库待确认状态",getNowTime());
        Message message1 = new Message(orderID, "入库待确认状态", orderMapper.getSuid(orderID));
        if(message1.getSuid()==null){
            return "flase";
        }
        orderMapper.insertMessage(message1);
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd");
        cargoMapper.updateInputTime(orderID,tFormat.parse(tFormat.format(now)));
        saveMapper.updateInputTimeAndState(orderID,tFormat.parse(tFormat.format(now)));
        List<Integer> list_plan_cid = logMapper.getAllCidByOrderID(orderID);
        List<Integer> list_release_cid = logMapper.getReleaseCidByOrderID(orderID);
        for(int cid:list_plan_cid){
            if(!list_release_cid.contains(cid)){
                Log old_log = logMapper.getLogByCid(cid);
                Log log = new Log(old_log.getSid(),cid, old_log.getRid(), old_log.getCeid(),old_log.getSuid(),"入库待确认状态",orderID);
                logMapper.addLog(log);
            }
        }
        return "true";
    }
    public String actual_input_confirm(int orderID) throws ParseException {
        // 修改成“⼊库已确认状态”,更改save、log表
        orderMapper.modifyOrderState(orderID,"入库已确认状态",getNowTime());
        Message message1 = new Message(orderID, "入库已确认状态", orderMapper.getSuid(orderID));
        if (message1.getSuid()==null){
            return "flase";
        }
        orderMapper.insertMessage(message1);

        saveMapper.updateConfirmState(orderID);
        List<Integer> list = logMapper.getConfirmCidByOrderID(orderID);
        for(int cid:list){
            Log old_log = logMapper.getLogByCid(cid);
            Log log = new Log(old_log.getSid(),cid, old_log.getRid(), old_log.getCeid(),old_log.getSuid(),"入库已确认状态",orderID);
            logMapper.addLog(log);
        }
        return "true";
    }

    public void income(double money){
        // 仓库收入income表增加，repository收入增加
        //double money=orderMapper.getOrderByOrderID(orderID).getCost();
        repositoryMapper.updateIncome(Id.getRepositoryID(), money);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int yearMonth = year*100+month+1;
        List<Income> list = incomeMapper.findByYearMonth(Id.getRepositoryID(),yearMonth);
        if(list.size()==0){
            Income income = new Income(Id.getRepositoryID(),yearMonth,money);
            incomeMapper.insertIncome(income);
        }else{
            incomeMapper.updateIncome(yearMonth,money,Id.getRepositoryID());
        }
    }

}
