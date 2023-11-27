package com.itheima.mp.controller;


import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.impl.BlogService;
import com.itheima.mp.util.CommomMethod;
import com.itheima.mp.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    BlogService blogService;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    HttpServletRequest servletRequest;

    @GetMapping("/getBlogTagByUserId")
    public DataResponse getBlogTagByUserId () {
        String token = servletRequest.getHeader("Authorization");
        Map<String,Object> payLoad = tokenUtil.parseToken(token);
        Integer userId = (Integer) payLoad.get("userId");
        return CommomMethod.getReturnData(blogService.getBlogTag(userId));
    }

    @GetMapping("/getAllBlogTag")
    public DataResponse getAllBlogTag () {
        String token = servletRequest.getHeader("Authorization");
        Map<String,Object> payLoad = tokenUtil.parseToken(token);
        Integer role = (Integer)payLoad.get("role");
        if(role == 1) {
            return CommomMethod.getReturnData(blogService.getAllBlogTag());
        }else{
            return CommomMethod.getReturnMessageError("无权限");
        }
    }

    @PostMapping("/getBlogByBlogId")
    public DataResponse getBlogByBlogId (@RequestBody DataRequest dataRequest) {
        Integer blogId = dataRequest.getInteger("blogId");
        return CommomMethod.getReturnData(blogService.getBlog(blogId));
    }

    @PostMapping("/createBlog")
    public DataResponse createBlog (@RequestBody DataRequest dataRequest) {
        Map<String,Object> payload = getPayLoad();
        return CommomMethod.getReturnMessageOK(blogService.createBlog(dataRequest,(Integer) payload.get("userId")));
    }

    @PostMapping("/saveBlog")
    public DataResponse saveBlog (@RequestBody DataRequest dataRequest) {
        return CommomMethod.getReturnMessageOK(blogService.saveBlog(dataRequest));
    }

    private Map<String,Object> getPayLoad () {
        String token = servletRequest.getHeader("Authorization");
        return tokenUtil.parseToken(token);
    }
}
