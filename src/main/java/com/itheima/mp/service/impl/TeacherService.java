package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.Teacher;
import com.itheima.mp.mapper.StudentMapper;
import com.itheima.mp.mapper.TeacherMapper;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.iservice.IStudentService;
import com.itheima.mp.service.iservice.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class TeacherService extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private UserMapper userMapper;

    public Integer getNewTeacherId(){
        return teacherMapper.findMaxTeacherId()+1;
    }

    public Integer getNewUserId(){
        return userMapper.findMaxUserId()+1;
    }
}
