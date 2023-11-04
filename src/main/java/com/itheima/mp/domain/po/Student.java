package com.itheima.mp.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.itheima.mp.enums.Grade;
import com.itheima.mp.enums.Major;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.EnumTypeHandler;

import javax.validation.constraints.NotBlank;


/*
基本信息：Basic information
联系信息：Contact information
学籍信息：Academic information
教育背景：Education background
家庭背景：Family background
健康信息：Health information
奖惩记录：Rewards and punishments record
学习成绩：Academic performance
课外活动：Extracurricular activities
 */
/*
基本信息：包括学生姓名、学生学号、性别、出生日期、民族、籍贯等个人身份信息。
联系信息：包括学生家庭住址、家庭电话、父母或监护人信息、学生本人联系电话、电子邮箱等。
学籍信息：包括学生所在班级、所在年级、学生所属学校、入学日期、学生当前状态（如在读、休学、退学等）等信息。

//教育背景：包括学生的毕业学校、上一学校所在年级、上一学校所在班级、毕业年份等。
//家庭背景：包括学生父母或监护人的职业、工作单位、联系方式等。
//健康信息：包括学生身体状况、过敏史、特殊医疗需求、紧急联系人等信息。
奖惩记录：包括学生荣誉奖项、学术竞赛成绩、违纪记录等。
学习成绩：包括学生各科目的考试成绩、平均分、排名等。
课外活动：包括学生参与的社团、俱乐部、义工活动、实习经历等。
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "student")
public class Student {
    @TableId(type = IdType.AUTO,value = "student_id")
    @NotBlank
    private Integer studentId;

    private int userId;

    private String name;

    @EnumValue
    @TableField(typeHandler = EnumTypeHandler.class)
    private Major major;

    private Grade grade;

    private double gpa;

    private Integer studentClass;


    @TableField(value = "rank_class")
    private Integer rankClass;

    @TableField(value = "rank_college")
    private Integer rankCollege;

}
