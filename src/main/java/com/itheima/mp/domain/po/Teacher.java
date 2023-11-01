package com.itheima.mp.domain.po;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.mp.enmus.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "teacher")
public class Teacher {
    @TableId(value = "teacher_id")
    private Integer teacherId;

    private int userId;


    private String name;

    @EnumValue
    private Gender gender;

    @ApiModelProperty("职务")
    private String position;

    @ApiModelProperty("学位")
    private String degree;

    @ApiModelProperty("所在学院")
    private String college;

    @ApiModelProperty("研究方向")
    private String research;

    @ApiModelProperty("论文著作")
    private String paper;

    private LocalDateTime entryDate;

    @ApiModelProperty("个人简历")
    private String resume;


}
