package com.itheima.mp.controller;


import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.impl.ScoreService;
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
    @PostMapping("/selectAllScoreVOListUnmarked")
    public DataResponse selectAllScoreVOListUnmarked(@RequestBody DataRequest dataRequest){
        return scoreService.selectAllScoreVOListUnmarked(dataRequest);
    }
    @PostMapping("/selectAllScoreVOListMarked")
    public DataResponse selectAllScoreVOListMarked(@RequestBody DataRequest dataRequest){
        return scoreService.selectAllScoreVOListMarked(dataRequest);
    }
    @PostMapping("/selectAllScoreVOListVisible")
    public DataResponse selectAllScoreVOListVisible(@RequestBody DataRequest dataRequest){
        return scoreService.selectAllScoreVOListVisible(dataRequest);
    }
    @PostMapping("/selectAllScoreVOListByCourseId")
    public DataResponse selectAllScoreVOListByCourseId(@RequestBody DataRequest dataRequest){
        return scoreService.selectAllScoreVOListByCourseId(dataRequest);
    }
    @PostMapping("/updateGPAByStudentId")
    public DataResponse updateGPAByStudentId(@RequestBody DataRequest dataRequest){
        return scoreService.updateGPAByStudentId(dataRequest);
    }



}
