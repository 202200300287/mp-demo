package com.itheima.mp.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseVO {
    private Integer courseId;
    private String num;
    private String name;
    private double credit;

    private double score;
    private Integer rankClass;
    private Integer rankCollege;
}
