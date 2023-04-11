package com.example.auto_warehouse.mapper;

import com.example.auto_warehouse.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User findById(String uid); //根据id查询某个用户，返回user实体类
    List<User> allUser();
    //添加
    int addUser(User user);
    //修改
    int modifyName(String uid,String name);
    int modifyPassword(String uid,String password);
}
