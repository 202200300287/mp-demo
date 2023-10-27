package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.payload.request.LoginRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.payload.response.LoginResponse;
import com.itheima.mp.service.iservice.IUserService;
import com.itheima.mp.util.CommomMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    public DataResponse login(LoginRequest loginRequest){
        DataResponse dataResponse;
        String u=loginRequest.getUsername();
        String p=loginRequest.getPassword();

        QueryWrapper<User> wrapper=new QueryWrapper<User>()
                .select("*")
                .eq("username", u)
                .eq("password", p);
        List<User> userList=userMapper.selectList(wrapper);

        if (userList.isEmpty()) {
            return CommomMethod.getReturnMessageError("不存在该用户");
        } else {
            return CommomMethod.getReturnData(userList);

        }
    }


}
