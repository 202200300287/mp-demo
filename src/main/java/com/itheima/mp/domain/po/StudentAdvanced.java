package com.itheima.mp.domain.po;


import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAdvanced {

    @TableId(value = "student_advanced_id")
    private Integer student_advanced_id;

    @ApiModelProperty("个人荣誉")
    private String honors;
    @ApiModelProperty("竞赛经历")
    private String competitions;
    @ApiModelProperty("处分")
    private String disciplinary;
    @ApiModelProperty("社团")
    private String clubs;
    @ApiModelProperty("志愿")
    private String volunteer;
    @ApiModelProperty("实习经历")
    private String internship;
}
