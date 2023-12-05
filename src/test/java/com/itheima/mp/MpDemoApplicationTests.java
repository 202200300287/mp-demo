package com.itheima.mp;

import com.itheima.mp.domain.vo.StudentVO;
import com.itheima.mp.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MpDemoApplicationTests {

    @Autowired
    StudentMapper studentMapper;

    @Test
    void contextLoads() {
        List<StudentVO> students = studentMapper.selectStudentVOList();
        System.out.println("students = " + students);
    }
}
