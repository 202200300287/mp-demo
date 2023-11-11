package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    @Select("SELECT MAX(course_id) FROM course")
    Integer findMaxCourseId();

    //寻找所给id位置，若没有，返回0
    @Select("SELECT COUNT(*) FROM course WHERE course_id = #{courseId}")
    int checkCourseId(Integer courseId);
}
