package com.itheima.mp.domain.vo;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.itheima.mp.enums.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseVO {
//    private Student student;
//    private User user;
//    private Course course;
//    private StudentCourse studentCourse;

    private Integer studentId;
    private String name;
    @EnumValue
    private Major major;
    private Grade grade;
    private Double gpa;
    private Integer studentClass;
    private Integer rankClassGPA;
    private Integer rankCollegeGPA;

    private String username;
    private String photo;
    @EnumValue
    private UserType userType;


    private Integer courseId;
    private String num;
    private String courseName;
    private Double credit;
    @EnumValue
    private CourseType courseType;

    private double score;
    @ApiModelProperty("单科班级排名")
    private Integer rankClassCourse;
    @ApiModelProperty("单科学院成绩排名")
    private Integer rankCollegeCourse;
    @ApiModelProperty("学生成绩状态")
    @EnumValue
    private ScoreStatus scoreStatus;
}
