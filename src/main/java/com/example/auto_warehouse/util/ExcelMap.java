package com.example.auto_warehouse.util;

import java.util.*;

// 全局出入库的excel表生成的map
public class ExcelMap {
    // 超市传入一个excel表，就放进队列，处理完一个excel之后，就会删除队列中的这个excel记录
    public static final LinkedList<List<Map<String,String>>> input_map_queue = new LinkedList<>();
    public static final LinkedList<List<Map<String,String>>> output_map_queue = new LinkedList<>();
    public static final LinkedList<List<Map<String,String>>> actual_input_map_queue = new LinkedList<>();

}
