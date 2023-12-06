package com.itheima.mp.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "blog")
public class StudentEvaluate {
    @TableId(type = IdType.AUTO, value = "course_id")
    @NotBlank
    private Integer studentEvaluateId;
    private String text;
    private LocalDateTime createTime;
    private Integer evaluateStudent;
    private Integer evaluatedStudent;
    private Integer like;
}
