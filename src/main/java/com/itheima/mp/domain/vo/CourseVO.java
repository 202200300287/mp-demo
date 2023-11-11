package com.itheima.mp.domain.vo;

import com.itheima.mp.domain.po.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseVO {
    private Integer courseId;
    private String num;
    private String name;
    private double credit;
    private List<Teacher> teacherList;
    private double score;
    private Integer rankClass;
    private Integer rankCollege;
}
