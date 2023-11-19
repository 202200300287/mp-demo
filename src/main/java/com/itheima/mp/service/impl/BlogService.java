package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Blog;
import com.itheima.mp.domain.po.Course;
import com.itheima.mp.mapper.BlogMapper;
import com.itheima.mp.mapper.CourseMapper;
import com.itheima.mp.service.iservice.IBlogService;
import com.itheima.mp.service.iservice.ICourseService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class BlogService extends ServiceImpl<BlogMapper, Blog> implements IBlogService {
}
