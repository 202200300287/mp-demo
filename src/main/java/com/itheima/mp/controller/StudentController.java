package com.itheima.mp.controller;


import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.vo.StudentVO;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.impl.StudentCourseService;
import com.itheima.mp.service.impl.StudentService;
import com.itheima.mp.util.CommomMethod;
import com.itheima.mp.util.JsonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentCourseService studentCourseService;

/*
    @PostMapping("/getStudentVOById")
    public DataResponse getStudentVOById(@RequestBody Integer studentId){
        Student student=studentService.getById(studentId);

        String basicInfoString = JsonMethod.removeBackslash(student.getBasicInfo());
        System.out.println(basicInfoString);

        //Gson gson=new Gson();
        //BasicInfo basicInfo=gson.fromJson(basicInfoString,BasicInfo.class);

        StudentVO studentVO=new StudentVO(student.getStudentId(),student.getUserId(),student.getMajor(), basicInfoString,studentCourseService.getCourseListByStudentId(student.getStudentId()) );


        return CommomMethod.getReturnData(studentVO);
    }

 */



}
