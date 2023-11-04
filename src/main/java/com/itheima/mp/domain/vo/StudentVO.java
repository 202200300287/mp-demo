package com.itheima.mp.domain.vo;

import com.itheima.mp.domain.po.StudentAdvanced;
import com.itheima.mp.domain.po.StudentBasic;
import com.itheima.mp.enums.Major;
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

    private double gpa;

    private Integer rankClass;

    private Integer rankCollege;

    private StudentBasic studentBasic;

    private StudentAdvanced studentAdvanced;

    private List<CourseVO> courseVOList;






}
