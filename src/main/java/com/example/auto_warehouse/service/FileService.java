package com.example.auto_warehouse.service;

import com.example.auto_warehouse.controller.LoadFileController;
import com.example.auto_warehouse.util.ExcelMap;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(LoadFileController.class);

    public boolean excelToListMap(MultipartFile multipartFile, String type){
        try {
            // 将MultipartFile转为file
            String fileName = multipartFile.getOriginalFilename();
            File file = new File(fileName);
            OutputStream out = null;
            try{
                //获取文件流，以文件流的方式输出到新文件
                //InputStream in = multipartFile.getInputStream();
                out = new FileOutputStream(file);
                byte[] ss = multipartFile.getBytes();
                for(int i = 0; i < ss.length; i++){
                    out.write(ss[i]);
                }
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                if (out != null){
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            FileInputStream fileInputStream = new FileInputStream(file);

            // 创建工作簿对象
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

            // 获取第一个工作表
            XSSFSheet sheet = workbook.getSheetAt(0);

            // 声明变量，用于存储数据
            List<Map<String, String>> dataList = new ArrayList<>();
            NumberFormat nf = NumberFormat.getInstance();

            // 遍历行
            for (Row row : sheet) {
                if(row== sheet.getRow(0)){
                    continue;
                }
                // 声明一行数据的 map
                Map<String, String> rowData = new HashMap<>();

                // 遍历列
                for (Cell cell : row) {
                    // 根据单元格类型获取到单元格的值，并将其转换为字符串类型
                    String value = "";
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            value = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                Date date = cell.getDateCellValue();
                                DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                                value = formater.format(date);
                            }else{
                                value = nf.format(cell.getNumericCellValue());
                                value = value.replace(",","");
                            }
                            break;
                        case BOOLEAN:
                            value = String.valueOf(cell.getBooleanCellValue());
                            break;
                        default:
                            break;
                    }

                    // 将列名和列值添加到行数据的 map 中
                    String key = sheet.getRow(0).getCell(cell.getColumnIndex()).getStringCellValue();
                    rowData.put(key, value);
                }

                // 将行数据添加到数据列表中
                dataList.add(rowData);
            }

            // 关闭工作簿
            workbook.close();

            // 操作完上的文件 需要删除在根目录下生成的文件
//            File f = new File(file.toURI());
//            if (f.delete()){
//                System.out.println("删除成功");
//            }else {
//                System.out.println("删除失败");
//            }

            // 输出结果
            System.out.println(dataList);

            // 写入全局队列
            if(type.equals("input")){
                ExcelMap.input_map_queue.add(dataList);
                return true;
            }else if(type.equals("output")) {
                ExcelMap.output_map_queue.add(dataList);
                return true;
            }else if(type.equals("actual_input")){
                ExcelMap.actual_input_map_queue.add(dataList);
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
