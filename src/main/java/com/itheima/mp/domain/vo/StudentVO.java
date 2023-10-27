package com.itheima.mp.domain.vo;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.itheima.mp.domain.info.studentInfo.BasicInfo;
import com.itheima.mp.domain.po.Course;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.enmus.Major;
import com.itheima.mp.service.impl.StudentCourseService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.EnumTypeHandler;

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
