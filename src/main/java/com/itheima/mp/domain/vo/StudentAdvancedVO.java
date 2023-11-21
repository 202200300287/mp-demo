package com.itheima.mp.domain.vo;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.itheima.mp.domain.po.StudentAdvanced;
import com.itheima.mp.enums.Grade;
import com.itheima.mp.enums.Major;
import com.itheima.mp.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;


//一个学生的advanced信息
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAdvancedVO {
    private Integer studentId;
    private String name;
    @EnumValue
    private Major major;
    @EnumValue
    private Grade grade;
    private String username;
    @EnumValue
    private UserType userType;

    private List<StudentAdvanced> honors;
    private List<StudentAdvanced> competitions;
    private List<StudentAdvanced> disciplinary;
    private List<StudentAdvanced> clubs;
    private List<StudentAdvanced> volunteer;
    private List<StudentAdvanced> internship;

}
