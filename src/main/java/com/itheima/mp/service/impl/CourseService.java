package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Course;
import com.itheima.mp.mapper.CourseMapper;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.iservice.ICourseService;
import com.itheima.mp.util.CommomMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class CourseService extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Autowired
    private CourseMapper courseMapper;

    public DataResponse getCourseList(){
        QueryWrapper<Course> courseQueryWrapper=new QueryWrapper<Course>()
                .select("*");

        List<Course> courseList=courseMapper.selectList(courseQueryWrapper);
        if(courseList.isEmpty())
            return CommomMethod.getReturnMessageError("列表中没有课程");

        return CommomMethod.getReturnData(courseList,"获取所有课程数据");
    }
}

