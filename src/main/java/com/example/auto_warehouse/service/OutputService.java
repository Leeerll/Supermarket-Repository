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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private static final Logger logger = LoggerFactory.getLogger(LoadFileController.class);

    // 出库data：sid、num、suid
    public void check(List<Map<String,String>> data) throws ParseException {
        // 不能出库的数据
        List<Map<String,String>> notOutputData = new ArrayList<>();
        // 遍历data
        for(Map map:data){
            // 检查sid是否存在
            if(speciesMapper.findById((String) map.get("sid"))==null){
                map.put("reason","商品不存在");
                notOutputData.add(map);
            }
            // 检查sid是否存在
            else if(supermarketMapper.findById((String) map.get("suid"))==null){
                map.put("reason","超市不存在");
                notOutputData.add(map);
            }
            // 检查num是否够
            else if(Integer.parseInt((String) map.get("num"))<= cargoMapper.getNotExpireNum((String) map.get("sid"), (String) map.get("suid"))){
                map.put("reason","余量不足");
                notOutputData.add(map);
            }
            // 更改表
            else{
                callOutput(map);
            }

        }
        // 针对不能出库的货物
        if(notOutputData.size()>0){
            notOutput(notOutputData);
        }

    }

    private void notOutput(List<Map<String, String>> list) {
        int insert_num = 0;
        try {
            for(int i=0;i<list.size();i++){
                String sid = list.get(i).get("sid");
                int num = Integer.parseInt(list.get(i).get("num"));
                String suid = list.get(i).get("suid");
                String reason = list.get(i).get("reason");
                NotOutput notOutput = new NotOutput(sid,suid,num,reason);
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

    private void callOutput(Map<String,String> map) throws ParseException {
        // 超市端需要的量
        int needNum = Integer.parseInt((String)map.get("num"));
        // 当前货物的sid
        String sid = map.get("sid");
        // (1) 对species表的操作
        speciesMapper.reduceNum((String) map.get("sid"), needNum);
        // 出库时间
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date outputTime = tFormat.parse(tFormat.format(now));

        // 设计这个while循环的目的：从不同批次的货物中分批出货
        while(needNum>0){
            // 获取：未出库、现在还未过期、但最近要过期的一批货物
            List<Cargo> cargoList = cargoMapper.outputBatch();
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
                saveMapper.updateOutputTime(cargoList.get(i).getCid(), outputTime);
                // (6) 对log表的操作
                Log log = new Log(sid, cid, Id.getRepositoryID(), ceid, map.get("suid"), "output");
                logMapper.addLog(log);

            }

            needNum -= thisNum;
        }
        // (7) 生成订单
        double cost = needNum * 1;
        Order order = new Order((String)map.get("suid"), (String)map.get("rid"), cost);
        orderMapper.insertOrder(order);
    }

    // 清空仓库柜
    boolean clearCell(Cell cell){
        if(cell.getType()=="s" && cell.getRestNum()==3){
            return true;
        }
        if(cell.getType()=="m" && cell.getRestNum()==1){
            return true;
        }
        if(cell.getType()=="l" && cell.getRestNum()==0){
            return true;
        }
        return false;
    }


}
