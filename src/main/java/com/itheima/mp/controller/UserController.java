package com.itheima.mp.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户管理接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {


}