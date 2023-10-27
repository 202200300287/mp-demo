package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Course;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.StudentCourse;
import com.itheima.mp.mapper.CourseMapper;
import com.itheima.mp.mapper.StudentCourseMapper;
import com.itheima.mp.mapper.StudentMapper;
import com.itheima.mp.service.iservice.IStudentCourseService;
import com.itheima.mp.service.iservice.IStudentService;
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
public class StudentCourseService extends ServiceImpl<StudentCourseMapper, StudentCourse> implements IStudentCourseService {

    @Autowired
    private StudentCourseMapper studentCourseMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CourseMapper courseMapper;




    @ApiOperation(value = "用课程id查询学生列表")
    public List<Student> getStudentListByCourseId(Integer courseId){

        QueryWrapper<StudentCourse> studentCourseQueryWrapper=new QueryWrapper<StudentCourse>()
                .select("student_id")
                .eq("course_id",courseId);

        List<StudentCourse> studentCourseList=studentCourseMapper.selectList(studentCourseQueryWrapper);
        List<Student> studentList = new ArrayList<>();
        for (StudentCourse sc : studentCourseList) {
            studentList.add(studentMapper.selectById(sc.getStudentId()));
        }
        return studentList;
    }

    @ApiOperation(value = "用学生id查询课程列表")
    public  List<Course> getCourseListByStudentId(Integer studnetId){
        QueryWrapper<StudentCourse> studentCourseQueryWrapper=new QueryWrapper<StudentCourse>()
                .select("*")
                .eq("student_id",studnetId);

        List<StudentCourse> studentCourseList=studentCourseMapper.selectList(studentCourseQueryWrapper);
        List<Course> courseList = new ArrayList<>();
        for (StudentCourse sc : studentCourseList) {
            QueryWrapper<Course> courseQueryWrapper=new QueryWrapper<Course>().select("*").eq("course_id",sc.getCourseId());
            courseList.add(courseMapper.selectOne(courseQueryWrapper));
        }
        return courseList;
    }
}
