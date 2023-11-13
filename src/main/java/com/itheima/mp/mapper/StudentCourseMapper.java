package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {
    @Select("SELECT COUNT(*) FROM student_course WHERE student_id = #{studentId} AND course_id = #{courseId}")
    Integer getCountByStudentIdAndCourseId(Integer studentId,Integer courseId);

    @Select("SELECT MAX(student_course_id) FROM student_course")
    Integer findMaxStudentCourseId();
}
