package com.example.auto_warehouse.service;

import com.example.auto_warehouse.controller.LoadFileController;
import com.example.auto_warehouse.util.ExcelMap;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(LoadFileController.class);

    public boolean excelToListMap(MultipartFile file, String type){
        try {
            FileInputStream fileInputStream = new FileInputStream((File) file);

            // 创建工作簿对象
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

            // 获取第一个工作表
            XSSFSheet sheet = workbook.getSheetAt(0);

            // 声明变量，用于存储数据
            List<Map<String, String>> dataList = new ArrayList<>();

            // 遍历行
            for (Row row : sheet) {
                // 声明一行数据的 map
                Map<String, String> rowData = new HashMap<>();

                // 遍历列
                for (Cell cell : row) {
                    // 根据单元格类型获取到单元格的值，并将其转换为字符串类型
                    String value = cell.getStringCellValue();

                    // 将列名和列值添加到行数据的 map 中
                    String key = sheet.getRow(0).getCell(cell.getColumnIndex()).getStringCellValue();
                    rowData.put(key, value);
                }

                // 将行数据添加到数据列表中
                dataList.add(rowData);
            }

            // 关闭工作簿
            workbook.close();

            // 输出结果
            System.out.println(dataList);

            // 写入全局队列
            if(type.equals("input")){
                ExcelMap.input_map_queue.add(dataList);
                return true;
            }else if(type.equals("output")){
                ExcelMap.output_map_queue.add(dataList);
                return true;
            }else{
                logger.warn("excelToListMap()'s parameter type is wrong");
                return false;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
