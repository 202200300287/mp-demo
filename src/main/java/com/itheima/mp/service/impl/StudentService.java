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
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

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


    public DataResponse insertStudent(DataRequest dataRequest){
        Integer userId=getNewUserId();
        Map userMap=dataRequest.getMap("user");
        if(userMap.isEmpty())
            return CommomMethod.getReturnMessageError("数据传输格式错误");

        User user=new User();
        user.setUserId(userId);

        String username=CommomMethod.getString(userMap,"username");
        if(!judgeNewUsername(username)){
            return CommomMethod.getReturnMessageError("新学号已经存在");
        }

        user.setUsername(username);
        user.setUserType(UserType.STUDENT);

        String password=CommomMethod.getString(userMap,"password");
        if(password.equals("")||password==null){
            password="123456";
        }
        user.setPassword(password);
        userMapper.insert(user);


        Integer studentId=getNewStudentId();
        Map studentMap=dataRequest.getMap("student");
        if(studentMap.isEmpty()) {
            return CommomMethod.getReturnMessageError("数据传输格式错误");
        }

        Student student=new Student();
        student.setStudentId(studentId);
        student.setUserId(userId);

        String name=CommomMethod.getString(studentMap,"name");
        Integer studentClass=CommomMethod.getInteger(studentMap,"student_class");
        if(name.equals("") ||name==null||studentClass==null){
            return CommomMethod.getReturnMessageError("必须输入学生姓名与班级");
        }
        student.setName(name);
        student.setStudentClass(studentClass);

        student.setMajor(Major.getByCode(CommomMethod.getInteger0(studentMap,"major")));
        student.setGrade(Grade.getByCode(CommomMethod.getInteger0(studentMap,"grade")));

        studentMapper.insert(student);

        Map studentBasicMap=dataRequest.getMap("student_basic");
        Map studentAdvancedMap=dataRequest.getMap("student_advanced");

        if(studentBasicMap.isEmpty()){
            StudentBasic studentBasic=new StudentBasic();
            studentBasic.setStudent_basic_id(studentId);
            studentBasicMapper.insert(studentBasic);
        }else{
            StudentBasic studentBasic=getStudentBasicFrom(studentBasicMap,studentId);
            studentBasicMapper.insert(studentBasic);
        }
        if(studentAdvancedMap.isEmpty()){
            StudentAdvanced studentAdvanced=new StudentAdvanced();
            studentAdvanced.setStudent_advanced_id(studentId);
            studentAdvancedMapper.insert(studentAdvanced);
        }else{
            StudentAdvanced studentAdvanced=getStudentAdvancedFrom(studentAdvancedMap,studentId);
            studentAdvancedMapper.insert(studentAdvanced);
        }

        return CommomMethod.getReturnMessageOK("正确增加了一名学生");
    }




    public Student getStudentFromMap(Map studentMap,Integer studentId){
        Student student=new Student();
        student.setStudentId(studentId);
        //student.setUserId(CommomMethod.getInteger0(studentMap,"user_id"));
        student.setName(CommomMethod.getString(studentMap,"name"));
        student.setMajor(Major.getByCode(CommomMethod.getInteger0(studentMap,"major")));
        student.setGrade(Grade.getByCode(CommomMethod.getInteger0(studentMap,"grade")));
        student.setStudentClass(CommomMethod.getInteger(studentMap,"student_class"));
        return student;

    }

    public StudentBasic getStudentBasicFrom(Map map,Integer studentId){
        StudentBasic s=new StudentBasic();
        s.setStudent_basic_id(studentId);
        s.setGender(Gender.getByCode(CommomMethod.getInteger0(map,"gender")));
        s.setBirthday(CommomMethod.getString(map,"birthday"));
        s.setEthnicity(CommomMethod.getString(map,"ethnicity"));
        s.setBirthplace(CommomMethod.getString(map,"birthplace"));
        s.setAddress(CommomMethod.getString(map,"address"));
        s.setPhone(CommomMethod.getString(map,"phone"));
        s.setEmail(CommomMethod.getString(map,"email"));
        return s;
    }

    public StudentAdvanced getStudentAdvancedFrom(Map map,Integer studentId){
        StudentAdvanced s=new StudentAdvanced();
        s.setStudent_advanced_id(studentId);
        s.setHonors(CommomMethod.getString(map,"honors"));
        s.setCompetitions(CommomMethod.getString(map,"competitions"));
        s.setDisciplinary(CommomMethod.getString(map,"disciplinary"));
        s.setClubs(CommomMethod.getString(map,"clubs"));
        s.setVolunteer(CommomMethod.getString(map,"volunteer"));
        s.setInternship(CommomMethod.getString(map,"internship"));
        return s;
    }

    public Integer getNewStudentId(){
       return studentMapper.findMaxStudentId()+1;
    }

    public Integer getNewUserId(){
        return userMapper.findMaxUserId()+1;
    }

    @ApiModelProperty("判断是否已存在所给学号，不存在返回true")
    public boolean judgeNewUsername(String username){
        List<String> usernames=userMapper.findAllUsername();
        return !usernames.contains(username);
    }
}
