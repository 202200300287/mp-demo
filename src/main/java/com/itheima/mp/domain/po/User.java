package com.itheima.mp.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.itheima.mp.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user")
public class User {


    @TableId(type = IdType.AUTO,value = "user_id")
    @NotBlank
    private Integer userId;


    private String username;

    private String password;

    private String photo;


    @EnumValue
    private UserType userType;

    LocalDateTime createTime;

    private LocalDateTime updateTime;




}
