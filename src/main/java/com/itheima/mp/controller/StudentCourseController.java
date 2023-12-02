package com.itheima.mp.controller;

import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.impl.StudentCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/studentCourse")
public class StudentCourseController {
    @Autowired
    private StudentCourseService studentCourseService;

    @PostMapping("/insertStudentCourseByStudent")
    public DataResponse insertStudentCourseByStudent(@RequestBody DataRequest dataRequest) {
        return studentCourseService.insertCourseByStudent(dataRequest);
    }

    @PostMapping("/deleteStudentCourseByStudent")
    public DataResponse deleteStudentCourseByStudent(@RequestBody DataRequest dataRequest) {
        return studentCourseService.deleteStudentCourseByStudent(dataRequest);
    }
}
