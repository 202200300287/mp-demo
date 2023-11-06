package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.*;
import com.itheima.mp.domain.vo.CourseVO;
import com.itheima.mp.domain.vo.StudentVO;
import com.itheima.mp.enums.Gender;
import com.itheima.mp.enums.Grade;
import com.itheima.mp.enums.Major;
import com.itheima.mp.enums.UserType;
import com.itheima.mp.mapper.*;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.BaseService;
import com.itheima.mp.service.iservice.IStudentService;
import com.itheima.mp.util.CommomMethod;
import com.itheima.mp.util.FormatMethod;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import static com.itheima.mp.util.CommomMethod.getInteger;


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

    @Autowired
    private UserMapper userMapper;


    public Student getStudentById(Integer studentId){
        QueryWrapper<Student> queryWrapper=new QueryWrapper<Student>()
                .select("*")
                .eq("student_id",studentId);
        Student s=studentMapper.selectOne(queryWrapper);
        return s;
    }

    /*
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


     */

    @ApiModelProperty("添加一个学生，对其中username，姓名，班级，年级，邮箱格式进行判断")
    public DataResponse insertStudent(DataRequest dataRequest){
        Map map=dataRequest.getData();
        Integer userId=getNewUserId();
        Integer studentId =getNewStudentId();
        User user=getUserFromMap(CommomMethod.getMap(map,"user"),userId);
        Student student=getStudentFromMap(CommomMethod.getMap(map,"student"),studentId);
        StudentBasic studentBasic=getStudentBasicFromMap(CommomMethod.getMap(map,"studentBasic"),studentId);
        StudentAdvanced studentAdvanced=getStudentAdvancedFromMap(CommomMethod.getMap(map,"studentAdvanced"),studentId);

        DataResponse dataResponse=baseService.judgeStudentData(user,student,studentBasic);
        if(dataResponse.getCode()==1)return dataResponse;

        userMapper.insert(user);
        studentMapper.insert(student);
        studentBasicMapper.insert(studentBasic);
        studentAdvancedMapper.insert(studentAdvanced);
        return CommomMethod.getReturnMessageOK("成功添加了一名学生");
    }

    public DataResponse updateStudent(DataRequest dataRequest){
        Integer studentId=dataRequest.getInteger("studentId");
        if(studentId==null)return CommomMethod.getReturnMessageError("数据传输格式错误");

        if(studentMapper.checkStudentId(studentId)==0){
            return CommomMethod.getReturnMessageError("该学生不存在");
        }

        Student student=getStudentFromMap(dataRequest.getMap("student"),studentId);
        Integer userId=student.getUserId();
        User user=getUserFromMap(dataRequest.getMap("user"),userId);
        StudentBasic studentBasic=getStudentBasicFromMap(dataRequest.getMap("studentBasic"),studentId);
        StudentAdvanced studentAdvanced=getStudentAdvancedFromMap(dataRequest.getMap("studentAdvanced"),studentId);
        DataResponse dataResponse=baseService.judgeStudentData(user,student,studentBasic);
        if(dataResponse.getCode()==1)return dataResponse;

        userMapper.updateById(user);
        studentMapper.updateById(student);
        studentBasicMapper.updateById(studentBasic);
        studentAdvancedMapper.updateById(studentAdvanced);


        return CommomMethod.getReturnMessageOK("成功修改了学生信息");
    }

    public DataResponse deleteStudent(DataRequest dataRequest){
        Integer studentId = dataRequest.getInteger("student_id");

        if(studentId==null)return CommomMethod.getReturnMessageError("数据传输格式错误");

        if(studentMapper.checkStudentId(studentId)==0){
            return CommomMethod.getReturnMessageError("该学生不存在");
        }
        Student student=studentMapper.selectById(studentId);
        User user=userMapper.selectById(student.getUserId());
        userMapper.deleteById(user);
        studentBasicMapper.deleteById(studentId);
        studentAdvancedMapper.deleteById(studentId);
        studentMapper.deleteById(studentId);
        return CommomMethod.getReturnMessageOK("正确删除了一名学生");
    }

    public DataResponse selectStudent(DataRequest dataRequest){
        Integer studentId = dataRequest.getInteger("studentId");

        if(studentId==null)return CommomMethod.getReturnMessageError("数据传输格式错误");

        if(studentMapper.checkStudentId(studentId)==0){
            return CommomMethod.getReturnMessageError("该学生不存在");
        }

        Student student=studentMapper.selectById(studentId);
        User user=userMapper.selectById(student.getUserId());
        StudentVO studentVO=new StudentVO(studentId,user,student,studentBasicMapper.selectById(studentId),studentAdvancedMapper.selectById(studentId));
        return CommomMethod.getReturnData(studentVO);
    }

    public User getUserFromMap(Map map){
        User user=new User();
        user.setUsername(CommomMethod.getString(map,"username"));
        user.setPassword(CommomMethod.getString(map,"password"));
        user.setPhoto(CommomMethod.getString(map,"photo"));
        user.setUserType(UserType.STUDENT);
        user.setCreateTime(LocalDateTime.now());
        return user;
    }

    public User getUserFromMap(Map map,Integer userId){
        User user=getUserFromMap(map);
        user.setUserId(userId);
        return user;
    }

    public Student getStudentFromMap(Map map){
        Student student=new Student();
        student.setName(CommomMethod.getString(map,"name"));
        student.setMajor(Major.getByCode(CommomMethod.getInteger0(map,"major")));
        student.setGrade(Grade.getByCode(CommomMethod.getInteger0(map,"grade")));
        student.setStudentClass(CommomMethod.getInteger0(map,"studentClass"));
        return student;
    }

    public Student getStudentFromMap(Map map,Integer studentId){
        Student student=getStudentFromMap(map);
        student.setStudentId(studentId);
        return student;
    }

    public StudentBasic getStudentBasicFromMap(Map map){
        StudentBasic s=new StudentBasic();
        s.setGender(Gender.getByCode(CommomMethod.getInteger0(map,"gender")));
        s.setBirthday(CommomMethod.getString(map,"birthday"));
        s.setEthnicity(CommomMethod.getString(map,"ethnicity"));
        s.setBirthplace(CommomMethod.getString(map,"birthplace"));
        s.setAddress(CommomMethod.getString(map,"address"));
        s.setPhone(CommomMethod.getString(map,"phone"));
        s.setEmail(CommomMethod.getString(map,"email"));
        return s;
    }

    public StudentBasic getStudentBasicFromMap(Map map,Integer studentBasicId){
        StudentBasic studentBasic=getStudentBasicFromMap(map);
        studentBasic.setStudent_basic_id(studentBasicId);
        return studentBasic;
    }


    public StudentAdvanced getStudentAdvancedFromMap(Map map){
        StudentAdvanced s=new StudentAdvanced();
        s.setHonors(CommomMethod.getString(map,"honors"));
        s.setCompetitions(CommomMethod.getString(map,"competitions"));
        s.setDisciplinary(CommomMethod.getString(map,"disciplinary"));
        s.setClubs(CommomMethod.getString(map,"clubs"));
        s.setVolunteer(CommomMethod.getString(map,"volunteer"));
        s.setInternship(CommomMethod.getString(map,"internship"));
        return s;
    }

    public StudentAdvanced getStudentAdvancedFromMap(Map map,Integer studentAdvancedId){
        StudentAdvanced studentAdvanced=getStudentAdvancedFromMap(map);
        studentAdvanced.setStudent_advanced_id(studentAdvancedId);
        return studentAdvanced;
    }

    public Integer getNewStudentId(){
       return studentMapper.findMaxStudentId()+1;
    }

    public Integer getNewUserId(){
        return userMapper.findMaxUserId()+1;
    }


}
