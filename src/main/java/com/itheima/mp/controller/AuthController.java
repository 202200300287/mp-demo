package com.itheima.mp.controller;


import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.request.LoginRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.payload.response.LoginResponse;
import com.itheima.mp.service.AuthService;
import com.itheima.mp.service.BaseService;
import com.itheima.mp.service.impl.UserService;
import com.itheima.mp.service.iservice.MailService;
import com.itheima.mp.util.CommomMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;

    @Autowired
    private BaseService baseService;
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public DataResponse login(@RequestBody LoginRequest loginRequest) {

        return userService.login(loginRequest);
    }


    @PostMapping("/sendEmail")
    public DataResponse sendEmail(@RequestBody DataRequest dataRequest) {
        return authService.sendEmail(dataRequest);
    }

    @PostMapping("/forgetPassword")
    public DataResponse forgetPassword(@RequestBody DataRequest dataRequest) {
        return authService.forgetPassword(dataRequest);
    }


}

