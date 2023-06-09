package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.Income;
import com.example.auto_warehouse.bean.Repository;
import com.example.auto_warehouse.mapper.IncomeMapper;
import com.example.auto_warehouse.mapper.RepositoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/repository")
public class RepositoryController {

    @Autowired
    private RepositoryMapper repositoryMapper;
    @Autowired
    private IncomeMapper incomeMapper;

    @RequestMapping("/rate")
    @ResponseBody
    public List<Map<String, String>> rate(){
        List<Repository> repositories = repositoryMapper.rate();

        List<Map<String, String>> list = new ArrayList<>();

        for(Repository r:repositories){
            Map<String, String> stypemap = new HashMap<>();
            stypemap.put("rid",r.getRid());
            double temp=1-((double)r.getRestNum()/(double)r.getTotalNum());
            temp = temp*100;
            stypemap.put("rate", String.valueOf(temp));
            double mh_rate = (r.getMachineHealth()/100.0)*100;
            stypemap.put("machine_health_rate", String.valueOf(mh_rate));
            list.add(stypemap);
        }
        return list;
    }

    @PostMapping("/selectRateByRid")
    public Map<String,String> selectRateByRid(@RequestBody Map<String,String> map){
        String rid = map.get("rid");
        List<Repository> result = repositoryMapper.selectRateByRid(rid);
        if(result.isEmpty()){
            Map<String,String> stypemap = new HashMap<>();
            stypemap.put("machine_health_rate","0");
            stypemap.put("takerate","0");
            return stypemap;
        }
        double temp = 1-((double)result.get(0).getRestNum()/(double)result.get(0).getTotalNum());
        temp = temp*100;
        String rate = String.valueOf(temp);
        Map<String,String> stypemap = new HashMap<>();
        double mh_rate = (result.get(0).getMachineHealth()/100.0)*100;
        stypemap.put("machine_health_rate", String.valueOf(mh_rate));
        stypemap.put("takerate",rate);
        return stypemap;
    }

    @RequestMapping("/getMonthIncome")
    @ResponseBody
    public List<Map<String, String>> getMonthIncome(@RequestBody Map<String,String> map){
        String repositoryId = map.get("rid");
        int year = Calendar.getInstance().get(Calendar.YEAR);
        List<Income> incomes = repositoryMapper.selectMonthIncome(repositoryId,year*100);
        List<Map<String, String>> list = new ArrayList<>();

        for(Income income:incomes){
            Map<String, String> stypemap = new HashMap<>();
            stypemap.put("yearMonth",String.valueOf(income.getYearMonth()));
            stypemap.put("income",String.valueOf(income.getIncome()));
            list.add(stypemap);
        }
        return list;
    }

}
