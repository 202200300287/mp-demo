package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.Blog;
import com.itheima.mp.domain.po.BlogTag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    @Select("SELECT blog_id , blog.user_id , title , praise , create_time , update_time, text, name 'author' FROM blog, student WHERE blog.user_id = student.user_id and blog_id = #{ee}")
    Blog selectBlogByBlogId(Integer blogId);

    @Delete("DELETE FROM blog WHERE blog_id = #{blogId}")
    Integer deleteBlogByBlogId(Integer blogId);

    @Select("select  * from blog where blog_id = #{blogId}")
    Blog selectBlogById(Integer blogId);

    @Select("SELECT blog_id , blog.user_id , title , praise , create_time , update_time, left(text, 100) 'digest', name 'author' FROM blog, student WHERE blog.user_id = student.user_id and student.user_id = #{userId}")
    List<BlogTag> selectBlogTagByUserId (@Param("userId") Integer userId);

    @Select("SELECT blog_id , blog.user_id , title , praise , create_time , update_time, left(text, 100) 'digest', s.name 'author' FROM blog, student s where blog.user_id = s.user_id")
    List<BlogTag> selectALLBlogTag ();

    @Select("SELECT blog_id , user_id , title , praise , create_time , update_time, left(text, 100) 'digest' FROM blog WHERE user_id = #{userId} AND create_time LIKE CONCAT(#{date}, '%')")
    List<BlogTag> selectBlogTagByUserIdDate (@Param("userId") Integer userId,@Param("date") String date);

    @Update("update blog set praise = praise + 1 where blog_id = #{blogId}")
    Integer praiseBlog(Integer blogId);

    // 根据blogId更新博客
    @Update("update blog set title = #{title}, text = #{text}, update_time = #{updateTime} where blog_id = #{blogId}")
    Integer updateBlogByBlogId(Blog blog);
}
