package com.itheima.mp.controller;


import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.impl.StudentEvaluateService;
import com.itheima.mp.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/studentEvaluate")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudentEvaluateController {
    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    StudentEvaluateService studentEvaluateService;

    @Autowired
    HttpServletRequest servletRequest;

    @PostMapping("/createEvaluate")
    public DataResponse createEvaluate (@RequestBody DataRequest dataRequest) {
        Map<String,Object> payload = getPayload();
        Integer evaluateStudent = (Integer) payload.get("userId");
        Integer evaluatedStudent = dataRequest.getInteger("studentId");
        String text = dataRequest.getString("text");
        boolean like = dataRequest.getBoolean("like");
        return studentEvaluateService.createEvaluate(evaluateStudent,evaluatedStudent,text,like);
    }

    @PostMapping("/deleteEvaluate")
    public DataResponse deleteEvaluate (@RequestBody DataRequest dataRequest) {
        Integer evaluateId = dataRequest.getInteger("evaluateId");
        return studentEvaluateService.deleteEvaluateById(evaluateId);
    }

    @GetMapping("/getEvaluateForMe")
    public DataResponse getEvaluateForMe () {
        Map<String,Object> payload = getPayload();
        Integer userId = (Integer) payload.get("userId");
        return studentEvaluateService.selectEvaluateByEvaluatedStudent(userId);
    }

    @GetMapping("/getEvaluateByMe")
    public DataResponse getEvaluateByMe () {
        Map<String,Object> payload = getPayload();
        Integer userId = (Integer) payload.get("userId");
        return studentEvaluateService.selectEvaluateByEvaluateStudent(userId);
    }
    private Map<String,Object> getPayload () {
        String token = servletRequest.getHeader("Authorization");
        return tokenUtil.parseToken(token);
    }
}
