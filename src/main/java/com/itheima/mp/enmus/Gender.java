package com.itheima.mp.enmus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;


@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Gender {
    MALE(1,"男"),
    FEMALE(2,"女");

    @EnumValue
    private Integer code;
    @JsonValue
    private String gender;

    Gender(Integer code, String gender){
        this.code=code;
        this.gender=gender;
    }


}
