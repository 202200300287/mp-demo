package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.TeacherCourse;
import com.itheima.mp.mapper.TeacherCourseMapper;
import com.itheima.mp.service.iservice.ITeacherCourseService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class TeacherCourseService extends ServiceImpl<TeacherCourseMapper, TeacherCourse> implements ITeacherCourseService {
    private TeacherCourseMapper teacherCourseMapper;


}
