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
    @NotBlank
    private Integer blogId;
    private String title;
    private Integer userId;
    private Integer praise;
    LocalDateTime createTime;
    private LocalDateTime updateTime;
}
