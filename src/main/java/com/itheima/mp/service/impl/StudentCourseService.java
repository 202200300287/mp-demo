package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Course;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.StudentCourse;
import com.itheima.mp.enums.Grade;
import com.itheima.mp.enums.ScoreStatus;
import com.itheima.mp.mapper.CourseMapper;
import com.itheima.mp.mapper.StudentCourseMapper;
import com.itheima.mp.mapper.StudentMapper;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.iservice.IStudentCourseService;
import com.itheima.mp.util.CommomMethod;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class StudentCourseService extends ServiceImpl<StudentCourseMapper, StudentCourse> implements IStudentCourseService {

    @Autowired
    private StudentCourseMapper studentCourseMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CourseMapper courseMapper;


    @ApiOperation(value = "用课程id查询学生列表")
    public List<Student> getStudentListByCourseId(Integer courseId) {

        QueryWrapper<StudentCourse> studentCourseQueryWrapper = new QueryWrapper<StudentCourse>()
                .select("student_id")
                .eq("course_id", courseId);

        List<StudentCourse> studentCourseList = studentCourseMapper.selectList(studentCourseQueryWrapper);

        List<Student> studentList = new ArrayList<>();
        for (StudentCourse sc : studentCourseList) {
            studentList.add(studentMapper.selectById(sc.getStudentId()));
        }
        return studentList;
    }

    // 学生id查选的课程
    public List<StudentCourse> getStudentCourseListByStudentId(Integer studentId) {
        QueryWrapper<StudentCourse> studentCourseQueryWrapper = new QueryWrapper<StudentCourse>()
                .select("*")
                .eq("student_id", studentId);

        return studentCourseMapper.selectList(studentCourseQueryWrapper);
    }


    @ApiOperation(value = "用学生id查询课程列表")
    public List<Course> getCourseListByStudentId(Integer studentId) {

        List<StudentCourse> studentCourseList = getStudentCourseListByStudentId(studentId);
        List<Course> courseList = new ArrayList<>();
        for (StudentCourse sc : studentCourseList) {
            QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<Course>().select("*").eq("course_id", sc.getCourseId());
            courseList.add(courseMapper.selectOne(courseQueryWrapper));
        }
        return courseList;
    }

    // 获取关系表的新主键
    public Integer getNewStudentCourseId() {
        return studentCourseMapper.findMaxStudentCourseId() + 1;
    }

    // 通过学生id，课程id选ke
    // 年级符合，课程有效，且未选才可以选择，judgeInsertCourseByStudent实现判断
    public DataResponse insertCourseByStudent(DataRequest dataRequest) {
        Integer studentId = dataRequest.getInteger("studentId");
        Integer courseId = dataRequest.getInteger("courseId");
        Student student = studentMapper.selectById(studentId);
        Course course = courseMapper.selectById(courseId);
        DataResponse dataResponse = judgeInsertCourseByStudent(student, course);
        if (dataResponse.getCode() == 1) return dataResponse;
        Integer studentCourseId = getNewStudentCourseId();
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentCourseId(studentCourseId);
        studentCourse.setCourseId(courseId);
        studentCourse.setStudentId(studentId);
        studentCourse.setScoreStatus(ScoreStatus.Unmarked);
        studentCourseMapper.insert(studentCourse);
        return CommomMethod.getReturnMessageOK("成功选择了一门课程");
    }

    // 学生退课方法
    public DataResponse deleteStudentCourseByStudent(DataRequest dataRequest) {
        Integer studentId = dataRequest.getInteger("studentId");
        Integer courseId = dataRequest.getInteger("courseId");
        if (studentMapper.checkStudentId(studentId) == 0) return CommomMethod.getReturnMessageError("学生不存在");
        QueryWrapper<StudentCourse> studentCourseQueryWrapper = new QueryWrapper<StudentCourse>()
                .eq("student_id", studentId)
                .eq("course_id", courseId);
        List<StudentCourse> studentCourseList = studentCourseMapper.selectList(studentCourseQueryWrapper);
        if (studentCourseList.isEmpty()) return CommomMethod.getReturnMessageError("学生未选择这门课程");
        studentCourseMapper.deleteBatchIds(studentCourseList);
        String courseName = courseMapper.selectById(courseId).getName();
        return CommomMethod.getReturnMessageOK("您退选了" + courseName + "这门课");
    }

    public DataResponse judgeInsertCourseByStudent(Student student, Course course) {
        if (course == null) return CommomMethod.getReturnMessageError("没有该课程");
        if (student == null) return CommomMethod.getReturnMessageError("没有该学生");
        if (studentCourseMapper.getCountByStudentIdAndCourseId(student.getStudentId(), course.getCourseId()) > 0)
            return CommomMethod.getReturnMessageError("已经选择该课程");
        if (course.getCourseStatus().getCode() <= 2) return CommomMethod.getReturnMessageError("课程处于不可选状态");
        if (course.getGrade() != student.getGrade() && course.getGrade() != Grade.All)
            return CommomMethod.getReturnMessageError("您的年级与课程不匹配");
        return CommomMethod.getReturnMessageOK();
    }

}
