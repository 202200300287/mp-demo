package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.StudentAdvanced;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentAdvancedMapper extends BaseMapper<StudentAdvanced> {

    @Select("SELECT * FROM student_advanced")
    List<StudentAdvanced> findAll();
    @Select("SELECT * FROM student_advanced ORDER BY student_id,advanced_type,update_time")
    List<StudentAdvanced> findAllOrderByStudentIdAndAdvancedType();


    @Select("SELECT MAX(student_advanced_id) FROM student_advanced")
    Integer findMaxStudentAdvancedId();
    @Select("SELECT COUNT(*) FROM student_advanced WHERE student_advanced_id = #{studentAdvancedId}")
    int checkStudentAdvancedId(Integer studentAdvancedId);


    @Select("SELECT * FROM student_advanced WHERE student_id=#{studentId} ORDER BY update_time")
    List<StudentAdvanced> getListByStudentId(Integer studentId);

    @Select("SELECT * FROM student_advanced WHERE student_id=#{studentId} ORDER BY advanced_type,update_time")
    List<StudentAdvanced> getListByStudentIdOrderByAdvancedType(Integer studentId);

    @Select("SELECT * FROM student_advanced WHERE advanced_type=#{AdvancedType} ORDER BY student_id,update_time")
    List<StudentAdvanced> getListByAdvancedTypeOrderByStudentId(Integer AdvancedType);

    @Select("SELECT * FROM student_advanced WHERE student_id=#{studentId} AND advanced_type=#{AdvanceType} ORDER BY update_time")
    List<StudentAdvanced> getListByStudentIdAndAdvanceType(Integer studentId,Integer AdvanceType);
}
