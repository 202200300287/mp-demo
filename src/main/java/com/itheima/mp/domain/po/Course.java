package com.itheima.mp.domain.po;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @TableId(value = "course_id")
    private Integer courseId;
    private String num;
    private String name;
    private Integer credit;

}
