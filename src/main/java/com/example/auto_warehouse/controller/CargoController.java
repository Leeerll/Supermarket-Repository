package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.Cargo;
import com.example.auto_warehouse.bean.NotInput;
import com.example.auto_warehouse.bean.NotOutput;
import com.example.auto_warehouse.bean.Save;
import com.example.auto_warehouse.mapper.CargoMapper;
import com.example.auto_warehouse.service.InputService;
import com.example.auto_warehouse.service.OutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
            System.out.println("shelf_life:"+notInput.getShelf_life());
            map.put("shelf_life",String.valueOf(notInput.getShelf_life()));
            map.put("suid",notInput.getSuid());
            map.put("reason",notInput.getReason());
            list.add(map);
        }
        return list;
    }

    @PostMapping("/findByStype")
    public List<Map<String,String>> findByStype(@RequestBody Map<String,String> map){
        String stype = map.get("stype");
        List<Save> result = cargoMapper.findByStype(stype);
        List<Map<String,String>> list = new ArrayList<>();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        for(Save c:result){
            Map<String,String> stypemap = new HashMap<>();

            stypemap.put("stype",stype);
            stypemap.put("sname",c.getSname());
            stypemap.put("ceid",c.getCeid());
            stypemap.put("sid",c.getSid());
            stypemap.put("cid",String.valueOf(c.getCid()));
            if(c.getInputTime()==null){
                stypemap.put("input_time"," ");
            }else{
                stypemap.put("input_time",simpleDateFormat.format(c.getInputTime()));
            }
            if(c.getOutputTime()==null){
                stypemap.put("output_time"," ");
            }else{
                stypemap.put("output_time",simpleDateFormat.format(c.getOutputTime()));
            }
            stypemap.put("suid",c.getSuid());

            list.add(stypemap);
        }
        return list;
    }

    @PostMapping("/findByCid")
    public List<Map<String,String>> findByCid(@RequestBody Map<String,String> map){
        String cid = map.get("cid");
        int cid_int = Integer.parseInt(cid);
        List<Save> result = cargoMapper.findByCid(cid_int);
        List<Map<String,String>> list = new ArrayList<>();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        for(Save c:result){
            Map<String,String> stypemap = new HashMap<>();
            stypemap.put("sname",c.getSname());
            stypemap.put("sid",c.getSid());
            stypemap.put("ceid",c.getCeid());
            stypemap.put("stype",c.getStype());
            stypemap.put("cid",String.valueOf(c.getCid()));

            if(c.getInputTime()==null){
                stypemap.put("input_time"," ");
            }else{
                stypemap.put("input_time",simpleDateFormat.format(c.getInputTime()));
            }
            if(c.getOutputTime()==null){
                stypemap.put("output_time"," ");
            }else{
                stypemap.put("output_time",simpleDateFormat.format(c.getOutputTime()));
            }
            stypemap.put("suid",c.getSuid());
            list.add(stypemap);
        }
        return list;
    }

    @RequestMapping("/findBySid")
    public List<Map<String,String>> findBySid(@RequestBody Map<String,String> map){
        String sid = map.get("sid");
        List<Save> result = cargoMapper.findBySid(sid);

        List<Map<String,String>> list = new ArrayList<>();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        for(Save c:result){
            Map<String,String> stypemap = new HashMap<>();
            stypemap.put("sname",c.getSname());
            stypemap.put("stype",c.getStype());
            stypemap.put("sid",sid);
            stypemap.put("ceid",c.getCeid());
            stypemap.put("cid",String.valueOf(c.getCid()));
            if(c.getInputTime()==null){
                stypemap.put("input_time"," ");
            }else{
                stypemap.put("input_time",simpleDateFormat.format(c.getInputTime()));
            }
            if(c.getOutputTime()==null){
                stypemap.put("output_time"," ");
            }else{
                stypemap.put("output_time",simpleDateFormat.format(c.getOutputTime()));
            }
            stypemap.put("suid",c.getSuid());
            list.add(stypemap);
        }
        return list;
    }

    @PostMapping("/findByCeid")
    public List<Map<String,String>> findByCeid(@RequestBody Map<String,String> map){
        String ceid = map.get("ceid");
        List<Save> result = cargoMapper.findByCeid(ceid);
        List<Map<String,String>> list = new ArrayList<>();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        for(Save c:result){
            Map<String,String> stypemap = new HashMap<>();
            stypemap.put("sname",c.getSname());
            stypemap.put("sid",c.getSid());
            stypemap.put("stype",c.getStype());
            stypemap.put("ceid",c.getCeid());
            stypemap.put("cid",String.valueOf(c.getCid()));

            if(c.getInputTime()==null){
                stypemap.put("input_time"," ");
            }else{
                stypemap.put("input_time",simpleDateFormat.format(c.getInputTime()));
            }
            if(c.getOutputTime()==null){
                stypemap.put("output_time"," ");
            }else{
                stypemap.put("output_time",simpleDateFormat.format(c.getOutputTime()));
            }

            stypemap.put("suid",c.getSuid());
            list.add(stypemap);
        }
        return list;
    }

    @PostMapping("/findByStypeAndInputTime")
    public List<Map<String,String>> findByStypeAndInputTime(@RequestBody Map<String,String> map) throws ParseException {
        String stype = map.get("stype");
        String date = map.get("inputTime");

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date inputTime = ft.parse(date);
        System.out.println("输出格式"+inputTime);


        List<Save> result = cargoMapper.findByStypeAndInputTime(stype,inputTime);
        List<Map<String,String>> list = new ArrayList<>();

        for(Save c:result){
            Map<String,String> stypemap = new HashMap<>();
            stypemap.put("sname",c.getSname());
            stypemap.put("sid",c.getSid());
            stypemap.put("cid",String.valueOf(c.getCid()));
            stypemap.put("stype",stype);
            stypemap.put("ceid",c.getCeid());
            if(c.getInputTime()==null){
                stypemap.put("input_time"," ");
            }else{
                stypemap.put("input_time",ft.format(c.getInputTime()));
            }
            if(c.getOutputTime()==null){
                stypemap.put("output_time"," ");
            }else{
                stypemap.put("output_time",ft.format(c.getOutputTime()));
            }
            stypemap.put("suid",c.getSuid());
            list.add(stypemap);
        }
        return list;
    }

    @PostMapping("/findByStypeAndOutputTime")
    public List<Map<String,String>> findByStypeAndOutputTime(@RequestBody Map<String,String> map) throws ParseException {
        String stype = map.get("stype");
        String date = map.get("outputTime");

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date outputTime = ft.parse(date);

        List<Save> result = cargoMapper.findByStypeAndOutputTime(stype,outputTime);
        List<Map<String,String>> list = new ArrayList<>();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        for(Save c:result){
            Map<String,String> stypemap = new HashMap<>();
            stypemap.put("sname",c.getSname());
            stypemap.put("sid",c.getSid());
            stypemap.put("stype",stype);
            stypemap.put("ceid",c.getCeid());
            stypemap.put("cid",String.valueOf(c.getCid()));
            if(c.getInputTime()==null){
                stypemap.put("input_time"," ");
            }else{
                stypemap.put("input_time",simpleDateFormat.format(c.getInputTime()));
            }
            if(c.getOutputTime()==null){
                stypemap.put("output_time"," ");
            }else{
                stypemap.put("output_time",simpleDateFormat.format(c.getOutputTime()));
            }
            stypemap.put("suid",c.getSuid());
            list.add(stypemap);
        }
        return list;
    }

    @PostMapping("/findBySidAndInputTime")
    public List<Map<String,String>> findBySidAndInputTime(@RequestBody Map<String,String> map) throws ParseException {
        String sid = map.get("sid");
        String date = map.get("inputTime");

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date inputTime = ft.parse(date);


        List<Save> result = cargoMapper.findBySidAndInputTime(sid,inputTime);
        List<Map<String,String>> list = new ArrayList<>();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        for(Save c:result){
            Map<String,String> stypemap = new HashMap<>();
            stypemap.put("sname",c.getSname());
            stypemap.put("sid",sid);
            stypemap.put("ceid",c.getCeid());
            stypemap.put("stype",c.getStype());
            stypemap.put("cid",String.valueOf(c.getCid()));
            stypemap.put("input_time",simpleDateFormat.format(c.getInputTime()));

            if(c.getOutputTime()==null){
                stypemap.put("output_time"," ");
            }else{
                stypemap.put("output_time",simpleDateFormat.format(c.getOutputTime()));
            }
            stypemap.put("suid",c.getSuid());
            list.add(stypemap);
        }
        return list;
    }

    @PostMapping("/findBySidAndOutputTime")
    public List<Map<String,String>> findBySidAndOutputTime(@RequestBody Map<String,String> map) throws ParseException {
        String sid = map.get("sid");
        String date = map.get("outputTime");

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date outputTime = ft.parse(date);


        List<Save> result = cargoMapper.findBySidAndOutputTime(sid,outputTime);
        List<Map<String,String>> list = new ArrayList<>();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        for(Save c:result){
            Map<String,String> stypemap = new HashMap<>();
            stypemap.put("sname",c.getSname());
            stypemap.put("sid",sid);
            stypemap.put("ceid",c.getCeid());
            stypemap.put("cid",String.valueOf(c.getCid()));
            stypemap.put("stype",c.getStype());
            stypemap.put("output_time",simpleDateFormat.format(c.getOutputTime()));

            if(c.getInputTime()==null){
                stypemap.put("input_time"," ");
            }else{
                stypemap.put("input_time",simpleDateFormat.format(c.getInputTime()));
            }
            stypemap.put("suid",c.getSuid());
            list.add(stypemap);
        }
        return list;
    }

    @PostMapping("/findBySidAndSuid")
    public List<Map<String,String>> findBySidAndSuid(@RequestBody Map<String,String> map) {
        String sid = map.get("sid");
        String suid = map.get("suid");

        List<Save> result = cargoMapper.findBySidAndSuid(sid,suid);
        List<Map<String,String>> list = new ArrayList<>();
        String pattern = "yyyy-MM-dd-";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        for(Save c:result){
            Map<String,String> stypemap = new HashMap<>();
            stypemap.put("sname",c.getSname());
            stypemap.put("sid",sid);
            stypemap.put("stype",c.getStype());
            stypemap.put("ceid",c.getCeid());
            stypemap.put("cid",String.valueOf(c.getCid()));
            if(c.getOutputTime()==null){
                stypemap.put("output_time"," ");
            }else{
                stypemap.put("output_time",simpleDateFormat.format(c.getOutputTime()));
            }
            if(c.getInputTime()==null){
                stypemap.put("input_time"," ");
            }else{
                stypemap.put("input_time",simpleDateFormat.format(c.getInputTime()));
            }
            stypemap.put("suid",suid);
            list.add(stypemap);
        }
        return list;
    }

    @PostMapping("/findByStypeAndSuid")
    public List<Map<String,String>> findByStypeAndSuid(@RequestBody Map<String,String> map){
        String stype = map.get("stype");
        String suid = map.get("suid");

        List<Save> result = cargoMapper.findByStypeAndSuid(stype,suid);
        List<Map<String,String>> list = new ArrayList<>();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        for(Save c:result){
            Map<String,String> stypemap = new HashMap<>();
            stypemap.put("sname",c.getSname());
            stypemap.put("sid",c.getSid());
            stypemap.put("stype",stype);
            stypemap.put("ceid",c.getCeid());
            stypemap.put("cid",String.valueOf(c.getCid()));
            if(c.getInputTime()==null){
                stypemap.put("input_time"," ");
            }else{
                stypemap.put("input_time",simpleDateFormat.format(c.getInputTime()));
            }
            if(c.getOutputTime()==null){
                stypemap.put("output_time"," ");
            }else{
                stypemap.put("output_time",simpleDateFormat.format(c.getOutputTime()));
            }
            stypemap.put("suid",c.getSuid());
            list.add(stypemap);
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
        }
        return list;
    }
}