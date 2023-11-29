package com.itheima.mp.domain.vo;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.itheima.mp.domain.po.Teacher;
import com.itheima.mp.enums.Gender;
import com.itheima.mp.enums.UserType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherVO {
    private Integer teacherId;
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
    private String paper;//
    @ApiModelProperty("个人简历")
    private String resume;//

    private String username;
    private String photo;//
    @EnumValue
    private UserType userType;//

}
