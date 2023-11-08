package com.itheima.mp.domain.po;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.mp.enums.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "teacher")
public class Teacher {
    @TableId(type = IdType.AUTO,value = "teacher_id")
    @NotBlank
    private Integer teacherId;


    private Integer userId;


    private String name;

    private String phone;

    private String email;

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


    @ApiModelProperty("个人简历")
    private String resume;


}
