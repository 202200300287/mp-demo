package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.gson.Gson;
import com.itheima.mp.domain.po.*;
import com.itheima.mp.enmus.Gender;
import com.itheima.mp.enmus.UserType;
import com.itheima.mp.util.ImageMethod;
import com.mysql.cj.conf.PropertyDefinitions;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Compression;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
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
        Integer studentId=2;
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


        Student student=studentMapper.selectById(studentId);


        //StudentBasic studentBasic=new StudentBasic(studentId, Gender.Female,"1","1","1","1",123,"666");
        //StudentAdvanced studentAdvanced=new StudentAdvanced(studentId,"1","1","1","1","1","1");

        //studentBasicMapper.insert(studentBasic);
        //studentAdvancedMapper.insert(studentAdvanced);
        System.out.println(student.getMajor().getType());

        //studentMapper.update(student,wrapper);
    }



    @Autowired
    private TeacherMapper teacherMapper;
    @Test
    void updateTeacher(){
        Integer teacherId=3;
        QueryWrapper<Teacher> teacherQueryWrapper=new QueryWrapper<Teacher>()
                .select("*")
                .eq("teacher_id",teacherId);
        Teacher teacher=new Teacher(teacherId,7,"大牛",Gender.Male,"教授","博士","软件学院","","无",LocalDateTime.now(),"大牛");
        teacherMapper.insert(teacher);
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

    @SneakyThrows
    @Test
    void testImage(){

        QueryWrapper<User> userQueryWrapper=new QueryWrapper<User>()
            .eq("user_id",1L);
        String imagePath="D://photo//xiaoma.png";
        BufferedImage bufferedImage= ImageIO.read(new FileInputStream(imagePath));
       bufferedImage=ImageMethod.reduceImageQuality(bufferedImage,0.1f);

        //System.out.println(imageByte.length/1024);
        UpdateWrapper<User> userUpdateWrapper=new UpdateWrapper<User>()
                .eq(true,"user_id",1L)
                .set(true,"photo",ImageMethod.getBlobByByteArray(ImageMethod.getByteArrayFromBufferedImage(bufferedImage)));

        userMapper.update(null,userUpdateWrapper);
        //System.out.println(Arrays.toString(imageByte));


    }
    @SneakyThrows
    @Test
    void testImageOut() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>()
                .eq("user_id", 1L);
        User user = userMapper.selectOne(userQueryWrapper);
        BufferedImage bufferedImage = ImageMethod.getBufferedFromByteArray(ImageMethod.getByteArrayByBlob(user.getPhoto()));


    }
}