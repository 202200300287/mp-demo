package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.enums.UserType;
import com.itheima.mp.mapper.StudentMapper;
import com.itheima.mp.mapper.TeacherMapper;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.payload.request.LoginRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.payload.response.LoginResponse;
import com.itheima.mp.service.iservice.IUserService;
import com.itheima.mp.util.CommomMethod;
import com.itheima.mp.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    TokenUtil tokenUtil;


    public DataResponse login(LoginRequest loginRequest) {
        DataResponse dataResponse;
        String u = loginRequest.getUsername();
        String p = loginRequest.getPassword();
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select("*")
                .eq("username", u)
                .eq("password", p);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            return CommomMethod.getReturnMessageError("用户名或密码错误");
        }
        String token = tokenUtil.getToken(user.getUserId(), user.getUsername(), user.getUserType().getCode());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("userType", user.getUserType());
        // 非管理员用户的话就返回姓名
        // 能登录的话说明对应的学生或者老师一定存在, 就不进行非空判断了
        if (user.getUserType() == UserType.STUDENT) {
            map.put("name", studentMapper.selectStudentByUserId(user.getUserId()).getName());
        } else if (user.getUserType() == UserType.TEACHER) {
            map.put("name", teacherMapper.selectTeacherByUserId(user.getUserId()).getName());
        }

        return CommomMethod.getReturnData(map);
    }


}
