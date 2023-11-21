package com.itheima.mp.domain.vo;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.itheima.mp.enums.AdvancedType;
import com.itheima.mp.enums.Grade;
import com.itheima.mp.enums.Major;
import com.itheima.mp.enums.UserType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSingleAdvancedVO {
    private Integer studentId;
    private String name;
    @EnumValue
    private Major major;
    @EnumValue
    private Grade grade;
    private String username;
    @EnumValue
    private UserType userType;


    private Integer studentAdvancedId;
    @EnumValue
    private AdvancedType advancedType;
    @ApiModelProperty("这一条里干了什么事")
    private String content;
    @ApiModelProperty("时长")
    private Double duration;
    private LocalDateTime updateTime;
}
