package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.StudentEvaluate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentEvaluateMapper extends BaseMapper<StudentEvaluate> {
    @Select("SELECT * FROM student_evaluate WHERE evaluated_student = #{studentId}")
    List<StudentEvaluate> selectEvaluateByEvaluatedStudent (@Param("studentId") Integer studentId);

    @Select("SELECT * FROM student_evaluate WHERE evaluate_student = #{studentId}")
    List<StudentEvaluate> selectEvaluateByEvaluateStudent (@Param("studentId") Integer studentId);
}
