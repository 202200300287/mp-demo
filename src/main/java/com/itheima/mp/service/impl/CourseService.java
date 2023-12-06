package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Course;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.enums.CourseStatus;
import com.itheima.mp.enums.CourseType;
import com.itheima.mp.enums.Grade;
import com.itheima.mp.mapper.CourseMapper;
import com.itheima.mp.mapper.StudentMapper;
import com.itheima.mp.mapper.TeacherCourseMapper;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.BaseService;
import com.itheima.mp.service.iservice.ICourseService;
import com.itheima.mp.util.CommomMethod;
import com.itheima.mp.util.UpdateUtil;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class CourseService extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private BaseService baseService;
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherCourseMapper teacherCourseMapper;

    public DataResponse getCourseList() {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<Course>()
                .select("*");

        List<Course> courseList = courseMapper.selectList(courseQueryWrapper);
        if (courseList.isEmpty())
            return CommomMethod.getReturnMessageError("列表中没有课程");

        return CommomMethod.getReturnData(courseList, "获取所有课程数据");
    }

    public Integer getNewCourseId() {
        return courseMapper.findMaxCourseId() + 1;
    }

    public Course getCourseFrom(Map map) {
        Course course = new Course();
        course.setNum(CommomMethod.getString(map, "num"));
        course.setName(CommomMethod.getString(map, "name"));
        course.setCredit(CommomMethod.getDouble(map, "credit"));
        Integer courseStatusCode=CommomMethod.getInteger(map,"courseStatus");
        if(courseStatusCode!=null&&courseStatusCode>=1&&courseStatusCode<=3) course.setCourseStatus(CourseStatus.getByCode(courseStatusCode));
        course.setCourseStatus(CourseStatus.AvailableUnselectable);
        course.setGrade(Grade.getByCode(CommomMethod.getInteger0(map, "grade")));
        course.setCourseType(CourseType.getByCode(CommomMethod.getInteger0(map, "courseType")));
        return course;
    }

    public DataResponse insertCourse(DataRequest dataRequest) {
        Course course = new Course();
        course = getCourseFrom(dataRequest.getMap("course"));
        course.setCourseId(getNewCourseId());
        DataResponse dataResponse = baseService.judgeCourseData(course);
        if (dataResponse.getCode() == 1) return dataResponse;
        courseMapper.insert(course);
        return CommomMethod.getReturnMessageOK("成功添加了课程");

    }

    public DataResponse updateCourse(DataRequest dataRequest) {
        Integer courseId = dataRequest.getInteger("courseId");
        if (courseMapper.checkCourseId(courseId) == 0)
            return CommomMethod.getReturnMessageError("没有该课程");
        Course course = courseMapper.selectById(courseId);
        Course courseSource = getCourseFrom(dataRequest.getMap("course"));
        UpdateUtil.copyNullProperties(courseSource, course);
        DataResponse dataResponse = baseService.judgeCourseData(course);
        if (dataResponse.getCode() == 1) return dataResponse;
        courseMapper.updateById(course);
        return CommomMethod.getReturnMessageOK("成功更新了课程");
    }

    public DataResponse deleteCourse(DataRequest dataRequest) {
        Integer courseId = dataRequest.getInteger("courseId");
        if (courseMapper.checkCourseId(courseId) == 0)
            return CommomMethod.getReturnMessageError("没有该课程");
        UpdateWrapper<Course> courseUpdateWrapper = new UpdateWrapper<Course>()
                .eq("course_id", courseId)
                .set("course_status", 1);
        int affected = courseMapper.update(null, courseUpdateWrapper);
        if (affected == 0) {
            return CommomMethod.getReturnMessageError("删除课程失败");
        }
        return CommomMethod.getReturnMessageOK("成功删除了课程");
    }

    public DataResponse selectCourse(DataRequest dataRequest) {
        Integer courseId = dataRequest.getInteger("courseId");
        if (courseMapper.checkCourseId(courseId) == 0)
            return CommomMethod.getReturnMessageError("没有该课程");
        Course course = courseMapper.selectById(courseId);
        return CommomMethod.getReturnData(course);
    }


    @ApiModelProperty("通过学生id查询可选且未选的课程")
    public DataResponse selectCourseSelectableByStudent(DataRequest dataRequest) {
        Integer studentId = dataRequest.getInteger("studentId");
        Student student = studentMapper.selectById(studentId);
        Grade grade = student.getGrade();
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<Course>()
                .select("*")
                .eq("course_status", 3)
                .eq("grade", grade.getCode());
        List<Course> courseList = courseMapper.selectList(courseQueryWrapper);
        List<Course> courseListSelected = baseService.getCourseListByStudentId(studentId);
        courseList.removeIf(courseListSelected::contains);

        return CommomMethod.getReturnData(courseList);
    }


    // 通过老师id查询可选且未选的课程
    public DataResponse selectCourseSelectableByTeacher(DataRequest dataRequest) {
        Integer teacherId = dataRequest.getInteger("teacherId");
        List<Integer> courseIdList = teacherCourseMapper.findCourseIdByTeacherId(teacherId);
        List<Course> courseListSelected = courseMapper.selectBatchIds(courseIdList);
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<Course>()
                .select("*")
                .eq("course_status", 3);
        List<Course> courseList = courseMapper.selectList(courseQueryWrapper);
        courseList.removeIf(courseListSelected::contains);
        return CommomMethod.getReturnData(courseList);
    }

}

