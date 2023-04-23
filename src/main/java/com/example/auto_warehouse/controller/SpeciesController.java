package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.Species;
import com.example.auto_warehouse.mapper.SpeciesMapper;
import com.example.auto_warehouse.mapper.UserMapper;
import com.example.auto_warehouse.service.SpeciesService;
import com.example.auto_warehouse.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/species")
public class SpeciesController {

    @Autowired
    private SpeciesMapper speciesMapper;

    @Autowired
    private SpeciesService speciesService;

    @RequestMapping("findByType")
    public List<Species> findByType(String type){
        List<Species> result = speciesMapper.findByType(type);
        return result;
    }

    @RequestMapping("findById")
    public List<Species> findById(String sid){
        List<Species> result = speciesMapper.findById(sid);
        return result;
    }


}
