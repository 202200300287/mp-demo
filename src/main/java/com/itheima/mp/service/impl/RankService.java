package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.StudentCourse;
import com.itheima.mp.mapper.*;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.BaseService;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class RankService {
    @Autowired
    private StudentCourseMapper studentCourseMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private TeacherCourseMapper teacherCourseMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private BaseService baseService;
    @Autowired
    private UserMapper userMapper;
//    public DataResponse updateRank(DataRequest dataRequest){
//        List<Integer> courseIdList
//    }


    public void updateScoreRankClassOfSingleCourseAndSingleStudentClass(Integer studentClass,Integer courseId){
        if(courseMapper.checkCourseId(courseId)==0)return;
        List<Integer> studentIdList=studentMapper.findStudentIdListByStudentClass(studentClass);
        if(studentIdList.isEmpty())return;
        String studentIdListStr= StringUtils.join(studentIdList,",");
        List<StudentCourse> studentCourseList=studentCourseMapper.findByStudentIdListOrderByScoreDESC(studentIdListStr,courseId);
        if(studentCourseList.isEmpty())return;;
        for(int i=0;i<studentCourseList.size();i++){
            StudentCourse studentCourse=studentCourseList.get(i);
            studentCourse.setRankClass(i+1);
            studentCourseMapper.updateById(studentCourse);
        }
    }

    public void updateScoreRankClassOfAllCourseAndSingleStudentClass(Integer studentClass){
        List<Integer> courseIdList = courseMapper.getCourseIdAll();
        if(courseIdList.isEmpty())return;
        for(Integer courseId:courseIdList){
            updateScoreRankClassOfSingleCourseAndSingleStudentClass(studentClass,courseId);
        }
    }

    public void updateScoreRankClassAll(){
        List<Integer> studentClassList=studentMapper.findStudentClassListAll();
        if(studentClassList.isEmpty())return;
        for(Integer studentClass:studentClassList){
            updateScoreRankClassOfAllCourseAndSingleStudentClass(studentClass);
        }
    }

    public void updateScoreRankCollegeOfSingleCourse(Integer courseId){
        if(courseMapper.checkCourseId(courseId)==0)return;
        List<Integer> studentIdList=studentMapper.getStudentIdListAll();
        if(studentIdList.isEmpty())return;
        String studentIdListStr= StringUtils.join(studentIdList,",");
        List<StudentCourse> studentCourseList=studentCourseMapper.findByStudentIdListOrderByScoreDESC(studentIdListStr,courseId);
        if(studentCourseList.isEmpty())return;;
        for(int i=0;i<studentCourseList.size();i++){
            StudentCourse studentCourse=studentCourseList.get(i);
            studentCourse.setRankCollege(i+1);
            studentCourseMapper.updateById(studentCourse);
        }
    }

    public void updateScoreRankCollegeAll(){
        List<Integer> courseIdList = courseMapper.getCourseIdAll();
        if(courseIdList.isEmpty())return;
        for(Integer courseId:courseIdList){
            updateScoreRankCollegeOfSingleCourse(courseId);
        }
    }

    public void updateGPARankClassOfSingleStudentClass(Integer studentClass){
        List<Student> studentList=studentMapper.findByStudentClassOrderByGPADESC(studentClass);
        if(studentList.isEmpty())return;
        for(int i=0;i<studentList.size();i++){
            Student student=studentList.get(i);
            student.setRankClass(i+1);
            studentMapper.updateById(student);
        }
    }
    public void updateGPARankClassAll(){
        List<Integer> studentClassList=studentMapper.findStudentClassListAll();
        if(studentClassList.isEmpty())return;
        for(Integer studentClass:studentClassList){
            updateGPARankClassOfSingleStudentClass(studentClass);
        }
    }

    public void updateGPARankCollege(){
        List<Student> studentList=studentMapper.findOrderByGPADESC();
        if(studentList.isEmpty())return;
        for(int i=0;i<studentList.size();i++){
            Student student=studentList.get(i);
            student.setRankCollege(i+1);
            studentMapper.updateById(student);
        }
    }


}
