package com.example.auto_warehouse.util;

import java.util.List;
import java.util.Map;
import java.util.Queue;

// 全局出入库的excel表生成的map
public class ExcelMap {
    // 超市传入一个excel表，就放进队列，处理完一个excel之后，就会删除队列中的这个excel记录
    public static Queue<List<Map<String,String>>> input_map_queue;
    public static Queue<List<Map<String,String>>> output_map_queue;

}
