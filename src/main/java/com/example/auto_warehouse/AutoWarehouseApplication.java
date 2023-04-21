package com.example.auto_warehouse;

import org.mybatis.spring.annotation.MapperScan;
import com.example.auto_warehouse.service.InputService;
import com.example.auto_warehouse.service.OutputService;
import com.example.auto_warehouse.util.ExcelMap;
import com.example.auto_warehouse.util.Id;
import com.example.auto_warehouse.util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@MapperScan("com.example.auto_warehouse.mapper")
public class AutoWarehouseApplication {
    private static InputService inputService;
    private static OutputService outputService;


    public static void main(String[] args) {
        SpringApplication.run(AutoWarehouseApplication.class, args);

        ApplicationContext applicationContext2 = SpringContextUtil.getApplicationContext();
        outputService = applicationContext2.getBean(OutputService.class);

        // 创建一个入库监听定时器
        Timer timer1 = new Timer();

        // 定义一个计划任务，每秒钟检查一次队列是否为空，并输出结果
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (ExcelMap.output_map_queue.isEmpty()) {
                    //System.out.println("入库队列为空");
                } else {
                    System.out.println("出库队列不为空");
                    Id.setRepositoryID("1");
                    try {
                        outputService.check(ExcelMap.output_map_queue.getFirst());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    ExcelMap.output_map_queue.removeFirst();
                }
            }
        };

        // 每秒钟执行一次计划任务，立即启动
        timer1.scheduleAtFixedRate(task, 0, 1000);




    }

}
