package com.itheima.mp.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.BlogEvaluate;
import com.itheima.mp.mapper.BlogEvaluateMapper;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.iservice.IBlogEvaluateService;
import com.itheima.mp.util.CommomMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class BlogEvaluateService extends ServiceImpl<BlogEvaluateMapper, BlogEvaluate> implements IBlogEvaluateService {
    @Autowired
    BlogEvaluateMapper mapper;

    public DataResponse getEvaluateByBlogId (Integer blogId) {
        return CommomMethod.getReturnData(mapper.selectBlogEvaluateByBlogId(blogId));
    }

    public DataResponse getEvaluateByUserId (Integer userId) {
        return CommomMethod.getReturnData(mapper.selectEvaluateByUserId(userId));
    }

    public DataResponse editEvaluate (Integer evaluateId,String text) {
        if (evaluateId == null || text == null) {
            return CommomMethod.getReturnMessageError("参数异常");
        }
        BlogEvaluate blogEvaluate = mapper.selectById(evaluateId);
        blogEvaluate.setText(text);
        mapper.updateById(blogEvaluate);
        return CommomMethod.getReturnMessageOK("修改成功");
    }

    public DataResponse createEvaluate (Integer blogId,Integer userId,String text) {
        if (text == null) {
            return CommomMethod.getReturnMessageError("参数异常");
        }
        BlogEvaluate evaluate = new BlogEvaluate();
        evaluate.setBlogId(blogId);
        evaluate.setUserId(userId);
        evaluate.setText(text);
        evaluate.setCreateTime(LocalDateTime.now());
        mapper.insert(evaluate);
        return CommomMethod.getReturnMessageOK("创建成功");
    }

    public DataResponse deleteEvaluate (Integer evaluateId) {
        if (evaluateId == null) {
            return CommomMethod.getReturnMessageError("参数异常");
        }
        mapper.deleteById(evaluateId);
        return CommomMethod.getReturnMessageOK("删除成功");
    }
}
