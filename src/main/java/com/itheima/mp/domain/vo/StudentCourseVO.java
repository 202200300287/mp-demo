package com.itheima.mp.domain.vo;


import com.itheima.mp.domain.po.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseVO {
    private List<Course> courseList;


}
