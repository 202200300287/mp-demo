package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.StudentAdvanced;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.StudentBasic;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentAdvancedMapper studentAdvancedMapper;
    @Autowired
    private StudentBasicMapper studentBasicMapper;
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
        Integer studentId=1;
        Gson gson=new Gson();
        QueryWrapper<Student> wrapper = new QueryWrapper<Student>()
                .select("*")
                .eq("student_id", studentId);

        QueryWrapper<StudentAdvanced> studentAdvancedQueryWrapper=new QueryWrapper<StudentAdvanced>()
                .select("*")
                .eq("student_advanced_id",studentId);
        QueryWrapper<StudentBasic> studentBasicQueryWrapper=new QueryWrapper<StudentBasic>()
                .select("*")
                .eq("student_basic_id",studentId);


        Student student=new Student();
        StudentBasic studentBasic=new StudentBasic(studentId,2,"1","1","1","1",123,"666");
        StudentAdvanced studentAdvanced=new StudentAdvanced(studentId,"1","1","1","1","1","1");

        studentBasicMapper.insert(studentBasic);
        studentAdvancedMapper.insert(studentAdvanced);
        studentMapper.update(student,wrapper);




        //studentMapper.update(student,wrapper);

    }

    @Test
    void testQueryInfo(){
        QueryWrapper<StudentBasic> wrapper=new QueryWrapper<StudentBasic>()
                .select("*")
                .eq("name","ming");

    }

    @Test
    void testBasicInfoReturn(){
        Student s=studentMapper.selectById(1);
        System.out.println(s);
    }



}