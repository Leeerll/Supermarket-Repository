package com.example.auto_warehouse.service;

import com.example.auto_warehouse.bean.Supermarket;
import com.example.auto_warehouse.bean.User;
import com.example.auto_warehouse.mapper.UserMapper;
import com.example.auto_warehouse.util.Id;
import com.example.auto_warehouse.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findById(String uid){
        return userMapper.findById(uid);
    }

    public List<User> allUser(){
        return userMapper.allUser();
    }

    //登录
    public JsonResult<User> login(String userid, String password){
        // 检查该用户是否存在
        User user = findById(userid);
        if(user != null){
            // 用户存在 匹配密码
            // 相等
            if(user.getPassword().equals(password)){
                Id.setRepositoryID(userid);
                return new JsonResult<>(user,"登录成功!");
            }else {
                //密码不相等
                return new JsonResult<>("0","密码错误!");
            }
        }else{
            // 用户不存在
            return new JsonResult<>("0","账号错误，用户不存在!");
        }
    }

    // 修改用户信息
    public JsonResult<User> modify(User user){
        int flag = 0;
        String userid = user.getUid();
        //根据是否为空值判断 哪些数据需要修改
        if(user.getPassword() != null){
            flag = userMapper.modifyPassword(userid,user.getPassword());
        }
        if(flag == 1){
            // 修改成功，返回User和状态码
            // 查询到uid -> user
            user = this.findById(userid);
            return new JsonResult<>(user,"修改成功!");
        }else {
            //修改失败
            return new JsonResult<>("0","修改失败!");
        }
    }

    //添加用户
    public JsonResult<User> addSupermarket(String suid,String suregion,String rid,String password,String bankCardNumber){
        int num = 0;
        //if((suid!=null) && (password=null)){
            num = userMapper.addSupermarket(suid,suregion,rid,password,bankCardNumber);
        //}
        if(num==0){
            return new JsonResult<>("0","添加失败！");
        }else{
            //此处前端连接需判断num是否为传过来的添加用户数
            return new JsonResult<>(String.valueOf(num),"添加成功！");
        }
    }
    public boolean checkPassword(String userid, String password){
        // 检查该用户是否存在
        User user = findById(userid);
        if(user != null){
            // 用户存在 匹配密码
            // 相等
            if(user.getPassword().equals(password)){
                return true;
            }else {
                //密码不相等
                return false;
            }
        }else{
            // 用户不存在
            return false;
        }
    }
}
