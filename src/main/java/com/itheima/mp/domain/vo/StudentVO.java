package com.itheima.mp.domain.vo;

import com.itheima.mp.domain.po.Course;
import com.itheima.mp.enmus.Major;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentVO {
    private Integer studentId;

    private int userId;

    private Major major;

    private String basicInfo;

    private List<Course> courseList;


}
