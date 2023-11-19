package com.itheima.mp.service;


import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.StudentAdvanced;
import com.itheima.mp.domain.po.StudentBasic;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.vo.StudentVO;
import com.itheima.mp.mapper.StudentAdvancedMapper;
import com.itheima.mp.mapper.StudentBasicMapper;
import com.itheima.mp.mapper.StudentMapper;
import com.itheima.mp.mapper.UserMapper;
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

}
