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
public class Blog {
    @TableId(type = IdType.AUTO, value = "course_id")
    @NotBlank
    private Integer blogId;
    private Integer userId;
    private String text;
    private String title;
    private Integer praise;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;//设定是作者可以在任意时间修改
}
