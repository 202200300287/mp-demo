package com.itheima.mp.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.mp.domain.po.*;
import com.itheima.mp.enums.Grade;
import com.itheima.mp.mapper.CourseMapper;
import com.itheima.mp.mapper.StudentCourseMapper;
import com.itheima.mp.mapper.StudentMapper;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.iservice.MailService;
import com.itheima.mp.util.CommomMethod;
import com.itheima.mp.util.FormatMethod;
import io.swagger.annotations.ApiModelProperty;
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

    @Autowired
    private UserMapper userMapper;

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
        String to="2776959538@qq.com";
        String title="标题：简单的文本发送测试";
        String content="<html><body><h1>欢迎来到 Spring boot 的世界</h1></body></html>";
        mailService.sendSimpleText(to,title,content);
        return CommomMethod.getReturnMessageOK("成功发送邮件！");
    }

    @ApiModelProperty("判断是否已存在所给学号，不存在返回true")
    public boolean judgeNewUsername(String username) {
        List<String> usernames = userMapper.findAllUsername();
        return !usernames.contains(username);
    }

    public DataResponse judgeStudentData(User user, Student student, StudentBasic studentBasic){
        String username = user.getUsername();
        String password = user.getPassword();
        String name=student.getName();
        Grade grade =student.getGrade();
        Integer studentClass=student.getStudentClass();
        String email=studentBasic.getEmail();
        if(username.isBlank()||password.isBlank()||name.isBlank()||grade.getCode()<1||studentClass<1|| email.isBlank()){
            return CommomMethod.getReturnMessageError("用户名，密码，姓名，年级班级，邮箱不可为空");
        }
        if(!judgeNewUsername(username))return CommomMethod.getReturnMessageError("学号已存在");

        if(!FormatMethod.validateEmail(email))return CommomMethod.getReturnMessageError("邮箱格式错误");


        //if()
        return CommomMethod.getReturnMessageOK();
    }

    public DataResponse judgeTeacherData(User user,Teacher teacher){
        String username=user.getUsername();
        String password=user.getPassword();
        String name=teacher.getName();
        String email=teacher.getEmail();
        if(username.isBlank()||password.isBlank()||name.isBlank()||email.isBlank()){
            return CommomMethod.getReturnMessageError("用户名、密码、姓名、邮箱不可为空");
        }
        if(!judgeNewUsername(username))return CommomMethod.getReturnMessageError("学号已存在");
        if(!FormatMethod.validateEmail(email))return CommomMethod.getReturnMessageError("邮箱格式错误");
        return CommomMethod.getReturnMessageOK();
    }


}
