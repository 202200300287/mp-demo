package com.itheima.mp.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.mp.domain.po.Course;
import com.itheima.mp.domain.po.StudentCourse;
import com.itheima.mp.mapper.CourseMapper;
import com.itheima.mp.mapper.StudentCourseMapper;
import com.itheima.mp.mapper.StudentMapper;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.iservice.MailService;
import com.itheima.mp.util.CommomMethod;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class BaseService {
    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired(required = false)
    private MailService mailService;
    public List<StudentCourse> getStudentCourseListByStudentId(Integer studentId){
        QueryWrapper<StudentCourse> studentCourseQueryWrapper=new QueryWrapper<StudentCourse>()
                .select("*")
                .eq("student_id",studentId);

        return studentCourseMapper.selectList(studentCourseQueryWrapper);
    }

    @ApiOperation(value = "用学生id查询课程列表")
    public  List<Course> getCourseListByStudentId(Integer studentId){

        List<StudentCourse> studentCourseList=getStudentCourseListByStudentId(studentId);
        List<Course> courseList = new ArrayList<>();
        for (StudentCourse sc : studentCourseList) {
            QueryWrapper<Course> courseQueryWrapper=new QueryWrapper<Course>().select("*").eq("course_id",sc.getCourseId());
            courseList.add(courseMapper.selectOne(courseQueryWrapper));
        }
        return courseList;
    }

    public DataResponse sendEmail(){
        String to="3192024219@qq.com";
        String title="标题：简单的文本发送测试";
        String content="<html><body><h1>欢迎来到 Spring boot 的世界</h1></body></html>";
        mailService.sendSimpleText(to,title,content);
        return CommomMethod.getReturnMessageOK("成功发送邮件！");
    }
}
