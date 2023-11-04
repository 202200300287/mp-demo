package com.itheima.mp.controller;


import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.vo.StudentVO;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.impl.StudentCourseService;
import com.itheima.mp.service.impl.StudentService;
import com.itheima.mp.util.CommomMethod;
import com.itheima.mp.util.JsonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentCourseService studentCourseService;


    @PostMapping("/getStudentVOById")
    public DataResponse getStudentVOById(@RequestBody Integer studentId){
        StudentVO studentVO=studentService.getStudentVOById(studentId);
        if(studentVO == null)
            return CommomMethod.getReturnMessageError("不存在该学生");
        return CommomMethod.getReturnData(studentVO);
    }

    @PostMapping("/getStudentVOAll")
    public DataResponse getStudentVOAll(){
        List<StudentVO> studentVOList=studentService.getStudentVOAll();
        if(studentVOList.isEmpty())return CommomMethod.getReturnMessageError("不存在该学生");
        return CommomMethod.getReturnData(studentVOList);
    }

    @PostMapping("/getStudentById")
    public DataResponse getStudentById(@RequestBody Integer studentId){

        Student student=studentService.getStudentById(studentId);
        return CommomMethod.getReturnData(student);
    }

    @PostMapping("/insertStudent")
    public DataResponse editStudent(@RequestBody DataRequest dataRequest){
        return studentService.insertStudent(dataRequest);
    }









}
