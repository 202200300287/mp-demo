package com.itheima.mp.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogTag {
    LocalDateTime createTime;
    @NotBlank
    private Integer blogId;
    private String title;
    private Integer userId;
    private String author;
    private Integer praise;
    private LocalDateTime updateTime;
    private String digest; // 博客的摘要
}
