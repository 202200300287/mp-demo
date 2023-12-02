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
public enum CourseType {
    Required(1, "必修课"),
    OptionalLimited(2, "限选课"),
    OptionalLimitless(3, "任选课"),
    RequiredUngraded(4, "必修但不计入绩点");
    @JsonValue
    @EnumValue
    private Integer code;

    private String type;

    CourseType(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    @JsonCreator
    public static CourseType getByCode(@JsonProperty("code") int code) {
        return Arrays.stream(CourseType.values()).filter(item -> item.getCode() == code).findFirst().get();
    }
}
