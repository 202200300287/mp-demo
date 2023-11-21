package com.itheima.mp.controller;

import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.impl.StudentAdvancedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/studentAdvanced")
public class StudentAdvancedController {
    @Autowired
    private StudentAdvancedService studentAdvancedService;

    @PostMapping("/insertStudentAdvanced")
    public DataResponse insertStudentAdvanced(@RequestBody DataRequest dataRequest){
        return studentAdvancedService.insertStudentAdvanced(dataRequest);
    }
    @PostMapping("/updateStudentAdvanced")
    public DataResponse updateStudentAdvanced(@RequestBody DataRequest dataRequest){
        return studentAdvancedService.updateStudentAdvanced(dataRequest);
    }
    @PostMapping("/deleteStudentAdvanced")
    public DataResponse deleteStudentAdvanced(@RequestBody DataRequest dataRequest){
        return studentAdvancedService.deleteStudentAdvanced(dataRequest);
    }
    @PostMapping("/selectStudentAdvanceVOAll")
    public DataResponse selectStudentAdvanceVOAll(){
        return studentAdvancedService.selectStudentAdvanceVOAll();
    }
    @PostMapping("/selectStudentAdvancedVOListByStudentId")
    public DataResponse selectStudentAdvancedVOListByStudentId(@RequestBody DataRequest dataRequest){
        return studentAdvancedService.selectStudentAdvancedVOListByStudentId(dataRequest);
    }
    @PostMapping("/selectStudentSingleAdvancedVOListByStudentIdAndAdvancedType")
    public DataResponse selectStudentSingleAdvancedVOListByStudentIdAndAdvancedType(@RequestBody DataRequest dataRequest){
        return studentAdvancedService.selectStudentSingleAdvancedVOSingleListByStudentIdAndAdvancedType(dataRequest);
    }

    @PostMapping("/selectStudentSingleAdvancedVOListByAdvancedType")
    public DataResponse selectStudentSingleAdvancedVOListByAdvancedType(@RequestBody DataRequest dataRequest){
        return studentAdvancedService.selectStudentSingleAdvancedVOListByAdvancedType(dataRequest);
    }
}
