package com.itheima.mp.domain.vo;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableField;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.StudentAdvanced;
import com.itheima.mp.domain.po.StudentBasic;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.enums.Gender;
import com.itheima.mp.enums.Grade;
import com.itheima.mp.enums.Major;
import com.itheima.mp.enums.UserType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentVO {



    private Integer studentId;
    private String name;
    @EnumValue
    private Major major;
    @EnumValue
    private Grade grade;
    private Double gpa;
    private Integer studentClass;
    private Integer rankClass;
    private Integer rankCollege;

    private String username;
    private String photo;
    @EnumValue
    private UserType userType;


    @EnumValue
    private Gender gender;

    @ApiModelProperty("生日")
    private Date birthday;

    @ApiModelProperty("民族")
    private String ethnicity;

    @ApiModelProperty("籍贯")
    private String birthplace;

    @ApiModelProperty("住址")
    private String address;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

//    @ApiModelProperty("个人荣誉")
//    private String honors;
//    @ApiModelProperty("竞赛经历")
//    private String competitions;
//    @ApiModelProperty("处分")
//    private String disciplinary;
//    @ApiModelProperty("社团")
//    private String clubs;
//    @ApiModelProperty("志愿")
//    private String volunteer;
//    @ApiModelProperty("实习经历")
//    private String internship;


}
