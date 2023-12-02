package com.itheima.mp.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.Teacher;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.enums.UserType;
import com.itheima.mp.mapper.StudentBasicMapper;
import com.itheima.mp.mapper.StudentMapper;
import com.itheima.mp.mapper.TeacherMapper;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.iservice.MailService;
import com.itheima.mp.util.CommomMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class AuthService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired(required = false)
    private MailService mailService;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StudentBasicMapper studentBasicMapper;

    public String getCodeVer() {
        Random random = new Random();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            s.append(random.nextInt(10));
        }
        return s.toString();
    }

    public DataResponse sendEmail(DataRequest dataRequest) {
        String to = dataRequest.getString("email");
        String code = getCodeVer();
        String title = "邮箱验证";
        String content = "<html>"
                + "<head>"
                + "    <style>"
                + "        body {"
                + "            font-family: Arial, sans-serif;"
                + "            background-color: #f5f5f5;"
                + "            padding: 20px;"
                + "        }"
                + "        h1 {"
                + "            color: #333;"
                + "            text-align: center;"
                + "        }"
                + "        .container {"
                + "            text-align:center;"
                + "            width: 90%;"
                + "            height: 50%"
                + "            margin: 0 auto;"
                + "            background-color: #fff;"
                + "            padding: 20px;"
                + "            border-radius: 5px;"
                + "            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);"
                + "        }"
                + "        code {"
                + "            background-color: #f2f2f2;"
                + "            padding: 2px 5px;"
                + "            border-radius: 3px;"
                + "        }"
                + "    </style>"
                + "</head>"
                + "<body>"
                + "    <div class='container'>"
                + "        <h1>欢迎来到 CutePony 的系统</h1>"
                + "        <p>验证码<code>" + code + "</code></p>"
                + "        <p>祝您使用愉快！</p>"
                + "    </div>"
                + "</body>"
                + "</html>";
        mailService.sendSimpleText(to, title, content);
        return CommomMethod.getReturnData(code);
    }

    public DataResponse forgetPassword(DataRequest dataRequest) {
        String username = dataRequest.getString("username");
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>()
                .select("*")
                .eq("username", username);
        List<User> userList = userMapper.selectList(userQueryWrapper);
        if (userList.isEmpty()) return CommomMethod.getReturnMessageError("不存在该用户名");
        User user = userList.get(0);
        Integer userId = user.getUserId();

        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<Teacher>()
                .select("*")
                .eq("user_id", userId);
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<Student>()
                .select("*")
                .eq("user_id", userId);
        String email = "";
        if (user.getUserType() == UserType.TEACHER) {
            email = teacherMapper.selectList(teacherQueryWrapper).get(0).getEmail();
        }
        if (user.getUserType() == UserType.STUDENT) {
            email = studentBasicMapper.selectById(studentMapper.selectList(studentQueryWrapper).get(0).getStudentId()).getEmail();
        }

        String to = email;
        String code = getCodeVer();
        String title = "邮箱验证";
        String content = "<html>"
                + "<head>"
                + "    <style>"
                + "        body {"
                + "            font-family: Arial, sans-serif;"
                + "            background-color: #f5f5f5;"
                + "            padding: 20px;"
                + "        }"
                + "        h1 {"
                + "            color: #333;"
                + "            text-align: center;"
                + "        }"
                + "        .container {"
                + "            text-align:center;"
                + "            width: 90%;"
                + "            height: 50%"
                + "            margin: 0 auto;"
                + "            background-color: #fff;"
                + "            padding: 20px;"
                + "            border-radius: 5px;"
                + "            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);"
                + "        }"
                + "        code {"
                + "            background-color: #f2f2f2;"
                + "            padding: 2px 5px;"
                + "            border-radius: 3px;"
                + "        }"
                + "    </style>"
                + "</head>"
                + "<body>"
                + "    <div class='container'>"
                + "        <h1>您正在找回密码</h1>"
                + "        <p>验证码<code>" + code + "</code></p>"
                + "        <p>祝您使用愉快！</p>"
                + "    </div>"
                + "</body>"
                + "</html>";
        mailService.sendSimpleText(to, title, content);
        return CommomMethod.getReturnData(code);
    }

}
