package com.itheima.mp.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "blog_evaluate")
public class BlogEvaluate {
    LocalDateTime createTime;
    @TableId(type = IdType.AUTO, value = "blog_evaluate_id")
    private Integer blogEvaluateId;
    private Integer blogId;
    private Integer userId;
    private String text;
}
