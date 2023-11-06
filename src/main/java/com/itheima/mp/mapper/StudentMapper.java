package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    //查询最大id
    @Select("SELECT MAX(student_id) FROM student")
    Integer findMaxStudentId();

    //寻找所给id位置，若没有，返回0
    @Select("SELECT COUNT(*) FROM student WHERE student_id = #{studentId}")
    int checkStudentId(Integer studentId);
}
