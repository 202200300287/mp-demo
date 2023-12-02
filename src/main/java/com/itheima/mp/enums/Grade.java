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
public enum Grade {
    All(5, "所有时间可选"),
    One(1, "大一"),
    Two(2, "大二"),
    Three(3, "大三"),
    Four(4, "大四");
    @JsonValue
    @EnumValue
    private final Integer code;

    private final String grade;

    Grade(Integer code, String grade) {
        this.code = code;
        this.grade = grade;
    }

    @JsonCreator
    public static Grade getByCode(@JsonProperty("code") int code) {
        return Arrays.stream(Grade.values())
                .filter(item -> item.getCode() == code)
                .findFirst()
                .get();
    }
}
