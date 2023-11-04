package com.itheima.mp.domain.po;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.itheima.mp.enums.ScoreStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.EnumTypeHandler;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourse {
    @TableId(type = IdType.AUTO)
    @NotBlank
    private Integer studentCourseId;
    private Integer studentId;
    private Integer courseId;

    @ApiModelProperty("学生单科成绩")
    private double score;
    @ApiModelProperty("单科班级排名")
    private Integer rankClass;
    @ApiModelProperty("单科学院成绩排名")
    private Integer rankCollege;

    @ApiModelProperty("学生成绩状态")
    @EnumValue
    @TableField(typeHandler = EnumTypeHandler.class)
    private ScoreStatus scoreStatus;
}
