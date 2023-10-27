package com.itheima.mp.domain.po;


import com.baomidou.mybatisplus.annotation.TableId;
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
}
