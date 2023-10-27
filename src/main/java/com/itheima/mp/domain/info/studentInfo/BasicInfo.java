package com.itheima.mp.domain.info.studentInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//基本信息：包括学生姓名、学生学号、性别、出生日期、民族、籍贯等个人身份信息。
//name, student ID, gender, date of birth, ethnicity, birthplace

/*
基本信息：包括学生姓名、学生学号、性别、出生日期、民族、籍贯等个人身份信息。
联系信息：包括学生家庭住址、家庭电话、父母或监护人信息、学生本人联系电话、电子邮箱等。
学籍信息：包括学生所在班级、所在年级、学生当前状态（如在读、休学、退学等）等信息。
奖惩记录：包括学生荣誉奖项、学术竞赛成绩、违纪记录等。
学习成绩：包括学生各科目的考试成绩、平均分、排名等。
课外活动：包括学生参与的社团、俱乐部、义工活动、实习经历等。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicInfo {
    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("生日")
    private String birthday;

    @ApiModelProperty("民族")
    private String ethnicity;

    @ApiModelProperty("籍贯")
    private String birthplace;

    @ApiModelProperty("住址")
    private String address;

    @ApiModelProperty("电话")
    private Integer phone;

    @ApiModelProperty("邮箱")
    private String email;
}