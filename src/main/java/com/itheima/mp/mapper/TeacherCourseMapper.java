package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.TeacherCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherCourseMapper extends BaseMapper<TeacherCourse> {
    @Select("SELECT * FROM teacher_course WHERE teacher_id = #{teacherId}")
    List<TeacherCourse> findByTeacherId(@Param("teacherId") int teacherId);
    @Select("SELECT * FROM teacher_course WHERE course_id = #{courseId}")
    List<TeacherCourse> findByCourseId(@Param("courseId") int courseId);
    @Select("SELECT * FROM teacher_course WHERE student_class = #{studentClass}")
    List<TeacherCourse> findByStudentClass(@Param("studentClass") int studentClass);

    @Select("SELECT DISTINCT teacher_id FROM teacher_course WHERE course_id = #{courseId} AND student_class = #{studentClass}")
    List<Integer> findTeacherIdByCourseIdAndStudentClass(@Param("courseId") int courseId, @Param("studentClass") int studentClass);
    @Select("SELECT DISTINCT course_id FROM teacher_course WHERE teacher_id = #{teacherId}")
    List<Integer> findCourseIdByTeacherId(@Param("teacherId") int teacherId);


}
