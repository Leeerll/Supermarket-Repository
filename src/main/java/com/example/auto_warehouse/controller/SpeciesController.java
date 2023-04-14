package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.Species;
import com.example.auto_warehouse.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/species")
public class SpeciesController {
    @Autowired
    private SpeciesService speciesService;


}
