package com.itheima.mp.domain.po;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.itheima.mp.enmus.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.EnumTypeHandler;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @TableId(value = "course_id")
    private Integer courseId;
    private String num;
    private String name;
    private Integer credit;

    @EnumValue
    @TableField(typeHandler = EnumTypeHandler.class)
    private CourseStatus courseStatus;

}
