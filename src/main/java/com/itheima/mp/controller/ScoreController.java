package com.itheima.mp.controller;


import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.impl.ScoreService;
import com.itheima.mp.util.CommomMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;



    @PostMapping("/getCourseVOListByStudentId")
    public DataResponse getCourseVOListByStudentId(@RequestBody DataRequest dataRequest){
        return scoreService.getCourseVOListByStudentId(dataRequest.getInteger("studentId"));
    }
    @PostMapping("/updateScore")
    public DataResponse updateScore(@RequestBody DataRequest dataRequest){
        return scoreService.updateScore(dataRequest);
    }
    @PostMapping("/updateScoreOfStudentList")
    public DataResponse updateScoreOfStudentList(@RequestBody DataRequest dataRequest){
        return scoreService.updateScoreOfStudentList(dataRequest);
    }
    @PostMapping("/updateScoreStatus")
    public DataResponse updateScoreStatus(@RequestBody DataRequest dataRequest){
        return scoreService.updateScoreStatus(dataRequest);
    }
    @PostMapping("/updateScoreStatusOfStudentList")
    public DataResponse updateScoreStatusOfStudentList(@RequestBody DataRequest dataRequest){
        return scoreService.updateScoreStatusOfStudentList(dataRequest);
    }
    @PostMapping("/selectAllCourseVOListUnmarked")
    public DataResponse selectAllCourseVOListUnmarked(@RequestBody DataRequest dataRequest){
        return scoreService.selectAllCourseVOListUnmarked(dataRequest);
    }
    @PostMapping("/selectAllCourseVOListMarked")
    public DataResponse selectAllCourseVOListMarked(@RequestBody DataRequest dataRequest){
        return scoreService.selectAllCourseVOListMarked(dataRequest);
    }
    @PostMapping("/selectAllCourseVOListVisible")
    public DataResponse selectAllCourseVOListVisible(@RequestBody DataRequest dataRequest){
        return scoreService.selectAllCourseVOListVisible(dataRequest);
    }
    @PostMapping("/selectAllCourseVOListByCourseId")
    public DataResponse selectAllCourseVOListByCourseId(@RequestBody DataRequest dataRequest){
        return scoreService.selectAllCourseVOListByCourseId(dataRequest);
    }
    @PostMapping("/updateGPAAll")
    public DataResponse updateGPAAll(@RequestBody DataRequest dataRequest){
        scoreService.updateGPAAll();
        return CommomMethod.getReturnMessageOK();
    }
    @PostMapping("/updateGPAByStudentId")
    public DataResponse updateGPAByStudentId(@RequestBody DataRequest dataRequest){
        scoreService.updateGPAByStudentId(dataRequest.getInteger("studentId"));
        return CommomMethod.getReturnMessageOK();
    }





}
