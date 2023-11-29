package com.itheima.mp.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserType {
    ADMIN(1,"ADMIN"),
    STUDENT(2,"STUDENT"),
    TEACHER(3,"TEACHER");

    @JsonValue
    @EnumValue
    private Integer code;

    private String type;

    UserType(Integer code, String type){
        this.code=code;
        this.type=type;
    }

    /**
     * 反序列化
     * @param code 数据库对应的值
     * @return 枚举对象
     */
    @JsonCreator
    public static UserType getByCode(@JsonProperty("code") int code) {
        return Arrays.stream(UserType.values()).filter(item -> item.code == code).findFirst().get();
    }

}