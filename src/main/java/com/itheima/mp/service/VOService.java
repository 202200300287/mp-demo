package com.itheima.mp.service;


import com.itheima.mp.domain.po.*;
import com.itheima.mp.domain.vo.StudentVO;
import com.itheima.mp.domain.vo.TeacherVO;
import com.itheima.mp.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class VOService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StudentBasicMapper studentBasicMapper;
    @Autowired
    private StudentAdvancedMapper studentAdvancedMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    public StudentVO getStudentVO(Student student){
        Integer studentId=student.getStudentId();
        User user=userMapper.selectById(student.getUserId());
        StudentBasic studentBasic=studentBasicMapper.selectById(studentId);
        if(studentBasic==null)studentBasic=new StudentBasic();
        StudentAdvanced studentAdvanced=studentAdvancedMapper.selectById(studentId);
        if(studentAdvanced==null)studentAdvanced=new StudentAdvanced();
        return new StudentVO(studentId,student.getName(),student.getMajor(),student.getGrade(),student.getGpa(),student.getStudentClass(),student.getRankClass(),student.getRankCollege(),
                user.getUsername(),user.getPhoto(),user.getUserType(),studentBasic.getGender(),studentBasic.getBirthday(),studentBasic.getEthnicity(),studentBasic.getBirthplace(),studentBasic.getAddress(),studentBasic.getPhone(),studentBasic.getEmail(),
                studentAdvanced.getHonors(),studentAdvanced.getCompetitions(),studentAdvanced.getDisciplinary(),studentAdvanced.getClubs(),studentAdvanced.getVolunteer(),studentAdvanced.getInternship());
    }

    public StudentVO getStudentVO(Integer studentId){
        Student student=studentMapper.selectById(studentId);
        User user=userMapper.selectById(student.getUserId());
        StudentBasic studentBasic=studentBasicMapper.selectById(studentId);
        if(studentBasic==null)studentBasic=new StudentBasic();
        StudentAdvanced studentAdvanced=studentAdvancedMapper.selectById(studentId);
        if(studentAdvanced==null)studentAdvanced=new StudentAdvanced();
        return new StudentVO(studentId,student.getName(),student.getMajor(),student.getGrade(),student.getGpa(),student.getStudentClass(),student.getRankClass(),student.getRankCollege(),
                user.getUsername(),user.getPhoto(),user.getUserType(),studentBasic.getGender(),studentBasic.getBirthday(),studentBasic.getEthnicity(),studentBasic.getBirthplace(),studentBasic.getAddress(),studentBasic.getPhone(),studentBasic.getEmail(),
                studentAdvanced.getHonors(),studentAdvanced.getCompetitions(),studentAdvanced.getDisciplinary(),studentAdvanced.getClubs(),studentAdvanced.getVolunteer(),studentAdvanced.getInternship());
    }

    public TeacherVO getTeacherVO(Teacher teacher){
        Integer userId=teacher.getUserId();
        User user=userMapper.selectById(userId);
        if(user==null)user=new User();
        return new TeacherVO(teacher.getTeacherId(),teacher.getName(),teacher.getPhone(),teacher.getEmail(),teacher.getGender(),teacher.getPosition(),teacher.getDegree(),teacher.getCollege(),teacher.getResearch(),teacher.getPaper(),teacher.getResume(),
                user.getUsername(),user.getPhoto(),user.getUserType());
    }
    public TeacherVO getTeacherVO(Integer teacherId){
        TeacherVO teacherVO=getTeacherVO(teacherMapper.selectById(teacherId));
        if(teacherVO==null)return new TeacherVO();
        return teacherVO;
    }

}
