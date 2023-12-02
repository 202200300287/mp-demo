package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Course;
import com.itheima.mp.domain.po.TeacherCourse;
import com.itheima.mp.mapper.CourseMapper;
import com.itheima.mp.mapper.TeacherCourseMapper;
import com.itheima.mp.mapper.TeacherMapper;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.iservice.ITeacherCourseService;
import com.itheima.mp.util.CommomMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class TeacherCourseService extends ServiceImpl<TeacherCourseMapper, TeacherCourse> implements ITeacherCourseService {
    @Autowired
    private TeacherCourseMapper teacherCourseMapper;


    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private CourseMapper courseMapper;

    public DataResponse selectCourseByTeacher(DataRequest dataRequest) {
        Integer teacherId = dataRequest.getInteger("teacherId");
        if (teacherMapper.checkTeacherId(teacherId) == 0) return CommomMethod.getReturnMessageError("不存在该老师");
        List<Integer> courseIdList = teacherCourseMapper.findCourseIdByTeacherId(teacherId);
        if (courseIdList.isEmpty()) return CommomMethod.getReturnMessageError("老师没有教任何一门课");
        List<Course> courseList = courseMapper.selectBatchIds(courseIdList);
        return CommomMethod.getReturnData(courseList);
    }


    public DataResponse insertTeacherCourse(DataRequest dataRequest) {
        Integer teacherId = dataRequest.getInteger("teacherId");
        Integer courseId = dataRequest.getInteger("courseId");
        Integer studentClass = dataRequest.getInteger("studentClass");
        QueryWrapper<TeacherCourse> teacherCourseQueryWrapper = new QueryWrapper<TeacherCourse>()
                .eq("teacher_id", teacherId)
                .eq("course_id", courseId)
                .eq("student_class", studentClass);
        List<TeacherCourse> teacherCourseList = teacherCourseMapper.selectList(teacherCourseQueryWrapper);
        if (teacherMapper.checkTeacherId(teacherId) == 0) return CommomMethod.getReturnMessageError("不存在该老师");
        if (courseMapper.checkCourseId(courseId) == 0) return CommomMethod.getReturnMessageError("不存在这门课程");
        if (!teacherCourseList.isEmpty())
            return CommomMethod.getReturnMessageError("有老师已经教授了" + studentClass + "班的" + courseMapper.selectById(courseId).getName() + "课程");
        TeacherCourse teacherCourse = new TeacherCourse(getNewTeacherCourseId(), teacherId, courseId, studentClass);
        teacherCourseMapper.insert(teacherCourse);
        return CommomMethod.getReturnMessageOK("成功添加了老师的课程");
    }

    public DataResponse deleteTeacherCourse(DataRequest dataRequest) {
        Integer teacherId = dataRequest.getInteger("teacherId");
        Integer courseId = dataRequest.getInteger("courseId");
        Integer studentClass = dataRequest.getInteger("studentClass");
        QueryWrapper<TeacherCourse> teacherCourseQueryWrapper = new QueryWrapper<TeacherCourse>()
                .eq("teacher_id", teacherId)
                .eq("course_id", courseId)
                .eq("student_class", studentClass);
        List<TeacherCourse> teacherCourseList = teacherCourseMapper.selectList(teacherCourseQueryWrapper);
        if (teacherMapper.checkTeacherId(teacherId) == 0) return CommomMethod.getReturnMessageError("不存在该老师");
        if (courseMapper.checkCourseId(courseId) == 0) return CommomMethod.getReturnMessageError("不存在这门课程");
        if (teacherCourseList.isEmpty())
            return CommomMethod.getReturnMessageError("老师不负责" + studentClass + "班的" + courseMapper.selectById(courseId).getName() + "课程");
        teacherCourseMapper.deleteBatchIds(teacherCourseList);
        return CommomMethod.getReturnMessageOK("成功删除了老师的课程");
    }

    public Integer getNewTeacherCourseId() {
        return teacherCourseMapper.findMaxTeacherCourseId() + 1;
    }


}
