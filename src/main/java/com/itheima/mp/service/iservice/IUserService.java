package com.itheima.mp.service.iservice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.payload.request.LoginRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.payload.response.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface IUserService extends IService<User> {

     DataResponse login(LoginRequest loginRequest);
}
