package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.Course;
import com.itheima.mp.domain.vo.TeacherVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    @Select("SELECT MAX(course_id) FROM course")
    Integer findMaxCourseId();

    // 寻找所给id位置，若没有，返回0
    @Select("SELECT COUNT(*) FROM course WHERE course_id = #{courseId}")
    int checkCourseId(Integer courseId);

    @Select("SELECT course_id FROM course")
    List<Integer> getCourseIdAll();

    @Select("select * from course where course_id in (${courseIdList})")
    List<Course> selectCourseListByCourseIdList(String courseIdList);
}
