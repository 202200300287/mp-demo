package com.itheima.mp.controller;


import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.impl.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/selectTeacherVOList")
    public DataResponse selectTeacherVOList(){
        return teacherService.selectTeacherVOList();
    }

    @PostMapping("/insertTeacher")
    public DataResponse insertTeacher(@RequestBody DataRequest dataRequest){
        return teacherService.insertTeacher(dataRequest);
    }

    @PostMapping("/updateStudent")
    public DataResponse updateStudent(@RequestBody DataRequest dataRequest){
        return  teacherService.updateTeacher(dataRequest);
    }

    @PostMapping("/deleteTeacher")
    public  DataResponse deleteTeacher(@RequestBody DataRequest dataRequest){
        return teacherService.deleteTeacher(dataRequest);
    }

    @PostMapping("/selectTeacher")
    public DataResponse selectTeacher(@RequestBody DataRequest dataRequest){
        return teacherService.selectTeacher(dataRequest);
    }

}
