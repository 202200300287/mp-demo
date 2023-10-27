package com.itheima.mp.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "teacher")
public class Teacher {
    @TableId(value = "teacher_id")
    private int teacherId;

    private int userId;

    private String position;

    private String degree;


}
