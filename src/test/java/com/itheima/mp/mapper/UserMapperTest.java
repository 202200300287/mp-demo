package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.info.studentInfo.BasicInfo;
import com.itheima.mp.enmus.Major;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentMapper studentMapper;
    @Test
    void testInsert() {
        User user = new User();
        user.setUsername("202200300287");
        user.setPassword("123");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Test
    void testSelectById() {
        User user = userMapper.selectById(5L);
        System.out.println("user = " + user);
    }


    @Test
    void testQueryByIds() {
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void newStudent(){


    }

    @Test
    void updateStudent(){

        Gson gson=new Gson();
        QueryWrapper<Student> wrapper = new QueryWrapper<Student>()
                .select("*")
                .eq("student_id", 3);
        Student student=new Student();
        BasicInfo basicInfo=new BasicInfo(2,"1","1","1","1",123,"666");

        System.out.println(gson.toJson(basicInfo));
        String s=gson.toJson(basicInfo);
        student.setBasicInfo(s);
        studentMapper.update(student,wrapper);
        //student.setBasicInfo(gson.toJson(basicInfo));
        //AdvancedInfo advancedInfo=new AdvancedInfo("1","1","1","1","1","1");

        String s1 = StringEscapeUtils.unescapeJava(studentMapper.selectById(3).getBasicInfo());
        System.out.println(s1);

        System.out.println(studentMapper.selectById(3).getBasicInfo());
        //studentMapper.update(student,wrapper);

    }

    @Test
    void testQueryInfo(){
        QueryWrapper<BasicInfo> wrapper=new QueryWrapper<BasicInfo>()
                .select("*")
                .eq("name","ming");

    }

    @Test
    void testBasicInfoReturn(){
        Student s=studentMapper.selectById(1);
        System.out.println(s);
    }



}