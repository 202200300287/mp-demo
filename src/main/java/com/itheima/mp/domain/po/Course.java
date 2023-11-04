package com.itheima.mp.domain.po;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.itheima.mp.enums.Grade;
import com.itheima.mp.enums.CourseStatus;
import com.itheima.mp.enums.CourseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.EnumTypeHandler;

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
    @TableField(typeHandler = EnumTypeHandler.class)
    private CourseStatus courseStatus;

    @EnumValue
    @TableField(typeHandler = EnumTypeHandler.class)
    private Grade grade;

    @EnumValue
    @TableField(typeHandler = EnumTypeHandler.class)
    private CourseType courseType;



}
