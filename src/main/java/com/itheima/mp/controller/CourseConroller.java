package com.itheima.mp.controller;


import com.itheima.mp.domain.po.Course;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.mapper.TeacherCourseMapper;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.impl.CourseService;
import com.itheima.mp.service.impl.StudentCourseService;
import com.itheima.mp.service.impl.TeacherCourseService;
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

    @Autowired
    private TeacherCourseService teacherCourseService;

    @PostMapping("/getStudentListByCourseId")
    public DataResponse getStudentListByCourseId(@RequestBody DataRequest dataRequest) {
        Integer courseId=dataRequest.getInteger("courseId");
        return CommomMethod.getReturnData(studentCourseService.getStudentListByCourseId(courseId));
    }

    @PostMapping("/getCourseSelectedByStudentId")
    public List<Course> getCourseSelectByStudentId(@RequestBody DataRequest dataRequest) {
        Integer studentId=dataRequest.getInteger("studentId");
        return studentCourseService.getCourseListByStudentId(studentId);
    }

    @PostMapping("/selectCourseSelectableByTeacher")
    public DataResponse selectCourseSelectableByTeacher(@RequestBody DataRequest dataRequest){
        return courseService.selectCourseSelectableByTeacher(dataRequest);
    }
    @PostMapping("/selectCourseSelectableByStudent")
    public DataResponse selectCourseSelectableByStudent(@RequestBody DataRequest dataRequest){
        return courseService.selectCourseSelectableByStudent(dataRequest);
    }


    @PostMapping("/getCourseListAll")
    public DataResponse getCourseListAll() {
        return courseService.getCourseList();

    }

    @PostMapping("/insertCourse")
    public DataResponse insertCourse(@RequestBody DataRequest dataRequest){
        return courseService.insertCourse(dataRequest);
    }
    @PostMapping("/updateCourse")
    public DataResponse updateCourse(@RequestBody DataRequest dataRequest){
        return courseService.updateCourse(dataRequest);
    }
    @PostMapping("/deleteCourse")
    public DataResponse deleteCourse(@RequestBody DataRequest dataRequest){
        return courseService.deleteCourse(dataRequest);
    }
    @PostMapping("/selectCourse")
    public DataResponse selectCourse(@RequestBody DataRequest dataRequest){
        return courseService.selectCourse(dataRequest);
    }





}