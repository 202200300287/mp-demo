package com.itheima.mp.controller;

import com.itheima.mp.mapper.TeacherCourseMapper;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.impl.TeacherCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/teacherCourse")
public class TeacherCourseController {
    @Autowired
    private TeacherCourseService teacherCourseService;


    @PostMapping("/selectCourseByTeacher")
    public DataResponse selectCourseByTeacher(@RequestBody DataRequest dataRequest){
        return teacherCourseService.selectCourseByTeacher(dataRequest);
    }

    @PostMapping("/insertTeacherCourse")
    public DataResponse insertTeacherCourse(@RequestBody DataRequest dataRequest){
        return teacherCourseService.insertTeacherCourse(dataRequest);
    }

    @PostMapping("/deleteTeacherCourse")
    public DataResponse deleteTeacherCourse(@RequestBody DataRequest dataRequest){
        return teacherCourseService.deleteTeacherCourse(dataRequest);
    }
}
