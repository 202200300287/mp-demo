package com.itheima.mp.domain.vo;

import com.itheima.mp.domain.po.Course;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.StudentCourse;
import com.itheima.mp.domain.po.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreVO {
    private Student student;
    private User user;
    private Course course;
    private StudentCourse studentCourse;
}
