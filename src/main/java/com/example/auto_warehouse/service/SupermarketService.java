package com.example.auto_warehouse.service;

import com.example.auto_warehouse.bean.Supermarket;
import com.example.auto_warehouse.bean.User;
import com.example.auto_warehouse.mapper.SupermarketMapper;
import com.example.auto_warehouse.mapper.UserMapper;
import com.example.auto_warehouse.util.Id;
import com.example.auto_warehouse.util.JsonResult;
import com.example.auto_warehouse.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupermarketService {
    @Autowired
    private SupermarketMapper SupermarketMapper;

    public Supermarket findById(String uid){
        return SupermarketMapper.findById(uid);
    }
//    public JsonResult<Supermarket> login(String userid, String password){
    public JsonResult login(String userid, String password){
        // 检查该用户是否存在
        Supermarket supermarket = findById(userid);
        if(supermarket != null){
            //用户存在 匹配密码
            // 相等
            if(supermarket.getPassword().equals(password)){
                String token = JwtUtil.createToken(supermarket);

                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("supermarket",supermarket);
                dataMap.put("token",token);

                Id.setRepositoryID(userid);
                return new JsonResult<>(dataMap,"登录成功!");

            }else {
                //密码不相等
                return new JsonResult<>("0","密码错误!");
            }
        }else{
            // 用户不存在
            return new JsonResult<>("0","账号错误，用户不存在!");
        }
    }
}
