package com.itheima.mp.domain.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCourse {

    @TableId(type = IdType.AUTO)
    @NotBlank
    private Integer teacherCourseId;

    private Integer teacherId;
    private Integer courseId;
    private Integer studentClass;
}
