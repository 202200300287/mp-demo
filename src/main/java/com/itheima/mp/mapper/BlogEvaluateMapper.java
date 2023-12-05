package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.BlogEvaluate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlogEvaluateMapper extends BaseMapper<BlogEvaluate> {

    @Select("SELECT * FROM blog_evaluate WHERE blog_id = #{blogId}")
    List<BlogEvaluate> selectBlogEvaluateByBlogId (@Param("blogId") Integer blogId);

    @Select("SELECT * FROM blog_evaluate WHERE user_id = #{userId}")
    List<BlogEvaluate> selectEvaluateByUserId (@Param("userId") Integer userId);
}
