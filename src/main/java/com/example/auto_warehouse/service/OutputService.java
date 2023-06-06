package com.example.auto_warehouse.service;

import com.example.auto_warehouse.bean.*;
import com.example.auto_warehouse.controller.LoadFileController;
import com.example.auto_warehouse.mapper.*;
import com.example.auto_warehouse.util.Id;
import com.example.auto_warehouse.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OutputService {
    @Autowired
    private SpeciesMapper speciesMapper;  // 更改存量
    @Autowired
    private CargoMapper cargoMapper;      // 更新出库时间
//    @Autowired
//    private CellMapper cellMapper;      // 更改使用状态
    @Autowired
    private RepositoryMapper repositoryMapper; // 更改柜子剩余数量
    @Autowired
    private SaveMapper saveMapper;
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private SupermarketMapper supermarketMapper;
    @Autowired
    private CargoStatusMapper cargoStatusMapper;
    @Autowired
    private OrderMapper orderMapper;
    public Date now_time;

    private static final Logger logger = LoggerFactory.getLogger(LoadFileController.class);
    public Date getNowTime() throws ParseException {
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        now_time = tFormat.parse(tFormat.format(now));
        return now_time;
    }

    // 出库data：sid、num、suid
    public String check(List<Map<String,String>> data) throws ParseException {
        int orderID = Integer.parseInt(data.get(0).get("orderID"));
        // 不能出库的数据
        List<Map<String,String>> notOutputData = new ArrayList<>();
        // 遍历data
        for(Map map:data){
            // 检查sid是否存在
            if(speciesMapper.findById((String) map.get("sid"))==null || cargoStatusMapper.getSameSpeciesByOrderID((String) map.get("sid"), orderID).size()==0){
                map.put("reason","商品不存在");
                notOutputData.add(map);
            }
            // 检查sid是否存在
            else if(supermarketMapper.findById((String) map.get("suid"))==null){
                map.put("reason","超市不存在");
                notOutputData.add(map);
            }
            // 检查num是否够
            else if(Integer.parseInt((String) map.get("num"))> cargoMapper.getNotExpireNum((String) map.get("sid"), orderID)){
                map.put("reason","余量不足");
                notOutputData.add(map);
            }
            // 更改表
            else{

            }

        }
        // 针对不能出库的货物
        if(notOutputData.size()>0){
            notOutput(notOutputData);
            orderMapper.modifyOrderState(orderID,"出库请求未通过系统审核",getNowTime());
            Message message1 = new Message(orderID, "出库请求未通过系统审核", orderMapper.getSuid(orderID));
            orderMapper.insertMessage(message1);
        }else{
            // 写入outputThings表
            outputThings(data);
            getOrderPayment(data);
            if(getActualOrderPayment(orderID)){
                // 如果要补缴费
                orderMapper.modifyOrderState(orderID,"出库重计费补缴费状态",getNowTime());
                Message message1 = new Message(orderID, "出库重计费补缴费状态", orderMapper.getSuid(orderID));
                orderMapper.insertMessage(message1);
            }
        }
        return "true";
    }

    public void outputThings(List<Map<String,String>> data){
        for(Map<String,String> map:data){
            int num = Integer.parseInt(map.get("num"));
            int orderID = Integer.parseInt(map.get("orderID"));
            OutputThings outputThings = new OutputThings(map.get("sid"),map.get("suid"),num,map.get("name"),orderID);
            orderMapper.insertOutputThings(outputThings);
        }
    }

    private void notOutput(List<Map<String, String>> list) {
        int orderID = Integer.parseInt(list.get(0).get("orderID"));
        int insert_num = 0;
        try {
            for(int i=0;i<list.size();i++){
                String sid = list.get(i).get("sid");
                int num = Integer.parseInt(list.get(i).get("num"));
                String suid = list.get(i).get("suid");
                String reason = list.get(i).get("reason");
                String name = list.get(i).get("name");
                NotOutput notOutput = new NotOutput(sid,suid,num,reason,name,orderID);
                insert_num += cargoStatusMapper.addNotOutput(notOutput);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(insert_num!=list.size()){
            logger.warn("不能出库数据未完全写入");
        }
    }
    public List<NotOutput> allNotOutput(){
        return cargoStatusMapper.allNotOutput();
    }

    // 清理货柜 写入出库时间
    public void callOutput(Map<String,String> map) throws ParseException {
        int orderID = Integer.parseInt(map.get("orderID"));
        // 超市端需要的量
        int needNum = Integer.parseInt((String)map.get("num"));
        // 当前货物的sid
        String sid = (String) map.get("sid");
        // (1) 对species表的操作
        speciesMapper.reduceNum((String) map.get("sid"), needNum);
        // 出库时间
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date outputTime = tFormat.parse(tFormat.format(now));
        // 把outputThings中的状态改成已出库
        orderMapper.modifyOutputThingsState(orderID,sid);

        // 设计这个while循环的目的：从不同批次的货物中分批出货
        while(needNum>0){
            // 获取：未出库、现在还未过期、但最近要过期的一批货物
            List<Cargo> cargoList = cargoMapper.outputBatch(sid,orderID);
            int thisNum = cargoList.size();

            // 遍历每一个cargo
            for(int i=0; i<thisNum; i++){
                // 当前货物的cid
                int cid = cargoList.get(i).getCid();
                // 当前货物所处仓库柜ceid
                String ceid = saveMapper.getCellByCid(cid);
                // 当前货物所处仓库柜Cell对象
                Cell cell = repositoryMapper.getCellByCeid(ceid, Id.getRepositoryID());
                // (2) 对cargo表的操作: outputTime
                cargoMapper.updateOutputTime(cid, outputTime);
                cargoMapper.modifyCargoStateCommon(cid,"已出库");
                // 先判断是否清柜
                // (3) 对cell表的操作: 按需更改restNum、isFull、type
                // (4) 对repository表的操作: restNum（彻底空出一个cell才改）
                if(clearCell(cell)){    // 若需清柜
                    repositoryMapper.clearCargo(ceid);
                    repositoryMapper.updateRepositoryRestNum(Id.getRepositoryID());
                }else{                  // 若不需清柜：restNum++
                    repositoryMapper.outCargo(ceid);
                }

                // (5) 对save表的操作：outputTime
                saveMapper.updateOutputTime(cid, outputTime);
                saveMapper.modifySaveStateCommon(cid,"已出库");
                // (6) 对log表的操作
                Log old_log = logMapper.getLogByCid(cid);
                Log log = new Log(old_log.getSid(), cid, Id.getRepositoryID(), ceid, old_log.getSuid(), "已出库",orderID);
                logMapper.addLog(log);

            }

            needNum -= thisNum;
        }

        // (7) 生成订单------------------------------------------------------------------------------------------------待修改
//        double cost = needNum * 1;
//        Order order = new Order((String)map.get("suid"), Id.getRepositoryID(), cost);
//        orderMapper.insertOrder(order);

        // 修改order的状态为“部分出库状态”、“已出库结束状态”
        List<Save> list = saveMapper.findInputState(orderID);
        if(list.size()!=0){
            // “部分出库状态”
            orderMapper.modifyOrderState(orderID,"部分出库状态",getNowTime());
            Message message1 = new Message(orderID, "部分出库状态", orderMapper.getSuid(orderID));
            orderMapper.insertMessage(message1);
        }else{
            // “已出库结束状态”
            orderMapper.modifyOrderState(orderID,"已出库结束状态",getNowTime());
            Message message1 = new Message(orderID, "已出库结束状态", orderMapper.getSuid(orderID));
            orderMapper.insertMessage(message1);
        }
    }
    // 清空仓库柜
    boolean clearCell(Cell cell){
        if(cell.getType()=="l" && cell.getRestNum()==3){
            return true;
        }
        if(cell.getType()=="m" && cell.getRestNum()==7){
            return true;
        }
        if(cell.getType()=="s" && cell.getRestNum()==15){
            return true;
        }
        return false;
    }

    // 在提交出库订单时，计算该费用
    public void getOrderPayment(List<Map<String, String>> data) throws ParseException {
        // 当前出库订单编号
        int orderID = Integer.parseInt((String)data.get(0).get("orderID"));
        Order order = orderMapper.getOrderByOrderID(orderID);
        // 出库的excel中的出库日期
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

        double amount = 0;
        for(Map<String, String> map:data){
            Date outputTime = sdf1.parse((String) map.get("output_time"));
            String sid = (String) map.get("sid");
            int num = Integer.parseInt((String) map.get("num"));
            Save save = orderMapper.getOrderPayment(orderID,sid);
            int day = (int) ((outputTime.getTime() - save.getInputTime().getTime())
                    / (24 * 60 * 60 * 1000));
            amount+=day*2*num;
        }
        // 更新出库费用
        orderMapper.updatePayment(orderID,order.getActualCost()+amount);
    }

    // 出库补交费用,返回true就是要超市缴费，走确认流程的
    public boolean getActualOrderPayment(int orderID) throws ParseException {
        Order order = orderMapper.getOrderByOrderID(orderID);
        // actualCost是实际累计出库需要交的费用，一个订单的货物可以分批出库
        double actualCost = order.getActualCost();
        double cost = order.getPaidMoney();
        double diff = actualCost-cost;
        if(diff>0){
            // actual > 0 需要补偿差价
            return true;
        }else if(diff<0){
            // 需要退款
            // 更新orderCostLog表
            OrderCostLog orderCostLog = new OrderCostLog(order.getSuid(),orderID,Math.abs(diff), "出库退款");
            orderMapper.insertOrderCostLog(orderCostLog);
            orderMapper.updatePaidMoney(order.getOrderID(), order.getPaidMoney()-Math.abs(diff));
            // 执行出库操作
            List<OutputThings> list1 = orderMapper.getOutputThingsByOrderID(orderID);
            for(OutputThings outputThings:list1){
                Map<String,String> map1 = new HashMap<>();
                map1.put("sid",outputThings.getSid());
                map1.put("suid",outputThings.getSuid());
                map1.put("name",outputThings.getName());
                map1.put("num",String.valueOf(outputThings.getNum()));
                map1.put("orderID",String.valueOf(outputThings.getOrderID()));
                callOutput(map1);
            }
            return false;
        }else {
            // 不需要补缴费和退款的，直接出库
            List<OutputThings> list1 = orderMapper.getOutputThingsByOrderID(orderID);
            for(OutputThings outputThings:list1){
                Map<String,String> map1 = new HashMap<>();
                map1.put("sid",outputThings.getSid());
                map1.put("suid",outputThings.getSuid());
                map1.put("name",outputThings.getName());
                map1.put("num",String.valueOf(outputThings.getNum()));
                map1.put("orderID",String.valueOf(outputThings.getOrderID()));
                callOutput(map1);
            }
            return false;
        }

    }
    // 获取超市缴费记录
    public List<OrderCostLog>getPaymentOrderLog(String suid){
        List<OrderCostLog>list = orderMapper.getOrderPaymentLog(suid);
        return list;
    }

}
