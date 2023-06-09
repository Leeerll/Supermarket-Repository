//package com.example.auto_warehouse.service;
//
//import jakarta.annotation.Resource;
//import org.apache.catalina.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import java.util.UserInfo;
//
//import java.util.List;
//
///*public class CustomUserDetailsService {
//}*/
//@Component
//public class CustomUserDetailsService implements UserDetailsService {
//    @Resource
//    private UserInfoService userInfoService;
//    @Resource
//    private PasswordEncoder passwordEncoder;
//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        /**
//         * 1/通过userName 获取到userInfo信息
//         * 2/通过User（UserDetails）返回details。
//         */
//        //通过userName获取用户信息
//        UserInfo userInfo = userInfoService.getUserInfoByUsername(userName);
//        if(userInfo == null) {
//            throw new UsernameNotFoundException("not found");
//        }
//        //定义权限列表.
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
//        authorities.add(new SimpleGrantedAuthority("ROLE_"+ userInfo.getRole()));
//        User userDetails = new User(userInfo.getUserName(),passwordEncoder.encode(userInfo.getPassword()),authorities);
//        return userDetails;
//    }
//}