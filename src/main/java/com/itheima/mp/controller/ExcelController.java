package com.itheima.mp.controller;


import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    private ExcelService excelService;

    @PostMapping("/getStudentVOListExcel")
    public ResponseEntity<StreamingResponseBody> getStudentVOListExcl(@RequestBody DataRequest dataRequest){
        return excelService.getStudentVOListExcl(dataRequest);
    }
    @PostMapping("/getTeacherVOListExcel")
    public ResponseEntity<StreamingResponseBody> getTeacherVOListExcl(@RequestBody DataRequest dataRequest){
        return excelService.getTeacherVOListExcl(dataRequest);
    }
    @PostMapping("/getCourseListExcel")
    public ResponseEntity<StreamingResponseBody> getCourseListExcl(@RequestBody DataRequest dataRequest){
        return excelService.getCourseListExcl(dataRequest);
    }
}
