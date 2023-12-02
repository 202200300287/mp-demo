package com.itheima.mp.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.mp.domain.po.*;
import com.itheima.mp.enums.CourseType;
import com.itheima.mp.enums.Grade;
import com.itheima.mp.mapper.CourseMapper;
import com.itheima.mp.mapper.StudentCourseMapper;
import com.itheima.mp.mapper.TeacherCourseMapper;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.util.CommomMethod;
import com.itheima.mp.util.FormatMethod;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

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
    @Autowired
    private TeacherCourseMapper teacherCourseMapper;

    public List<StudentCourse> getStudentCourseListByStudentId(Integer studentId) {
        QueryWrapper<StudentCourse> studentCourseQueryWrapper = new QueryWrapper<StudentCourse>()
                .select("*")
                .eq("student_id", studentId);

        return studentCourseMapper.selectList(studentCourseQueryWrapper);
    }

    @ApiOperation(value = "用学生id查询课程列表")
    public List<Course> getCourseListByStudentId(Integer studentId) {

        List<StudentCourse> studentCourseList = getStudentCourseListByStudentId(studentId);
        List<Course> courseList = new ArrayList<>();
        for (StudentCourse sc : studentCourseList) {
            QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<Course>().select("*").eq("course_id", sc.getCourseId());
            courseList.add(courseMapper.selectOne(courseQueryWrapper));
        }
        return courseList;
    }

    @ApiModelProperty("判断是否已存在所给学号，不存在返回true")
    public boolean judgeNewUsername(String username) {
        List<String> usernames = userMapper.findAllUsername();
        return !usernames.contains(username);
    }

    public boolean judgeNewUserNameExcept(String usernameNew, String usernameOld) {
        List<String> usernameList = userMapper.findAllUsername();
        usernameList.remove(usernameOld);
        return !usernameList.contains(usernameNew);
    }


    public DataResponse judgeStudentDataInsert(User user, Student student, StudentBasic studentBasic) {
        String username = user.getUsername();
        String password = user.getPassword();
        String name = student.getName();
        Grade grade = student.getGrade();
        Integer studentClass = student.getStudentClass();
        String email = studentBasic.getEmail();
        if (password.isBlank()) user.setPassword("123");
        if (grade.getCode() > 4 || grade.getCode() < 1) return CommomMethod.getReturnMessageError("年级错误");
        if (username.isBlank() || name.isBlank() || studentClass < 1 || email.isBlank()) {
            return CommomMethod.getReturnMessageError("用户名，姓名，年级班级，邮箱不可为空");
        }
        if (!judgeNewUsername(username)) return CommomMethod.getReturnMessageError("学号已存在");

        if (!FormatMethod.validateEmail(email)) return CommomMethod.getReturnMessageError("邮箱格式错误");


        // if()
        return CommomMethod.getReturnMessageOK();
    }

    public DataResponse judgeStudentDataUpdate(User user, Student student, StudentBasic studentBasic, String usernameOld) {
        String usernameNew = user.getUsername();
        String password = user.getPassword();
        String name = student.getName();
        Grade grade = student.getGrade();
        Integer studentClass = student.getStudentClass();
        String email = studentBasic.getEmail();
        if (usernameNew.isBlank() || password.isBlank() || name.isBlank() || grade.getCode() < 1 || studentClass < 1 || email.isBlank()) {
            return CommomMethod.getReturnMessageError("用户名，姓名，年级班级，邮箱不可为空");
        }
        if (!FormatMethod.validateEmail(email)) return CommomMethod.getReturnMessageError("邮箱格式错误");
        if (!judgeNewUserNameExcept(usernameNew, usernameOld)) return CommomMethod.getReturnMessageError("学号冲突");
        // if()
        return CommomMethod.getReturnMessageOK();
    }


    public DataResponse judgeTeacherDataInsert(User user, Teacher teacher) {
        String username = user.getUsername();
        String password = user.getPassword();
        String name = teacher.getName();
        String email = teacher.getEmail();
        if (username.isBlank() || password.isBlank() || name.isBlank() || email.isBlank()) {
            return CommomMethod.getReturnMessageError("用户名、密码、姓名、邮箱不可为空");
        }
        if (!judgeNewUsername(username)) return CommomMethod.getReturnMessageError("工号已存在");
        if (!FormatMethod.validateEmail(email)) return CommomMethod.getReturnMessageError("邮箱格式错误");
        return CommomMethod.getReturnMessageOK();
    }

    public DataResponse judgeTeacherDataUpdate(User user, Teacher teacher, String usernameOld) {
        String usernameNew = user.getUsername();
        String password = user.getPassword();
        String name = teacher.getName();
        String email = teacher.getEmail();
        if (usernameNew.isBlank() || password.isBlank() || name.isBlank() || email.isBlank()) {
            return CommomMethod.getReturnMessageError("用户名、密码、姓名、邮箱不可为空");
        }
        if (!judgeNewUserNameExcept(usernameNew, usernameOld))
            return CommomMethod.getReturnMessageError("与其他工号重复");
        if (!FormatMethod.validateEmail(email)) return CommomMethod.getReturnMessageError("邮箱格式错误");
        return CommomMethod.getReturnMessageOK();
    }

    public DataResponse judgeCourseData(Course course) {
        String num = course.getNum();
        String name = course.getName();
        Double credit = course.getCredit();
        Grade grade = course.getGrade();
        CourseType courseType = course.getCourseType();
        if (num.isBlank() || name.isBlank() || credit == 0 || courseType.getCode() < 1) {
            return CommomMethod.getReturnMessageOK("课序号，课程名，学分，年级，课程类型不可为空");
        }
        if (grade.getCode() > 4) return CommomMethod.getReturnMessageError("年级有误");
        if (courseType.getCode() > 4) return CommomMethod.getReturnMessageOK("课程类型错误");
        return CommomMethod.getReturnMessageOK();

    }


}
