package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.Repository;
import com.example.auto_warehouse.mapper.RepositoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/repository")
public class RepositoryController {

    @Autowired
    private RepositoryMapper repositoryMapper;

    @RequestMapping("/rate")
    @ResponseBody
    public List<Map<String, String>> rate(){
        List<Repository> repositories = repositoryMapper.rate();

        List<Map<String, String>> list = new ArrayList<>();

        for(Repository r:repositories){
            Map<String, String> stypemap = new HashMap<>();
            stypemap.put("rid",r.getRid());
            int temp=1-(r.getRestNum()/r.getTotalNum());
            stypemap.put("rate",Integer.toString(temp));
            list.add(stypemap);
        }
        return list;
    }
}
