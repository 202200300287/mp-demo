package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {
    @Select("SELECT COUNT(*) FROM student_course WHERE student_id = #{studentId} AND course_id = #{courseId}")
    Integer getCountByStudentIdAndCourseId(Integer studentId, Integer courseId);

    @Select("SELECT MAX(student_course_id) FROM student_course")
    Integer findMaxStudentCourseId();

    @Select("SELECT * FROM student_course WHERE student_id = #{studentId} AND course_id = #{courseId}")
    StudentCourse findByStudentIdAndCourseId(Integer studentId, Integer courseId);

    @Select("SELECT * FROM student_course WHERE  course_id = #{courseId}")
    List<StudentCourse> findByCourseId(Integer courseId);

    @Select("SELECT * FROM student_course WHERE student_id IN (${studentIdList}) AND course_id = #{courseId} ORDER BY score DESC")
    List<StudentCourse> findByStudentIdListOrderByScoreDESC(@Param("studentIdList") String studentIdList, Integer courseId);
}
