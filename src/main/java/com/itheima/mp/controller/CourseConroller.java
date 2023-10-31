package com.itheima.mp.controller;


import com.itheima.mp.domain.po.Course;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.impl.CourseService;
import com.itheima.mp.service.impl.StudentCourseService;
import com.itheima.mp.util.CommomMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/course")
public class CourseConroller {

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/getStudentListByCourseId")
    public DataResponse getStudentListByCourseId(@RequestBody Integer courseId) {
        return CommomMethod.getReturnData(studentCourseService.getStudentListByCourseId(courseId));

    }

    @PostMapping("/getCourseListByStudentId")
    public List<Course> getCourseListByStudentId(@RequestBody Integer studentId) {
        return studentCourseService.getCourseListByStudentId(studentId);

    }

    @PostMapping("/getCourseListAll")
    public DataResponse getCourseListAll() {
        return courseService.getCourseList();

    }


}