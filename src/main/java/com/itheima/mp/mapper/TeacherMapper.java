package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.Teacher;
import com.itheima.mp.domain.vo.TeacherVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TeacherMapper extends BaseMapper<Teacher> {

    @Select("SELECT MAX(teacher_id) FROM teacher")
    Integer findMaxTeacherId();

    // 寻找所给id位置，若没有，返回0
    @Select("SELECT COUNT(*) FROM teacher WHERE teacher_id = #{teacherId}")
    int checkTeacherId(Integer teacherId);

    @Select("SELECT * FROM teacher ")
    List<Teacher> selectTeacherList();

    @Select("SELECT * FROM teacher WHERE teacher_id IN (${teacherIdList})")
    List<Teacher> selectByTeacherIdList(@Param("teacherIdList") String teacherIdList);

    @Select("SELECT * FROM teacher WHERE user_id = #{userId}")
    Teacher selectTeacherByUserId(Integer userId);

    @Select("select u.*, teacher_id, name, phone, email, gender, position, degree, college from teacher t, user u where t.user_id = u.user_id")
    List<TeacherVO> selectTeacherVOList();

    @Select("select research, paper, resume from teacher where teacher_id = #{teacherId}")
    Map<String, String> getTeacherInfoByTeacherId(Integer teacherId);
}
