package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.service.FileService;
import com.example.auto_warehouse.service.FileService;
import com.example.auto_warehouse.util.JsonResult;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class LoadFileController {
    @Autowired
    private FileService fileService;
    private static final Logger logger = LoggerFactory.getLogger(LoadFileController.class);


    @PostMapping("/importFileInput")
    public JsonResult<String> importData_input(MultipartFile file) throws IOException {
//        MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
//        if (file == null) {
//            return new JsonResult<>("0","文件为空，上传失败");
//        }

        //读取excel，形成list<map>
        boolean result = fileService.excelToListMap(file,"input");
        if(result){
            logger.info("file is saved into queue");
            return new JsonResult <>("1","文件上传成功");
        }else{
            return new JsonResult <>("0","文件上传失败");
        }
    }




    @PostMapping("/importFileOutput")
    public JsonResult<String> importData_output(MultipartFile file) throws IOException {
        //MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
        if (file == null) {
            return new JsonResult<>("0","文件为空，上传失败");
        }

        //读取excel，形成list<map>
        boolean result = fileService.excelToListMap(file,"output");
        if(result){
            logger.info("file is saved into queue");
            return new JsonResult <>("1","文件上传成功");
        }else{
            return new JsonResult <>("0","文件上传失败");
        }
    }

    @PostMapping("/importFileActualInput")
    public JsonResult<String> importData_actualInput(MultipartFile file) throws IOException {
        //MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
        if (file == null) {
            return new JsonResult<>("0","文件为空，上传失败");
        }

        //读取excel，形成list<map>
        boolean result = fileService.excelToListMap(file,"actual_input");
        if(result){
            logger.info("file is saved into queue");
            return new JsonResult <>("1","文件上传成功");
        }else{
            return new JsonResult <>("0","文件上传失败");
        }
    }

}
