package com.itheima.mp.controller;


import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.impl.StudentService;
import com.itheima.mp.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/student")
public class StudentController {

    HttpServletRequest request;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    private StudentService studentService;

    @PostMapping("/selectStudentVOList")
    public DataResponse selectStudentVOList() {
        return studentService.selectStudentVOList1();
    }

    @PostMapping("/insertStudent")
    public DataResponse insertStudent(@RequestBody DataRequest dataRequest) {
        return studentService.insertStudent(dataRequest);
    }

    @PostMapping("/updateStudent")
    public DataResponse updateStudent(@RequestBody DataRequest dataRequest) {
        return studentService.updateStudent(dataRequest);
    }

    @PostMapping("/deleteStudent")
    public DataResponse deleteStudent(@RequestBody DataRequest dataRequest) {
        return studentService.deleteStudent(dataRequest);
    }


    @PostMapping("/selectStudent")
    public DataResponse selectStudent(@RequestBody DataRequest dataRequest) {
        return studentService.selectStudent(dataRequest);
    }

    @PostMapping("/selectStudentByUsernameOrName")
    public DataResponse selectStudentVOByUsernameOrName(@RequestBody DataRequest dataRequest) {
        return studentService.selectStudentByNameOrNum(dataRequest.getString("string"));
    }

}