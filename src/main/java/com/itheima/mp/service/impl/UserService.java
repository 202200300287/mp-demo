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
import com.itheima.mp.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    TokenUtil tokenUtil;


    public DataResponse login(LoginRequest loginRequest){
        DataResponse dataResponse;
        String u=loginRequest.getUsername();
        String p=loginRequest.getPassword();
        QueryWrapper<User> wrapper=new QueryWrapper<User>()
                .select("*")
                .eq("username", u)
                .eq("password", p);
        User user=userMapper.selectOne(wrapper);
        if(user == null) {
            return CommomMethod.getReturnMessageError("用户名或密码错误");
        }
        String token = tokenUtil.getToken(user.getUserId(),user.getUsername(),user.getUserType().getCode());
        return CommomMethod.getReturnData(token);
    }


}
