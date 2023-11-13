package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Course;
import com.itheima.mp.domain.po.TeacherCourse;
import com.itheima.mp.mapper.CourseMapper;
import com.itheima.mp.mapper.TeacherCourseMapper;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.iservice.ITeacherCourseService;
import com.itheima.mp.util.CommomMethod;
import io.swagger.annotations.ApiModelProperty;
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
    private CourseMapper courseMapper;

    public DataResponse selectCourseByTeacher(DataRequest dataRequest){
        Integer teacherId=dataRequest.getInteger("teacherId");
        List<Integer> courseIdList=teacherCourseMapper.findCourseIdByTeacherId(teacherId);
        if (courseIdList.isEmpty())return CommomMethod.getReturnMessageError("老师没有教任何一门课");
        List<Course> courseList=courseMapper.selectBatchIds(courseIdList);
        return CommomMethod.getReturnData(courseList);
    }
}
