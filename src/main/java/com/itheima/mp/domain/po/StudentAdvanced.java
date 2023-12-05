package com.itheima.mp.domain.po;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.mp.enums.AdvancedType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "student_advanced")
public class StudentAdvanced {

    @TableId(value = "student_advanced_id")
    @NotBlank
    private Integer studentAdvancedId;

    private Integer studentId;

    @EnumValue
    private AdvancedType advancedType;

    @ApiModelProperty("加一个标题")
    private String title;
    @ApiModelProperty("这一条里干了什么事")
    private String content;

    @ApiModelProperty("时长")
    private Double duration;

    private LocalDateTime updateTime;


}
