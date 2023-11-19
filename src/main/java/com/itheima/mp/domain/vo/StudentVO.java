package com.itheima.mp.domain.vo;

import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.StudentAdvanced;
import com.itheima.mp.domain.po.StudentBasic;
import com.itheima.mp.domain.po.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentVO {
    private String username;

    private Student student;

    private StudentBasic studentBasic;

    private StudentAdvanced studentAdvanced;

    //private List<CourseVO> courseVOList;






}
