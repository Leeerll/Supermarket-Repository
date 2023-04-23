package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.Cargo;
import com.example.auto_warehouse.bean.NotInput;
import com.example.auto_warehouse.bean.NotOutput;
import com.example.auto_warehouse.mapper.CargoMapper;
import com.example.auto_warehouse.mapper.CargoStatusMapper;
import com.example.auto_warehouse.service.InputService;
import com.example.auto_warehouse.service.OutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

@RestController
@RequestMapping("/cargo")
public class CargoController {
    @Autowired
    private InputService inputService;
    @Autowired
    private CargoMapper cargoMapper;
    @Autowired
    private OutputService outputService;
    @Autowired
    private CargoStatusMapper cargoStatusMapper;

    @PostMapping("/findByStype")
    public List<Map<String,String>> findByStype(@RequestBody Map<String,String> map){
        String stype = map.get("stype");
        List<Cargo> result = cargoMapper.findByStype(stype);
        List<Map<String,String>> list = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for(Cargo c:result){
            Map<String,String> stypemap = new HashMap<>();
            stypemap.put("stype",stype);
            stypemap.put("Sid",c.getSid());
            stypemap.put("cid",String.valueOf(c.getCid()));
            if(c.getInputTime()==null){
                stypemap.put("input_time"," ");
            }else{
                stypemap.put("input_time",simpleDateFormat.format(c.getInputTime()));
            }
            if(c.getOutput_time()==null){
                stypemap.put("output_time"," ");
            }else{
                stypemap.put("output_time",simpleDateFormat.format(c.getOutput_time()));
            }
            stypemap.put("suid",c.getSuid());
            list.add(stypemap);
        }
        return list;
    }

    @PostMapping("/findByCid")
    public List<Cargo> findByCid(@RequestBody Map<String,String> map){
        String cid = map.get("cid");
        int cid_int = Integer.parseInt(cid);
        List<Cargo> result = cargoMapper.finfByCid(cid_int);
        return result;
    }

    @RequestMapping("/show_notInput")
    @ResponseBody
    public List<Map<String,String>> show_notInput(){
        List<Map<String,String>> list = new ArrayList<>();
        List<NotInput> list_notInput = inputService.allNotInput();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        for(NotInput notInput:list_notInput){
            Map<String,String> map = new HashMap<>();
            map.put("id",notInput.getId());
            map.put("name",notInput.getName());
            map.put("type",notInput.getType());
            map.put("num",String.valueOf(notInput.getNum()));
            map.put("production_date", sdf1.format(notInput.getProduction_date()));
            map.put("shelf_life",String.valueOf(notInput.getShelf_life()));
            map.put("suid",notInput.getSuid());
            map.put("reason",notInput.getReason());
            list.add(map);
            cargoStatusMapper.modifyIsReadInput(notInput.getNotInputID());
        }
        return list;
    }
    @RequestMapping("/show_notOutput")
    @ResponseBody
    public List<Map<String,String>> show_notOutput(){
        List<Map<String,String>> list = new ArrayList<>();
        List<NotOutput> list_not = outputService.allNotOutput();
        for(NotOutput notOutput:list_not){
            Map<String,String> map = new HashMap<>();
            map.put("sid",notOutput.getSid());
            map.put("num",String.valueOf(notOutput.getNum()));
            map.put("suid",notOutput.getSuid());
            map.put("reason",notOutput.getReason());
            list.add(map);
            cargoStatusMapper.modifyIsReadOutput(notOutput.getNotOutputID());
        }
        return list;
    }
}
