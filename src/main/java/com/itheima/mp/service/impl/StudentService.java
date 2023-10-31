package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.*;
import com.itheima.mp.domain.vo.CourseVO;
import com.itheima.mp.domain.vo.StudentVO;
import com.itheima.mp.mapper.StudentAdvancedMapper;
import com.itheima.mp.mapper.StudentBasicMapper;
import com.itheima.mp.mapper.StudentCourseMapper;
import com.itheima.mp.mapper.StudentMapper;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.BaseService;
import com.itheima.mp.service.iservice.IStudentService;
import com.itheima.mp.util.CommomMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class StudentService extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentBasicMapper studentBasicMapper;

    @Autowired
    private StudentAdvancedMapper studentAdvancedMapper;

    @Autowired
    private BaseService baseService;


    public Student getStudentById(Integer studentId){
        QueryWrapper<Student> queryWrapper=new QueryWrapper<Student>()
                .select("*")
                .eq("student_id",studentId);
        Student s=studentMapper.selectOne(queryWrapper);
        return s;
    }

    public StudentVO getStudentVOById(Integer studentId){
        Student student=getStudentById(studentId);

        if(student==null){
            return null;
        }

        StudentBasic studentBasic=studentBasicMapper.selectById(studentId);
        StudentAdvanced studentAdvanced=studentAdvancedMapper.selectById(studentId);
        List<Course> courseList=baseService.getCourseListByStudentId(studentId);
        List<StudentCourse> studentCourseList=baseService.getStudentCourseListByStudentId(studentId);
        List<CourseVO> courseVOList=new ArrayList<CourseVO>();
        for(int i=0;i<courseList.size();i++){
            Course course=courseList.get(i);
            StudentCourse studentCourse=studentCourseList.get(i);
            CourseVO courseVO=new CourseVO(course.getCourseId(),course.getNum(),course.getName(),course.getCredit(),studentCourse.getScore(),studentCourse.getRankClass(),studentCourse.getRankCollege());
            courseVOList.add(courseVO);
        }
        return new StudentVO(studentId,student.getUserId(),student.getMajor(),student.getGpa(),student.getRankClass(),student.getRankCollege(),studentBasic,studentAdvanced,courseVOList);
    }

    public List<StudentVO> getStudentVOAll(){
        QueryWrapper<Student> studentQueryWrapper=new QueryWrapper<Student>()
                .select("*");
        List<Student> studentList=studentMapper.selectList(studentQueryWrapper);

        if(studentList.isEmpty()){
            return null;
        }

        List<StudentVO> studentVOList=new ArrayList<StudentVO>();
        for (Student student : studentList) {
            studentVOList.add(getStudentVOById(student.getStudentId()));
        }
        return studentVOList;
    }



}
