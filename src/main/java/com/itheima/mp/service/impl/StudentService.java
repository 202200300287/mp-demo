package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.*;

import com.itheima.mp.domain.vo.StudentVO;
import com.itheima.mp.enums.Gender;
import com.itheima.mp.enums.Grade;
import com.itheima.mp.enums.Major;
import com.itheima.mp.enums.UserType;
import com.itheima.mp.mapper.*;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.BaseService;
import com.itheima.mp.service.VOService;
import com.itheima.mp.service.iservice.IStudentService;
import com.itheima.mp.util.CommomMethod;
import com.itheima.mp.util.UpdateUtil;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;





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
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private BaseService baseService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VOService voService;

    public List<Student> getStudentListAll(){
        QueryWrapper<Student> studentQueryWrapper=new QueryWrapper<Student>()
                .select("*");
        return studentMapper.selectList(studentQueryWrapper);
    }

    public List<Student> getStudentListByUserIdList(List<Integer> userIdList){
        String userIdListStr= StringUtils.join(userIdList,",");
        return studentMapper.selectByUserIdList(userIdListStr);
    }


    public DataResponse selectStudentVOList(){
        List<Student> studentList=getStudentListAll();
        if(studentList.isEmpty())return CommomMethod.getReturnMessageError("这里空空如野");
        List<StudentVO> studentVOList=new ArrayList<>();
        for(Student student:studentList){
            Integer studentId=student.getStudentId();
            studentVOList.add(voService.getStudentVO(studentId));
        }
        return CommomMethod.getReturnData(studentVOList);
    }

    public DataResponse selectStudentByNameOrNum(String string){

        List<Integer> userIdList=userMapper.getUserIdListLikeUsername(string);
        List<Student> studentList=studentMapper.getStudentListLikeName(string);
        List<Student> studentListFromUserIdList=getStudentListByUserIdList(userIdList);
        for(Student student: studentListFromUserIdList){
            if(studentList.contains(student))studentList.add(student);
        }
        if(studentList.isEmpty())return CommomMethod.getReturnMessageError("这里空空如也");
        List<StudentVO> studentVOList = new ArrayList<StudentVO>();
        for(Student student:studentList){
            Integer studentId=student.getStudentId();
            studentVOList.add(voService.getStudentVO(studentId));
        }
        return CommomMethod.getReturnData(studentVOList);
    }

    @ApiModelProperty("添加一个学生，对其中username，姓名，班级，年级，邮箱格式进行判断")
    public DataResponse insertStudent(DataRequest dataRequest){
        Map map=dataRequest.getData();
        Integer userId=getNewUserId();//新的id
        Integer studentId =getNewStudentId();

        User user=getUserFromMap(map,userId);
        Student student=getStudentFromMap(map,studentId,userId);
        StudentBasic studentBasic=getStudentBasicFromMap(map,studentId);

        DataResponse dataResponse=baseService.judgeStudentDataInsert(user,student,studentBasic);
        if(dataResponse.getCode()==1)return dataResponse;

        userMapper.insert(user);
        studentMapper.insert(student);
        studentBasicMapper.insert(studentBasic);
        return CommomMethod.getReturnMessageOK("成功添加了一名学生");
    }

    public DataResponse updateStudent(DataRequest dataRequest){
        //对学生是否存在的判断
        Integer studentId=dataRequest.getInteger("studentId");
        Map map=dataRequest.getData();
        if(studentId==null)return CommomMethod.getReturnMessageError("数据传输格式错误");
        if(studentMapper.checkStudentId(studentId)==0){
            return CommomMethod.getReturnMessageError("该学生不存在");
        }
        //将数据库中的相应行取出存为实体类
        Student student=studentMapper.selectById(studentId);
        StudentBasic studentBasic=studentBasicMapper.selectById(studentId);
        Integer userId=student.getUserId();
        User user=userMapper.selectById(userId);
        String usernameOld=user.getUsername();

        //将前端所给的需要更新的数据存为实体类
        Student studentSource=getStudentFromMap(map);
        User userSource=getUserFromMap(map);
        StudentBasic studentBasicSource=getStudentBasicFromMap(map,studentId);

        //核心方法copyNullProperties，对于不为null或blank的属性更新到实体类中
        UpdateUtil.copyNullProperties(studentSource,student);//目标为student
        UpdateUtil.copyNullProperties(userSource,user);
        UpdateUtil.copyNullProperties(studentBasicSource,studentBasic);

        //定义好的格式判断
        DataResponse dataResponse=baseService.judgeStudentDataUpdate(user,student,studentBasic,usernameOld);
        if(dataResponse.getCode()==1)return dataResponse;
        //存入
        userMapper.updateById(user);
        studentMapper.updateById(student);
        studentBasicMapper.updateById(studentBasic);
        return CommomMethod.getReturnMessageOK("成功修改了学生信息");
    }


    public DataResponse deleteStudent(DataRequest dataRequest){
        Integer studentId = dataRequest.getInteger("studentId");
        if(studentId==null)return CommomMethod.getReturnMessageError("数据传输格式错误");

        if(studentMapper.checkStudentId(studentId)==0){
            return CommomMethod.getReturnMessageError("该学生不存在");
        }

        QueryWrapper<StudentCourse> studentCourseQueryWrapper=new QueryWrapper<StudentCourse>()
                .select("*")
                .eq("student_id",studentId);
        studentCourseMapper.delete(studentCourseQueryWrapper);

        Student student=studentMapper.selectById(studentId);
        User user=userMapper.selectById(student.getUserId());

        userMapper.deleteById(user);
        studentBasicMapper.deleteById(studentId);
        studentMapper.deleteById(studentId);
        return CommomMethod.getReturnMessageOK("正确删除了一名学生");
    }

    public DataResponse selectStudent(DataRequest dataRequest){
        Integer studentId = dataRequest.getInteger("studentId");
        if(studentId==null)return CommomMethod.getReturnMessageError("数据传输格式错误");

        if(studentMapper.checkStudentId(studentId)==0){
            return CommomMethod.getReturnMessageError("该学生不存在");
        }
        StudentVO studentVO=voService.getStudentVO(studentId);
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
        Integer major=CommomMethod.getInteger0(map,"major");
        Integer grade=CommomMethod.getInteger0(map,"grade");
        student.setName(CommomMethod.getString(map,"name"));
        if(major>=1&&major<=3)student.setMajor(Major.getByCode(major));
        if(grade>=1&&grade<=4)student.setGrade(Grade.getByCode(grade));
        student.setStudentClass(CommomMethod.getInteger0(map,"studentClass"));
        return student;
    }

    public Student getStudentFromMap(Map map,Integer studentId,Integer userId){
        Student student=getStudentFromMap(map);
        student.setUserId(userId);
        student.setStudentId(studentId);
        return student;
    }


    public StudentBasic getStudentBasicFromMap(Map map){
        StudentBasic s=new StudentBasic();
        Integer gender=CommomMethod.getInteger0(map,"gender");
        if(gender==1||gender==2) s.setGender(Gender.getByCode(gender));
        s.setBirthday(CommomMethod.getLocalDate(map,"birthday"));
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

    public Integer getNewStudentId(){
       return studentMapper.findMaxStudentId()+1;
    }

    public Integer getNewUserId(){
        return userMapper.findMaxUserId()+1;
    }
}
