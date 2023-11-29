package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TeacherMapper extends BaseMapper<Teacher> {

    @Select("SELECT MAX(teacher_id) FROM teacher")
    Integer findMaxTeacherId();

    //寻找所给id位置，若没有，返回0
    @Select("SELECT COUNT(*) FROM teacher WHERE teacher_id = #{teacherId}")
    int checkTeacherId(Integer teacherId);

    @Select("SELECT * FROM teacher ")
    List<Teacher> selectTeacherList();

    @Select("SELECT * FROM teacher WHERE teacher_id IN (${teacherIdList})")
    List<Teacher> selectByTeacherIdList(@Param("teacherIdList") String teacherIdList);

    @Select("SELECT * FROM teacher WHERE user_id = #{userId}")
    Teacher selectTeacherByUserId(Integer userId);
}
