package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Blog;
import com.itheima.mp.domain.po.BlogTag;
import com.itheima.mp.mapper.BlogMapper;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.service.iservice.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author huaisui
 */

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class BlogService extends ServiceImpl<BlogMapper, Blog> implements IBlogService {
    @Autowired
    BlogMapper blogMapper;

    public List<BlogTag> getBlogTag(Integer userId) {
        return blogMapper.selectBlogTagByUserId(userId);
    }

    public List<Blog> getBlogs(Integer userId) {
        return blogMapper.selectBlogsByUserId(userId);
    }

    public Blog getBlog(Integer blogId) {
        return blogMapper.selectById(blogId);
    }

    public List<BlogTag> getAllBlogTag() {
        return blogMapper.selectALLBlogTag();
    }

    public String createBlog (DataRequest dataRequest,Integer userId) {
        String text = dataRequest.getString("text");
        LocalDateTime createTime = LocalDateTime.now();
        String title = dataRequest.getString("title");
        if (text == null || title == null) return "内容或标题为空";
        Blog newBlog = new Blog();
        newBlog.setText(text);
        newBlog.setTitle(title);
        newBlog.setCreateTime(createTime);
        newBlog.setUpdateTime(createTime);
        newBlog.setPraise(0);
        newBlog.setUserId(userId);
        blogMapper.insert(newBlog);
        return "创建成功";
    }
    
    public String saveBlog (DataRequest dataRequest) {
        Integer blogId = dataRequest.getInteger("blogId");
        String title = dataRequest.getString("title");
        String text = dataRequest.getString("text");
        if (text == null && title != null) {
            editTitle(title,blogId);
            return "标题修改成功";
        }else if (text != null && title == null) {
            editText(text,blogId);
            return "内容修改成功";
        } else if (text == null) {
            return "错误提交";
        }else {
            editTitle(title,blogId);
            editText(title,blogId);
            return "标题与内容修改成功";
        }
    }
    
    private void editTitle (String title,Integer blogId) {
        Blog blog = blogMapper.selectById(blogId);
        LocalDateTime updateTime = LocalDateTime.now();
        blog.setTitle(title);
        blog.setUpdateTime(updateTime);
    }
    
    private void editText (String text,Integer blogId) {
        Blog blog = blogMapper.selectById(blogId);
        LocalDateTime updateTime = LocalDateTime.now();
        blog.setText(text);
        blog.setUpdateTime(updateTime);
    }

}
