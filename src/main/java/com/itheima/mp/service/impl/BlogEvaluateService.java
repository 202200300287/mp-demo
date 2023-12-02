package com.itheima.mp.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.BlogEvaluate;
import com.itheima.mp.mapper.BlogEvaluateMapper;
import com.itheima.mp.service.iservice.IBlogEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class BlogEvaluateService extends ServiceImpl<BlogEvaluateMapper, BlogEvaluate> implements IBlogEvaluateService {
    @Autowired
    BlogEvaluateMapper mapper;
}
