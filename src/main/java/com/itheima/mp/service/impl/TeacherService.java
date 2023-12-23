package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Teacher;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.vo.TeacherVO;
import com.itheima.mp.enums.Gender;
import com.itheima.mp.enums.UserType;
import com.itheima.mp.mapper.TeacherMapper;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.BaseService;
import com.itheima.mp.service.VOService;
import com.itheima.mp.service.iservice.ITeacherService;
import com.itheima.mp.util.CommomMethod;
import com.itheima.mp.util.UpdateUtil;
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
public class TeacherService extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BaseService baseService;

    @Autowired
    private VOService voService;

    public Integer getNewTeacherId() {
        return teacherMapper.findMaxTeacherId() + 1;
    }

    public Integer getNewUserId() {
        return userMapper.findMaxUserId() + 1;
    }


    public DataResponse selectTeacherVOList() {
        List<Teacher> teacherList = teacherMapper.selectTeacherList();
        List<TeacherVO> teacherVOList = new ArrayList<>();
        for (Teacher teacher : teacherList) {
            teacherVOList.add(voService.getTeacherVO(teacher));
        }
        return CommomMethod.getReturnData(teacherVOList);
    }

    public DataResponse selectTeacherVOList1() {
        return CommomMethod.getReturnData(teacherMapper.selectTeacherVOList());
    }

    public DataResponse insertTeacher(DataRequest dataRequest) {
        Map map = dataRequest.getData();
        Integer userId = getNewUserId();
        Integer teacherId = getNewTeacherId();
        Teacher teacher = getTeacherFromMap(map, teacherId, userId);
        User user = getUserFromMap(map, userId);
        DataResponse dataResponse = baseService.judgeTeacherDataInsert(user, teacher);

        if (dataResponse.getCode() == 1) return dataResponse;
        teacherMapper.insert(teacher);
        userMapper.insert(user);
        return CommomMethod.getReturnMessageOK("成功添加了一名教师");

    }

    public DataResponse updateTeacher(DataRequest dataRequest) {
        Integer teacherId = dataRequest.getInteger("teacherId");
        if (teacherMapper.checkTeacherId(teacherId) == 0) return CommomMethod.getReturnMessageError("没有该老师");
        Map map = dataRequest.getData();
        Teacher teacher = teacherMapper.selectById(teacherId);
        User user = userMapper.selectById(teacher.getUserId());
        String usernameOld = user.getUsername();
        Teacher teacherSource = getTeacherFromMap(map);
        User userSource = getUserFromMap(map);
        UpdateUtil.copyNullProperties(teacherSource, teacher);
        UpdateUtil.copyNullProperties(userSource, user);
        DataResponse dataResponse = baseService.judgeTeacherDataUpdate(user, teacher, usernameOld);
        if (dataResponse.getCode() == 1) return dataResponse;
        teacherMapper.updateById(teacher);
        userMapper.updateById(user);
        return CommomMethod.getReturnMessageOK("成功修改了教师信息");
    }

    public DataResponse deleteTeacher(DataRequest dataRequest) {
        Integer teacherId = dataRequest.getInteger("teacherId");

        if (teacherMapper.checkTeacherId(teacherId) == 0) {
            return CommomMethod.getReturnMessageError("该老师不存在");
        }

        Teacher teacher = teacherMapper.selectById(teacherId);
        User user = userMapper.selectById(teacher.getUserId());
        teacherMapper.deleteById(teacher);
        userMapper.deleteById(user);
        return CommomMethod.getReturnMessageOK("正确删除了一名老师");

    }

    public DataResponse selectTeacher(DataRequest dataRequest) {
        // 优先根据teacherId查询
        Integer teacherId = dataRequest.getInteger("teacherId");
        TeacherVO teacherVO;
        // 也可以根据userId查询
        if (teacherId == null) {
            Integer userId = dataRequest.getInteger("userId");
            System.out.println("根据userId查询老师. userId = " + userId);
            if (userId == null) {
                return CommomMethod.getReturnMessageError("参数错误");
            }
            teacherVO = teacherMapper.selectTeacherVOByUserId(userId);
            if (teacherVO == null) {
                return CommomMethod.getReturnMessageError("不存在该老师");
            }
            return CommomMethod.getReturnData(teacherVO);
        }

        if (teacherMapper.checkTeacherId(teacherId) == 0) {
            return CommomMethod.getReturnMessageError("不存在该老师");
        }

        return CommomMethod.getReturnData(voService.getTeacherVO(teacherId));
    }


    public Teacher getTeacherFromMap(Map map) {

        Teacher teacher = new Teacher();
        teacher.setName(CommomMethod.getString(map, "name"));
        teacher.setPhone(CommomMethod.getString(map, "phone"));
        teacher.setEmail(CommomMethod.getString(map, "email"));
        Integer gender = CommomMethod.getInteger0(map, "gender");
        if (gender == 1 || gender == 2) teacher.setGender(Gender.getByCode(gender));
        teacher.setPosition(CommomMethod.getString(map, "position"));
        teacher.setDegree(CommomMethod.getString(map, "degree"));
        teacher.setCollege(CommomMethod.getString(map, "college"));
        teacher.setResearch(CommomMethod.getString(map, "research"));
        teacher.setPaper(CommomMethod.getString(map, "paper"));
        teacher.setResume(CommomMethod.getString(map, "resume"));

        return teacher;
    }

    public Teacher getTeacherFromMap(Map map, Integer teacherId, Integer userId) {
        Teacher teacher = getTeacherFromMap(map);
        teacher.setTeacherId(teacherId);
        teacher.setUserId(userId);
        return teacher;
    }

    public User getUserFromMap(Map map) {
        User user = new User();
        user.setUsername(CommomMethod.getString(map, "username"));
        user.setPassword(CommomMethod.getString(map, "password"));
        user.setPhoto(CommomMethod.getString(map, "photo"));
        user.setUserType(UserType.TEACHER);
        user.setCreateTime(LocalDateTime.now());
        return user;
    }

    public User getUserFromMap(Map map, Integer userId) {
        User user = getUserFromMap(map);
        user.setUserId(userId);
        return user;
    }

    public DataResponse selectTeacherInfo(DataRequest dataRequest) {
        Integer teacherId = dataRequest.getInteger("teacherId");
        Map<String, String> teacherInfo = teacherMapper.getTeacherInfoByTeacherId(teacherId);
        if (teacherInfo == null) {
            return CommomMethod.getReturnMessageError("不存在该教师");
        }
        return CommomMethod.getReturnData(teacherInfo);
    }
}
