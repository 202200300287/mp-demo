package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.Blog;
import com.itheima.mp.domain.po.BlogTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    @Select("SELECT * FROM blog WHERE user_id = #{userId}")
    List<Blog> selectBlogsByUserId (@Param("userId") Integer userId);
    @Select("SELECT blog_id , user_id , title , praise , create_time , update_time FROM blog WHERE user_id = #{userId}")
    List<BlogTag> selectBlogTagByUserId (@Param("userId") Integer userId);

    @Select("SELECT blog_id , user_id , title , praise , create_time , update_time FROM blog")
    List<BlogTag> selectALLBlogTag ();
}
