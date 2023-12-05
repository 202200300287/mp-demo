package com.itheima.mp.controller;

import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.impl.BlogEvaluateService;
import com.itheima.mp.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/blogEvaluate")
public class BlogEvaluateController {
    @Autowired
    BlogEvaluateService service;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    HttpServletRequest servletRequest;

    /**
     * 传入blogId获取该blog下的评论
     * @param dataRequest
     * @return
     */
    @PostMapping("/getEvaluateByBlogId")
    public DataResponse getEvaluateByBlogId (@RequestBody DataRequest dataRequest) {
        Integer blogId = dataRequest.getInteger("blogId");
        return service.getEvaluateByBlogId(blogId);
    }

    /**
     * 解析token获取当前用户发表的评论
     * @return
     */
    @GetMapping("/getUserEvaluate")
    public DataResponse getUserEvaluate () {
        Map<String,Object> payload = getPayLoad();
        Integer userId = (Integer) payload.get("userId");
        return service.getEvaluateByUserId(userId);
    }

    /**
     * 传入blogEvaluateId和修改的text修改该评论
     * @param dataRequest
     * @return
     */
    @PostMapping("/editEvaluate")
    public DataResponse editEvaluate (@RequestBody DataRequest dataRequest) {
        Integer blogEvaluateId = dataRequest.getInteger("evaluateId");
        String text = dataRequest.getString("text");
        return service.editEvaluate(blogEvaluateId,text);
    }

    /**
     * 传入评论内容text和目标blog创建新的评论
     * @param dataRequest
     * @return
     */
    @PostMapping("/createEvaluate")
    public DataResponse createEvaluate (@RequestBody DataRequest dataRequest) {
        String text = dataRequest.getString("text");
        Integer blogId = dataRequest.getInteger("blogId");
        Map<String,Object> payload = getPayLoad();
        Integer userId = (Integer) payload.get("userId");
        return service.createEvaluate(blogId,userId,text);
    }

    /**
     * 传入evaluateId删除对应评论
     * @param dataRequest
     * @return
     */
    @PostMapping("deleteEvaluateById")
    public DataResponse deleteEvaluateById (@RequestBody DataRequest dataRequest) {
        Integer evaluateId = dataRequest.getInteger("evaluateId");
        return service.deleteEvaluate(evaluateId);
    }

    private Map<String,Object> getPayLoad () {
        String token = servletRequest.getHeader("Authorization");
        return tokenUtil.parseToken(token);
    }
}
