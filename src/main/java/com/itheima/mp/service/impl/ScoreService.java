package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Course;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.StudentCourse;
import com.itheima.mp.domain.vo.CourseVO;
import com.itheima.mp.enums.CourseType;
import com.itheima.mp.enums.ScoreStatus;
import com.itheima.mp.mapper.*;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.BaseService;
import com.itheima.mp.service.VOService;
import com.itheima.mp.service.iservice.IStudentCourseService;
import com.itheima.mp.util.CommomMethod;
import com.itheima.mp.util.FormatMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class ScoreService extends ServiceImpl<StudentCourseMapper, StudentCourse> implements IStudentCourseService {
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
    @Autowired
    private VOService voService;

    // 获取学生所选的所有课程信息，包括老师，成绩，排名,学生用
    public DataResponse getCourseVOListByStudentId(Integer studentId) {
        List<CourseVO> courseVOList = new ArrayList<>();
        List<Course> courseList = baseService.getCourseListByStudentId(studentId);
        if (courseList.isEmpty()) return CommomMethod.getReturnMessageError("该学生没有选课呦");
        for (Course course : courseList) {
            courseVOList.add(voService.getCourseVO(course, studentMapper.selectById(studentId)));
        }
        return CommomMethod.getReturnData(courseVOList);
    }

    // 老师根据学生id与课程id修改成绩
    public DataResponse updateScore(DataRequest dataRequest) {
        Integer courseId = dataRequest.getInteger("courseId");
        if (dataRequest.getInteger("scoreStatus") < 2 || dataRequest.getInteger("scoreStatus") > 3)
            return CommomMethod.getReturnMessageError("不存在该分数状态");
        ScoreStatus scoreStatus = ScoreStatus.getByCode(dataRequest.getInteger("scoreStatus"));
        if (courseMapper.checkCourseId(courseId) == 0) return CommomMethod.getReturnMessageError("没有该课程");
        Integer studentId = dataRequest.getInteger("studentId");
        if (studentMapper.checkStudentId(studentId) == 0) return CommomMethod.getReturnMessageError("没有该学生");
        Double score = dataRequest.getDouble("score");
        if (score < 0 || score > 100) return CommomMethod.getReturnMessageError("分数超出范围");
        StudentCourse studentCourse = studentCourseMapper.findByStudentIdAndCourseId(studentId, courseId);
        studentCourse.setScoreStatus(scoreStatus);
        studentCourse.setScore(score);
        studentCourseMapper.updateById(studentCourse);
        return CommomMethod.getReturnMessageOK("成功修改了学生成绩");
    }

    // 老师批量修改某一门课的学生成绩，并设置分数状态
    public DataResponse updateScoreOfStudentList(DataRequest dataRequest) {
        if (dataRequest.getInteger("scoreStatus") < 2 || dataRequest.getInteger("scoreStatus") > 3)
            return CommomMethod.getReturnMessageError("分数状态有误");
        ScoreStatus scoreStatus = ScoreStatus.getByCode(dataRequest.getInteger("scoreStatus"));
        Integer courseId = dataRequest.getInteger("courseId");
        if (courseMapper.checkCourseId(courseId) == 0) return CommomMethod.getReturnMessageError("没有该课程");
        Map<Integer, Integer> studentIdAndScoreMap = dataRequest.getMap("studentIdAndScoreMap");
        List<Integer> studentIdList = studentMapper.getStudentIdListAll();
        for (Map.Entry<Integer, Integer> entry : studentIdAndScoreMap.entrySet()) {
            Integer studentId = entry.getKey();
            Integer score = entry.getValue();
            if (score < 0 || score > 100) {
                return CommomMethod.getReturnMessageError("分数超出范围");
            }
            if (!studentIdList.contains(studentId)) return CommomMethod.getReturnMessageError("没有该学生");
            StudentCourse studentCourse = studentCourseMapper.findByStudentIdAndCourseId(studentId, courseId);
            if (studentCourse == null) return CommomMethod.getReturnMessageError("有学生并未选择这门课程");
            studentCourse.setScore(score);
            studentCourse.setScoreStatus(scoreStatus);
            studentCourseMapper.updateById(studentCourse);
        }
        return CommomMethod.getReturnMessageOK("成功修改了一组学生成绩与成绩状态");
    }

    // 老师修改一个学生一门课的成绩状态
    public DataResponse updateScoreStatus(DataRequest dataRequest) {
        Integer courseId = dataRequest.getInteger("courseId");
        if (courseMapper.checkCourseId(courseId) == 0) return CommomMethod.getReturnMessageError("没有该课程");
        Integer studentId = dataRequest.getInteger("studentId");
        if (studentMapper.checkStudentId(studentId) == 0) return CommomMethod.getReturnMessageError("没有该学生");
        StudentCourse studentCourse = studentCourseMapper.findByStudentIdAndCourseId(studentId, courseId);
        ScoreStatus scoreStatus = ScoreStatus.getByCode(dataRequest.getInteger("scoreStatus"));
        if (scoreStatus.getCode() < 2 || scoreStatus.getCode() > 3) {
            return CommomMethod.getReturnMessageError("分数状态有误");
        }
        studentCourse.setScoreStatus(scoreStatus);
        studentCourseMapper.updateById(studentCourse);
        return CommomMethod.getReturnMessageOK("成功将" + courseMapper.selectById(courseId).getName() + "的分数状态修改为" + scoreStatus.getType());
    }

    // 老师批量修改一门课的很多学生的成绩状态
    public DataResponse updateScoreStatusOfStudentList(DataRequest dataRequest) {
        if (dataRequest.getInteger("scoreStatus") < 2 || dataRequest.getInteger("scoreStatus") > 3)
            return CommomMethod.getReturnMessageError("分数状态有误");
        Integer courseId = dataRequest.getInteger("courseId");
        if (courseMapper.checkCourseId(courseId) == 0) return CommomMethod.getReturnMessageError("没有该课程");
        ScoreStatus scoreStatus = ScoreStatus.getByCode(dataRequest.getInteger("scoreStatus"));
        List<Integer> studentIdListAll = studentMapper.getStudentIdListAll();
        List<Integer> studentIdList = dataRequest.getList("studentIdList");
        for (Integer studentId : studentIdList) {
            if (!studentIdListAll.contains(studentId)) return CommomMethod.getReturnMessageError("没有该学生");
            StudentCourse studentCourse = studentCourseMapper.findByStudentIdAndCourseId(studentId, courseId);
            if (studentCourse == null)
                return CommomMethod.getReturnMessageError("学生" + studentMapper.selectById(studentId).getName() + "并未选择该课程");
            studentCourse.setScoreStatus(scoreStatus);
        }
        return CommomMethod.getReturnMessageOK("成功修改了一组学生的" + courseMapper.selectById(courseId).getName() + "成绩状态");
    }

    public DataResponse selectAllCourseVOListUnmarked(DataRequest dataRequest) {
        Integer courseId = dataRequest.getInteger("courseId");
        if (courseMapper.checkCourseId(courseId) == 0) return CommomMethod.getReturnMessageError("不存在该课程");
        List<Student> studentList = getStudentListByCourseIdByScoreStatus(courseId, ScoreStatus.Unmarked);
        return CommomMethod.getReturnData(getCourseVOList(studentList, courseMapper.selectById(courseId)));
    }

    public DataResponse selectAllCourseVOListMarked(DataRequest dataRequest) {
        Integer courseId = dataRequest.getInteger("courseId");
        if (courseMapper.checkCourseId(courseId) == 0) return CommomMethod.getReturnMessageError("不存在该课程");
        List<Student> studentList = getStudentListByCourseIdByScoreStatus(courseId, ScoreStatus.Marked);
        return CommomMethod.getReturnData(getCourseVOList(studentList, courseMapper.selectById(courseId)));
    }

    public DataResponse selectAllCourseVOListVisible(DataRequest dataRequest) {
        Integer courseId = dataRequest.getInteger("courseId");
        if (courseMapper.checkCourseId(courseId) == 0) return CommomMethod.getReturnMessageError("不存在该课程");
        List<Student> studentList = getStudentListByCourseIdByScoreStatus(courseId, ScoreStatus.Visible);
        return CommomMethod.getReturnData(getCourseVOList(studentList, courseMapper.selectById(courseId)));
    }

    public DataResponse selectAllCourseVOListByCourseId(DataRequest dataRequest) {
        Integer courseId = dataRequest.getInteger("courseId");
        if (courseMapper.checkCourseId(courseId) == 0) return CommomMethod.getReturnMessageError("不存在该课程");
        List<StudentCourse> studentCourseList = studentCourseMapper.findByCourseId(courseId);
        List<Student> studentList = new ArrayList<>();
        for (StudentCourse studentCourse : studentCourseList) {
            studentList.add(studentMapper.selectById(studentCourse.getStudentId()));
        }
        List<CourseVO> scoreVOList = getCourseVOList(studentList, courseMapper.selectById(courseId));
        return CommomMethod.getReturnData(scoreVOList);
    }

    // 根据不同的课程状态选择学生列表
    public List<Student> getStudentListByCourseIdByScoreStatus(Integer courseId, ScoreStatus scoreStatus) {
        List<StudentCourse> studentCourseList = studentCourseMapper.findByCourseId(courseId);
        List<Student> studentList = new ArrayList<>();
        for (StudentCourse studentCourse : studentCourseList) {
            if (studentCourse.getScoreStatus() == scoreStatus) {
                studentList.add(studentMapper.selectById(studentCourse.getStudentId()));
            }
        }
        return studentList;
    }

    public List<CourseVO> getCourseVOList(List<Student> studentList, Course course) {
        List<CourseVO> scoreVOList = new ArrayList<>();
        for (Student student : studentList) {
            scoreVOList.add(voService.getCourseVO(course, student));
        }
        return scoreVOList;
    }

    public void updateGPAByStudentId(Integer studentId) {
        List<Integer> studentIdList = studentMapper.getStudentIdListAll();
        if (!studentIdList.contains(studentId)) return;
        Double gpa = getGPA(studentId);
        Student student = studentMapper.selectById(studentId);
        student.setGpa(gpa);
        studentMapper.updateById(student);
    }

    public void updateGPAAll() {
        List<Integer> studentIdList = studentMapper.getStudentIdListAll();
        for (Integer studentId : studentIdList) {
            updateGPAByStudentId(studentId);
        }
    }

    public Double getGPA(Integer studentId) {
        List<StudentCourse> studentCourseList = baseService.getStudentCourseListByStudentId(studentId);
        Double gpa = (double) 0;
        Map<Double, Double> scoreCreditMap = new HashMap<>();
        for (StudentCourse studentCourse : studentCourseList) {
            CourseType courseType = courseMapper.selectById(studentCourse.getCourseId()).getCourseType();
            if (studentCourse.getScoreStatus() == ScoreStatus.Visible &&
                    (courseType == CourseType.Required || courseType == CourseType.OptionalLimited)) {
                Double credit = courseMapper.selectById(studentCourse.getCourseId()).getCredit();
                Double score = studentCourse.getScore();
                scoreCreditMap.put(score, credit);
            }
        }
        Double creditSum = (double) 0;
        for (Map.Entry<Double, Double> entry : scoreCreditMap.entrySet()) {
            creditSum += entry.getValue();
        }
        for (Map.Entry<Double, Double> entry : scoreCreditMap.entrySet()) {
            Double singleGPA = entry.getKey() / 20;
            gpa += entry.getValue() / creditSum * singleGPA;
        }
        return FormatMethod.GPAFormat(gpa);
    }


}
