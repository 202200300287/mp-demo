package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Course;
import com.itheima.mp.domain.po.StudentAdvanced;
import com.itheima.mp.mapper.CourseMapper;
import com.itheima.mp.mapper.StudentAdvancedMapper;
import com.itheima.mp.service.iservice.ICourseService;
import com.itheima.mp.service.iservice.IStudentAdvancedService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class StudentAdvancedService extends ServiceImpl<StudentAdvancedMapper, StudentAdvanced> implements IStudentAdvancedService {
}
