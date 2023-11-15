package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.*;
import com.itheima.mp.domain.vo.CourseVO;
import com.itheima.mp.domain.vo.ScoreVO;
import com.itheima.mp.domain.vo.StudentVO;
import com.itheima.mp.enums.CourseType;
import com.itheima.mp.enums.ScoreStatus;
import com.itheima.mp.mapper.*;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.BaseService;
import com.itheima.mp.service.iservice.IStudentCourseService;
import com.itheima.mp.util.CommomMethod;
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
public class ScoreService extends ServiceImpl<StudentCourseMapper, StudentCourse> implements IStudentCourseService{
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

    public CourseVO getCourseVO(Integer studentId,Integer courseId){
        CourseVO courseVO=new CourseVO();
        if(studentMapper.checkStudentId(studentId)==0||courseMapper.checkCourseId(courseId)==0)return courseVO;
        Course course=courseMapper.selectById(courseId);
        Student student=studentMapper.selectById(studentId);
        List<Integer> teacherIdList=teacherCourseMapper.findTeacherIdByCourseIdAndStudentClass(courseId,student.getStudentClass());
        Teacher teacher = teacherMapper.selectById(teacherIdList.get(0));
        StudentCourse studentCourse=studentCourseMapper.findByStudentIdAndCourseId(studentId,courseId);
        Double score= (double) 0;
        if(studentCourse.getScoreStatus()== ScoreStatus.Visible)score=studentCourse.getScore();
        courseVO=new CourseVO(courseId,studentId,course.getNum(),course.getName(),course.getCredit(),teacher,score,studentCourse.getRankClass(),studentCourse.getRankCollege());
        return courseVO;
    }


    //获取学生所选的所有课程信息，包括老师，成绩，排名,学生用
    public DataResponse getCourseVOListByStudentId(Integer studentId){
        List<CourseVO> courseVOList=new ArrayList<>();
        List<Course> courseList=baseService.getCourseListByStudentId(studentId);
        if(courseList.isEmpty())return CommomMethod.getReturnMessageError("该学生没有选课呦");
        for (Course course: courseList){
            courseVOList.add(getCourseVO(studentId,course.getCourseId()));
        }
        return CommomMethod.getReturnData(courseVOList);
    }

    //老师根据学生id与课程id修改成绩
    public DataResponse updateScore(DataRequest dataRequest){
        Integer courseId=dataRequest.getInteger("courseId");
        ScoreStatus scoreStatus=ScoreStatus.getByCode(dataRequest.getInteger("scoreStatus"));
        if(courseMapper.checkCourseId(courseId)==0)return CommomMethod.getReturnMessageError("没有该课程");
        Integer studentId=dataRequest.getInteger("studentId");
        if(studentMapper.checkStudentId(studentId)==0)return CommomMethod.getReturnMessageError("没有该学生");
        Double score=dataRequest.getDouble("score");
        if(score<0||score>100)return CommomMethod.getReturnMessageError("分数超出范围");
        StudentCourse studentCourse=studentCourseMapper.findByStudentIdAndCourseId(studentId,courseId);
        if(scoreStatus.getCode()<2||scoreStatus.getCode()>3)return CommomMethod.getReturnMessageError("不存在该分数状态");
        studentCourse.setScoreStatus(scoreStatus);
        studentCourse.setScore(score);
        studentCourseMapper.updateById(studentCourse);
        return CommomMethod.getReturnMessageOK("成功修改了学生成绩");
    }

    //老师批量修改某一门课的学生成绩，并设置分数状态
    public DataResponse updateScoreOfStudentList(DataRequest dataRequest){
        ScoreStatus scoreStatus =ScoreStatus.getByCode(dataRequest.getInteger("scoreStatus"));
        if(scoreStatus.getCode()<2||scoreStatus.getCode()>3)return CommomMethod.getReturnMessageError("分数状态有误");
        Integer courseId = dataRequest.getInteger("courseId");
        if(courseMapper.checkCourseId(courseId)==0)return CommomMethod.getReturnMessageError("没有该课程");
        Map<Integer,Integer> studentIdAndScoreMap=dataRequest.getMap("studentIdAndScoreMap");
        List<Integer> studentIdList=studentMapper.getStudentIdListAll();
        for(Map.Entry<Integer,Integer> entry:studentIdAndScoreMap.entrySet()){
            Integer studentId=entry.getKey();
            Integer score=entry.getValue();
            if(score<0||score>100){
                return CommomMethod.getReturnMessageError("分数超出范围");
            }
            if(!studentIdList.contains(studentId))return CommomMethod.getReturnMessageError("没有该学生");
            StudentCourse studentCourse=studentCourseMapper.findByStudentIdAndCourseId(studentId,courseId);
            studentCourse.setScore(score);
            studentCourse.setScoreStatus(scoreStatus);
        }
        return  CommomMethod.getReturnMessageOK("成功修改了一组学生成绩与成绩状态");
    }

    //老师修改一个学生一门课的成绩状态
    public DataResponse updateScoreStatus(DataRequest dataRequest){
        Integer courseId=dataRequest.getInteger("courseId");
        if(courseMapper.checkCourseId(courseId)==0)return CommomMethod.getReturnMessageError("没有该课程");
        Integer studentId=dataRequest.getInteger("studentId");
        if(studentMapper.checkStudentId(studentId)==0)return CommomMethod.getReturnMessageError("没有该学生");
        StudentCourse studentCourse= studentCourseMapper.findByStudentIdAndCourseId(studentId,courseId);
        ScoreStatus scoreStatus=ScoreStatus.getByCode(dataRequest.getInteger("scoreStatus"));
        if(scoreStatus.getCode()<2||scoreStatus.getCode()>3){
            return CommomMethod.getReturnMessageError("分数状态有误");
        }
        studentCourse.setScoreStatus(scoreStatus);
        studentCourseMapper.updateById(studentCourse);
        return CommomMethod.getReturnMessageOK("成功将"+courseMapper.selectById(courseId).getName()+"的分数状态修改为"+scoreStatus.getType());
    }

    //老师批量修改一门课的很多学生的成绩状态
    public DataResponse updateScoreStatusOfStudentList(DataRequest dataRequest){
        Integer courseId=dataRequest.getInteger("courseId");
        if(courseMapper.checkCourseId(courseId)==0)return CommomMethod.getReturnMessageError("没有该课程");
        ScoreStatus scoreStatus=ScoreStatus.getByCode(dataRequest.getInteger("scoreStatus"));
        List<Integer> studentIdListAll=studentMapper.getStudentIdListAll();
        if(scoreStatus.getCode()<3||scoreStatus.getCode()>4){
            return CommomMethod.getReturnMessageError("分数状态有误");
        }
        List<Integer> studentIdList=dataRequest.getList("studentIdList");
        for(Integer studentId: studentIdList){
            if(!studentIdListAll.contains(studentId))return CommomMethod.getReturnMessageError("没有该学生");
            StudentCourse studentCourse=studentCourseMapper.findByStudentIdAndCourseId(studentId,courseId);
            studentCourse.setScoreStatus(scoreStatus);
        }
        return CommomMethod.getReturnMessageOK("成功修改了一组学生的"+courseMapper.selectById(courseId).getName()+"成绩状态");
    }

    public DataResponse selectAllScoreVOListUnmarked(DataRequest dataRequest){
        Integer courseId=dataRequest.getInteger("courseId");
        if(courseMapper.checkCourseId(courseId)==0)return CommomMethod.getReturnMessageError("不存在该课程");
        List<Student> studentList=getStudentListByCourseIdByScoreStatus(courseId,ScoreStatus.Unmarked);
        return CommomMethod.getReturnData(getScoreVOList(studentList,courseMapper.selectById(courseId)));
    }
    public DataResponse selectAllScoreVOListMarked(DataRequest dataRequest){
        Integer courseId=dataRequest.getInteger("courseId");
        if(courseMapper.checkCourseId(courseId)==0)return CommomMethod.getReturnMessageError("不存在该课程");
        List<Student> studentList=getStudentListByCourseIdByScoreStatus(courseId,ScoreStatus.Marked);
        return CommomMethod.getReturnData(getScoreVOList(studentList,courseMapper.selectById(courseId)));
    }
    public DataResponse selectAllScoreVOListVisible(DataRequest dataRequest){
        Integer courseId=dataRequest.getInteger("courseId");
        if(courseMapper.checkCourseId(courseId)==0)return CommomMethod.getReturnMessageError("不存在该课程");
        List<Student> studentList=getStudentListByCourseIdByScoreStatus(courseId,ScoreStatus.Visible);
        return CommomMethod.getReturnData(getScoreVOList(studentList,courseMapper.selectById(courseId)));
    }

    public DataResponse selectAllScoreVOListByCourseId(DataRequest dataRequest){
        Integer courseId=dataRequest.getInteger("courseId");
        if(courseMapper.checkCourseId(courseId)==0)return CommomMethod.getReturnMessageError("不存在该课程");
        List<StudentCourse> studentCourseList=studentCourseMapper.findByCourseId(courseId);
        List<Student> studentList=new ArrayList<>();
        for(StudentCourse studentCourse:studentCourseList){
                studentList.add(studentMapper.selectById(studentCourse.getStudentId()));
        }
        List<ScoreVO> scoreVOList=getScoreVOList(studentList,courseMapper.selectById(courseId));
        return CommomMethod.getReturnData(scoreVOList);
    }




    //根据不同的课程状态选择学生列表
    public List<Student> getStudentListByCourseIdByScoreStatus(Integer courseId,ScoreStatus scoreStatus){
        List<StudentCourse> studentCourseList=studentCourseMapper.findByCourseId(courseId);
        List<Student> studentList=new ArrayList<>();
        for(StudentCourse studentCourse:studentCourseList){
            if(studentCourse.getScoreStatus()==scoreStatus){
                studentList.add(studentMapper.selectById(studentCourse.getStudentId()));
            }
        }
        return studentList;
    }
    public ScoreVO getScoreVO(Student student,Course course){
        ScoreVO scoreVO=new ScoreVO();

        scoreVO.setStudent(student);
        scoreVO.setCourse(course);
        scoreVO.setUser(userMapper.selectById(student.getUserId()));
        scoreVO.setStudentCourse(studentCourseMapper.findByStudentIdAndCourseId(student.getStudentId(),course.getCourseId()));
        return scoreVO;
    }

    public List<ScoreVO> getScoreVOList(List<Student> studentList,Course course){
        List<ScoreVO> scoreVOList=new ArrayList<>();
        for(Student student:studentList){
            scoreVOList.add(getScoreVO(student,course));
        }
        return scoreVOList;
    }

    public DataResponse updateGPAByStudentId(DataRequest dataRequest){
        Integer studentId=dataRequest.getInteger("studentId");
        Double gpa=getGPA(studentId);
        Student student=studentMapper.selectById(studentId);
        student.setGpa(gpa);
        studentMapper.updateById(student);
        return CommomMethod.getReturnMessageOK("成功更新了学生GPA");
    }

    public Double getGPA(Integer studentId){
        List<StudentCourse> studentCourseList=baseService.getStudentCourseListByStudentId(studentId);
        Double gpa= (double) 0;
        Map<Double, Double> scoreCreditMap=new HashMap<>();
        for(StudentCourse studentCourse:studentCourseList){
            CourseType courseType=courseMapper.selectById(studentCourse.getCourseId()).getCourseType();
            if(studentCourse.getScoreStatus()==ScoreStatus.Visible&&
                    (courseType== CourseType.Required||courseType==CourseType.OptionalLimited)) {
                Double credit=courseMapper.selectById(studentCourse.getCourseId()).getCredit();
                Double score=studentCourse.getScore();
                scoreCreditMap.put(score,credit);
            }
        }
        Double creditSum= (double) 0;
        for(Map.Entry<Double,Double> entry : scoreCreditMap.entrySet()){
            creditSum+=entry.getValue();
        }
        for(Map.Entry<Double,Double> entry : scoreCreditMap.entrySet()){
            Double singleGPA=entry.getKey()/20;
            gpa+=entry.getValue()/creditSum*singleGPA;
        }
        return gpa;
    }




}
