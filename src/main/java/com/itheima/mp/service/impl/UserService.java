package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.enums.UserType;
import com.itheima.mp.mapper.StudentMapper;
import com.itheima.mp.mapper.TeacherMapper;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.request.LoginRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.iservice.IUserService;
import com.itheima.mp.util.CommomMethod;
import com.itheima.mp.util.FormatMethod;
import com.itheima.mp.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    public DataResponse login(LoginRequest loginRequest) {
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
        map.put("userId", user.getUserId());
        map.put("avatar", user.getPhoto());
        // 能登录的话说明对应的学生或者老师一定存在, 就不进行非空判断了
        if (user.getUserType() == UserType.STUDENT) {
            map.put("name", studentMapper.selectStudentByUserId(user.getUserId()).getName());
        } else if (user.getUserType() == UserType.TEACHER) {
            map.put("name", teacherMapper.selectTeacherByUserId(user.getUserId()).getName());
        } else {
            map.put("name", "管理员");
        }

        return CommomMethod.getReturnData(map);
    }

    public DataResponse changePassword(DataRequest dataRequest){
        Integer userId=dataRequest.getInteger("userId");
        if(userId==null)return CommomMethod.getReturnMessageError("数据传输错误");
        User user=userMapper.selectById(userId);
        if(user==null)return CommomMethod.getReturnMessageError("用户不存在");
        String password=user.getPassword();
        String passwordOld=dataRequest.getString("passwordOld");
        if(!Objects.equals(passwordOld, password))return CommomMethod.getReturnMessageError("原密码输入错误");
        String passwordNew1=dataRequest.getString("passwordNew1");
        if(passwordNew1.length()<6)return CommomMethod.getReturnMessageError("密码长度不可以小于6");
        if(passwordNew1.length()>18)return CommomMethod.getReturnMessageError("密码长度不可大于18");
        if(!FormatMethod.containsLetterOrDigit(passwordNew1))return CommomMethod.getReturnMessageError("密码必须包含字母或数字");
        String passwordNew2=dataRequest.getString("passwordNew2");
        if(!passwordNew1.equals(passwordNew2))return CommomMethod.getReturnMessageError("请重新确认密码");
        user.setPassword(passwordNew1);
        userMapper.updateById(user);
        return CommomMethod.getReturnMessageOK("成功修改了密码");
    }


}
