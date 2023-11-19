package com.itheima.mp.domain.vo;

import com.itheima.mp.domain.po.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherVO {
    private String username;
    private Teacher teacher;
}
