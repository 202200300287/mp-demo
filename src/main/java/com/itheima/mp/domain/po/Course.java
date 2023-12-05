package com.itheima.mp.domain.po;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.itheima.mp.enums.CourseStatus;
import com.itheima.mp.enums.CourseType;
import com.itheima.mp.enums.Grade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @TableId(type = IdType.AUTO, value = "course_id")
    @NotBlank
    private Integer courseId;
    private String num;
    private String name;
    private Double credit;

    @EnumValue
    private CourseStatus courseStatus;

    @EnumValue
    private Grade grade;

    @EnumValue
    private CourseType courseType;


}
