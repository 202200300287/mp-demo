package com.itheima.mp.controller;



import com.itheima.mp.payload.request.LoginRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.payload.response.LoginResponse;
import com.itheima.mp.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


   private final UserService userService;


    @RequestMapping("/test")
    String test0(){
        return "666";
    }

    @PostMapping("/login")
    public DataResponse login(@RequestBody LoginRequest loginRequest) {

        return userService.login(loginRequest);
    }




}

