package com.itheima.mp.controller;


import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.vo.StudentVO;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.impl.StudentCourseService;
import com.itheima.mp.service.impl.StudentService;
import com.itheima.mp.util.CommomMethod;
import com.itheima.mp.util.JsonMethod;
import com.itheima.mp.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    HttpServletRequest request;

    @Autowired
    TokenUtil tokenUtil;

    @PostMapping("/insertStudent")
    public DataResponse insertStudent(@RequestBody DataRequest dataRequest){
        return studentService.insertStudent(dataRequest);
    }
    @PostMapping("/updateStudent")
    public DataResponse updateStudent(@RequestBody DataRequest dataRequest){
        return studentService.updateStudent(dataRequest);
    }
    @PostMapping("/deleteStudent")
    public DataResponse deleteStudent(@RequestBody DataRequest dataRequest){
        return studentService.deleteStudent(dataRequest);
    }


    @PostMapping("/selecctStudent")
    public DataResponse selectStudent(@RequestBody DataRequest dataRequest){
        return studentService.selectStudent(dataRequest);
    }
    @PostMapping("/selectStudentByUsernameOrName")
    public DataResponse selectStudentVOByUsernameOrName(@RequestBody DataRequest dataRequest){
        return studentService.selectStudentByNameOrNum(dataRequest.getString("string"));
    }

}