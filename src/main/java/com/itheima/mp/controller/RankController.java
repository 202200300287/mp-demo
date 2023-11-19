package com.itheima.mp.controller;


import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.impl.RankService;
import com.itheima.mp.util.CommomMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/score")
public class RankController {
    @Autowired
    private RankService rankService;
    @PostMapping("/updateScoreRankClassAll")
    public DataResponse updateScoreRankClassAll(@RequestBody DataRequest dataRequest){
        rankService.updateScoreRankClassAll();
        return CommomMethod.getReturnMessageOK();
    }
    @PostMapping("/updateScoreRankCollegeAll")
    public DataResponse updateScoreRankCollegeAll(@RequestBody DataRequest dataRequest){
        rankService.updateScoreRankCollegeAll();
        return CommomMethod.getReturnMessageOK();
    }
    @PostMapping("/updateGPARankClassAll")
    public DataResponse updateGPARankClassAll(@RequestBody DataRequest dataRequest){
        rankService.updateGPARankClassAll();
        return CommomMethod.getReturnMessageOK();
    }
    @PostMapping("updateGPARankCollege")
    public DataResponse updateGPARankCollege(@RequestBody DataRequest dataRequest){
        rankService.updateGPARankCollege();
        return CommomMethod.getReturnMessageOK();
    }
}
