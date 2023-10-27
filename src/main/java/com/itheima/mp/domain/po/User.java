package com.itheima.mp.domain.po;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.mp.enmus.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.EnumTypeHandler;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user")
public class User {


    @TableId(value = "user_id")
    private Long userId;


    private String username;

    private String password;


    @EnumValue
    @TableField(typeHandler = EnumTypeHandler.class)
    private UserType userType;

    LocalDateTime createTime;

    private LocalDateTime updateTime;
}
