package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.vo.StudentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    // 查询最大id
    @Select("SELECT MAX(student_id) FROM student")
    Integer findMaxStudentId();

    @Select("SELECT MAX(student_class) FROM student")
    Integer findMaxStudentClass();

    // 寻找所给id位置，若没有，返回0
    @Select("SELECT COUNT(*) FROM student WHERE student_id = #{studentId}")
    int checkStudentId(Integer studentId);

    @Select("SELECT COUNT(*) FROM student WHERE student_class = #{studentClass}")
    int checkStudentClass(Integer studentClass);

    @Select("SELECT student_id FROM student WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Integer> getStudentIdListLikeName(String name);

    @Select("SELECT student_id FROM student")
    List<Integer> getStudentIdListAll();

    @Select("SELECT * FROM student")
    List<Student> getStudentListAll();

    @Select("SELECT * FROM student WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Student> getStudentListLikeName(String name);

    //@Select("SELECT * FROM student WHERE user_id IN #{userIdList}")
    // List<Student> selectByUserIdList(@Param("userIdList") List<Integer> userIdList);

    @Select("SELECT * FROM student WHERE user_id IN (${userIdList})")
    List<Student> selectByUserIdList(@Param("userIdList") String userIdList);


    @Select("SELECT student_id FROM student WHERE student_class = #{studentClass}")
    List<Integer> findStudentIdListByStudentClass(Integer studentClass);

    @Select("SELECT student_class FROM student")
    List<Integer> findStudentClassListAll();

    @Select("SELECT * FROM student WHERE student_class =#{studentClass}  ORDER BY gpa DESC")
    List<Student> findByStudentClassOrderByGPADESC(Integer studentClass);

    @Select("SELECT * FROM student ORDER BY gpa DESC")
    List<Student> findOrderByGPADESC();

    @Select("SELECT * FROM student WHERE student_id = #{studentIdList}")
    Student selectStudentByUserId(Integer userId);

    @Select("select * from student s, student_basic sb, user u where s.student_id = sb.student_basic_id and s.user_id = u.user_id")
    List<StudentVO> selectStudentVOList();

    @Select("select * from student s, student_basic sb, user u where s.student_id = sb.student_basic_id and s.user_id = u.user_id and student_id in (${studentIdList})")
    List<StudentVO> selectStudentVOListByStudentIdList(@Param("studentIdList")String studentIdList);
}
