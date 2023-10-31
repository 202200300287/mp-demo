package com.itheima.mp.domain.po;


import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourse {
    @TableId
    private Integer studentCourseId;
    private Integer studentId;
    private Integer courseId;

    @ApiModelProperty("学生单科成绩")
    private double score;
    @ApiModelProperty("单科班级排名")
    private Integer rankClass;
    @ApiModelProperty("单科学院成绩排名")
    private Integer rankCollege;
}
