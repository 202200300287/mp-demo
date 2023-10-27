package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.mapper.StudentMapper;
import com.itheima.mp.service.iservice.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class StudentService extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;


    public Student getStudentById(Integer studentId){
        QueryWrapper<Student> queryWrapper=new QueryWrapper<Student>()
                .select("*")
                .eq("student_id",studentId);
        Student s=studentMapper.selectOne(queryWrapper);
        return s;
    }
}
